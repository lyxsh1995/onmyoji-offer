package com.lyxsh.myxposed;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.callStaticMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;
import static de.robv.android.xposed.XposedHelpers.newInstance;

/**
 * Created by Administrator on 2018/3/14.
 */

public class Sousuo extends AsyncTask {
    Object[] params;

    @Override
    protected Object doInBackground(Object[] params) {
        this.params = params;
        Document doc = null;
        try {
//                File in = new File("file:///android_assets/xuanshang.html");
//                doc = Jsoup.parse(in, "UTF-8", "");
            //从一个URL加载一个Document对象。
            doc = Jsoup.connect("http://news.4399.com/yyssy/xinde/m/733179.html").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    @Override
    protected void onPostExecute(Object o) {
        if (o == null) {
            return;
        }
        Document doc = (Document) o;
        //选择“美食天下”所在节点
        Elements elements = doc.select("tr");
        for (Element element : elements) {
            if (element.toString().contains("阴阳师" + params[0])) {
                Elements td = element.select("td");
                String leirong = td.get(2).text();
                XposedBridge.log("输出: "+leirong);
                send((ClassLoader) params[1], params[2], (Application) params[3], (String) params[4], (String) params[5], (int) params[6], leirong);
            }
        }
    }

    private void send(ClassLoader classLoader, Object sApplication, Application application, String frienduin, String selfuin, int istroop, String text) {
        Object qqAppInterface = callMethod(sApplication, "getAppRuntime", selfuin);
        Object sessionInfo = newInstance(findClass(Common.SessionInfo, classLoader));
        Object sendMsgParams = newInstance(findClass(Common.SendMsgParams, classLoader));
        try {
//            Field field = classLoader.loadClass(Common.SessionInfo).getDeclaredField("a");
//            field.setAccessible(true);
//            field.set(sessionInfo, frienduin);
//            field.set(sessionInfo, istroop);

            //https://www.52pojie.cn/thread-511845-1-1.html luckymoney源码
            Field[] fields = sessionInfo.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
            }
            fields[0].setInt(sessionInfo, istroop);
            fields[3].set(sessionInfo, frienduin);
            fields[5].set(sessionInfo,frienduin);
            fields[6].setInt(sessionInfo, 1);


        } catch (Exception e) {
            e.printStackTrace();
        }
        callStaticMethod(XposedHelpers.findClass(Common.ChatActivityFacade, classLoader), "a", qqAppInterface, application, sessionInfo, text, new ArrayList<>(), sendMsgParams);
    }
}
