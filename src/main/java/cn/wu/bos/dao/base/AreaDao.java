package cn.wu.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cn.wu.bos.domain.base.Area;

/**
 * 区域的持久层接口
 *
 * @author 武灵森
 * @version 1.0，2017-10-27 13:26:08
 */
public interface AreaDao extends JpaRepository<Area, String>, JpaSpecificationExecutor<Area> {

	@Query("select distinct t.province from Area t")
	List<String> findProvince();

	@Query("select distinct t.city from Area t where t.province = ?")
	List<String> findCity(String province);

	@Query("select t.district from Area t where t.province = ? and t.city = ?")
	List<String> findDistrict(String province, String city);
	
	Area findByProvinceAndCityAndDistrict(String province, String city,
			String district);

}
