package cn.wu.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.wu.bos.domain.base.Courier;

/**
 * 快递员的业务层接口
 *
 * @author 武灵森
 * @version 1.0，2017-10-25 16:33:54
 */
public interface CourierService {

	public void save(Courier courier);

	public Page<Courier> findAll(Specification<Courier> specification, Pageable pageable);

	public void delCourier(String[] idArray);

	public void restore(String[] idArray);

	public List<Courier> findNoAssociation();
	
}
