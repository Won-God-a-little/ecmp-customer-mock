package cn.eqxiu.mock.infra.web;

import cn.eqxiu.mock.common.BaseController;
import cn.eqxiu.mock.domain.entity.Corp;
import cn.hutool.core.lang.hash.Hash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 定义页面
 * 页面使用Freemaker技术,以html为后缀
 *
 * @author will
 */
@Controller
@RequestMapping("/page")
public class PageController extends BaseController {

    @RequestMapping("/{pageName}.html")
    public String toPage(@PathVariable String pageName, Model model,HttpServletRequest request) {
        Map<String,String> paramMap = new HashMap<>();
        request.getParameterMap().keySet().stream().forEach( key -> paramMap.put(key,request.getParameter(key)));
        model.addAttribute("urlParam",paramMap);
        //平台型企业会携带，作品所属企业
        Corp corp = getCurrentCorp();
        if (corp!=null){
            model.addAttribute("corpName",corp.getName());
            model.addAttribute("logoUrl",corp.getLogoUrl());
        }else {
            model.addAttribute("corpName","某某公司");
            model.addAttribute("logoUrl",request.getContextPath()+"/static/logo.png");
        }
        model.addAttribute("ctx",request.getContextPath());
        return pageName;
    }
}
