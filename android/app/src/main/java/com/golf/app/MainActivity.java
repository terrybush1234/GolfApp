package com.golf.app;

import android.os.Bundle;
import androidx.core.splashscreen.SplashScreen;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTime = System.currentTimeMillis();

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        splashScreen.setKeepOnScreenCondition(() -> {
            long currentTime = System.currentTimeMillis();
            return (currentTime - startTime) < 2500;
        });
    }
}
