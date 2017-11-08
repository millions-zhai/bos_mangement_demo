package cn.wu.bos.action.base;

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

import cn.wu.bos.domain.base.Courier;
import cn.wu.bos.domain.base.Standard;
import cn.wu.bos.service.base.CourierService;
import org.apache.commons.lang3.StringUtils;
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
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 快递员WEB层的action
 *
 * @author 武灵森
 * @version 1.0，2017-10-25 15:01:46
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
@SuppressWarnings("all")
public class MyCourierAction extends ActionSupport implements ModelDriven<Courier> {

	private Courier courier = new Courier();

	@Override
	public Courier getModel() {
		return courier;
	}
	
	@Autowired
	private CourierService courierService;
	
	private int page;
	
	private int rows;
	
	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	@Action(value="courier_add",results=@Result(name="success",
			location="./pages/base/courier.html", type="redirect"))
	public String save(){
		courierService.save(courier);
		return SUCCESS;
	}
	
	@Action(value="courier_pageQuery", results=@Result(name="success", type="json"))
	public String findPage(){
		Pageable pageable = new PageRequest(page-1, rows);
		Specification<Courier> specification = new Specification<Courier>() {
			
			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// 创建一个集合存Predicate
				List<Predicate> list = new ArrayList<Predicate>();
				
				// 简单单表查询
				if (StringUtils.isNotBlank(courier.getCourierNum())) {
					Predicate p1 = cb.equal(root.get("courierNum").as(String.class), 
							courier.getCourierNum());
					list.add(p1);
				}
				
				if (StringUtils.isNotBlank(courier.getCompany())) {
					Predicate p2 = cb.like(root.get("company").as(String.class), 
							"%"+courier.getCompany()+"%");
					list.add(p2);
				}
				
				if (StringUtils.isNotBlank(courier.getType())) {
					Predicate p3 = cb.equal(root.get("type").as(String.class), courier.getType());
					list.add(p3);
				}
				Join<Courier, Standard> standardJoin = root.join("standard",JoinType.INNER);
				if (courier.getStandard()!= null && StringUtils.isNotBlank(courier.getStandard().getName())) {
					Predicate p4 = cb.like(standardJoin.get("name").as(String.class), 
							"%"+courier.getStandard().getName()+"%");
					list.add(p4);
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		Page<Courier> pageData = courierService.findAll(specification, pageable);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", pageData.getTotalElements());
		result.put("rows", pageData.getContent());
		ActionContext.getContext().getValueStack().push(result);;
		return SUCCESS;
	}
	
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}

	@Action(value="courier_delBatch", results=@Result(name="success", 
			location="./pages/base/courier.html", type="redirect"))
	public String delCourier(){
		String[] idArray = ids.split(",");
		courierService.delCourier(idArray);
		return SUCCESS;
	}
	
	@Action(value="courier_restore", results=@Result(name="success", 
			location="./pages/base/courier.html", type="redirect"))
	public String restore(){
		String[] idArray = ids.split(",");
		courierService.restore(idArray);
		return SUCCESS;
	}
	
	@Action(value="courier_findNoAssociation", results=@Result(name="success", type="json"))
	public String findNoAssociation(){
		List<Courier> list = courierService.findNoAssociation();
		ActionContext.getContext().getValueStack().push(list);
		return SUCCESS;
	}
	
}
