package com.Golf.App;

import android.os.Bundle;
import android.util.Log;
import androidx.core.splashscreen.SplashScreen;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onStart() {
        super.onStart();

        // --- TEMPORARY DEBUGGING --- //
        // Print the key to Logcat to see what the app is actually using.
        Log.d("API_KEY_DEBUG", "The key is: [" + BuildConfig.MAPS_API_KEY + "]");

        // Pass the key to the webview
        getBridge().getWebView().evaluateJavascript("window.setGoogleMapsKey('" + BuildConfig.MAPS_API_KEY + "')", null);
    }
}
