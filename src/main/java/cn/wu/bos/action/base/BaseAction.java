package cn.wu.bos.action.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 *
 * @author 武灵森
 * @version 1.0，
 */
@SuppressWarnings("all")
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	protected T model;

	@Override
	public T getModel() {
		return model;
	}

	public BaseAction() {
		Type genericSuperclass = this.getClass().getGenericSuperclass();
		// 获取类型第一个泛型参数
		ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
		Class<T> modelClass = (Class<T>) parameterizedType
				.getActualTypeArguments()[0];
		try {
			model = modelClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			System.out.println("模型构造失败...");
		}
	}
	
	protected int page;
	
	protected int rows;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public void pushPageDataToValueStack(Page<T> pageData){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", pageData.getTotalElements());
		result.put("rows", pageData.getContent());
		ActionContext.getContext().getValueStack().push(result);
	}
}
