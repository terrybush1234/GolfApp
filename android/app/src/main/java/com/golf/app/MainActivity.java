package com.Golf.App;

import com.Golf.App.BuildConfig;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.core.splashscreen.SplashScreen;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {

    private boolean isSplashScreenReady = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        new Handler(Looper.getMainLooper()).postDelayed(new SetSplashScreenReady(), 2000);

        final View content = findViewById(android.R.id.content);
        content.getViewTreeObserver().addOnPreDrawListener(new KeepSplashScreenOnScreen(content));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Securely pass both keys to the webview
        String script = "window.setGoogleMapsKey('" + BuildConfig.MAPS_API_KEY + "');" +
                      "window.setFirebaseApiKey('" + BuildConfig.FIREBASE_API_KEY + "');";
        getBridge().getWebView().evaluateJavascript(script, null);
    }

    private class SetSplashScreenReady implements Runnable {
        @Override
        public void run() {
            isSplashScreenReady = true;
        }
    }

    private class KeepSplashScreenOnScreen implements ViewTreeObserver.OnPreDrawListener {
        private final View contentView;

        public KeepSplashScreenOnScreen(View contentView) {
            this.contentView = contentView;
        }

        @Override
        public boolean onPreDraw() {
            if (isSplashScreenReady) {
                contentView.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            } else {
                return false;
            }
        }
    }
}
