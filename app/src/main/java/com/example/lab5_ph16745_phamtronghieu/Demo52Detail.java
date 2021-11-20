package com.example.lab5_ph16745_phamtronghieu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class Demo52Detail extends AppCompatActivity {
WebView webView;
Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo52_detail);
        webView = findViewById(R.id.demo52wv);
        intent = getIntent();
        String link = intent.getStringExtra("linkWeb");
        webView.loadUrl(link);
        webView.setWebChromeClient(new WebChromeClient());
    }
}