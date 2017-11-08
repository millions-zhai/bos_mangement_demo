package cn.wu.bos.action.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
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

import cn.wu.domain.Customer;
import cn.wu.bos.domain.base.FixedArea;
import cn.wu.bos.service.base.FixedAreaService;

/**
 * 定区的WEB层的action
 *
 * @author 武灵森
 * @version 1.0，2017-10-29 16:05:53
 */
@Controller("myFixedAreaAction")
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class FixedAreaAction extends BaseAction<FixedArea> {

	@Autowired
	private FixedAreaService fixedAreaService;

	/**
	 * 添加操作
	 * 
	 * @return 跳转路径
	 */
	@Action(value = "fixedArea_save", 
			results = @Result(name = "success", 
			location = "./pages/base/fixed_area.html", type = "redirect"))
	public String save() {
		fixedAreaService.save(model);
		return SUCCESS;
	}

	/**
	 * 带条件的分页查询
	 * 
	 * @return 跳转路径
	 */
	@Action(value = "fixedArea_pageQuery", results = @Result(name = "success", type = "json"))
	public String pageQuery() {
		Pageable pageable = new PageRequest(page - 1, rows);
		Specification<FixedArea> specification = new Specification<FixedArea>() {

			@Override
			public Predicate toPredicate(Root<FixedArea> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(model.getId())) {
					Predicate p1 = cb.equal(root.get("id").as(String.class),
							model.getId());
					list.add(p1);
				}
				if (StringUtils.isNotBlank(model.getCompany())) {
					Predicate p2 = cb.equal(root.get("company")
							.as(String.class), model.getCompany());
					list.add(p2);
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		Page<FixedArea> pageData = fixedAreaService.pageQuery(specification,
				pageable);
		pushPageDataToValueStack(pageData);
		return SUCCESS;
	}

	/**
	 * 远程通信查询未关联定区的客户
	 * 
	 * @return 跳转路径
	 */
	@Action(value = "fixedArea_findNoAssociationCustomers", 
			results = @Result(name = "success", type = "json"))
	public String findNoAssociationCustomers() {
		Collection<? extends Customer> collection = WebClient
				.create("http://localhost:8866/crm_management/services/customerService/noassociationcustomers")
				.accept(MediaType.APPLICATION_JSON)
				.getCollection(Customer.class);
		ActionContext.getContext().getValueStack().push(collection);
		return SUCCESS;
	}

	/**
	 * 远程通信查询已关联定区的客户
	 * 
	 * @return 跳转路径
	 */
	@Action(value = "fixedArea_findHasAssociationCustomers", 
			results = @Result(name = "success", type = "json"))
	public String findHasAssociationCustomers() {
		Collection<? extends Customer> collection = WebClient
				.create("http://localhost:8866/crm_management/services/customerService/associationfixedareacustomers?fixedAreaId="
						+ model.getId()).type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.getCollection(Customer.class);
		ActionContext.getContext().getValueStack().push(collection);
		return SUCCESS;
	}

	private String[] customerIds;
	
	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}

	/**
	 * 远程通信将客户关联定区
	 * 
	 * @return 跳转路径
	 */
	@Action(value = "fixedArea_associationCustomerToFixedArea", 
			results = @Result(name = "success", 
			location = "./pages/base/fixed_area.html", type = "redirect"))
	public String associationCustomerToFixedArea() {
		String customerStr = StringUtils.join(customerIds, ",");
		WebClient
				.create("http://localhost:8866/crm_management/services/customerService/associationcustomerstofixedarea?customerStr="
						+ customerStr + "&fixedAreaId=" + model.getId()).type(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON).put(null);
		return SUCCESS;
	}
	
	private Integer courierId;
	
	private Integer taketimeId;
	
	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}

	public void setTaketimeId(Integer taketimeId) {
		this.taketimeId = taketimeId;
	}

	@Action(value="fixedArea_associationCourierToFixedArea", 
			results=@Result(name="success", 
			location="./pages/base/fixed_area.html", type="redirect"))
	public String associationCourierToFixedArea(){
		fixedAreaService.associationCourierToFixedArea(model, courierId, taketimeId);
		return SUCCESS;
	}

}
