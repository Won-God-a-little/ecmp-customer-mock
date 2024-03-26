package cn.eqxiu.mock.infra.web.callback;

import cn.eqxiu.mock.common.BaseController;
import cn.eqxiu.mock.common.Result;
import cn.eqxiu.mock.domain.entity.Visitor;
import cn.eqxiu.mock.infra.service.mock.VisitorMock;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访客体系集成
 * 自有用户系统集成
 *
 * @author will
 */
@RestController
@RequestMapping("/visitor")
public class VisitorIntegrationController extends BaseController {

    private VisitorMock visitorMock;

    public VisitorIntegrationController(VisitorMock visitorMock) {
        this.visitorMock = visitorMock;
    }

    @PostMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName =  request.getParameter("userName");
        String password =  request.getParameter("password");
        String redirectUrl =  request.getParameter("redirectUri");
        //参数兼容
        if (StrUtil.isEmpty(redirectUrl)){
            redirectUrl = request.getParameter("redirectUrl");
        }
        String channelKey = request.getParameter("channelKey");
        // 登录逻辑
        Visitor visitor = visitorMock.login(userName,password,channelKey);
        // 生成用户信息临时授权码
        String code = visitorMock.genVisitorAuthCode(visitor.getNickName());
        // 重定向到作品页面
        response.sendRedirect(redirectUrl + "&code=" + code);
    }

    /**
     * 获取访客用户信息
     * @param request
     * @return
     */
    @GetMapping("/info")
    public Result getUserInfo(String code, HttpServletRequest request){
        if (StrUtil.isEmpty(code)){
            return fail("code is empty");
        }
        Visitor visitor = visitorMock.getVisitorByCode(code);
        if (visitor == null){
            return fail("code is invalid");
        }
        return success().setObj(visitor);
    }

    @GetMapping("/randomInfo")
    public Result getRandomInfo(String code){
        Visitor visitor = visitorMock.getVisitorByRandomCode(code);
        return success().setObj(visitor);
    }

    /**
     * 获取访客用户信息失败
     * @return
     */
    @GetMapping("/infoError")
    public Result getUserInfoError(){
        return fail("获取访客信息失败！");
    }
}
