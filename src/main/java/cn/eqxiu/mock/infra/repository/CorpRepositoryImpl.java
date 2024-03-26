package cn.eqxiu.mock.infra.repository;

import cn.eqxiu.mock.domain.entity.Corp;
import cn.eqxiu.mock.domain.repository.CorpRepository;
import cn.eqxiu.mock.infra.persistence.mapper.CorpMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author will
 */
@Repository
public class CorpRepositoryImpl implements CorpRepository {

    private static final Logger logger = LoggerFactory.getLogger(CorpRepositoryImpl.class);

    @Resource
    private CorpMapper corpMapper;

    @Override
    public Corp findByName(String name) {
        return null;
    }

    @Override
    public Corp findByCode(String code) {
        return corpMapper.selectByCode(code);
    }

    @Override
    public void updateCorp(Corp corp) {
        corpMapper.updateCorp(corp);
    }

    @Override
    public void createCorp(Corp corp) {
        corpMapper.insertCorp(corp);
    }
}
