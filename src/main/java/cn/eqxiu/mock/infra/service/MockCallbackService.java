package cn.eqxiu.mock.infra.service;

import cn.eqxiu.mock.domain.entity.LogInfo;
import cn.eqxiu.mock.domain.repository.CallbackRepository;
import cn.eqxiu.mock.infra.web.vo.CallbackUrlListVO;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  模拟回调服务
 * @author will
 */
@Service
public class MockCallbackService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    CallbackRepository callbackRepository;

    public List<LogInfo> selectLogList(String url, String code) {
        return callbackRepository.selectLogList(url, code);
    }

    public ArrayList<JSONObject> selectCallbackList(String key) {
        List<CallbackUrlListVO> callbackUrlListVOS = callbackRepository.selectCallbackList(key);
        ArrayList<JSONObject> objects1 = new ArrayList<>();
        HashMap<Object, Object> idGroups = new HashMap<>();
        for (CallbackUrlListVO i : callbackUrlListVOS) {
            JSONObject jsonObject = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            ArrayList<JSONObject> jsonObjectssss = new ArrayList<>();
            if (i.getGroupId() == 0) {
                jsonObject.put("id", i.getId());
                jsonObject.put("title", i.getName());
                jsonObject.put("url", i.getUrl());
                jsonObject.put("group_id", i.getGroupId());
                objects1.add(jsonObject);
            } else {
                if (idGroups.containsKey(i.getGroupName())) {
                    for (JSONObject j : objects1) {
                        if (j.get("title").equals(i.getGroupName())) {
                            List<JSONObject> subMenus = (List<JSONObject>) j.get("subMenus");
                            jsonObject.put("id", i.getId());
                            jsonObject.put("title", i.getName());
                            jsonObject.put("url", i.getUrl());
                            subMenus.add(jsonObject);
                            j.put("subMenus", subMenus);
                        }
                    }
                } else {
                    jsonObject1.put("id", i.getId());
                    jsonObject1.put("title", i.getName());
                    jsonObject1.put("url", i.getUrl());
                    jsonObjectssss.add(jsonObject1);
                    jsonObject.put("group_id", i.getGroupId());
                    jsonObject.put("title", i.getGroupName());
                    jsonObject.put("subMenus", jsonObjectssss);
                    objects1.add(jsonObject);
                    idGroups.put(i.getGroupName(), i.getGroupName());
                }
            }
        }
        return objects1;
    }
}
