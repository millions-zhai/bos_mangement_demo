package cn.wu.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wu.bos.dao.base.StandardDao;
import cn.wu.bos.domain.base.Standard;
import cn.wu.bos.service.base.StandardService;

/**
 * 收派标准的业务层接口的实现类
 *
 * @author 武灵森
 * @version 1.0，2017-10-24 20:31:27
 */
@Service
@Transactional
public class StandardServiceImpl implements StandardService {

	@Autowired
	private StandardDao standardDao;
	
	@Override
	public void save(Standard standard) {
		standardDao.save(standard);
	}

	@Override
	public List<Standard> findAll() {
		List<Standard> list = standardDao.findAll();
		return list;
	}

	@Override
	public Page<Standard> findAll(Pageable pageable) {
		return standardDao.findAll(pageable);
	}
	
}
