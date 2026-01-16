# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# --- THE MOST IMPORTANT RULE --- #
# Keep all classes in your app's package from being obfuscated.
# This is crucial for serialization and for preventing crashes when using data models.
-keep class com.Golf.App.** { *; }

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
