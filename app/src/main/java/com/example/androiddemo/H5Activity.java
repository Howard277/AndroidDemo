package com.example.androiddemo;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class H5Activity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);

        // 获取进度条对象
        progressBar = findViewById(R.id.progressBar);
        // 获取web view 对象
        webView = findViewById(R.id.webView1);
        //添加js监听 这样html就能调用客户端
        webView.addJavascriptInterface(this, "android");
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
        WebSettings webSettings = webView.getSettings();
        //允许使用js
        webSettings.setJavaScriptEnabled(true);
        //不使用缓存，只从网络获取数据.
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //不支持屏幕缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);

        //根据参数，加载页面
        webView.loadUrl(getIntent().getStringExtra("url"));
    }

    /**
     * JS调用android的方法
     *
     * @param str
     * @return
     */
    @JavascriptInterface
    public void getClient(String str) {
        Log.i("ansen", "html调用客户端:" + str);
    }


    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("ansen", "拦截url:" + url);
            if (url.equals("http://www.google.com/")) {
                Toast.makeText(H5Activity.this, "国内不能访问google,拦截该url", Toast.LENGTH_LONG).show();
                return true;//表示我已经处理过了
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

    };

    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private WebChromeClient webChromeClient = new WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message).setPositiveButton("确定", null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();

            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            result.confirm();
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.i("ansen", "网页标题:" + title);
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setProgress(newProgress);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        webView.destroy();
        webView = null;
    }
}
