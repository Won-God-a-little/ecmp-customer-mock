package cn.eqxiu.mock.infra.service.mock;

import cn.eqxiu.mock.common.EqxiuCallCmd;
import cn.eqxiu.mock.common.Result;
import cn.eqxiu.mock.domain.entity.Corp;
import cn.eqxiu.mock.domain.entity.LogInfo;
import cn.eqxiu.mock.domain.repository.CallbackRepository;
import cn.eqxiu.mock.infra.util.DecryptUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class EventReceiveService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    CallbackRepository callbackRepository;

    public void saveDate(EqxiuCallCmd callCmd, Corp corp, String requestURI) {
        String timeStampStr = callCmd.getTimestamp();
        long timeStamp = Long.parseLong(timeStampStr);
        Date date = new Date();
        date.setTime(timeStamp);
        JSONArray paramArr = callCmd.getParamArr(corp);
        logger.info("receive event: {}", paramArr);
        LogInfo logInfo = new LogInfo();
        logInfo.setRequestId(UUID.randomUUID().toString().replace("-","").substring(0,12));
        logInfo.setCode(corp.getCode());
        logInfo.setRequestUrl(requestURI);
        logInfo.setRequestParam(paramArr.toString());
        logInfo.setReturnValue(Result.ofSuccess("成功接收消息").toString());
        logInfo.setRequestTime(date);
        callbackRepository.saveLogInfo(logInfo);

    }
}
