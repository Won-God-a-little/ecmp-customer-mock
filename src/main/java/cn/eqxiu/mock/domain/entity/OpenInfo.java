package cn.eqxiu.mock.domain.entity;

import java.util.List;

/**
 * 内容中台开放信息
 * 包括开放API接口,可集成页面,回调接口
 *
 * @author will
 */
public class OpenInfo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String title;

    /**
     * url
     */
    private String url;

    /**
     * 开放类型:
     * 1. 接口
     * 2. 页面
     * 3. 回调
     */
    private int openType;

    /**
     * 接口请求方式:
     * GET|POST
     */
    private String method;

    /**
     * 支持请求头
     */
    private String requestHeader;
    /**
     * 支持请求参数
     */
    private List<RequestParam> requestParams;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 响应结果
     */
    private String responseData;

    /**
     * 父级id,用于分组
     * parentId=1,表示为顶级分组
     */
    private Integer parentId;

    /**
     * 排序
     */
    private int sort;

    /**
     * 状态:
     * 1 正常
     * 2 停用
     */
    private int status;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setOpenType(int openType) {
        this.openType = openType;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public void setRequestParams(List<RequestParam> requestParams) {
        this.requestParams = requestParams;
    }

    public void setResponseHeader(String responseHeader) {
        this.responseHeader = responseHeader;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public int getOpenType() {
        return openType;
    }

    public String getMethod() {
        return method;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public List<RequestParam> getRequestParams() {
        return requestParams;
    }

    public String getResponseHeader() {
        return responseHeader;
    }

    public String getResponseData() {
        return responseData;
    }

    public Integer getParentId() {
        return parentId;
    }

    public int getSort() {
        return sort;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "OpenInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", openType=" + openType +
                ", method='" + method + '\'' +
                ", requestHeader='" + requestHeader + '\'' +
                ", requestParams=" + requestParams +
                ", responseHeader='" + responseHeader + '\'' +
                ", responseData='" + responseData + '\'' +
                ", parentId=" + parentId +
                ", sort=" + sort +
                ", status=" + status +
                '}';
    }
}
