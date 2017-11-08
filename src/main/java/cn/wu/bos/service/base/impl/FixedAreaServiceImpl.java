package cn.wu.bos.service.base.impl;

import cn.wu.bos.service.base.FixedAreaService;
import cn.wu.bos.dao.base.FixedAreaDao;
import cn.wu.bos.dao.base.TakeTimeDao;
import cn.wu.bos.domain.base.TakeTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wu.bos.dao.base.CourierDao;
import cn.wu.bos.domain.base.Courier;
import cn.wu.bos.domain.base.FixedArea;

/**
 * 定区的业务层接口的实现类
 *
 * @author 武灵森
 * @version 1.0，2017-10-29 16:09:38
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

	@Autowired
	private FixedAreaDao fixedAreaDao;

	@Override
	public void save(FixedArea model) {
		fixedAreaDao.save(model);
	}

	@Override
	public Page<FixedArea> pageQuery(Specification<FixedArea> specification,
			Pageable pageable) {
		return fixedAreaDao.findAll(specification, pageable);
	}

	@Autowired
	private CourierDao courierDao;
	
	@Autowired
	private TakeTimeDao taketimeDao;
	
	@Override
	public void associationCourierToFixedArea(FixedArea model,
			Integer courierId, Integer taketimeId) {
		FixedArea fixedArea = fixedAreaDao.findOne(model.getId());
		Courier courier = courierDao.findOne(courierId);
		TakeTime takeTime = taketimeDao.findOne(taketimeId);
		fixedArea.getCouriers().add(courier);
		courier.setTakeTime(takeTime);
	}
	
}
