package cn.eqxiu.mock.domain.entity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class InterfaceInfo {

    /**
     * 主键
     */
    private Long id;

    /**
     * 接口名称
     */
    private String name;
    /**
     * 接口url
     */
    private String url;
    /**
     * 请求方式
     */
    private String method;
    /**
     * 请求参数
     */
    private List<RequestParam> requestParams;

    /**
     * 响应结果
     */
    private String responseParams;

    private Integer groupId;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer group_id) {
        this.groupId = group_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResponseParams() {
        return responseParams;
    }
    public List<RequestParam> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(List<RequestParam> requestParams) {
     this.requestParams = requestParams;
    }

    public void setResponseParams(String responseParams) {
        this.responseParams = responseParams;
    }
}
