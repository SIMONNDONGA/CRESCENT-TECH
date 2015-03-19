package com.example.crescenttechticketing;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * This is the activity for feature 5 in the dashboard application.
 * It displays some text and provides a way to get back to the home activity.
 */

public class Graph extends AdminClass {

    //** Called when the activity is first created. */
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_main);

        WebView webView = (WebView) this.findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webView.requestFocusFromTouch();
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        // Load the URL
        webView.loadUrl("http://10.0.2.2/crescenttech/graph/graph.php");
    }
}
