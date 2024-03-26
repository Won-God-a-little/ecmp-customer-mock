package cn.eqxiu.mock.domain.repository;

import cn.eqxiu.mock.domain.entity.OpenInfo;

import java.util.List;
import java.util.Map;

public interface InterfaceInfoRepository {

    List<Map<String, Object>>getAllInterface(String key);

    OpenInfo getInterfaceById(Long interfaceId);
}
