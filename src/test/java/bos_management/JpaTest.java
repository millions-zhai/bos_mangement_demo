package bos_management;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.wu.bos.domain.base.Standard;
import cn.wu.bos.service.base.StandardService;

/**
 * Jpa查询方法的测试类
 *
 * @author 武灵森
 * @version 1.0，2017-10-25 08:08:22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class JpaTest {

	@Autowired
	private StandardService standaedService;
	
	@Test
	public void test01(){
		List<Standard> all = standaedService.findAll();
		System.out.println(all);
	}
	
}
