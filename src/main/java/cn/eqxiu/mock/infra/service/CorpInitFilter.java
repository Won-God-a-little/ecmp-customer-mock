package cn.eqxiu.mock.infra.service;

import cn.eqxiu.mock.common.Result;
import cn.eqxiu.mock.infra.util.JWTUtils2;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author will
 */
@Component
public class CorpInitFilter implements Filter {

    private MockCorpService mockCorpService;

    public CorpInitFilter(MockCorpService mockCorpService) {
        this.mockCorpService = mockCorpService;
    }

    protected Logger logger = LoggerFactory.getLogger(getClass());

    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        String authorization = request.getHeader("authorization");

        logger.info("requestURI:{}", requestURI);

        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/",
                "/corp/info",
                "/api/list",
                "/integration/list",
                "/callback/list",
                "/callback/log",
                "/event/receive"
        };
        if (requestURI.startsWith("/static/")  || check(urls, requestURI)) {

            chain.doFilter(request,response);
            return;
        }

         // 登录页面直接放行
        if (StringUtils.isNotBlank(requestURI) ) {
            // 令牌不存在
            if (StringUtils.isBlank(authorization)) {
                Result result = Result.ofFail("请先创建或登录模拟企业");
                String not_login = JSONObject.toJSONString(result);
                logger.info("报错信息：{}",not_login);
                response.setCharacterEncoding("utf-8");
                response.getWriter().write(not_login);
                return;
            }

            // 令牌解析错误

            try {
                //JwtUtils.parseJWT(authorization);
                JWTUtils2.verify(authorization);
            } catch (Exception e) {
                e.printStackTrace();
                Result result = Result.ofFail("令牌解析错误");
                String not_login = JSONObject.toJSONString(result);
                response.setCharacterEncoding("utf-8");
                response.getWriter().write(not_login);
                return;
            }
            chain.doFilter(request,response);
            return;
        }
        chain.doFilter(request,response);

    }
    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
