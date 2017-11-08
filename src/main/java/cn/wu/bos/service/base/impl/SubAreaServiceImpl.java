package cn.wu.bos.service.base.impl;

import java.util.List;

import cn.wu.bos.service.base.SubAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wu.bos.dao.base.SubAreaDao;
import cn.wu.bos.domain.base.SubArea;

/**
 * 分区的业务层接口的实现类
 *
 * @author 武灵森
 * @version 1.0，2017-10-28 14:13:39
 */
@Service
@Transactional
@SuppressWarnings("all")
public class SubAreaServiceImpl implements SubAreaService {

	@Autowired
	private SubAreaDao subAreaDao;

	@Override
	public void save(SubArea model) {
		subAreaDao.save(model);
	}

	@Override
	public void save(List<SubArea> subAreas) {
		subAreaDao.save(subAreas);
	}

	@Override
	public List<SubArea> findAll() {
		return subAreaDao.findAll();
	}

	@Override
	public Page<SubArea> findAll(Specification<SubArea> specification,
			Pageable pageable) {
		return subAreaDao.findAll(specification, pageable);
	}
	
}
