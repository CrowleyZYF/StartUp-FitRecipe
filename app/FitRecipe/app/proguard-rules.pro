# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\奕峰\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}pro


# 指定代码的压缩级别
-optimizationpasses 5
# 是否使用大小写混合
-dontusemixedcaseclassnames
# 是否混淆第三方jar
-dontskipnonpubliclibraryclasses
# 混淆时是否做预校验
-dontpreverify
# 混淆时是否记录日志
-verbose
# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#-ignorewarnings
-keep public class cn.fitrecipe.android.R$*{
    public static final int *;
}
-keep public class cn.fitrecipe.android.entity.*
-keep public class cn.fitrecipe.android.entity.*{*;}
-keep public class cn.fitrecipe.android.FrApplication.*
-keep public class cn.fitrecipe.android.FrApplication.*{*;}


# 保持哪些类不被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-dontwarn com.daimajia.**
-keep class com.daimajia*.**{*;}
-dontwarn com.google.**
-keep class com.google*.**{*;}
-dontwarn com.j256.**
-keep class com.j256*.**{*;}
-dontwarn com.qiniu.**
-keep class com.qiniu*.**{*;}
-dontwarn cn.smssdk.**
-keep class cn.smssdk*.**{*;}
-dontwarn com.nostra13.**
-keep class com.nostra13*.**{*;}
-dontwarn com.android.volley
-keep class com.android.volley{*;}
-dontwarn com.umeng.**
-keep class com.umeng*.**{*;}
-dontwarn com.youku.**
-keep class com.youku*.**{*;}
-dontwarn android.support-v4.**
-keep class android.support-v4.**{*;}
-dontwarn org.apache.**
-keep class org.apache.**{*;}
-dontwarn java.awt.**
-keep class java.awt.**{*;}
-dontwarn com.facebook.**
-keep class com.facebook.**{*;}
-dontwarn com.tencent.**
-keep class com.tencent.**{*;}