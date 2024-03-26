package cn.eqxiu.mock.infra.service.mock;

import cn.eqxiu.mock.common.Result;
import cn.eqxiu.mock.domain.entity.Creation;
import cn.eqxiu.mock.domain.entity.Visitor;
import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 根据业务执行自定义抽奖逻辑
 *
 * @author will
 */
@Service
public class LotteryMock {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final Cache<String,Integer> lotteryCache = CacheUtil.newTimedCache(1000 * 60 * 60 * 72);

    /**
     * 初始化系统积分
     */
    private final int initialPoint = 1002;

    /**
     * 单次抽奖消耗积分
     */
    private final int cast = 200;


    public LotteryResult lottery(Creation creation, Visitor visitor) {

        String userId = visitor.getOpenId() != null ? visitor.getOpenId() : visitor.getMobile();
        //模拟用户剩余积分
        Integer leftPoints = lotteryCache.get(userId) != null ? lotteryCache.get(userId) :initialPoint;
        Integer lottery = creation.getLottery();
        Integer drawNum = creation.getDrawNum();
        LotteryResult result = new LotteryResult(true,"200","抽奖完成");
        result.put("lottery",lottery);
        if (drawNum != null) {
            multiDraw(creation, userId, leftPoints,result);
        }
        if (drawNum == null) {
            singleDraw(userId, leftPoints, lottery, result);
        }
        logger.info("lotttery backInfo: {}", result);
        return result;
    }

    private void multiDraw(Creation creation, String userId, Integer leftPoints,LotteryResult result) {
        JSONArray lotteryList = new JSONArray();
        JSONObject oo1 = new JSONObject();
        oo1.put("isWin",0);
        lotteryList.add(oo1);
        for (int i = 1; i < creation.getDrawNum(); i++) {
            JSONObject oo = new JSONObject();
            oo.put("isWin",1);
            oo.put("prizeLevel",(int)(Math.random()*2));
            Map<String, String> dynData = new HashMap<>();
            dynData.put("rsWinNum",UUID.randomUUID().toString().replaceAll("-",""));
            oo.put("dynData",dynData);
            lotteryList.add(oo);
        }
        lotteryCache.put(userId, Math.max(leftPoints - cast * creation.getDrawNum(), 0));
        result.put("lotteryList", lotteryList);
    }

    private void singleDraw(String userId, Integer leftPoints, Integer lottery, LotteryResult result) {
        if (lottery == 4) {
            result.put("prizeLevel",(int)(Math.random()*3));
//            result.put("prizeLevel",prizeLevel);
//            result.put("prizeCode",prizeCode);
            Map<String, String> dynData = new HashMap<>();
            dynData.put("rsWinNum", UUID.randomUUID().toString().replaceAll("-",""));
            result.put("dynData",dynData);
            lotteryCache.put(userId,Math.max(leftPoints - cast, 0));
        }
        if (lottery == 2) {
            result.put("message", "非会员不可参与抽奖，请先注册!");
            result.put("url","https://www.eqxiu.com");
        }
        if (lottery == 1) {
            lotteryCache.put(userId, Math.max(leftPoints - cast, 0));
        }
    }

    public Result leftTimes(Visitor visitor) {
        String userId = visitor.getOpenId() != null ? visitor.getOpenId() : visitor.getMobile();
        //模拟用户剩余积分
        Integer leftPoints = lotteryCache.get(userId) != null ? lotteryCache.get(userId) :initialPoint;
        Result result = new Result(true,"200","查询成功");
        int count = leftPoints/cast;
        JSONObject obj = new JSONObject();
        obj.put("count",count);
        if (0 == count) {
            obj.put("message","您还不是会员，请先升级会员！");
            obj.put("url","https://www.eqxiu.cn");
        }
        result.put("obj",obj);
        logger.info("leftTimes backInfo: {}", result);
        return result;
    }

    public Result qryMemRightPoint(Visitor visitor) {
        String userId = visitor.getOpenId() != null ? visitor.getOpenId() : visitor.getMobile();
        //模拟用户剩余积分
        Integer leftPoints = lotteryCache.get(userId) != null ? lotteryCache.get(userId) :initialPoint;
        Result result = new Result(true,"200","查询成功");
        JSONObject obj = new JSONObject();
        obj.put("benefitShowDesc","当前剩余" + leftPoints + "积分，<br/>可抽" + leftPoints/cast + "次");
        result.put("obj",obj);
        logger.info("qryMemRightPoint backInfo: {}", result);
        return result;
    }
}
