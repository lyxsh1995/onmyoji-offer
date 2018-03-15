package com.lyxsh.myxposed;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.callStaticMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;
import static de.robv.android.xposed.XposedHelpers.getObjectField;
import static de.robv.android.xposed.XposedHelpers.newInstance;

/**
 * Created by Administrator on 2018/3/12.
 */

public class zhuaizhuaixposed implements IXposedHookLoadPackage {
    private Object sApplication;
    private Context context;
    private Activity activity;
    private Application application;

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        XposedBridge.log("zhuaizhuai xposed OK ");
//        if (loadPackageParam.packageName.equals("com.lyxsh.myxposed")) {
//            final Class ceshiclass = findClass("com.lyxsh.myxposed.MainActivity.Ceshi", loadPackageParam.classLoader);
//            findAndHookMethod("com.lyxsh.myxposed.MainActivity", loadPackageParam.classLoader, "setnumber", ceshiclass, new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    Field ceshia = ceshiclass.getDeclaredField("a");
//                    Field ceshib = ceshiclass.getDeclaredField("b");
//                    ceshia.setAccessible(true);
//                    ceshib.setAccessible(true);
//
//                    String a = (String) ceshia.get(param.args[0]);
//                    int b = (int) ceshib.get(param.args[0]);
//
//                    Toast.makeText((Context) param.thisObject, a + " " + b, Toast.LENGTH_SHORT).show();
//
//                    ceshia.set(param.args[0], "A");
//                    ceshib.set(param.args[0], 5);
//                }
//            });
//        }
        if (!loadPackageParam.packageName.equals(Common.QQ_PACKAGE_NAME)) {
            return;
        }

        findAndHookMethod(Common.InjectUtils, loadPackageParam.classLoader, "injectExtraDexes",
                Application.class, boolean.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        application = (Application) param.args[0];
                        sApplication = callStaticMethod(findClass(Common.BaseApplicationImpl, loadPackageParam.classLoader), "getApplication");
                    }
                });

        findAndHookMethod(Common.MessageHandlerUtils, loadPackageParam.classLoader, "a",
                Common.QQAppInterface,
                Common.MessageRecord,
                boolean.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);

                        final String frienduin = getObjectField(param.args[1], "frienduin").toString();
                        final String selfuin = getObjectField(param.args[1], "selfuin").toString();
                        final int istroop = (int) getObjectField(param.args[1], "istroop");
                        final String senderuin = getObjectField(param.args[1], "senderuin").toString();
                        final String msg = getObjectField(param.args[1], "msg").toString();
                        boolean isread = (boolean) getObjectField(param.args[1], "isread");
                        if (isread || senderuin.equals(selfuin)) {
                            return;
                        }
                        XposedBridge.log("frienduin:" + frienduin);
                        XposedBridge.log("selfuin:" + selfuin);
                        XposedBridge.log("senderuin:" + senderuin);
                        XposedBridge.log("msg:" + msg);
                        XposedBridge.log("param:" + param.args[1].toString());

                        new Sousuo().execute(msg,loadPackageParam.classLoader,sApplication,application,frienduin,selfuin,istroop);
                    }
                });

//        //https://gist.github.com/gitaiQAQ/f07aee1cf240010f09de
//        Class<?> QQAppInterface = loadPackageParam.classLoader.loadClass("com.tencent.mobileqq.app.QQAppInterface");
//        Class<?> SessionInfo = loadPackageParam.classLoader.loadClass("com.tencent.mobileqq.activity.aio.SessionInfo");
//        findAndHookMethod(Common.ChatActivityFacade, loadPackageParam.classLoader, "a", QQAppInterface, Application.class, SessionInfo, String.class, ArrayList.class, findClass(Common.SendMsgParams, loadPackageParam.classLoader), new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//            }
//        });
    }
}
