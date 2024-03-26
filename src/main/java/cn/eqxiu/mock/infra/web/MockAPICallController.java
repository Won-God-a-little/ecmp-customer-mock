package cn.eqxiu.mock.infra.web;

import cn.eqxiu.mock.common.BaseController;
import cn.eqxiu.mock.common.Result;
import cn.eqxiu.mock.domain.entity.Corp;
import cn.eqxiu.mock.domain.entity.OpenInfo;
import cn.eqxiu.mock.infra.service.MockAPICallService;
import cn.eqxiu.mock.infra.util.HttpClientUtils;
import cn.eqxiu.mock.infra.util.SHA256Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 模拟接口调用
 *
 * @author will
 */
@RestController
@RequestMapping("/api")
public class MockAPICallController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MockAPICallService mockAPICallService;

    /**
     * 返回模拟调用的调用接口列表
     *
     * @param key 用于关键词检索列表
     * @return
     */
    @GetMapping("/list")
    public Result list(@RequestParam(required = false) String key) {
        logger.info("收到请求参数, data:{}", key);
        List<Map<String, Object>>list = mockAPICallService.getAllInterface(key);
        return Result.ofSuccess().setObj(list);
    }

    /**
     * 获取某一接口的参数信息
     *
     * @param id 用于关键词检索
     * @return
     */
    @GetMapping("/interfaceInfo")
    public Result info(@RequestParam Long id) {
        OpenInfo info = mockAPICallService.getInterfaceById(id);
        return Result.ofSuccess().setObj(info);
    }

    /**
     * 刷新参数
     * @param info 需要用到id和requestParams
     * @return
     */
    @GetMapping("/token")
    public Result token(@RequestBody OpenInfo info) {
        if (getCurrentCorp() == null) {
            return Result.ofFail("请先登录企业");
        }
        Corp corp = getCurrentCorp();
        List<cn.eqxiu.mock.domain.entity.RequestParam> requestParams = mockAPICallService.refreshParam(info, corp);
        logger.info("刷新得到的参数列表,{}", JSON.toJSONString(requestParams));
        return Result.ofSuccess().setObj(requestParams);
    }

    /**
     * 模拟调用
     * @param info
     * @return
     */
    @PostMapping("/mockCall")
    public Result mockCall(@RequestBody OpenInfo info) {
        Corp corp = getCurrentCorp();
        return mockAPICallService.mockCall(info, corp);
    }
}
