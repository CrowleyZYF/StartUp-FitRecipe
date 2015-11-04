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


-dontusemixedcaseclassnames
-dontshrink
-dontoptimize
-dontpreverify
-dontwarn com.umeng.comm.**
-dontwarn com.google.android.maps.**
-dontwarn javax.persistence.**
-dontwarn com.facebook.android.**
-dontwarn java.awt.**
-dontwarn org.slf4j.**
-dontwarn com.baseproject.utils.**
-dontwarn com.qiniu.http.**
-dontwarn android.webkit.WebView
-keepattributes *Annotation*
-keep class com.activeandroid.** {*;}

-keep,allowshrinking class org.android.agoo.service.* {
    public <fields>;
    public <methods>;
}
-keep,allowshrinking class com.umeng.message.* {
    public <fields>;
    public <methods>;
}

-keep public class [应用包名].R$*{
   public static final int *;
}

-keep class com.umeng.comm.push.UmengPushImpl {
    public * ;
}

#分享相关混淆
-dontwarn android.webkit.WebView
-dontwarn com.umeng.**
-dontwarn com.tencent.weibo.sdk.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes SourceFile,LineNumberTable
-keep public interface com.tencent.**
-keep public interface com.umeng.socialize.**
-keep public class com.umeng.socialize.* {*;}
-keep public class javax.**
-keep public class android.webkit.**
-keep public class com.tencent.** {*;}
-keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
-keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}