package cn.eqxiu.mock.infra.web;

import cn.eqxiu.mock.common.Result;
import cn.eqxiu.mock.domain.entity.BatchExcuteInfo;
import cn.eqxiu.mock.infra.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class BatchExcutionController {

    @Autowired
    private BatchService batchService;

    @PostMapping("/excute")
    public Result excute(@RequestBody BatchExcuteInfo batchExcuteInfo) {
        if (StringUtils.isEmpty(batchExcuteInfo.getEnv())) {
            return Result.ofFail("请输入运行环境信息");
        }
        return batchService.excute(batchExcuteInfo);
    }
}
