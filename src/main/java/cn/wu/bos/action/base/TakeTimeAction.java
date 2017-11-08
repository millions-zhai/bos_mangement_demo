package cn.wu.bos.action.base;

import java.util.List;

import cn.wu.bos.domain.base.TakeTime;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.wu.bos.service.base.TakeTimeService;

/**
 * 收派时间的WEB的action
 *
 * @author 武灵森
 * @version 1.0，2017-11-01 18:49:59
 */
@Controller("myTakeTimeAction")
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
@SuppressWarnings("all")
public class TakeTimeAction extends BaseAction<TakeTime> {

	@Autowired
	private TakeTimeService taketimeService;
	
	@Action(value="taketime_findAll", results=@Result(name="success", type="json"))
	public String findAll(){
		List<TakeTime> list = taketimeService.findAll();
		ActionContext.getContext().getValueStack().push(list);
		return SUCCESS;
	}
	
}
