# Keep Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Keep Kotlin Serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep,includedescriptorclasses class com.myapp.**$$serializer { *; }
-keepclassmembers class com.myapp.** {
    *** Companion;
}
-keepclasseswithmembers class com.myapp.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep Compose
-dontwarn androidx.compose.**
