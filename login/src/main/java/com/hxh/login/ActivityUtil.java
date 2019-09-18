package com.hxh.login;

import com.hxh.route.ARoute;
import com.hxh.route.IRoute;

/**
 * Created by HXH at 2019/9/17
 * 注解处理器写代码的模板代码
 */
public class ActivityUtil implements IRoute {
    @Override
    public void putActivity() {
        ARoute.getInstance().putActivity("login/login", LoginActivity.class);
    }
}
