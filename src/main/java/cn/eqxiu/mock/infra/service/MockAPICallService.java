package cn.eqxiu.mock.infra.service;

import cn.eqxiu.mock.common.Result;
import cn.eqxiu.mock.domain.entity.Corp;
import cn.eqxiu.mock.domain.entity.OpenInfo;
import cn.eqxiu.mock.domain.entity.RequestParam;
import cn.eqxiu.mock.domain.repository.InterfaceInfoRepository;
import cn.eqxiu.mock.domain.type.CacheEnum;
import cn.eqxiu.mock.domain.type.InterfaceTypeEnum;
import cn.eqxiu.mock.infra.util.HttpClientUtils;
import cn.eqxiu.mock.infra.util.SHA256Util;
import cn.eqxiu.mock.infra.util.TimedCacheUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * API调用模拟服务
 * @author will
 */
@Service
public class MockAPICallService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private InterfaceInfoRepository interfaceInfoRepository;

    @Value("${mock.server}")
    private String mockServer;

    @Value("${mock.open-id}")
    private String mockOpenId;

    public List<Map<String, Object>> getAllInterface(String key) {
        return interfaceInfoRepository.getAllInterface(key);
    }

    public OpenInfo getInterfaceById(Long id) {
        return interfaceInfoRepository.getInterfaceById(id);
    }



    /**
     * 宁松
     *
     * @param secretId
     * @param secretKey
     * @return
     */
    public Map<String, String> toEncrypt(String secretId, String secretKey) {
        Long timestamp = System.currentTimeMillis();
        List<String> paramList = Arrays.asList(secretId, "server", timestamp.toString(), secretKey);
        Collections.sort(paramList);
        String signatureStr = org.springframework.util.StringUtils.arrayToDelimitedString(paramList.toArray(), "");
        String signature = SHA256Util.getSHA256Str(signatureStr);

        Map<String, String> map = new HashMap();
        map.put("secretId", secretId);
        map.put("type", "server");
        map.put("timestamp", timestamp.toString());
        map.put("signature", signature);
        return map;
    }

    public Result mockCall(OpenInfo info, Corp corp) {
        JSONObject result = null;
        String httpUrl = mockServer + info.getUrl();
        List<RequestParam> newRequestParams = refreshParam(info, corp);
        logger.info("requestParams:{}", JSON.toJSONString(newRequestParams));
        HashMap<String, String> map = new HashMap<>();
        for (RequestParam requestParam : newRequestParams) {
            String key = requestParam.getName();
            String value = null;
            if (requestParam.getRequired().equals("是")) {
                if (requestParam.getName().equals("token")) {
                    if (requestParam.getDefaultValue().equals("${token}")) {
                        return Result.ofFail("请先获取token");
                    }
                }
                value = requestParam.getDefaultValue();
                if (StringUtils.isBlank(value)) {
                    return Result.ofFail("请填充请求参数");
                }
            } else {
                value = requestParam.getDefaultValue();
            }
            map.put(key, value);
        }
        logger.info("map:{}",map);
        if (info.getMethod().equals(InterfaceTypeEnum.POST.getDescription())) {
            result = HttpClientUtils.httpPost(httpUrl, null, map);
        } else {
            result = HttpClientUtils.httpGet(httpUrl, null, map);
        }

        switch (String.valueOf(info.getId())) {
            case "4" :
                setTokenCache(result, corp);
                break;
            case "5" :
                setTempCodeCache(result, corp);
                break;
            case "6" :
                setAuthorizationCache(result, corp);
                break;
            case "7" :

        }
        logger.info("mockcall请求参数,{}", info.getRequestParams().toString());
        return Result.ofSuccess().setObj(result);
    }

    public void setTokenCache(JSONObject result, Corp corp) {
        String map = result.getString("map");
        if (!StringUtils.isBlank(map)) {
            JSONObject jsonObject = JSONObject.parseObject(map);
            String token = jsonObject.getString("token");
            //缓存令牌token
            String  tokenKey = CacheEnum.TOKEN.getCacheKey(corp.getSecretId());
            TimedCacheUtils.timedCache.put(tokenKey, token, 1000 * 60 * 60 * 2);
            logger.info("获取到的token：{}", TimedCacheUtils.timedCache.get(tokenKey));
        }
    }

    private void setTempCodeCache(JSONObject result, Corp corp) {
        String tempCode = result.getString("obj");
        String tempCodeKey = CacheEnum.TEMPCODE.getCacheKey(corp.getSecretId());
        TimedCacheUtils.timedCache.put(tempCodeKey, tempCode, 1000 * 60 * 5);
        logger.info("获取到的TempCode：{}", TimedCacheUtils.timedCache.get(tempCodeKey));
    }

    private void setAuthorizationCache(JSONObject result, Corp corp) {
        String authorization = result.getString("obj");
        String authorizationKey = CacheEnum.AUTHORIATION.getCacheKey(corp.getSecretId());
        TimedCacheUtils.timedCache.put(authorizationKey, authorization, 1000 * 60 * 60 *24);
        logger.info("获取到的Authorization：{}", TimedCacheUtils.timedCache.get(authorizationKey));
    }

    public List<RequestParam> refreshParam(OpenInfo info,Corp corp) {

        Long id = info.getId();
        switch (String.valueOf(id)) {
            case "1" :
            case "2" :
            case "3" :
            case "8" :
            case "9" :
            case "10" :
            case "11" :
            case "12" :
            case "13" :
            case "14" :
            case "15" :
            case "16" :
            case "17" :
            case "18" :
            case "19" :
            case "20" :
                return getPlatfformClassify(info,corp);

            case "4" :
                return getToken(info, corp);
            case "5" :
                return getTempCode(info, corp);
            case "6" :
                return getAuthorization(info, corp);
            case "7" :
                return getTempCode(info, corp);
        }
        return null;
    }

    // 1、2 平台模板分类接口参数列表、平台模板列表接口
    private List<RequestParam> getPlatfformClassify(OpenInfo info, Corp corp) {
        //OpenInfo defaultInfo = interfaceInfoRepository.getInterfaceById(info.getId());
        //List<RequestParam> defaulRequestParams = defaultInfo.getRequestParams();
        String token = TimedCacheUtils.timedCache.get(CacheEnum.TOKEN.getCacheKey(corp.getSecretId()));
        List<RequestParam> requestParams = info.getRequestParams();
        for (RequestParam requestParam : requestParams) {
            if (requestParam.getName().equals("token")) {
                requestParam.setDefaultValue(token);
            }
        }
        return requestParams;
    }



    // 4 获取令牌(Token)接口参数
    public List<RequestParam> getToken (OpenInfo info, Corp corp){
        //OpenInfo defaultInfo = interfaceInfoRepository.getInterfaceById(info.getId());
        String type = null;
        String signatureMethod = null;
        //List<RequestParam> requestParams = defaultInfo.getRequestParams();
    /*    for (RequestParam requestParam : requestParams) {
            if (requestParam.getName().equals("type")) {
                type = requestParam.getDefaultValue();
            }
            if (requestParam.getName().equals("signatureMethod")) {
                signatureMethod = requestParam.getDefaultValue();
            }
        }*/
        List<RequestParam> requestParams = info.getRequestParams();
        for (RequestParam requestParam : requestParams) {
            if (requestParam.getName().equals("type")) {
                type = requestParam.getDefaultValue();
            }
            if (requestParam.getName().equals("signatureMethod")) {
                signatureMethod = requestParam.getDefaultValue();
            }
        }
        Long timestamp = System.currentTimeMillis();
        List<String> paramList = Arrays.asList(corp.getSecretId(), type, timestamp.toString(), corp.getSecretKey(), signatureMethod);
        Collections.sort(paramList);
        String signatureStr = org.springframework.util.StringUtils.arrayToDelimitedString(paramList.toArray(), "");
        String signature = SHA256Util.getSHA256Str(signatureStr);

        for (RequestParam requestParam : requestParams) {
            if (requestParam.getName().equals("secretId")) {
                requestParam.setDefaultValue(corp.getSecretId());
            } else if (requestParam.getName().equals("timestamp")) {
                requestParam.setDefaultValue(timestamp.toString());
            } else if (requestParam.getName().equals("signature")) {
                requestParam.setDefaultValue(signature);
            }
        }
        return requestParams;
    }

    // 5、7 获取授权码接口参数、获取登录退出接口参数
    private List<RequestParam> getTempCode (OpenInfo info, Corp corp){
        OpenInfo defaultInfo = interfaceInfoRepository.getInterfaceById(info.getId());
        String token = TimedCacheUtils.timedCache.get(CacheEnum.TOKEN.getCacheKey(corp.getSecretId()));
        List<RequestParam> requestParams = defaultInfo.getRequestParams();
        for (RequestParam requestParam : requestParams) {
            if (requestParam.getName().equals("openId")) {
                requestParam.setDefaultValue(mockOpenId);
            }
            if (requestParam.getName().equals("token")) {
                requestParam.setDefaultValue(token);
            }
        }
        return requestParams;
    }
    // 6 获取静默登录接口参数
    public List<RequestParam> getAuthorization(OpenInfo info, Corp corp) {
        //OpenInfo defaultInfo = interfaceInfoRepository.getInterfaceById(info.getId());
        String tempCode = TimedCacheUtils.timedCache.get(CacheEnum.TEMPCODE.getCacheKey(corp.getSecretId()));
        //List<RequestParam> requestParams = defaultInfo.getRequestParams();
        List<RequestParam> requestParams = info.getRequestParams();
        for (RequestParam requestParam : requestParams) {
            if (requestParam.getName().equals("code")) {
                requestParam.setDefaultValue(tempCode);
            }
        }
        return requestParams;
    }


}



