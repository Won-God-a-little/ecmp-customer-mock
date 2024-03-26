package cn.eqxiu.mock.infra.service;

import cn.eqxiu.mock.common.Result;
import cn.eqxiu.mock.domain.entity.BatchExcuteInfo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BatchService {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${mock.batchUrl}")
    private String batchUrl;
    public Result excute(BatchExcuteInfo batchExcuteInfo) {
        HttpEntity<BatchExcuteInfo> entity = new HttpEntity<>(batchExcuteInfo);
        ResponseEntity<JSONObject> exchange = restTemplate.exchange(batchUrl, HttpMethod.POST, entity, JSONObject.class);
        if (200 == exchange.getStatusCodeValue() && 200 == exchange.getBody().getInteger("code")) {
            return Result.ofSuccess("操作成功，请注意机器人推送执行结果");
        }
        return Result.ofFail(exchange.getBody().getString("msg"));
    }
}
