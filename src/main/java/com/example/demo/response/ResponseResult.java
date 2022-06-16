package com.example.demo.response;

/**
 * @auther:Helen
 * @date 2022/5/31&15:36
 */
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})  //作用于方法和类（接口）上
@Documented
public @interface ResponseResult {
}
