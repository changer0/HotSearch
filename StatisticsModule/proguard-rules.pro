# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\liuxiaoyang\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
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
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#-libraryjars libs

#QM统计
-dontwarn	com.sijla.**
-keep	class com.sijla.**{*;	}
-dontwarn	com.q.**
-keep	class com.q.Qt{*;	}

#灯塔
#-dontwarn com.tencent.beacon.**
-keep class com.tencent.beacon.** {*;}

#keep 艾瑞sdk 可以混淆
#-dontwarn com.hmt.analytics.**
#-keepclasseswithmembers class com.hmt.analytics.** { <methods>; }