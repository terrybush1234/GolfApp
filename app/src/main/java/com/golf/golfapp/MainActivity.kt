package com.golf.golfapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    private var isDataReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { !isDataReady }

        Handler(Looper.getMainLooper()).postDelayed({
            isDataReady = true
        }, 3000)

        setContent {
            AndroidView(factory = { context ->
                WebView(context).apply {
                    // --- NATIVE FIXES ---
                    setOnLongClickListener { true } // Fixes the cut/copy/paste pop-up
                    setLayerType(View.LAYER_TYPE_SOFTWARE, null) // Fixes rendering glitches
                    webChromeClient = WebChromeClient()

                    // --- JAVASCRIPT & CSS FIXES (This is the final, complete version) ---
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            val script = """
                                // This re-enables scrolling on the page.
                                document.body.classList.remove('overflow-hidden');

                                // This fixes the 1/3 screen layout bug.
                                document.documentElement.style.height = '100%';
                                document.body.style.height = '100%';
                                const mainContainer = document.body.querySelector('div.w-full.max-w-lg');
                                if (mainContainer) {
                                    mainContainer.style.height = 'auto';
                                    mainContainer.style.flexGrow = '1';
                                }

                                // This injects a stylesheet with all necessary visual fixes.
                                const styleSheet = document.createElement('style');
                                styleSheet.innerText = `
                                    /* This makes the header stay at the top */
                                    header {
                                        position: -webkit-sticky; /* For Safari compatibility */
                                        position: sticky;
                                        top: 0;
                                        z-index: 30; /* Ensure header is on top */
                                    }

                                    /* Fix for sticky elements in the scorecard */
                                    #gc-header-names, #gc-header-hcps, .gc-col-fixed, .gc-input-cell {
                                        transform: translateZ(0);
                                    }
                                    /* Fix for flexbox scrolling bug in the main content area */
                                    #main-scroll {
                                        min-height: 0;
                                    }
                                `;
                                document.head.appendChild(styleSheet);
                            """
                            view?.evaluateJavascript(script, null)
                        }
                    }

                    // --- NATIVE WEBVIEW SETTINGS ---
                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        allowUniversalAccessFromFileURLs = true

                        // This combination forces a stable layout that fixes the dots
                        layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
                        useWideViewPort = true
                        loadWithOverviewMode = true
                    }

                    loadUrl("file:///android_asset/index.html")
                }
            }, modifier = Modifier.fillMaxSize())
        }
    }
}