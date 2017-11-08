package cn.wu.bos.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import cn.wu.bos.service.base.AreaService;
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

import cn.wu.bos.domain.base.Area;
import cn.wu.bos.utils.PinYin4jUtils;

import com.opensymphony.xwork2.ActionContext;

/**
 * 区域的WEB层的action
 *
 * @author 武灵森
 * @version 1.0，2017-10-27 13:24:02
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
@SuppressWarnings("all")
public class MyAreaAction extends BaseAction<Area>{
	
	@Autowired
	private AreaService areaService;
	
	private File file;
	
	private String fileFileName;
	
	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	@Action("area_batchImport")
	public String save() throws Exception{
		List<Area> areas = new ArrayList<Area>();
		
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
			if (row.getCell(0)==null||StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
				continue;
			}
			Area area = new Area();
			area.setId(row.getCell(0).getStringCellValue());
			area.setProvince(row.getCell(1).getStringCellValue());
			area.setCity(row.getCell(2).getStringCellValue());
			area.setDistrict(row.getCell(3).getStringCellValue());
			area.setPostcode(row.getCell(4).getStringCellValue());
			// pinyin4j编辑citycode和shortcode
			String province = area.getProvince();
			String city = area.getCity();
			String district = area.getDistrict();
			province = province.substring(0, province.length()-1);
			city = city.substring(0, city.length()-1);
			district = district.substring(0, district.length()-1);
			// 获得简码
			String[] headArray = PinYin4jUtils.getHeadByString(province+city+district);
			StringBuilder sb = new StringBuilder();
			for (String headStr : headArray) {
				sb.append(headStr);
			}
			String shortcode = sb.toString();
			area.setShortcode(shortcode);
			// 获得城市编码
			String citycode = PinYin4jUtils.hanziToPinyin(city, "");
			area.setCitycode(citycode);
			areas.add(area);
		}
		areaService.save(areas);
		return NONE;
	}
	
	@Action("area_export")
	public String export(){
		List<Area> areas = areaService.findAll();
		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("区域数据");
		Row hRow = sheet.createRow(0);
		hRow.createCell(0).setCellValue("区域编号");
		hRow.createCell(1).setCellValue("省份");
		hRow.createCell(2).setCellValue("城市");
		hRow.createCell(3).setCellValue("区县");
		hRow.createCell(4).setCellValue("邮政编码");
		for (int i = 0; i < areas.size(); i++) {
			Area area = areas.get(i);
			XSSFRow row = (XSSFRow) sheet.createRow(i+1);
			row.createCell(0).setCellValue(area.getId());
			row.createCell(1).setCellValue(area.getProvince());
			row.createCell(2).setCellValue(area.getCity());
			row.createCell(3).setCellValue(area.getDistrict());
			row.createCell(4).setCellValue(area.getPostcode());
		}
		// 设置两个头一个流
		String filename = "a.xlsx";
		String mimeType = ServletActionContext.getServletContext().getMimeType(filename);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType(mimeType);
		response.setHeader("content-disposition", "attachment;filename="+filename);
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

	@Action(value="area_pageQuery", results=@Result(name="success", type="json"))
	public String pageQuery(){
		Pageable pageable = new PageRequest(page-1, rows);
		Specification<Area> specification = new Specification<Area>() {
			
			@Override
			public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(model.getProvince())) {
					Predicate p1 = cb.equal(root.get("province").as(String.class), model.getProvince());
					list.add(p1);
				}
				if (StringUtils.isNotBlank(model.getCity())) {
					Predicate p2 = cb.equal(root.get("city").as(String.class), model.getCity());
					list.add(p2);
				}
				if (StringUtils.isNotBlank(model.getDistrict())) {
					Predicate p3 = cb.equal(root.get("district").as(String.class), model.getDistrict());
					list.add(p3);
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		Page<Area> pageData = areaService.findAll(specification, pageable);
		pushPageDataToValueStack(pageData);
		return SUCCESS;
	}
	
	@Action(value="area_findProvince", results=@Result(name="success", type="json"))
	public String area_findProvince(){
		List<String> list = areaService.findProvince();
		List<Area> areas = new ArrayList<Area>();
		for (String province : list) {
			Area area = new Area();
			area.setProvince(province);
			areas.add(area);
		}
		ActionContext.getContext().getValueStack().push(areas);
		return SUCCESS;
	}
	
	@Action(value="area_findCity", results=@Result(name="success", type="json"))
	public String findCity(){
		try {
			String province = model.getProvince();
			province = new String(province.getBytes("iso-8859-1"),"utf-8");
			List<String> list = areaService.findCity(province);
			List<Area> areas = new ArrayList<Area>();
			for(String city : list){
				Area area = new Area();
				area.setCity(city);
				areas.add(area);
			}
			ActionContext.getContext().getValueStack().push(areas);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@Action(value="area_findDistrict", results=@Result(name="success", type="json"))
	public String findDistrict(){
		try {
			String province = model.getProvince();
			province = new String(province.getBytes("iso-8859-1"),"utf-8");
			String city = model.getCity();
			city = new String(city.getBytes("iso-8859-1"),"utf-8");
			List<String> list = areaService.findDistrict(province, city);
			List<Area> areas = new ArrayList<Area>();
			for(String district : list){
				Area area = new Area();
				area.setDistrict(district);
				areas.add(area);
			}
			ActionContext.getContext().getValueStack().push(areas);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@Action(value="area_findArea", results=@Result(name="success", type="json"))
	public String findArea(){
		Area area = areaService.findAll(model);
		ActionContext.getContext().getValueStack().push(area);
		return SUCCESS;
	}
	
}
