package bos_management;


import cn.itcast.bos.dao.Area;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by millions on 2017/11/8.
 */
public class HashMapTest {


    @Test
    public void test1(){

        HashMap<Object, String> map = new HashMap<>();
        Area area = new Area();
        System.out.println(area.toString());
        map.put(area,"234");
        area.setId("sdfsf");
        area.setName("sdfsdfsf");
        System.out.println(area.toString());
        System.out.println(map.get(area));
    }

}
