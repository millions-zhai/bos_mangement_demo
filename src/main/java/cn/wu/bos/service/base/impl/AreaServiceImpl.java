package cn.wu.bos.service.base.impl;

import java.util.List;

import cn.wu.bos.service.base.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wu.bos.dao.base.AreaDao;
import cn.wu.bos.domain.base.Area;

/**
 * 区域的业务层接口的实现类
 *
 * @author 武灵森
 * @version 1.0，2017-10-27 13:29:15
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDao areaDao;
	
	@Override
	public void save(List<Area> areas) {
		areaDao.save(areas);
	}

	/**
	 * @see AreaService#findAll()
	 */
	@Override
	public List<Area> findAll() {
		return areaDao.findAll();
	}

	@Override
	public Page<Area> findAll(Specification<Area> specification,
			Pageable pageable) {
		return areaDao.findAll(specification, pageable);
	}

	@Override
	public List<String> findProvince() {
		return areaDao.findProvince();
	}

	@Override
	public List<String> findCity(String province) {
		return areaDao.findCity(province);
	}

	@Override
	public List<String> findDistrict(String province, String city) {
		return areaDao.findDistrict(province, city);
	}

	@Override
	public Area findAll(Area model) {
		return areaDao.findByProvinceAndCityAndDistrict(model.getProvince(),model.getCity(), model.getDistrict());
	}

}
