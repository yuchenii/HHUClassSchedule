package com.example.hhuclassschedule;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class parseHtmlActivity extends AppCompatActivity {

    private static final String TAG = "parseHtmlActivity";

    WebView webview;
    TextView btn;
    String parseHtmlJS;
    String URL = "http://jwxs.hhu.edu.cn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_html);
        try {
            btn_openWeb();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @SuppressLint("SetJavaScriptEnabled")
    public void btn_openWeb() throws IOException {
        webview = (WebView) findViewById(R.id.classWeb);
        WebSettings ws = webview.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setBuiltInZoomControls(true);  // 开启缩放
        ws.setDisplayZoomControls(false);  // 隐藏原生的缩放控件
        ws.setAllowFileAccess(true);


        InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open("parseHtml.js"));
        BufferedReader bufReader = new BufferedReader(inputReader);
        String line = "";
        parseHtmlJS = "";
        while ((line = bufReader.readLine()) != null)
            parseHtmlJS += line;

        Log.e(TAG,"parseHtmlJS: "+parseHtmlJS);

        webview.loadUrl(parseHtmlJS);
        webview.loadUrl(URL);

        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String Url) {
                view.loadUrl(Url);
                return true;
            }

            // js 注入
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                if (Build.VERSION.SDK_INT >= 19) {
                    webview.evaluateJavascript(parseHtmlJS, new ValueCallback<String>() {
                        @Override public void onReceiveValue(String value) {//js与native交互的回调函数
                            Log.e(TAG, "insertJs: " + value);
                        }
                    });
                    String str = "PageStarted";
                    Log.e(TAG, "onPageStarted: " + str);
                }
            }

//            @Override
//            public void onPageFinished(WebView view, String url){
//                if (Build.VERSION.SDK_INT >= 19) {
//                    webview.evaluateJavascript(Result, new ValueCallback<String>() {
//                        @Override public void onReceiveValue(String value) {//js与native交互的回调函数
//                            Log.e("myout: ", "value2=" + value);
//                        }
//                    });
//                    String str = "nihao";
//                    Log.e("myout: ", "value3=" + str);
//                }
//            }

        });

        // alert 弹窗
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(parseHtmlActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;

            }

        });



        btn = (TextView) findViewById(R.id.tv_button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 通过Handler发送消息
                webview.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {

                        // 注意调用的JS方法名要对应上
                        // 调用javascript的callJS()方法
                        // webview.loadUrl("javascript:callJS()");
                        webview.evaluateJavascript("javascript:parseHtml()", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                //此处为 js 返回的结果
                                SharedPreferences sp = getSharedPreferences("SP_Data_List", Context.MODE_PRIVATE);//创建sp对象
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("htmlToString", value); //存入json串
                                editor.commit();//提交
                                Log.e(TAG, "htmlToString: "+ value);

                                // 更新课表
                                MainActivity mainActivity = new MainActivity();
                                mainActivity.mySubjects = SubjectRepertory.loadDefaultSubjects();
                                mainActivity.initTimetableView();
                            }
                        });

                    }
                });

            }
        });


    }


//    public void btn_getClass(View view) {
//        webview.post(new Runnable() {
//            @Override
//            public void run() {
//                webview.loadUrl("javascript:callJS()");
//            }
//        });
//    }

}