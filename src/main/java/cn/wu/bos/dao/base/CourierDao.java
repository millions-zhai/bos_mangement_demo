package cn.wu.bos.dao.base;

import cn.wu.bos.domain.base.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 快递员的持久层接口
 *
 * @author 武灵森
 * @version 1.0，2017-10-25 16:37:06
 */
public interface CourierDao extends JpaRepository<Courier, Integer>, JpaSpecificationExecutor<Courier>{

	@Query(value = "update Courier set deltag = '1' where id = ?")
	@Modifying
	public void updateDelTag(Integer id);

	@Query(value = "update Courier set deltag = null where id = ?")
	@Modifying
	public void restore(Integer id);

}
