package cn.eqxiu.mock.infra.util;

import com.google.common.base.CharMatcher;
import com.google.common.io.BaseEncoding;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.Arrays;

public class DecryptUtil {

    public static String decrypt(String cipherText, String encodingKey) {
        byte[] original;
        try {
            byte[] aesKey = BaseEncoding.base64().decode(CharMatcher.whitespace().removeFrom(encodingKey));
            // 注意优化这里
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            // 设置解密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
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
}
