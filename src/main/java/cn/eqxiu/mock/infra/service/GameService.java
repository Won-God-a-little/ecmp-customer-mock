package cn.eqxiu.mock.infra.service;


import cn.eqxiu.mock.common.Result;
import cn.eqxiu.mock.domain.entity.Creation;
import cn.eqxiu.mock.domain.entity.Visitor;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public Result play(Creation creation, Visitor visitor) {
        Integer gameScore = creation.getGameScore();
        if (gameScore == null) {
            return Result.ofFail("没有分数");
        }
        Result result = new Result(true,"200","操作成功");
        if (gameScore < 20) {
            result.put("isWin",0);
        }
        result.put("prizeCode","123456");
        result.put("prizeName","分数奖励");
        JSONObject object = new JSONObject();
        object.put("winNum","123456789987654321");
        result.put("dynData",object);
        if (gameScore >= 20 && gameScore < 30) {
            result.put("isWin",1);
            result.put("prizeLevel",2);
        }
        if (gameScore >= 30 && gameScore < 40) {
            result.put("isWin",1);
            result.put("prizeLevel",1);
        }
        if (gameScore >= 40) {
            result.put("isWin",1);
            result.put("prizeLevel",0);
        }
        return result;
    }
}
