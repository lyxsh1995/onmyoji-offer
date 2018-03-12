package com.lyxsh.myxposed;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by Administrator on 2018/3/12.
 */

public class zhuaizhuaixposed implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        XposedBridge.log("zhuaizhuai  "+loadPackageParam.packageName);
        if (loadPackageParam.packageName.equals("com.lyxsh.myxposed")) {
            XposedBridge.log("读取到包");
            XposedHelpers.findAndHookMethod("com.lyxsh.myxposed.MainActivity",loadPackageParam.classLoader,"onCreate",Bundle.class,new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("读取到方法");
                    Activity activity = (Activity) param.thisObject;
                    Toast.makeText(activity,"已经HOOK",Toast.LENGTH_SHORT).show();
                    TextView textView = (TextView) activity.findViewById(R.id.textview);
                    textView.setText("Hello xposed");
                }
            });
        }
    }
}
