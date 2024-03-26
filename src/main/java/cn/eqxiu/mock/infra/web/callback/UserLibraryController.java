package cn.eqxiu.mock.infra.web.callback;


import cn.eqxiu.mock.common.BaseController;
import cn.eqxiu.mock.common.EqxiuCallCmd;
import cn.eqxiu.mock.common.Result;
import cn.eqxiu.mock.infra.service.mock.PrizeLibraryMock;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于模拟用户的自定义库
 */
@RestController
@RequestMapping("/userLibrary")
public class UserLibraryController extends BaseController {
    private PrizeLibraryMock prizeLibraryMock;

    public UserLibraryController(PrizeLibraryMock prizeLibraryMock) {
        this.prizeLibraryMock = prizeLibraryMock;
    }

//    模拟用户奖品库
    @PostMapping("/prizesInfo")
    public Result prizesInfo(@RequestBody EqxiuCallCmd cmd) {
        //1. 参数校验
        String error = cmd.validate();
        if (error !=null){
            return fail(error);
        }
        //2. 解密参数
        JSONObject paraObj = cmd.getParamObj();
        logger.info("prizesInfo: {}", paraObj);
        return prizeLibraryMock.getPrizesInfo(paraObj);
    }

    //    模拟用户奖品库接收接口
    @PostMapping("/receive")
    public Result receive(@RequestBody EqxiuCallCmd cmd) {
        //1. 参数校验
        String error = cmd.validate();
        if (error !=null){
            return fail(error);
        }
        //2. 解密参数
        JSONObject paraObj = cmd.getParamObj();
        logger.info("receive: {}", paraObj);
        return Result.ofSuccess();
    }



    //    模拟获取用户方的系统邮寄地址库
    @PostMapping("/address")
    public Result queryAddress(@RequestBody EqxiuCallCmd cmd) {
        //1. 参数校验
        String error = cmd.validate();
        if (error !=null){
            return fail(error);
        }
        //2. 解密参数
        JSONObject paraObj = cmd.getParamObj();
        logger.info("address info: {}", paraObj);
        return prizeLibraryMock.getAddressList(paraObj.getString("userId"));
    }

}
