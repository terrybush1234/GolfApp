package com.Golf.App;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.core.splashscreen.SplashScreen;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {

    boolean isReady = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Handle the splash screen transition.
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        // Keep the splash screen visible until the 2-second delay is over.
        final View content = findViewById(android.R.id.content);
        content.getViewTreeObserver().addOnPreDrawListener(
            new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    // Check if the delay is complete.
                    if (isReady) {
                        // The content is ready, remove the listener and draw.
                        content.getViewTreeObserver().removeOnPreDrawListener(this);
                        return true;
                    } else {
                        // The content is not ready, wait.
                        return false;
                    }
                }
            });

        // Use a handler to set the isReady flag after 2 seconds (2000 milliseconds).
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            isReady = true;
        }, 2000);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Pass the key to the webview
        getBridge().getWebView().evaluateJavascript("window.setGoogleMapsKey('" + BuildConfig.MAPS_API_KEY + "')", null);
    }
}
