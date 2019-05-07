package com.example.mersultrenurilorez;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.os.Build;


public class MainActivity extends AppCompatActivity {
    private WebView myWebView2;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        myWebView2 = (WebView) findViewById(R.id.WebView2);
        myWebView2.setWebViewClient(new WebViewClient());
        myWebView2.setInitialScale(1);
        myWebView2.getSettings().setLoadWithOverviewMode(true);
        myWebView2.getSettings().setUseWideViewPort(true);
        String url = "https://mersultrenurilor.infofer.ro/ro-RO/Itineraries";
        myWebView2.loadUrl(url);
        myWebView2.getSettings().setJavaScriptEnabled(true);
        myWebView2.getSettings().setDisplayZoomControls(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006ee5")));
        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        String statie1 = mPreferences.getString("key1", "");
        String statie2 = mPreferences.getString("key2", "");

        final String js = "javascript:document.getElementById('input-station-departure-name').value='" + statie1 + "';" +
                "javascript:document.getElementById('input-station-arrival-name').value='" + statie2 + "';" +
                "javascript:document.getElementById('button-switch-stations').disabled = false;";

        myWebView2.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                if (Build.VERSION.SDK_INT >= 19) {
                    myWebView2.evaluateJavascript(js, new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                        }
                    });
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.super_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_gara_craiova:

                myWebView2.loadUrl("http://www.cfr.ro/webcamcrv.jpg");
                myWebView2.getSettings().setBuiltInZoomControls(true);
                break;

            case R.id.menu_gara_timisoaranord:
                myWebView2.loadUrl("http://www.cfr.ro/webcamtm.jpg");
                myWebView2.getSettings().setBuiltInZoomControls(true);
                break;

            case R.id.menu_gara_brasov:
                myWebView2.loadUrl("http://www.cfr.ro/webcambv.jpg");
                myWebView2.getSettings().setBuiltInZoomControls(true);
                break;

            case R.id.menu_gara_nord:
                myWebView2.loadUrl("http://www.cfr.ro/webcamgn.jpg");
                myWebView2.getSettings().setBuiltInZoomControls(true);
                break;

            case R.id.menu_gara_iasi:
                myWebView2.loadUrl("http://www.cfr.ro/webcamiasi.jpg");
                myWebView2.getSettings().setBuiltInZoomControls(true);
                break;


            case R.id.menu_backward:
                onBackPressed();
                break;

            case R.id.menu_forward:
                onForwardPressed();
                checkUrl();
                break;

            case R.id.menu_save:
                onSavePressed();
                break;

            case R.id.menu_refresh:
                myWebView2.reload();
                break;

            case R.id.menu_home:
                myWebView2.loadUrl("https://mersultrenurilor.infofer.ro/ro-RO/Itineraries");
                myWebView2.getSettings().setBuiltInZoomControls(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSavePressed() {
        final String stat2 = "javascript:document.getElementById('input-station-departure-name').value";
        myWebView2.evaluateJavascript(stat2, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                s = s.substring(1, s.length() - 1);
                mEditor.putString("key1", s);
                mEditor.commit();
            }
        });
        final String stat3 = "javascript:document.getElementById('input-station-arrival-name').value";
        myWebView2.evaluateJavascript(stat3, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                s = s.substring(1, s.length() - 1);
                mEditor.putString("key2", s);
                mEditor.commit();
            }
        });
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);


        Toast.makeText(this, "Rutele au fost salvate!", Toast.LENGTH_SHORT).show();
    }


    private void onForwardPressed() {
        if (myWebView2.canGoForward()) {
            myWebView2.goForward();
        } else {
            Toast.makeText(this, "Nu pot merge mai departe!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (myWebView2.canGoBack()) {
            myWebView2.goBack();
        } else {
            finish();
        }
        checkUrl();

    }

    public void checkUrl() {
        String webUrl = myWebView2.getOriginalUrl();
        if (webUrl.startsWith("https://mersultrenurilor.infofer")) {
            myWebView2.getSettings().setBuiltInZoomControls(false);
        } else myWebView2.getSettings().setBuiltInZoomControls(true);
    }
}




