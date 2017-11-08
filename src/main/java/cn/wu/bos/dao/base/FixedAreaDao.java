package cn.wu.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.wu.bos.domain.base.FixedArea;

/**
 * 定区的持久层接口
 *
 * @author 武灵森
 * @version 1.0，2017-10-29 16:08:21
 */
public interface FixedAreaDao extends JpaRepository<FixedArea, String> ,JpaSpecificationExecutor<FixedArea>{

}
