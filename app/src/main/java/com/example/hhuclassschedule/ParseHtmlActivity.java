package com.example.hhuclassschedule;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.hhuclassschedule.util.ContextApplication;
import com.example.hhuclassschedule.util.SharedPreferencesUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 抓取课程信息并保存为json字符串
 */
public class ParseHtmlActivity extends AppCompatActivity {

    private static final String TAG = "ParseHtmlActivity";

    WebView webView;
    TextView tv_import;
    String parseHtmlJS;
    String URL = "http://jwxs.hhu.edu.cn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_html);
        try {
            // 打开网页
            openWeb();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 导入课程
        importSubject();

    }

    /**
     * 打开网页
     * @throws IOException
     */
    public void openWeb() throws IOException {
        // toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// 添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // webView 设置
        webView = findViewById(R.id.classWeb);
        WebSettings ws = webView.getSettings();
        // 支持js
        ws.setJavaScriptEnabled(true);    // 允许js
        ws.setJavaScriptCanOpenWindowsAutomatically(true);  // 允许js打开新窗口

        // 缩放操作
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);   // 开启缩放
        ws.setDisplayZoomControls(false);  // 隐藏原生的缩放控件

        // 自适应屏幕
        ws.setUseWideViewPort(true);       // 自适应屏幕
        ws.setLoadWithOverviewMode(true);  // 缩放至屏幕的大小

        // 设置浏览器标识，以pc模式打开网页
        ws.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.134 Safari/537.36");

        ws.setAllowFileAccess(true);       // 可以访问文件

        // 读取 assets 里的js文件
        InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open("parseHtml.js"));
        BufferedReader bufReader = new BufferedReader(inputReader);
        String line;
        parseHtmlJS = "";
        while ((line = bufReader.readLine()) != null)
            parseHtmlJS += line;
        Log.e(TAG, "parseHtmlJS: " + parseHtmlJS);

        // 加载网页
        webView.loadUrl(parseHtmlJS);
        webView.loadUrl(URL);
        webView.setWebViewClient(new WebViewClient() {

//            // android 6.0 以下使用
//            public boolean shouldOverrideUrlLoading(WebView view, String Url) {
//                view.loadUrl(Url);
//                return true;
//            }
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                view.loadUrl(String.valueOf(request.getUrl()));
//                return true;
//            }

            // js 注入
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                webView.evaluateJavascript(parseHtmlJS, null);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String title = view.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    // 设置标题
                    // getSupportActionBar().setTitle(title);
                    TextView textView = findViewById(R.id.toolbar_title);
                    textView.setText(title);
                    Log.e(TAG, "title" + title);
                }
            }
        });
    }

    /**
     * 导入课程保存为json字符串
     */
    public void importSubject(){
        // 导入课程
        tv_import = findViewById(R.id.tv_button);
        tv_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 通过Handler发送消息
                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        // 注意调用的JS方法名要对应上
                        // 调用javascript的parseHtml()方法
                        // webView.loadUrl("javascript:parseHtml()");
                        webView.evaluateJavascript("javascript:parseHtml()", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                // 此处为 js 返回的结果
//                                SharedPreferences sp = getSharedPreferences("COURSE_DATA", Context.MODE_PRIVATE);//创建sp对象
//                                SharedPreferences.Editor editor = sp.edit();
//                                editor.clear();
//                                editor.putString("HTML_TO_SUBJECT", value); //存入json串
//                                editor.commit();//提交
                                SharedPreferencesUtil.init(ContextApplication.getAppContext(),"COURSE_DATA").putString("HTML_TO_SUBJECT", value);
                                SharedPreferencesUtil.init(ContextApplication.getAppContext(),"COURSE_DATA").remove("SUBJECT_LIST");
                                Log.e(TAG, "HTML_TO_SUBJECT: " + value);

                                Intent intent = new Intent(ParseHtmlActivity.this, MainActivity.class);
                                if(MainActivity.mainActivity != null){
                                    MainActivity.mainActivity.finish(); // 销毁MainActivity
                                }
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                });
            }
        });
    }


}