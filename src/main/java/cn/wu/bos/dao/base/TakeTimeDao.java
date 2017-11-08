package cn.wu.bos.dao.base;

import cn.wu.bos.domain.base.TakeTime;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 收派时间的持久层接口
 *
 * @author 武灵森
 * @version 1.0，2017-11-01 18:51:44
 */
public interface TakeTimeDao extends JpaRepository<TakeTime, Integer> {

}
