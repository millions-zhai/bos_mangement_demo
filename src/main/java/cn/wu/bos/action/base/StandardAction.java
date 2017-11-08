package cn.wu.bos.action.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wu.bos.domain.base.Standard;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.wu.bos.service.base.StandardService;

/**
 * 收派标准的action
 *
 * @author 武灵森
 * @version 1.0，2017-10-24 20:23:52
 */
@Controller("myStandardAction")
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
@SuppressWarnings("all")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {

	private Standard standard = new Standard();

	@Override
	public Standard getModel() {
		return standard;
	}
	
	@Autowired
	private StandardService standardService;
	
	/** 当前页数 */
	private int page;
	
	/** 每页的数据 */
	private int rows;
	
	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Action(value="standard_save",results=@Result(name="success",location="./pages/base/standard.html",type="redirect"))
	public String add(){
		standardService.save(standard);
		return SUCCESS;
	}
	
	@Action(value="standard_pageQuery", results=@Result(name="success", type="json"))
	public String pageQuery(){
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Standard> pageData = standardService.findAll(pageable);
		
		Map<String ,Object> result = new HashMap<String, Object>();
		result.put("total", pageData.getTotalElements());
		result.put("rows", pageData.getContent());
		ActionContext.getContext().getValueStack().push(result);
		return SUCCESS;
	}
	
	@Action(value = "standard_findAll", results = { @Result(name = "success", type = "json") })
	public String findAll(){
		List<Standard> list = standardService.findAll();
		ActionContext.getContext().getValueStack().push(list);
		return SUCCESS;
	}
	
}
