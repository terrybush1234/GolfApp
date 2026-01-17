package com.Golf.App;

// This is the required import for the auto-generated BuildConfig file.
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

        // Use named classes for all listeners and runnables to avoid nest host errors.
        new Handler(Looper.getMainLooper()).postDelayed(new SetSplashScreenReady(), 2000);

        final View content = findViewById(android.R.id.content);
        content.getViewTreeObserver().addOnPreDrawListener(new KeepSplashScreenOnScreen(content));
    }

    @Override
    public void onStart() {
        super.onStart();
        getBridge().getWebView().evaluateJavascript("window.setGoogleMapsKey('" + BuildConfig.MAPS_API_KEY + "')", null);
    }

    // Named Runnable to replace the lambda for the Handler
    private class SetSplashScreenReady implements Runnable {
        @Override
        public void run() {
            isSplashScreenReady = true;
        }
    }

    // Named listener for the splash screen
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
