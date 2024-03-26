package cn.eqxiu.mock.infra.web;

import cn.eqxiu.mock.common.BaseController;
import cn.eqxiu.mock.common.Result;
import cn.eqxiu.mock.domain.entity.Corp;
import cn.eqxiu.mock.infra.service.MockCorpService;
import cn.eqxiu.mock.infra.util.JWTUtils2;
import cn.eqxiu.mock.infra.util.JwtUtils;
import cn.hutool.jwt.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.UUID;

/**
 *  模拟企业数据
 * @author will
 */
@RestController
@RequestMapping("/corp")
public class MockCorpController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MockCorpService mockCorpService;

    /**
     * 根据企业代号获取企业信息
     * @param code 企业代号
     * @return 企业数据
     */
    @GetMapping("/info")
    public Result getMockCorpData(@RequestParam("code") String code, HttpSession session){
        Corp corpByCode = mockCorpService.findByCode(code);
        if (corpByCode == null) {
            return Result.ofFail("未查询到企业");
        }
        setCurrentCorp(corpByCode);
        HashMap<String, String > map = new HashMap<>();
        map.put("code",code);
        map.put("secretId",corpByCode.getSecretId());
        //String authorization = JwtUtils.generateJwt(map);
        //CorpThreadLocal.set(corpByCode);
        String authorization = JWTUtils2.getToken(map);
        logger.info("存放到緩存的企業{}",getCurrentCorp());
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("corp",corpByCode);
        map2.put("authorization",authorization);
        return success().setObj(map2);
    }

    /**
     * 创建企业
     * @param corp
     * @return
     */
    @PostMapping("/create")
    public Result createMockCorp(@RequestBody Corp corp){
        logger.info("传入的企业信息：{}",corp);
        Corp corpById = mockCorpService.findByCode(corp.getCode());
        if (corpById != null){
            return Result.ofFail("该企业已创建，请直接登录");
        }
        mockCorpService.createCorp(corp);
        return success();
    }

    /**
     * 修改企业信息
     * @param corp
     * @return
     */
    @PostMapping("/save")
    public Result saveMockCorp(@RequestBody Corp corp){
        logger.info("要修改的企业信息：{}",corp);
        Corp corpById = mockCorpService.findByCode(corp.getCode());
        if (corpById == null){
            return Result.ofFail("未找到企业，请先创建企业");
        }
        mockCorpService.updateCorp(corp);
        return success();
    }
}
