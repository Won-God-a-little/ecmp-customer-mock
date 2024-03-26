package cn.eqxiu.mock.infra.web;

import cn.eqxiu.mock.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author will
 */
@Controller
public class IndexController extends BaseController {

    @RequestMapping({"/","/index.html"})
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("ctx",request.getContextPath());
         return "index";
     }
}
