package cn.eqxiu.mock.domain.entity;

/**
 * @author will
 */
public class Secret {

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
    private String signatureKey = "ecmp2021";

    /**
     * 易企秀回调加密key,用于请求参数加密,避免明文传输参数
     */
    private String encodingKey = "62YNZ3Oi6bqv7PxYxpooCaXgrcEIFd7rKOqgcjeclgl";


    public String getSecretId() {
        return secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getSignatureKey() {
        return signatureKey;
    }

    public String getEncodingKey() {
        return encodingKey;
    }
}
