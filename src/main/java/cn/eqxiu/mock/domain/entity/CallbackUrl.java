package cn.eqxiu.mock.domain.entity;

public class CallbackUrl {

    private Integer id;

    /**
     * 接口功能
     */
    private String name;

    /**
     * 接口地址
     */
    private String url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public CallbackUrl(Integer id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public CallbackUrl() {
    }
}
