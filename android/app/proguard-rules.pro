# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# --- EXPLICIT FIX FOR MAINACTIVITY CRASH --- #
# This tells ProGuard to not touch MainActivity or any of its inner classes ($1, $2, etc.)
# This is necessary because of the OnPreDrawListener we added for the splash screen.
-keep class com.Golf.App.MainActivity
-keep class com.Golf.App.MainActivity$*

# --- THE MOST IMPORTANT RULE --- #
# Keep all classes in your app's package from being obfuscated.
# This is crucial for serialization and for preventing crashes when using data models.
-keep class com.Golf.App.** { *; }

# --- FIX FOR SPLASH SCREEN & NEST ERRORS --- #
# These rules are necessary for modern Android builds that use inner classes,
# like the OnPreDrawListener in MainActivity for the splash screen delay.
-keepattributes InnerClasses,NestHost,NestMembers

# Proguard rules for Capacitor
-keep public class com.getcapacitor.JSObject {
    public *;
}
-keep public class com.getcapacitor.PluginCall {
    public *;
}
-keep public class * extends com.getcapacitor.Plugin {
    public *;
}

# Preserve the line number information for debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you are using any Google services, you may need this
-keep class com.google.android.gms.common.** { *; }
-keep class com.google.android.gms.location.** { *; }
-keep class com.google.android.gms.maps.** { *; }

# Keep the JavaScript interface class if you use one
-keepclassmembers class com.Golf.App.MainActivity$WebAppInterface {
   public *;
}

-keepattributes JavascriptInterface
-keepattributes *Annotation*
-keepattributes Signature

-dontwarn com.google.android.gms.**
