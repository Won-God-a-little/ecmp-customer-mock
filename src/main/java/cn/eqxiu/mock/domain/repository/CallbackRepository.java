package cn.eqxiu.mock.domain.repository;

import cn.eqxiu.mock.domain.entity.CallbackUrl;
import cn.eqxiu.mock.domain.entity.LogInfo;
import cn.eqxiu.mock.infra.web.vo.CallbackUrlListVO;

import java.util.List;

public interface CallbackRepository {

    List<LogInfo> selectLogList(String url, String id);

    List<CallbackUrlListVO> selectCallbackList(String key);

    void saveLogInfo(LogInfo logInfo);
}
