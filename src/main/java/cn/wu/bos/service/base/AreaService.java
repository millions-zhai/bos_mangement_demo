package cn.wu.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.wu.bos.domain.base.Area;

/**
 * 区域的业务层接口
 *
 * @author 武灵森
 * @version 1.0，2017-10-27 13:27:59
 */
public interface AreaService {

	public void save(List<Area> areas);
	
	public Page<Area> findAll(Specification<Area> specification,
			Pageable pageable);
	
	public List<Area> findAll();

	public List<String> findProvince();

	public List<String> findCity(String province);

	public List<String> findDistrict(String province, String city);

	public Area findAll(Area model);
	
}
