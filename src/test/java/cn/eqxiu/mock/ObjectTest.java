package cn.eqxiu.mock;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ObjectTest {
    @Test
    public void mapTest(){
        Map<String,String> map= new HashMap<>();
        map.put("value","aaaaa");
        map.put("discription","tiancai");
        map.put("bbicjia","1");
        Object json = JSONObject.toJSON(map);
        System.out.println(json);
    }
}
