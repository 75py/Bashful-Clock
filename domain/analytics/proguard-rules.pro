-keepattributes *Annotation*
-keepattributes SourceFile, LineNumberTable, *Annotation*
-keep public class * extends java.lang.Exception

# https://qwx.jp/android-class-not-found-exception/
-keep class com.google.firebase.** { *; }
