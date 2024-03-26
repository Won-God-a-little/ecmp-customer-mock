package cn.eqxiu.mock.infra.util;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Arrays;

/**
 * 加解密工具类
 *
 * @author will
 */
public class SecretUtil {

    public final static String AES_CBC_PKCS_7_PADDING ="AES/CBC/PKCS7Padding";
    public final static String AES_CBC_PKCS_NO_PADDING ="AES/CBC/NoPadding";

    /**
     * 签名生成方法
     * 生成参数Hash值
     * @param param
     * @return
     */
    public static String genSign(String ...param){
        if (StrUtil.hasEmpty(param)) {
            throw new IllegalArgumentException("非法请求参数，有部分参数为空 : " + param);
        }

        Arrays.sort(param);
        StringBuilder sb = new StringBuilder();
        for (String p : param) {
            sb.append(p);
        }
        return DigestUtils.sha1Hex(sb.toString());
    }

    /**
     * 参数解密
     *
     * @param cipherText
     * @param transformation  AES/CBC/PKCS7Padding|AES/CBC/NoPadding
     * @return
     */
    public static String decrypt(String cipherText,String encodingKey,String transformation) {
        byte[] original;
        try {
            byte[] aesKey = Base64.decodeBase64(encodingKey);
            // 注意优化这里
            Security.addProvider(new BouncyCastleProvider());
            // 设置解密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance(transformation);
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);

            // 使用BASE64对密文进行解码
            byte[] encrypted = Base64.decodeBase64(cipherText);

            // 解密
            original = cipher.doFinal(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new String(original);
    }

    public static String decrypt(String cipherText,String encodingKey) {
        return decrypt(cipherText,encodingKey, AES_CBC_PKCS_7_PADDING);
    }

    public static String encrypt(String plainText,String encodingKey){
        return encrypt(plainText,encodingKey, AES_CBC_PKCS_7_PADDING);
    }
    public static String encrypt(String plainText,String encodingKey,String transformation) {
        byte[] original;
        try {
            byte[] aesKey = Base64.decodeBase64(encodingKey);
            // 设置解密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance(transformation);
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

            // 加密
            original = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new String(Base64.encodeBase64(original));
    }


}
