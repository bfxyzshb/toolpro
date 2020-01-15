package com.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonTest {
    public static void main(String[] args) {
        Map map=new HashMap();
        map.put("test",null);
        map.put("test1","test");
        map.put("list",new ArrayList<Integer>(){{add(1);add(2);}});
        System.out.println(JSON.toJSONString(map));
        String json=JSON.toJSONString(map);
        JSONObject jsonObject=JSON.parseObject(json);
        System.out.println(jsonObject.get("test"));
        JSONArray jsonArray=jsonObject.getJSONArray("list");

        System.out.println(jsonObject.get("list"));
        System.out.println(jsonArray.size());
    }
}
