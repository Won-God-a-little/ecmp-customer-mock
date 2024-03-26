package cn.eqxiu.mock.infra.web;


import cn.eqxiu.mock.common.BaseController;
import cn.eqxiu.mock.common.EqxiuCallCmd;
import cn.eqxiu.mock.common.Result;
import cn.eqxiu.mock.infra.service.IntegratedService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * 快速模拟用户对接接口
 */
@RestController
@RequestMapping("/quickMock")
public class IntegratedController extends BaseController {

    private IntegratedService integratedService;

    public IntegratedController(IntegratedService integratedService) {
        this.integratedService = integratedService;
    }

    //    预先放入缓存
    @PostMapping("/putCache")
    public Result autoFilling(@RequestBody JSONObject param) {
        return integratedService.putCache(param);
    }


//    互动活动数据导入
    @PostMapping("/autoFilling")
    public Result autoFilling(@RequestBody EqxiuCallCmd cmd) {
        //1. 参数校验
        String error = cmd.validate();
        if (error !=null){
            return fail(error);
        }
        //2. 解密参数
        JSONObject paraObj = cmd.getParamObj();
        logger.info("autoFilling info: {}", paraObj);
        return integratedService.autoFilling(paraObj.getJSONObject("otherParam"));
    }

    //    发放红包
    @PostMapping("/redPacket")
    public Result redPacket(@RequestBody EqxiuCallCmd cmd) {
        //1. 参数校验
        String error = cmd.validate();
        if (error !=null){
            return fail(error);
        }
        //2. 解密参数
        JSONObject paraObj = cmd.getParamObj();
        logger.info("paraObj info: {}", paraObj);
        return integratedService.SendRedPacket(paraObj);
    }

    //    自定义表单提交数据接口
    @PostMapping("/submitData")
    public Result submitData(@RequestBody JSONObject param) {
        logger.info("receive submitData: {}", param);
        return Result.ofSuccess();
    }


    //    外部统计数据接入
    @PostMapping("/pullOuterData")
    public Result pullOuterData(@RequestBody EqxiuCallCmd cmd) {
        String error = cmd.validate();
        if (error !=null){
            return fail(error);
        }
        //2. 解密参数
        JSONObject paraObj = cmd.getParamObj();
        logger.info("receive pullOuterData data: {}", paraObj);
        return integratedService.getOuterData(paraObj);
    }

    //    数值任务进度拉取
    @PostMapping("/pushTaskNum")
    public Result pushTaskNum(@RequestBody EqxiuCallCmd cmd) {
        String error = cmd.validate();
        if (error !=null){
            return fail(error);
        }
        //2. 解密参数
        JSONObject paraObj = cmd.getParamObj();
        logger.info("receive pushTaskNum data: {}", paraObj);
        return integratedService.getPushTaskNum(paraObj);
    }

    //    任务进度拉取
    @PostMapping("/pushTask")
    public Result pushTask(@RequestBody EqxiuCallCmd cmd) {
        String error = cmd.validate();
        if (error !=null){
            return fail(error);
        }
        //2. 解密参数
        JSONObject paraObj = cmd.getParamObj();
        logger.info("receive pushTask data: {}", paraObj);
        return integratedService.getPushTask(paraObj);
    }



//    动态数填充
    @PostMapping("/dynData")
    public Map<String,Object> test1(@RequestBody  JSONObject param){
        Map<String,Object> result = new HashMap<>();
        result.putAll(param);
        if(result.containsKey("encodeId")){
            result.put("myEn",result.get("encodeId").toString()+"_hsy");
        }
        return result;
    }
}
