package cn.eqxiu.mock.common;

import cn.eqxiu.mock.domain.entity.Corp;
import cn.eqxiu.mock.domain.type.CacheEnum;
import cn.eqxiu.mock.infra.util.HttpClientUtils;
import cn.eqxiu.mock.infra.util.SHA256Util;
import cn.eqxiu.mock.infra.util.TimedCacheUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import java.util.*;

/**
 * 获取授权信息类
 */
@Component
public class AccessAuthorization {

    @Value("${mock.server}")
    private  String prefixUrl;
    @Value("${mock.open-id}")
    private  String openId;

    /**
     * 获取令牌
     * @return
     */
    public String getTokenCache(Corp corp) {
        String tokenUrl = prefixUrl+"/api/v1/base/oauth/token";
        String tokenKey = CacheEnum.TOKEN.getCacheKey(corp.getSecretId());
        String oldToken = TimedCacheUtils.timedCache.get(tokenKey);
        if (oldToken == null) {
            Map<String, String> map = toEncrypt(corp);
            String newToken = HttpClientUtils.httpPost(tokenUrl, null, map).getJSONObject("map").getString("token");
            TimedCacheUtils.timedCache.put(tokenKey, newToken);
            return newToken;
        }
        return oldToken;
    }

    /**
     * 获取访问tempCode
     * @return
     */
    public String getTempCodeCache(Corp corp) {
        String TempCodeUrl = prefixUrl+"/api/v1/base/oauth/code";
        String tempCodeKey = CacheEnum.TEMPCODE.getCacheKey(corp.getSecretId());
        String oldTempCode = TimedCacheUtils.timedCache.get(tempCodeKey);
        if (oldTempCode == null) {
            String token = getTokenCache(corp);
            HashMap<String ,String > map = new HashMap<>();
            map.put("openId", openId);
            map.put("token", token);
            String newToken = HttpClientUtils.httpPost(TempCodeUrl, null, map).getString("obj");
            TimedCacheUtils.timedCache.put(tempCodeKey, newToken);
            return newToken;
        }
        return oldTempCode;
    }

    /**
     * 获取静默登录授权码
     * @param corp
     * @return
     */
    public String getAuthorizationCache(Corp corp) {
        String authorizationUrl = prefixUrl+"/api/v1/base/oauth/login";
        String authorizationKey = CacheEnum.AUTHORIATION.getCacheKey(corp.getSecretId());
        String oldAuthorization = TimedCacheUtils.timedCache.get(authorizationKey);
        if (oldAuthorization == null) {
            String tempCodeCache = getTempCodeCache(corp);
            HashMap<String, String> map = new HashMap<>();
            map.put("code",tempCodeCache);
            String newAuthorization = HttpClientUtils.httpPost(authorizationUrl, null, map).getString("obj");
            TimedCacheUtils.timedCache.put(authorizationKey,newAuthorization);
            return newAuthorization;
        }
        return oldAuthorization;
    }

    /**
     * 获取签名signature
     * @param corp
     * @return
     */
    public Map<String, String> toEncrypt(Corp corp) {
        Long timestamp = System.currentTimeMillis();
        List<String> paramList = Arrays.asList(corp.getSecretId(), "server", timestamp.toString(), corp.getSecretKey(), "SHA256");
        Collections.sort(paramList);
        String signatureStr = StringUtils.arrayToDelimitedString(paramList.toArray(), "");
        String signature = SHA256Util.getSHA256Str(signatureStr);
        Map<String, String> map = new HashMap();
        map.put("secretId", corp.getSecretId());
        map.put("type", "server");
        map.put("timestamp", timestamp.toString());
        map.put("signatureMethod","SHA256");
        map.put("signature", signature);
        return map;
    }


}
