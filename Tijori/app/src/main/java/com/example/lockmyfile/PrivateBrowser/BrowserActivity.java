package com.example.lockmyfile.PrivateBrowser;

import android.view.WindowManager;
import android.webkit.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.lockmyfile.R;

public class BrowserActivity extends AppCompatActivity {

    // to open google search engine
    WebView webView;

    //open google.com
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Screenshot protection
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_browser);


        url = getText(R.string.google_url).toString();

        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new customWebViewClient());

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);

        protectBrowser();

        webView.loadUrl(url);
    }

    // function to not let browser activity be visible in other browsers
    private void protectBrowser(){
        //Make sure No cookies are created
        CookieManager.getInstance().setAcceptCookie(false);

        //Make sure no caching is done
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setAppCacheEnabled(false);
        webView.clearHistory();
        webView.clearCache(true);


        //Make sure no autofill for Forms/ user-name password happens for the app
        webView.clearFormData();
        webView.getSettings().setSavePassword(false);
        webView.getSettings().setSaveFormData(false);
    }

    public class customWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }

    // to not open the app without authentication when this activity is closed
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}