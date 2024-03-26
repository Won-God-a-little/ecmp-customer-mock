package cn.eqxiu.mock.domain.repository;

import cn.eqxiu.mock.domain.entity.Corp;

/**
 * 企业信息持久化接口
 *
 * @author will
 */
public interface CorpRepository {

    Corp findByName(String name);

    Corp findByCode(String id);

    void updateCorp(Corp corp);

    void createCorp(Corp corp);
}
