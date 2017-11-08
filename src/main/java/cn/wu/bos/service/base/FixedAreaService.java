package cn.wu.bos.service.base;

import cn.wu.bos.domain.base.FixedArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * 定区的业务层接口
 *
 * @author 武灵森
 * @version 1.0，2017-10-29 16:08:37
 */
public interface FixedAreaService {

	void save(FixedArea model);

	Page<FixedArea> pageQuery(Specification<FixedArea> specification,
			Pageable pageable);

	void associationCourierToFixedArea(FixedArea model, Integer courierId,
			Integer taketimeId);

}
