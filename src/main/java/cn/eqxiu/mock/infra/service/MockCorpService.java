package cn.eqxiu.mock.infra.service;


import cn.eqxiu.mock.domain.entity.Corp;
import cn.eqxiu.mock.domain.repository.CorpRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author will
 */
@Service
public class MockCorpService {
    @Resource
    private CorpRepository corpRepository;


    public Corp getCorpByName(String name) {
        return corpRepository.findByName(name);
    }


    public Corp findByCode(String code) {
        return corpRepository.findByCode(code);
    }


    public void createCorp(Corp corp) {
        corpRepository.createCorp(corp);
    }


    public void updateCorp(Corp corp) {
        corpRepository.updateCorp(corp);
    }

}
