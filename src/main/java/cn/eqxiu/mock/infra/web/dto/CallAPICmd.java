package cn.eqxiu.mock.infra.web.dto;

import java.util.Map;

/**
 * 封装APC模拟调用参数
 * @author will
 */
public class CallAPICmd {
    private String code;

    private Long apiId;

    private Map<String,String> param;

}
