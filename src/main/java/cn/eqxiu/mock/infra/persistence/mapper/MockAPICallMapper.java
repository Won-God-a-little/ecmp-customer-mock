package cn.eqxiu.mock.infra.persistence.mapper;

import cn.eqxiu.common.mapper.BaseMapper;
import cn.eqxiu.mock.domain.entity.OpenInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MockAPICallMapper extends BaseMapper<OpenInfo, String> {
    List<Map<String, Object>> getAllInterface(@Param("key") String key);

    OpenInfo getInterfaceById(Long id);

}
