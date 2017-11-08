package cn.wu.bos.dao.base;

import cn.wu.bos.domain.base.Standard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 收派标准的持久层接口
 *
 * @author 武灵森
 * @version 1.0，2017-10-24 20:49:58
 */
public interface StandardDao extends JpaRepository<Standard, Integer> {

}
