package com.example.asif;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

/**
 * This class is responsible for displaying a web page in a WebView.
 */
public class UrlWebView extends Activity implements View.OnClickListener {

    private Button accueilBtn;
    private WebView webview;

    /**
     * Sets up the WebView and loads the URL passed as an Intent extra.
     *
     * @param savedInstanceState The saved instance state.
     */
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_webview);

        // Get the URL
        String url = getIntent().getStringExtra("url");

        // Set up the WebView
        webview = findViewById(R.id.urlWebView);
        WebSettings params = webview.getSettings();
        params.setJavaScriptEnabled(true);
        params.setBuiltInZoomControls(true);
        params.setSupportZoom(true);
        webview.addJavascriptInterface(new WebAppInterface(this), "Android");
        webview.setWebViewClient(new MyWebViewClient());

        webview.loadUrl(url);

        // Set up the menu button
        accueilBtn = findViewById(R.id.button);
        accueilBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * A private class that extends WebViewClient and allows us to handle new URLs in the
     * current WebView.
     */
    private class MyWebViewClient extends WebViewClient {

        /**
         * This method allows us to handle new URLs in the current WebView. If the WebViewClient
         * is not available in the Activity, the Activity Manager will prompt the user to choose
         * an available application to handle the URL.
         *
         * @param view The WebView that is initiating the callback.
         * @param url The URL that is being loaded.
         * @return True if the URL is handled by the WebView, false otherwise.
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }

    /**
     * Enables history and the back button. When the back button is pressed, the user will go
     * back to the previous site they visited, if there is one, using the WebView's canGoBack()
     * and goBack() methods.
     *
     * @param keyCode The keycode of the pressed key.
     * @param event The KeyEvent that occurred.
     * @return True if the back button was handled, false otherwise.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        // Check if the back button was pressed and if there is a history
        if((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()){
            webview.goBack();
            return true;
        }

        // If there is no history, use the default behavior of the back button
        return super.onKeyDown(keyCode, event);
    }
}
