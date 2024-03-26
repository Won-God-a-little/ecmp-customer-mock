package cn.eqxiu.mock.infra.service;

import cn.eqxiu.mock.common.Result;
import cn.eqxiu.mock.domain.entity.Corp;
import cn.eqxiu.mock.domain.entity.MockPage;
import cn.eqxiu.mock.domain.entity.MockPageInfo;
import cn.eqxiu.mock.domain.repository.MockPageRepository;
import cn.eqxiu.mock.domain.type.CacheEnum;
import cn.eqxiu.mock.infra.util.HttpClientUtils;
import cn.eqxiu.mock.infra.util.TimedCacheUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *  模拟页面集成服务
 * @author will
 */
@Service
public class MockPageIntegrationService {

    @Resource
    MockPageRepository mockPageRepository;

    @Value("${mock.open-id}")
    String openId;

    @Value("${mock.server}")
    private  String prefixUrl;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *获取集成页面集信息
     * @param key 页面名称
     * @return 返回页面组信息
     */
    public Result getMockPageInfoList(String key) {
        List<MockPageInfo> mockPageInfos = mockPageRepository.findByPageName(key);
        return Result.ofSuccess().setObj(mockPageInfos);
    }


    public String showPage(MockPage pageInfo, Corp corp) {
        Long pageId = pageInfo.getPageId();
        String authorizationKey = CacheEnum.AUTHORIATION.getCacheKey(corp.getSecretId());
        String authorization = TimedCacheUtils.timedCache.get(authorizationKey);
        if (authorization == null) {
            return authorization;
        }

        //String pageUrl = prefixUrl + pageInfo.getPageUrl();
        //String pageUrl = "https://www.eqxiu.cn" + pageInfo.getPageUrl();
        String pageUrl = "https://stz.eqshow.cn" + pageInfo.getPageUrl();

        StringBuilder reallyUrl = new StringBuilder(pageUrl);
        String secretIdAndAuth = "?secretId=" + corp.getSecretId() + "&Authorization=" + authorization;

        setMockPageUrl(reallyUrl, pageInfo, corp, secretIdAndAuth);
        return reallyUrl.toString();
    }

    /**
     * 设置界面地址
     * @param page 页面集信息
     */
    public void setMockPageUrl(StringBuilder reallyUrl, MockPage page, Corp corp, String secretIdAndAuth){


        JSONObject jsonObject = JSONObject.parseObject(page.getPageAges());
        List<String> keys = jsonObject.keySet().stream().collect(Collectors.toList());
        //进行排序
        //排序原因：作品编辑页面是以作品id作为地址，需要特别处理
        keys.sort((key1,key2) -> {
            if (Pattern.compile("[0-9]*").matcher(key1).matches()){
                return -1;
            }
            if (Pattern.compile("[0-9]*").matcher(key2).matches()){
                return 1;
            }
            return 0;
        });
        editingUrl(reallyUrl, keys, jsonObject, corp, secretIdAndAuth);
        page.setPageUrl(reallyUrl.substring(0, reallyUrl.length() - 1));
    }

    /**
     * 进行地址拼接
     * @param reallyUrl 访问地址
     * @param keys 参数名称
     * @param pageAges 参数信息
     */
    public void editingUrl(StringBuilder reallyUrl,List<String> keys,JSONObject pageAges, Corp corp, String secretIdAndAuth){
        for (String key : keys) {
            //判断是否为作品编辑器
            if (Pattern.compile("[0-9]*").matcher(key).matches()){
                //获取对应的作品地址
                getTypeByUrl(reallyUrl,corp);
                continue;
            }
            reallyUrl.append(secretIdAndAuth);
            Set<String> agesKeys = pageAges.keySet();
            for (String agesKey : agesKeys) {
                JSONObject agesValue = pageAges.getJSONObject(agesKey);
                String value = agesValue.getString("value");
                reallyUrl.append("&").append(agesKey).append("=").append(value);
            }

            //判断是否需要获取文件网络地址
            if (key.equals("filePath")){
                getFilePathValue(reallyUrl, corp);
                continue;
            }
        }
    }

