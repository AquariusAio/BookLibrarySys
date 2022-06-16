package com.example.demo.utils;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.security.AlgorithmConstraints;
import java.util.Date;

/**
 * @auther:Helen
 * @date 2022/6/14&17:46
 */
public class TokenUtils {

    /**
     * 生成token
     */

//    public static String genToken(String userId,String sign){
//        return JWT.create().withAudience(userId)  //将id保存作为载荷
//                .withExpiresAt(DateUtil.offsetHour((new Date(),2))//两个小时后过期
//                .sign(Algorithm.HMAC256(sign));//以password 作为token的密匙
//    }
}
