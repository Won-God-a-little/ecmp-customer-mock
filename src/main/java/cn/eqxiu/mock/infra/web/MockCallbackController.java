package cn.eqxiu.mock.infra.web;

import cn.eqxiu.mock.common.BaseController;
import cn.eqxiu.mock.common.Result;
import cn.eqxiu.mock.domain.entity.Corp;
import cn.eqxiu.mock.domain.entity.LogInfo;
import cn.eqxiu.mock.infra.service.MockCallbackService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 *  模拟回调业务
 * @author will
 */
@RestController
@RequestMapping("/callback")
public class MockCallbackController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MockCallbackService mockCallbackService;

    /**
     * 返回支持的回调业务列表树
     *
     * @param key 用于关键词检索
     * @return
     */
    @GetMapping("/list")
    public Result list(String key){
        ArrayList<JSONObject> jsonObjects = mockCallbackService.selectCallbackList(key);
        logger.info("查到的列表信息{}",jsonObjects);
        return success().setObj(jsonObjects);
    }

    /**
     *  查询回调日志,只返回最新的10条
     * @param url
     * @return
     */
    @GetMapping("/log")
    public Result queryLog(@RequestParam(required = false) String url, String code) {
        if (code == null) {
            code = getCurrentCorp().getCode();
        }
        List<LogInfo> logInfos = mockCallbackService.selectLogList(url,code);
        return success().setObj(logInfos);
    }
}
