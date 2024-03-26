package cn.eqxiu.mock.common;

import cn.eqxiu.mock.domain.entity.Corp;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 通用方法封装
 *
 * @author will
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 用于标识当前模拟企业的企业代码
     */
    public final static String REQUEST_PARAM_CNAME = "stz";

    /**
     * 用于传递当前模拟企业信息的属性名
     */
    public final static String REQUEST_ATTR_CORP = "current_corp";

    public TimedCache<String, String> timedCache = CacheUtil.newTimedCache(1000 * 60 * 5);

    protected Result success(){
        return Result.ofSuccess();
    }

    public Result fail(String msg){
        return Result.ofFail(msg);
    }

    protected Corp getCorpSecret(){
        Corp currentCorp = getCurrentCorp();
        if (currentCorp == null){
            throw new RuntimeException("当前模拟企业未配置密钥");
        }
        return currentCorp;
    }

    protected void setCurrentCorp(Corp corp) {
        getCurrentRequest().getSession().setAttribute(REQUEST_ATTR_CORP, corp);
    }

    protected Corp getCurrentCorp(){
        return (Corp) getCurrentRequest().getSession().getAttribute(REQUEST_ATTR_CORP);
    }

    protected HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }
}
