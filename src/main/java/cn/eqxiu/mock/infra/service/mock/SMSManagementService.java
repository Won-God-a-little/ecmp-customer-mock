package cn.eqxiu.mock.infra.service.mock;

import cn.eqxiu.mock.common.Result;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class SMSManagementService {
    public Result getCaptcha() {
        String base64Img = "data:image/png;base64,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";
        JSONObject captcha = new JSONObject();
        captcha.put("base64Img",base64Img);
        Result result = new Result(true,"200","查询成功");
        result.put("obj",captcha);
        return result;
    }

    public Result Valid(JSONObject paraObj) {
        if (paraObj.getJSONObject("args").getString("code").equals("123456")) {
            return Result.ofSuccess();
        }
        return Result.ofFail("300","验证码错误");
    }
}
