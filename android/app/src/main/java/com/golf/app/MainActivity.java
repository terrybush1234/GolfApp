package com.Golf.App;

import android.os.Bundle;
import androidx.core.splashscreen.SplashScreen;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onStart() {
        super.onStart();
        // Pass the key to the webview
        getBridge().getWebView().evaluateJavascript("window.setGoogleMapsKey('" + BuildConfig.MAPS_API_KEY + "')", null);
    }
}
