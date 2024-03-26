package cn.eqxiu.mock.infra.web;

import cn.eqxiu.mock.common.AccessAuthorization;
import cn.eqxiu.mock.common.BaseController;
import cn.eqxiu.mock.common.Result;
import cn.eqxiu.mock.domain.entity.Corp;
import cn.eqxiu.mock.domain.entity.MockPage;
import cn.eqxiu.mock.domain.entity.MockPageInfo;
import cn.eqxiu.mock.infra.service.MockPageIntegrationService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  模拟页面集成效果
 *
 * @author will
 */
@RestController
@RequestMapping("/integration")
public class MockPageIntegrationController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MockPageIntegrationService mockPageIntegrationService;

    @Autowired
    AccessAuthorization authorization;

    /**
     * 返回模拟集成的页面
     *
     * @param key 用于关键词检索
     * @return
     */
    @GetMapping("/list")
    public Result list(String key){
        Corp corp = getCurrentCorp();
        return mockPageIntegrationService.getMockPageInfoList(key);
    }

    @GetMapping("/getAuthorization")
    public Result getAuthorizationCache(){
        Corp corp = getCurrentCorp();
        String authorizationCache = authorization.getAuthorizationCache(corp);
        logger.info("authorizationCache:{}",authorizationCache);
        return Result.ofSuccess();
    }

    @PostMapping("/showPage")
    public Result showPage(@RequestBody MockPage page) {
        Corp corp = getCurrentCorp();
        String wholeUrl = mockPageIntegrationService.showPage(page, corp);
        if (wholeUrl == null) {
            return Result.ofFail("请获取Authorization");
        }
        return Result.ofSuccess().setObj(wholeUrl);
    }
}