    private void getFilePathValue(StringBuilder reallyUrl, Corp corp) {
        String photoUrl = prefixUrl + "/api/v1/biz/material/list";//token=TOKEN&openId=OPEN_ID
        String token = TimedCacheUtils.timedCache.get(CacheEnum.TOKEN.getCacheKey(corp.getCode()));
        Map<String, String> map = new HashMap<>();
        map.put("token",token);
        map.put("openId",openId);
        map.put("type","1");
        map.put("bizType","3");
        JSONObject jsonObject = HttpClientUtils.httpGet(photoUrl, null, map);
        if(jsonObject==null||jsonObject.getJSONArray("list")==null){
            return;
        }

        String filePath = jsonObject.getJSONArray("list").isEmpty()?null:jsonObject.getJSONArray("list").getJSONObject(0).getString("transcodePath");
        reallyUrl.append("&filePath=").append(filePath);
    }

    //如果是互动、表单、长页、海报，则需要拼接作品id，作为地址前置
    public void getTypeByUrl(StringBuilder reallyUrl, Corp corp){

        String creationsUrl = prefixUrl+"/api/v1/editor/creation/list/corpCreation";//?token=TOKEN&openId=OPEN_ID
        String tokenKey = CacheEnum.TOKEN.getCacheKey(corp.getSecretId());
        String token = TimedCacheUtils.timedCache.get(tokenKey);
        Map<String, String> map = new HashMap<>();
        map.put("token",token);
        map.put("openId",openId);

        if (reallyUrl.substring(reallyUrl.lastIndexOf("/")).equals("/c?")){
            map.put("type","h5");

            JSONObject jsonObject = HttpClientUtils.httpGet(creationsUrl, null, map);

            if(jsonObject==null||jsonObject.getJSONArray("list")==null){
                return;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            String creationId = jsonArray.isEmpty()?"-1":jsonArray.getJSONObject(0).getString("id");
            reallyUrl.deleteCharAt(reallyUrl.length()-1).append("/").append(creationId);
            return;
        }
        if (reallyUrl.substring(reallyUrl.lastIndexOf("/")).equals("/lc?")){
            map.put("type","lc");
            JSONArray jsonArray = HttpClientUtils.httpGet(creationsUrl, null, map).getJSONArray("list");
            String creationId = jsonArray.isEmpty()?"-1":jsonArray.getJSONObject(0).getString("id");
            reallyUrl.deleteCharAt(reallyUrl.length()-1).append("/").append(creationId);
            return;
        }
        if (reallyUrl.substring(reallyUrl.lastIndexOf("/")).equals("/fe?")){
            map.put("type","form");
            JSONArray jsonArray = HttpClientUtils.httpGet(creationsUrl, null, map).getJSONArray("list");
            String creationId = jsonArray.isEmpty()?"-1":jsonArray.getJSONObject(0).getString("id");
            reallyUrl.deleteCharAt(reallyUrl.length()-1).append("/").append(creationId);
            return;
        }
        if (reallyUrl.substring(reallyUrl.lastIndexOf("/")).equals("/gc?")){
            map.put("type","hd");
            JSONArray jsonArray = HttpClientUtils.httpGet(creationsUrl, null, map).getJSONArray("list");
            String creationId = jsonArray.isEmpty()?"-1":jsonArray.getJSONObject(0).getString("id");
            reallyUrl.deleteCharAt(reallyUrl.length()-1).append("/").append(creationId);
            return;
        }
        if (reallyUrl.substring(reallyUrl.lastIndexOf("/")).equals("/design?")){
            map.put("type","design");
            JSONArray jsonArray = HttpClientUtils.httpGet(creationsUrl, null, map).getJSONArray("list");
            String creationId = jsonArray.isEmpty()?"-1":jsonArray.getJSONObject(0).getString("id");
            reallyUrl.deleteCharAt(reallyUrl.length()-1).append("/").append(creationId);
        }
    }

}


