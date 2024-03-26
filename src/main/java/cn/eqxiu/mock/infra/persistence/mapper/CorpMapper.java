package cn.eqxiu.mock.infra.persistence.mapper;
import cn.eqxiu.common.mapper.BaseMapper;
import cn.eqxiu.mock.domain.entity.Corp;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CorpMapper extends BaseMapper<Corp,String>{

    /**
     * 通过企业code查询企业
     *
     * @param code
     * @return
     */
    Corp selectByCode(String code);

    /**
     * 新增企业
     * @param corp
     */
    void insertCorp(Corp corp);

    /**
     * 修改企业信息
     * @param corp
     */
    void updateCorp(Corp corp);
}
