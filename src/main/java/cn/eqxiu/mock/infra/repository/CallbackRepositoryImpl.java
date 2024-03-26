package cn.eqxiu.mock.infra.repository;

import cn.eqxiu.mock.domain.entity.CallbackUrl;
import cn.eqxiu.mock.domain.entity.LogInfo;
import cn.eqxiu.mock.domain.repository.CallbackRepository;
import cn.eqxiu.mock.infra.persistence.mapper.CallbackMapper;
import cn.eqxiu.mock.infra.web.vo.CallbackUrlListVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CallbackRepositoryImpl implements CallbackRepository {

    private static final Logger logger = LoggerFactory.getLogger(CallbackRepositoryImpl.class);

    @Resource
    private CallbackMapper CallbackMapper;

    @Override
    public List<LogInfo> selectLogList(String url, String code) {
        return CallbackMapper.selectLogList(url, code);
    }

    @Override
    public List<CallbackUrlListVO> selectCallbackList(String key) {
        List<CallbackUrlListVO> callbackUrlListVOS = CallbackMapper.selectCallbackList(key);
        if (callbackUrlListVOS == null || callbackUrlListVOS.isEmpty()) {
            return CallbackMapper.selectCallbackList("");
        }
        return callbackUrlListVOS;
    }

    @Override
    public void saveLogInfo(LogInfo logInfo) {
        CallbackMapper.saveLogInfo(logInfo);
    }
}
