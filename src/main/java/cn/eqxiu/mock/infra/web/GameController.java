package cn.eqxiu.mock.infra.web;

import cn.eqxiu.mock.common.BaseController;
import cn.eqxiu.mock.common.EqxiuCallCmd;
import cn.eqxiu.mock.common.Result;
import cn.eqxiu.mock.domain.entity.Creation;
import cn.eqxiu.mock.domain.entity.Visitor;
import cn.eqxiu.mock.infra.service.GameService;
import cn.eqxiu.mock.infra.util.ParseParamUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController extends BaseController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/play")
    public Result play(@RequestBody EqxiuCallCmd cmd) {
        //1. 参数校验
        String error = cmd.validate();
        if (error !=null){
            return fail(error);
        }
        //2. 解密参数
        JSONObject paraObj = cmd.getParamObj();
        logger.info("game info: {}", paraObj);
        Creation creation = ParseParamUtil.parseCreation(paraObj);
        Visitor visitor = ParseParamUtil.parseVisitor(paraObj);
        //4. 抽奖
        return gameService.play(creation,visitor);
    }

}
