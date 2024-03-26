package cn.eqxiu.mock.infra.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    private static String signKey = "hower"; //签名算法 的 名字
    private static Long expire = 43200000L; // 过期时间

    // 根据载荷 生成JWT令牌
    public static String generateJwt(Map<String, Object> claims){
        String jwt = Jwts.builder()
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expire)) // 过期时间
                .compact();
        return jwt;
    }

    //  解析JWT令牌
    public static Claims parseJWT(String jwt){
        Claims claims = Jwts.parser()
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }
}
