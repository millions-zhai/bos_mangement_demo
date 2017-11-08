package cn.wu.bos.service.base;

import java.util.List;

import cn.wu.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 收派标准的业务层接口
 *
 * @author 武灵森
 * @version 1.0，2017-10-24 20:29:17
 */
public interface StandardService {

	public void save(Standard standard);

	public Page<Standard> findAll(Pageable pageable);

	public List<Standard> findAll();
	
}
