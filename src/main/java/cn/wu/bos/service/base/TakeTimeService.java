package cn.wu.bos.service.base;

import java.util.List;

import cn.wu.bos.domain.base.TakeTime;

/**
 * 收派时间的业务层接口
 *
 * @author 武灵森
 * @version 1.0，2017-11-01 18:53:12
 */
public interface TakeTimeService {

	List<TakeTime> findAll();

}
