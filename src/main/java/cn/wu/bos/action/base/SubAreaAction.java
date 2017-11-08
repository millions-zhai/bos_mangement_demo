package cn.wu.bos.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import cn.wu.bos.service.base.SubAreaService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.wu.bos.domain.base.SubArea;

/**
 * 分区的WEB的action
 *
 * @author 武灵森
 * @version 1.0，2017-10-28 14:09:13
 */
@Controller("mySubAreaAction")
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
@SuppressWarnings("all")
public class SubAreaAction extends BaseAction<SubArea> {

	@Autowired
	/** 分区的业务层对象 */
	private SubAreaService subAreaService;
	
	/**
	 * 保存的操作
	 * 
	 * @return 跳转页面的路径
	 */
	@Action(value="subArea_save", results=@Result(name="success", 
			location="./pages/base/sub_area.html", type="redirect"))
	public String save(){
		subAreaService.save(model);
		return SUCCESS;
	}
	
	private File file;
	
	private String fileFileName;
	
	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	@Action("subArea_import")
	public String importOpt() throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		try {
			List<SubArea> subAreas = new ArrayList<SubArea>();
			Workbook workbook = null;
			if (fileFileName.endsWith(".xls")) {
				workbook = new HSSFWorkbook(new FileInputStream(file));
			}else if (fileFileName.endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(new FileInputStream(file));
			}
			Sheet sheet = workbook.getSheetAt(0);
			for (Row row : sheet) {
				if (row.getRowNum()==0) {
					continue;
				}
				if (row.getCell(0)==null || StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
					continue;
				}
				SubArea subArea = new SubArea();
				subArea.setId(row.getCell(0).getStringCellValue());
				subArea.setKeyWords(row.getCell(1).getStringCellValue());
				subArea.setStartNum(row.getCell(2).getStringCellValue());
				subArea.setEndNum(row.getCell(3).getStringCellValue());
				subArea.setSingle(row.getCell(4).getStringCellValue().charAt(0));
				subAreas.add(subArea);
			}
			System.out.println(subAreas.toString());
			subAreaService.save(subAreas);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		if (flag) {
			map.put("flag", true);
			map.put("msg", "上传成功了！");
		} else {
			map.put("flag", false);
			map.put("msg", "上传失败！");
		}
		ActionContext.getContext().getValueStack().push(map);
		return NONE;
	}
	
	@Action("subArea_export")
	public String export(){
		List<SubArea> list = subAreaService.findAll();
		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("分区数据");
		Row hRow = sheet.createRow(0);
		hRow.createCell(0).setCellValue("分区编号");
		hRow.createCell(1).setCellValue("关键字");
		hRow.createCell(2).setCellValue("起始号");
		hRow.createCell(3).setCellValue("结束号");
		hRow.createCell(4).setCellValue("单双号");
		for (int i = 0; i < list.size(); i++) {
			SubArea subArea = list.get(i);
			XSSFRow row = (XSSFRow) sheet.createRow(i+1);
			row.createCell(0).setCellValue(subArea.getId());
			row.createCell(1).setCellValue(subArea.getKeyWords());
			row.createCell(2).setCellValue(subArea.getStartNum());
			row.createCell(3).setCellValue(subArea.getEndNum());
			row.createCell(4).setCellValue(subArea.getSingle().toString());
		}
		// 设置两个头一个流
		String filename = "subArea.xlsx";
		String type = ServletActionContext.getServletContext().getMimeType(filename);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType(type);
		response.setHeader("content-disposition", "attrachment; filename="+filename);
		try {
			workbook.write(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	private int page;
	
	private int rows;
	
	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Action(value="subArea_pageQuery", results=@Result(name="success", type="json"))
	public String pageQuery(){
		Pageable pageable = new PageRequest(page-1, rows);
		Specification<SubArea> specification = new Specification<SubArea>() {
			
			@Override
			public Predicate toPredicate(Root<SubArea> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(model.getKeyWords())) {
					Predicate p1 = cb.like(root.get("keyWords").as(String.class), "%"+model.getKeyWords()+"%");
					list.add(p1);
				}
				Join<Object, Object> areaJoin = root.join("area",JoinType.INNER);
				if (model.getArea()!=null&&StringUtils.isNotBlank(model.getArea().getProvince())) {
					Predicate p2 = cb.like(areaJoin.get("province").as(String.class), "%"+model.getArea().getProvince()+"%");
					list.add(p2);
				}
				if (model.getArea()!=null&&StringUtils.isNotBlank(model.getArea().getCity())) {
					Predicate p3 = cb.like(areaJoin.get("city").as(String.class), "%"+model.getArea().getCity()+"%");
					list.add(p3);
				}
				if (model.getArea()!=null&&StringUtils.isNotBlank(model.getArea().getDistrict())) {
					Predicate p4 = cb.like(areaJoin.get("district").as(String.class), "%"+model.getArea().getDistrict()+"%");
					list.add(p4);
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		Page<SubArea> pageData = subAreaService.findAll(specification, pageable);
		pushPageDataToValueStack(pageData);
		return SUCCESS;
	}
	
}
