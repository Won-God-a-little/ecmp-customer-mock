package cn.eqxiu.mock.infra.repository;

import cn.eqxiu.mock.domain.entity.OpenInfo;
import cn.eqxiu.mock.domain.repository.InterfaceInfoRepository;
import cn.eqxiu.mock.infra.persistence.mapper.MockAPICallMapper;
import cn.eqxiu.mock.infra.web.vo.InterfaceInfoVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MockAPICallRepositoryImpl implements InterfaceInfoRepository {

    @Resource
    private MockAPICallMapper apiCallMapper;

    public List<Map<String, Object>> getAllInterface(String key) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> allInterface = apiCallMapper.getAllInterface(key);
        if (allInterface == null || allInterface.isEmpty()) {
            return apiCallMapper.getAllInterface("");
        }
        for (Map<String, Object> interfaceData : allInterface) {
            String interfacesJson = (String) interfaceData.get("interfaces");
            List<InterfaceInfoVo> interfaceInfoList = null;
            try {
                interfaceInfoList = objectMapper.readValue(interfacesJson, new com.fasterxml.jackson.core.type.TypeReference<List<InterfaceInfoVo>>() {
                });
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            interfaceData.put("interfaces", interfaceInfoList);
        }
        return allInterface;

    }


    public OpenInfo getInterfaceById(Long interfaceId) {
        return apiCallMapper.getInterfaceById(interfaceId);
    }
}
