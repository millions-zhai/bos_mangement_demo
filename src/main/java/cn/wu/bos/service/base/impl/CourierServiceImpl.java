package cn.wu.bos.service.base.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import cn.wu.bos.service.base.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wu.bos.dao.base.CourierDao;
import cn.wu.bos.domain.base.Courier;

/**
 * 快递员的业务层接口的实现类
 *
 * @author 武灵森
 * @version 1.0，2017-10-25 16:35:20
 */
@Service
@Transactional
@SuppressWarnings("all")
public class CourierServiceImpl implements CourierService {

	@Autowired
	private CourierDao courierDao;
	
	@Override
	public void save(Courier courier) {
		courierDao.save(courier);
	}

	@Override
	public Page<Courier> findAll(Specification<Courier> specification,
			Pageable pageable) {
		return courierDao.findAll(specification, pageable);
	}

	@Override
	public void delCourier(String[] idArray) {
		for (String idStr : idArray) {
			Integer id = Integer.parseInt(idStr);
			courierDao.updateDelTag(id);
		}
	}

	@Override
	public void restore(String[] idArray) {
		for (String idStr : idArray) {
			Integer id = Integer.parseInt(idStr);
			courierDao.restore(id);
		}
	}

	@Override
	public List<Courier> findNoAssociation() {
		Specification<Courier> specification = new Specification<Courier>() {
			
			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				Predicate p1 = cb.isEmpty(root.get("fixedAreas").as(Set.class));
				list.add(p1);
				Predicate p2 = cb.isNull(root.get("deltag").as(Character.class));
				list.add(p2);
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		return courierDao.findAll(specification);
	}

}
