# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.

# Keep Kotlin data classes
-keepclassmembers class * {
    @androidx.annotation.Keep <fields>;
}

# Keep models
-keep class com.podcast.kotlin.app.** { *; }
