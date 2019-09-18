package com.hxh.route;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;

/**
 * Created by HXH at 2019/9/17
 * 路由map管理器
 */
public class ARoute {

    private static class HOLDER {
        private static final ARoute INSTANCE = new ARoute();
    }

    public static ARoute getInstance() {
        return HOLDER.INSTANCE;
    }

    private Map<String, Class<? extends Activity>> mMap;
    private Context mContext;

    private ARoute() {
        mMap = new HashMap<>();
    }

    public void init(Application application) {
        mContext = application;
        List<String> list = getClassName("com.hxh.util");
        for (String fullClassName : list) {
            try {
                Class<?> clazz = Class.forName(fullClassName);
                // 判断当前的类对象是否是IRoute的实现类
                if (IRoute.class.isAssignableFrom(clazz)) {
                    IRoute newInstance = (IRoute) clazz.newInstance();
                    newInstance.putActivity();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void putActivity(String path, Class<? extends Activity> clazz) {
        if (TextUtils.isEmpty(path) || clazz == null) {
            return;
        }
        mMap.put(path, clazz);
    }

    /**
     * 跳转
     *
     * @param path   BindPath
     * @param bundle 参数
     */
    public void jumpActivity(String path, Bundle bundle) {
        // activity类对象
        Class<? extends Activity> clazz = mMap.get(path);
        if (clazz == null) {
            return;
        }
        Intent intent = new Intent(mContext, clazz)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        mContext.startActivity(intent);
    }

    /**
     * 跳转
     *
     * @param path   BindPath
     * @param bundle 参数
     */
    public void jumpActivity(Activity activity, String path, Bundle bundle) {
        // activity类对象
        Class<? extends Activity> clazz = mMap.get(path);
        if (clazz == null) {
            return;
        }
        Intent intent = new Intent(activity, clazz);
        if (bundle != null) {
            intent.putExtra("bundle", bundle);
        }
        activity.startActivity(intent);
    }

    private List<String> getClassName(String packageName) {
        List<String> classList = new ArrayList<>();
        String path;
        try {
            // 通过包管理器 获取到应用信息类 然后获取到apk的完整路径
            path = mContext.getPackageManager()
                    .getApplicationInfo(mContext.getPackageName(), 0)
                    .sourceDir;
            // 根据apk的完整路径获取到编译后的dex文件
            DexFile dexFile = new DexFile(path);
            // 获得编译后的dex文件中的所有class
            Enumeration<String> entries = dexFile.entries();
            while (entries.hasMoreElements()) {
                // 通过遍历所有的class的包名
                String name = entries.nextElement();
                if (name.startsWith(packageName)) {
                    classList.add(name);
                }
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return classList;
    }
}
