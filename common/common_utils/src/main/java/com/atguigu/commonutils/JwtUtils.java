package com.atguigu.commonutils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author helen
 * @since 2019/10/16
 */

/**
 * Jwt 工具类 验证登录注册
 */
public class JwtUtils {

    public static final long EXPIRE = 1000 * 60 * 60 * 24;  //常量: 设置 token 的过期时间
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO"; //常量: 对数据的加密和编码 密钥 自动的生产规则每个公司都有不同的

    /**
     * 生成 token 字符串的方法 id 用户 id,nickname 用户名称
     * @param id
     * @param nickname
     * @return
     */
    public static String getJwtToken(String id, String nickname){

        //构建 Jwt 的生成规则
        String JwtToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")//jwt 的头信息
                .setHeaderParam("alg", "HS256")

                .setSubject("guli-user")//改分类
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))//设置过期时间

                .claim("id", id) //设置 token 的主体部分,存储用户的信息
                .claim("nickname", nickname) //用户名称

                .signWith(SignatureAlgorithm.HS256, APP_SECRET)//签名哈希根据信息密钥生成
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token字符串获取会员id
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }
}









