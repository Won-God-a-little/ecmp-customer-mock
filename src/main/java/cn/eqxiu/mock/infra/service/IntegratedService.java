package cn.eqxiu.mock.infra.service;


import cn.eqxiu.mock.common.Result;
import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class IntegratedService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final Cache<String,Object> taskCache = CacheUtil.newTimedCache(1000 * 60 * 60);

    public Result autoFilling(JSONObject otherParam) {
        Result result = new Result(true,"200","查询完成");
        Map<String,Object> data = new HashMap<>();
        data.put("bindCreationId",otherParam.getString("bindCreationId"));
        if (StringUtils.isEmpty(otherParam.getString("extend"))) {
            JSONArray prizesArr = new JSONArray();
            for (int i = 0; i < otherParam.getInteger("prizeTabNum"); i++) {
                JSONObject prize = new JSONObject();
                prize.put("prizeLevel",i);
                prize.put("prizeLevelName",(i+1)+"等奖");
                prize.put("prizeName","奖品序号" + i);
                prize.put("prizeCode","abc"+i);
                prize.put("prizeDesc","这个奖品真棒");
                prize.put("prizeImg","https://asset.eqxiu.cn/common/icon-wechat.png");
                prize.put("redemptionAddr","http://www.baidu.com");
                prize.put("prizeNum",otherParam.getInteger("prizeNum"));
                prizesArr.add(prize);
            }
            JSONArray gamePrizesArr = new JSONArray();
            JSONObject scorePrize = new JSONObject();
            scorePrize.put("prizeType",1);
            scorePrize.put("open",1);
            JSONArray gamePrizes = new JSONArray();
            for (int i = 0; i < otherParam.getInteger("prizeTabNum"); i++) {
                JSONObject prize = new JSONObject();
                prize.put("prizeLevel",i);
                prize.put("prizeLevelName",(i+1)+"等奖");
                prize.put("prizeName","奖品序号" + i);
                prize.put("prizeCode","abc"+i);
                prize.put("prizeDesc","这个奖品真棒");
                prize.put("prizeImg","https://asset.eqxiu.cn/common/icon-wechat.png");
                prize.put("redemptionAddr","http://www.baidu.com");
                prize.put("prizeNum",otherParam.getInteger("prizeNum"));
                prize.put("score",i+1);
                gamePrizes.add(prize);
            }
            scorePrize.put("prizes",gamePrizes);
            gamePrizesArr.add(scorePrize);

            JSONObject rankPrize = new JSONObject();
            rankPrize.put("prizeType",2);
            rankPrize.put("open",1);
            JSONArray rankPrizes = new JSONArray();
            JSONObject prize = new JSONObject();
            prize.put("prizeLevel",0);
            prize.put("prizeLevelName","1等奖");
            prize.put("prizeName","奖品序号1" );
            prize.put("prizeCode","abc");
            prize.put("prizeDesc","这个奖品真棒");
            prize.put("prizeImg","https://asset.eqxiu.cn/common/icon-wechat.png");
            prize.put("redemptionAddr","http://www.baidu.com");
            prize.put("prizeNum",otherParam.getInteger("prizeNum"));
            prize.put("rankStart",1);
            prize.put("rankEnd",3);
            rankPrizes.add(prize);
            rankPrize.put("prizes",rankPrizes);
            gamePrizesArr.add(rankPrize);

            String str = "{\"activityName\": \""+otherParam.getString("activityName")+"\",\"startTime\": \"2022-11-18 15:44:17\",\"endTime\": \"2026-11-18 15:44:17\",\"loginPosition\": 1,\"myPrizeType\": 1}";
            JSONObject object = JSONObject.parseObject(str);
            object.put("prizes",prizesArr);
            object.put("gamePrizeSetting",gamePrizesArr);
            object.put("winNum",otherParam.getInteger("winNum"));
            object.put("myPrizeType",otherParam.getString("myPrizeType"));
            object.put("myPrizeLink",otherParam.getString("myPrizeLink"));
            object.put("activityRules",otherParam.getInteger("activityRules"));
            object.put("activityRulesLink",otherParam.getString("activityRulesLink"));
            data.put("hdSetting",object);
        }else {
            JSONObject data2 = JSONObject.parseObject(otherParam.getString("extend"));
            data.put("hdSetting",data2);
        }
        result.put("data",data);
        logger.info("autoFilling backInfo: {}", result);
        return result;
    }

    public Result SendRedPacket(JSONObject paraObj) {
        return new Result(true,"200","发放成功");
    }

    public Result getPushTaskNum(JSONObject paraObj) {
        JSONObject obj = new JSONObject();
        JSONObject data = new JSONObject();
        int taskNum =  taskCache.get(paraObj.getString("thirdUserId"))== null ? 0 : (int)taskCache.get(paraObj.getString("thirdUserId"));
        data.put("repostNum",taskNum);
        taskCache.put(paraObj.getString("thirdUserId"),taskNum+1);
        obj.put("data",data);
        Result result = Result.ofSuccess();
        result.put("obj",obj);
        return result;
    }

    public Result getPushTask(JSONObject paraObj) {
        JSONObject obj = new JSONObject();
        int taskNum =  taskCache.get(paraObj.getString("thirdUserId"))== null ? 0 : (int)taskCache.get(paraObj.getString("thirdUserId"));
        obj.put("data",taskNum);
        taskCache.put(paraObj.getString("thirdUserId"),taskNum+1);
        Result result = Result.ofSuccess();
        result.put("obj",obj);
        return result;
    }

    public Result getOuterData(JSONObject paraObj) {
        Result result = Result.ofSuccess();
        String distributeIds = (String)taskCache.get("distributeId");
        if (distributeIds == null) {
            return result;
        }
        String[] distributeLinkIds = distributeIds.split(",");
        JSONArray arr = new JSONArray();
        for (int i = 0; i < distributeLinkIds.length; i++) {
            JSONObject object = new JSONObject();
            object.put("distributeId",paraObj.getString("distributeId"));
            object.put("distributeLinkId",distributeLinkIds[i]);
            JSONObject data = new JSONObject();
            data.put("accountNum",i+16);
            data.put("wechatNum",i+8);
            object.put("data",data);
            arr.add(object);
        }
        result.put("list",arr);
        return result;
    }

    public Result putCache(JSONObject param) {
        for (Map.Entry<String, Object> entry:param.entrySet()) {
            taskCache.put(entry.getKey(),entry.getValue());
        }
        return Result.ofSuccess();
    }
}
