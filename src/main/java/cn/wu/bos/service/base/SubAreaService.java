package cn.wu.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.wu.bos.domain.base.SubArea;

/**
 * 分区的业务层接口
 *
 * @author 武灵森
 * @version 1.0，2017-10-28 14:12:23
 */
public interface SubAreaService {

	void save(SubArea model);

	void save(List<SubArea> subAreas);

	List<SubArea> findAll();

	Page<SubArea> findAll(Specification<SubArea> specification,
			Pageable pageable);

}
