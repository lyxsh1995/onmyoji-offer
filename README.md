# MyXposed

QQ机器人 阴阳师悬赏封印查询

Android studio 直接运行坑死了,它会给包名后面加-1 -2标识改变包名,导致xposed_init里的类入口读取不到

Xposed日志里可以看到以下报错信息

`java.lang.ClassNotFoundException: Didn't find class "com.lyxsh.myxposed.zhuaizhuaixposed" on path: DexPathList[[zip file "/data/app/com.lyxsh.myxposed-1/base.apk"],nativeLibraryDirectories=[/vendor/lib, /system/lib]]`

解决办法:build APK然后拖到模拟器里安装运行

没有逆向能力,所有涉及到反编译都是借鉴的别人代码
参考资料有:

https://www.52pojie.cn/thread-511845-1-1.html luckymoney源码

https://git.oschina.net/FYMD/QQRobot

入门教程:https://www.jianshu.com/p/8fbf9e88eb54

爬虫数据:http://news.4399.com/yyssy/xinde/m/733179.html

![image](https://github.com/lyxsh2016/MyXposed/blob/master/jieping1.png)
