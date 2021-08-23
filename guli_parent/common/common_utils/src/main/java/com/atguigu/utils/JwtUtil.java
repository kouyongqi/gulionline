package com.atguigu.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {
    public static final long EXPIRE = 1000 * 60 * 60 * 24;  //设置token超时/过期时间
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO"; //token秘钥

    /**
    * @Author yourname kou
    * @Date 2021/8/11 13:30
    * @Description  生成token字符串
    * @Param
    * @Return
    * @Since JDK 1.8
    */
    public static String getJwtToken(String id, String nickname){
        String JwtToken = Jwts.builder()

                .setHeaderParam("typ", "JWT")  //设置token头信息
                .setHeaderParam("alg", "HS256")

                .setSubject("guli-user")           //设置token过期时间等
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))

                .claim("id", id)   //设置token主体部分(id,nickname)
                .claim("nickname", nickname)

                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
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
            //从请求request中获取头信息
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
         * 根据token获取会员id
         * @param request
         * @return
         */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        //从请求request中获取头信息
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) return "";
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return (String)claims.get("id");
    }
}
