# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Tools\android-sdk-windows/tools/proguard/proguard-android.txt
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
-keep class com.coordispace.** { *; }
-keep interface com.coordispace.poinsnetworks.** { *; }
-keep public class com.coordispace.poinsnetworks.** { *; }
-keep class com.coordispace.poinsips.IPSConnector { *; }
-keep class com.coordispace.calibration.MgnCalibration { *; }
-keep class com.coordispace.data.** { *; }
-keep class com.coordispace.poinsmapview.** {*;}
-keep class bitter.jnibridge.** { *; }
-keep class com.unity3d.** { *; }
-keep class org.fmod.** { *; }