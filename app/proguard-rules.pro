# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /path/to/sdk/tools/proguard/proguard-android.txt
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

# Jetpack Compose
-keep class androidx.compose.** { *; }
-keepclassmembers class * {
    @androidx.compose.ui.tooling.preview.Preview *;
}
