package cn.eqxiu.mock.infra.web.callback;


import cn.eqxiu.mock.common.BaseController;
import cn.eqxiu.mock.common.EqxiuCallCmd;
import cn.eqxiu.mock.common.Result;
import cn.eqxiu.mock.infra.service.mock.SMSManagementService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/SmsManagement")
public class SMSManagementController extends BaseController {
    private SMSManagementService smsManagementService;

    public SMSManagementController(SMSManagementService smsManagementService) {
        this.smsManagementService = smsManagementService;
    }

    //    模拟获取短信验证码
    @PostMapping("/sendSMS")
    @CrossOrigin
    public Result sendSMS(@RequestBody EqxiuCallCmd cmd){
        //1. 参数校验
        String error = cmd.validate();
        if (error !=null){
            return fail(error);
        }
        //2. 解密参数
        JSONObject paraObj = cmd.getParamObj();
        logger.info("sendSMS info: {}", paraObj);
        return Result.ofSuccess();
    }

    //    模拟获取短信验证码异常
    @PostMapping("/sendSMSError")
    @CrossOrigin
    public Result sendSMSError(@RequestBody EqxiuCallCmd cmd){
        //1. 参数校验
        String error = cmd.validate();
        if (error !=null){
            return fail(error);
        }
        //2. 解密参数
        JSONObject paraObj = cmd.getParamObj();
        logger.info("sendSMS info: {}", paraObj);
        return Result.ofFail("500","获取短信验证码失败！");
    }

    //   获取图形验证码
    @PostMapping("/captcha")
    @CrossOrigin
    public Result captcha(@RequestBody EqxiuCallCmd cmd){
        //1. 参数校验
        String error = cmd.validate();
        if (error !=null){
            return fail(error);
        }
        //2. 解密参数
        JSONObject paraObj = cmd.getParamObj();
        logger.info("captcha info: {}", paraObj);
        return smsManagementService.getCaptcha();
    }

    //   获取图形验证码
    @PostMapping("/msgValid")
    @CrossOrigin
    public Result msgValid(@RequestBody EqxiuCallCmd cmd){
        //1. 参数校验
        String error = cmd.validate();
        if (error !=null){
            return fail(error);
        }
        //2. 解密参数
        JSONObject paraObj = cmd.getParamObj();
        logger.info("msgValid info: {}", paraObj);
        return smsManagementService.Valid(paraObj);
    }




}
