package cn.eqxiu.mock.infra.web.callback;

import cn.eqxiu.mock.common.BaseController;
import cn.eqxiu.mock.common.EqxiuCallCmd;
import cn.eqxiu.mock.common.Result;
import cn.eqxiu.mock.domain.entity.Corp;
import cn.eqxiu.mock.infra.service.MockCorpService;
import cn.eqxiu.mock.infra.service.mock.EventReceiveService;
import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 易企秀推送事件接收服务
 *
 * @author will
 */
@RestController
@RequestMapping("/event")
public class EventReceiveController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    EventReceiveService eventReceiveService;

    @Autowired
    MockCorpService mockCorpService;

    @PostMapping("/receive")
    public Result receiveEvent(@RequestBody EqxiuCallCmd callCmd, @RequestParam(value="cname") String cname, HttpServletRequest request) {
        String error = callCmd.validate();
        if (error != null) {
            return Result.ofFail(error);
        }
        String requestURI = request.getRequestURI();
        StringBuilder sb = new StringBuilder(requestURI);
        sb.append("?cname=").append(cname);
        Corp corpByCode = mockCorpService.findByCode(cname);
        eventReceiveService.saveDate(callCmd, corpByCode, sb.toString());
        return Result.ofSuccess("成功接收消息");
    }
}
