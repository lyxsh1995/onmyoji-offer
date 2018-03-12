# MyXposed

Android studio 直接运行坑死了,它会给包名后面加-1 -2标识改变包名,导致xposed_init里的类入口读取不到
Xposed日志里可以看到以下报错信息

`java.lang.ClassNotFoundException: Didn't find class "com.lyxsh.myxposed.zhuaizhuaixposed" on path: DexPathList[[zip file "/data/app/com.lyxsh.myxposed-1/base.apk"],nativeLibraryDirectories=[/vendor/lib, /system/lib]]`

解决办法:build APK然后拖到模拟器里安装运行
