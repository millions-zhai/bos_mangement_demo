package cn.wu.bos.service.base.impl;

import java.util.List;

import cn.wu.bos.dao.base.TakeTimeDao;
import cn.wu.bos.domain.base.TakeTime;
import cn.wu.bos.service.base.TakeTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 收派时间的业务层接口的实现类
 *
 * @author 武灵森
 * @version 1.0，2017-11-01 18:54:15
 */
@Service
@Transactional
@SuppressWarnings("all")
public class TakeTimeServiceImpl implements TakeTimeService {

	@Autowired
	private TakeTimeDao taketimeDao;

	@Override
	public List<TakeTime> findAll() {
		return taketimeDao.findAll();
	}
	
}
