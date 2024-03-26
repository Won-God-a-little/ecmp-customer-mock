package cn.eqxiu.mock.domain.entity;

public class Corp {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 易企秀侧企业唯一code
     */
    private String code;

    /**
     * 企业唯一标识,代号
     */
    private String name;

    /**
     * 企业展示logo
     */
    private String logoUrl;

    /**
     * 用于请求易企秀接口的SecretId
     */
    private String secretId;

    /**
     *  用于请求易企秀接口的SecretKey
     */
    private String secretKey;

    /**
     * 易企秀回调签名key,用于参数签名,防篡改
     */
    private String signatureKey;

    /**
     * 易企秀回调加密key,用于请求参数加密,避免明文传输参数
     */
    private String encodingKey;

    /**
     * 模拟员工id
     */
    private String openId;

    /**
     * 用户域名
     */
    private String server;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getId() {
        return id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSignatureKey() {
        return signatureKey;
    }

    public void setSignatureKey(String signatureKey) {
        this.signatureKey = signatureKey;
    }

    public String getEncodingKey() {
        return encodingKey;
    }

    public void setEncodingKey(String encodingKey) {
        this.encodingKey = encodingKey;
    }

    @Override
    public String toString() {
        return "Corp{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", secretId='" + secretId + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", signatureKey='" + signatureKey + '\'' +
                ", encodingKey='" + encodingKey + '\'' +
                ", openId='" + openId + '\'' +
                ", server='" + server + '\'' +
                '}';
    }
}
