package cn.eqxiu.mock.infra.web.callback;

import cn.eqxiu.mock.common.BaseController;
import cn.eqxiu.mock.common.EqxiuCallCmd;
import cn.eqxiu.mock.common.Result;
import cn.eqxiu.mock.domain.entity.Creation;
import cn.eqxiu.mock.domain.entity.Visitor;
import cn.eqxiu.mock.infra.service.LotteryService;
import cn.eqxiu.mock.infra.util.ParseParamUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义活动逻辑
 * 如自定义抽奖等
 * @author will
 */
@RestController
@RequestMapping("/lottery")
public class LotteryController extends BaseController {

    private LotteryService lotteryService;

    public LotteryController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    /**
     * 抽奖
     * @param cmd
     * @return
     */
    @PostMapping("/draw")
    public Result draw(@RequestBody EqxiuCallCmd cmd) {
        //1. 参数校验
        String error = cmd.validate();
        if (error !=null){
            return fail(error);
        }
        //2. 解密参数
        JSONObject paraObj = cmd.getParamObj();
        logger.info("lottery info: {}", paraObj);
        Creation creation = ParseParamUtil.parseCreation(paraObj);
        Visitor visitor = ParseParamUtil.parseVisitor(paraObj);
        //4. 抽奖
        return lotteryService.lottery(creation,visitor);
    }

    /**
     * 剩余次数查询
     * @param cmd
     * @return
     */
    @PostMapping("/leftTimes")
    public Result leftTimes(@RequestBody EqxiuCallCmd cmd) {
        //1. 参数校验
        String error = cmd.validate();
        if (error !=null){
            return fail(error);
        }
        //2. 解密参数
        JSONObject paraObj = cmd.getParamObj();
        logger.info("lotteryTimes info: {}", paraObj);
        Visitor visitor = ParseParamUtil.parseVisitor(paraObj);
        //3. 查询剩余次数
        return lotteryService.leftTimes(visitor);
    }

    /**
     * 用户权益查询
     * @return
     */
    @PostMapping("/qryMemRightPoint")
    public Result qryMemRightPoint(@RequestBody EqxiuCallCmd cmd) {
        //1. 参数校验
        String error = cmd.validate();
        if (error !=null){
            return fail(error);
        }
        //2. 解密参数
        JSONObject paraObj = cmd.getParamObj();
        Visitor visitor = ParseParamUtil.parseVisitor(paraObj);
        logger.info("qryMemRightPoint info: {}", paraObj);
        //3. 查询用户权益
        return lotteryService.qryMemRightPoint(visitor);
    }



}
