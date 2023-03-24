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

public class UrlWebView extends Activity implements View.OnClickListener{

    Button accueilBtn;
    WebView webview;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_webview);

        //récupérer l'url
        String url = getIntent().getStringExtra("url");

        //gérer la webview
        webview = (WebView) findViewById(R.id.urlWebView);
        WebSettings params = webview.getSettings();
        params.setJavaScriptEnabled(true);
        params.setBuiltInZoomControls(true);
        webview.addJavascriptInterface(new WebAppInterface(this), "Android");
        webview.setWebViewClient(new MyWebViewClient());

        webview.loadUrl(url);

        //gérer bouton menu
        accueilBtn = (Button)findViewById(R.id.button);
        accueilBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Classe privée permettant de redéfinir un client webview
    private class MyWebViewClient extends WebViewClient {
        /*
         * Avec ce mécanisme nous permettons à l'application de prendre en charge
         * une nouvelle url dans la webview actuelle
         *
         * Si WebViewClient n'est pas disponible dans l'activité, l'activity manager
         * demandera à l'utilisateur de choisir une application disponible pour
         * traiter l'url
         *
         * Ainsi le retour de la méthode permet de préciser si on prend
         * en charge l'url apssée (true) ou non (false)
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }

    /*
     * Activation de l'historique et du bouton retour.
     * Lors d'un appui sur le bouton retour, l'utilisateur reviens au site
     * précédent qu'il a consulté, s'il y en a un, grâce aux méthodes
     * canGoBack() et goBack() de la webview.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        //vérification de l'event Key égal au bouton retour et s'il y a un
        //historique
        if((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()){
            webview.goBack();
            return true;
        }

        //si il n'y a pas d'historique le bouton est utilisé comme apr défaut
        return super.onKeyDown(keyCode, event);
    }
}
