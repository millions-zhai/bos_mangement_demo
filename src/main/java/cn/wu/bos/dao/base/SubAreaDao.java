package cn.wu.bos.dao.base;

import cn.wu.bos.domain.base.SubArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 分区的持久层接口
 *
 * @author 武灵森
 * @version 1.0，2017-10-28 14:10:56
 */
public interface SubAreaDao extends JpaRepository<SubArea, String>, JpaSpecificationExecutor<SubArea> {

}
