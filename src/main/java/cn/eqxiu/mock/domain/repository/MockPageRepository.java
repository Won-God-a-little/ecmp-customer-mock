package cn.eqxiu.mock.domain.repository;

import cn.eqxiu.mock.domain.entity.MockPageInfo;

import java.util.List;

public interface MockPageRepository {
    List<MockPageInfo> findByPageName(String key);
}
