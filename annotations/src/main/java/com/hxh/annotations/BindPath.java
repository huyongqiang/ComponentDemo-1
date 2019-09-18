package com.hxh.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by HXH at 2019/9/17
 * 路由
 */
@Target(ElementType.TYPE) //声明注解的作用域
@Retention(RetentionPolicy.CLASS) //声明注解的生命周期
public @interface BindPath {
    String value();
}
