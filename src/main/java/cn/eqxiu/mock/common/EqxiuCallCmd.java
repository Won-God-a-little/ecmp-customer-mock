package cn.eqxiu.mock.common;

import cn.eqxiu.mock.domain.entity.Corp;
import cn.eqxiu.mock.domain.entity.Secret;
import cn.eqxiu.mock.infra.util.SecretUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 易企秀请求参数封装
 * 该类仅限于Controller层使用
 *
 * @author will
 */
public class EqxiuCallCmd {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 请求时的时间戳
     */
    private String  timestamp;

    /**
     * 随机字符串
     */
    private String nonce;

    /**
     * 请求签名
     */
    private String signature;

    /**
     * 加密后的消息
     */
    private String msgEncrypt;

    /**
     * 参数校验
     * 1. 不可为空
     * 2. 签名是否正确
     * @return
     */
    public String validate() {
        if (timestamp == null || nonce == null || signature == null || msgEncrypt == null) {
            return "参数不可为空";
        }
        Secret secret = new Secret();
        if (secret == null){
            return "未配置密钥信息";
        }
        String genSign = SecretUtil.genSign(this.nonce,this.timestamp,secret.getSignatureKey());
        if (!genSign.equals(this.signature)) {
            return "签名错误";
        }
        return null;
    }

    public JSONObject getParamObj(){
        String param = SecretUtil.decrypt(this.msgEncrypt,getCorpSecret().getEncodingKey());
//        logger.info("decode info: {}", param);
        return JSONObject.parseObject(param);
    }

    public JSONArray getParamArr(Corp corp){
        String param = SecretUtil.decrypt(this.msgEncrypt,corp.getEncodingKey());
        return JSONObject.parseArray(param);
    }

    private Corp getCorpSecret(){
        Corp currentCorp = (Corp)getCurrentRequest().getAttribute(BaseController.REQUEST_ATTR_CORP);
        if (currentCorp == null){
//            一般没有拼接企业名称，使用默认的
            return new Corp();
        }
        return currentCorp;
    }

    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getMsgEncrypt() {
        return msgEncrypt;
    }

    public void setMsgEncrypt(String msgEncrypt) {
        this.msgEncrypt = msgEncrypt;
    }

    @Override
    public String toString() {
        return "EqxiuCallCmd{" +
                "timestamp='" + timestamp + '\'' +
                ", nonce='" + nonce + '\'' +
                ", signature='" + signature + '\'' +
                ", msgEncrypt='" + msgEncrypt + '\'' +
                '}';
    }
}
