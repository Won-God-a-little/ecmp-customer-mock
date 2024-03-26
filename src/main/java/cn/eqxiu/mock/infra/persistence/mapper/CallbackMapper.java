package cn.eqxiu.mock.infra.persistence.mapper;

import cn.eqxiu.common.mapper.BaseMapper;
import cn.eqxiu.mock.domain.entity.CallbackUrl;
import cn.eqxiu.mock.domain.entity.LogInfo;
import cn.eqxiu.mock.infra.web.vo.CallbackUrlListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CallbackMapper extends BaseMapper<LogInfo,String > {

    List<LogInfo> selectLogList(@Param("url") String url, @Param("code") String id);

    List<CallbackUrlListVO> selectCallbackList(String key);

    void saveLogInfo(LogInfo logInfo);
}
