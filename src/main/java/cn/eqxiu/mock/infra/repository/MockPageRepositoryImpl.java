package cn.eqxiu.mock.infra.repository;

import cn.eqxiu.mock.domain.entity.MockPageInfo;
import cn.eqxiu.mock.domain.repository.MockPageRepository;
import cn.eqxiu.mock.infra.persistence.mapper.MockPageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MockPageRepositoryImpl implements MockPageRepository {

    @Autowired
    MockPageMapper mockPageMapper;

    /**
     * 通过页名称获取页面集合信息
     * @param key
     * @return
     */
    @Override
    public List<MockPageInfo> findByPageName(String key) {
        List<MockPageInfo> mockPageInfoByName = mockPageMapper.getMockPageInfoByName(key);
        if (mockPageInfoByName == null || mockPageInfoByName.isEmpty()) {
            return mockPageMapper.getMockPageInfoByName("");
        }
        return mockPageInfoByName;
    }

}
