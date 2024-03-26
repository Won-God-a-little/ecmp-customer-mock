package cn.eqxiu.mock.infra.persistence.mapper;

import cn.eqxiu.mock.domain.entity.MockPage;
import cn.eqxiu.mock.domain.entity.MockPageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MockPageMapper{
    List<MockPageInfo> getMockPageInfoByName(@Param("key") String key);
}
