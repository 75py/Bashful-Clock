-keepattributes *Annotation*
-keepattributes SourceFile, LineNumberTable, *Annotation*
-keep public class * extends java.lang.Exception

# https://qwx.jp/android-class-not-found-exception/
-keep class com.google.firebase.** { *; }

# https://github.com/Kotlin/kotlinx.serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class com.nagopy.android.bashfulclock.data.remoteconfig.**$$serializer { *; }
-keepclassmembers class com.nagopy.android.bashfulclock.data.remoteconfig.** {
    *** Companion;
}
-keepclasseswithmembers class com.nagopy.android.bashfulclock.data.remoteconfig.** {
    kotlinx.serialization.KSerializer serializer(...);
}