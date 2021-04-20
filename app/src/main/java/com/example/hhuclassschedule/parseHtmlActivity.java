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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class parseHtmlActivity extends AppCompatActivity {



    private static final String TAG = "parseHtmlActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_html);

    }



    WebView webview;
    Button btn;
    String Result;

    @SuppressLint("SetJavaScriptEnabled")
    public void btn_openWeb(View view) throws IOException {
        webview = (WebView) findViewById(R.id.my_web);
        WebSettings ws = webview.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setBuiltInZoomControls(true);
        ws.setAllowFileAccess(true);

        //  webview.loadUrl("file:///android_asset/javascript.html");
        //  webview.loadUrl("http://jwxs.hhu.edu.cn");


        InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open("parseHtml.js"));
        BufferedReader bufReader = new BufferedReader(inputReader);
        String line = "";
        //  String Result = "";
        Result = "";
        while ((line = bufReader.readLine()) != null)
            Result += line;

        Log.e("myout: ","value0="+Result);


        webview.loadUrl(Result);
        webview.loadUrl("http://jwxs.hhu.edu.cn");
        //   webview.loadUrl("file:///android_asset/javascript.html");





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
                    webview.evaluateJavascript(Result, new ValueCallback<String>() {
                        @Override public void onReceiveValue(String value) {//js与native交互的回调函数
                            Log.e("myout: ", "value2=" + value);
                        }
                    });
                    String str = "nihao";
                    Log.e("myout: ", "value3=" + str);
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



        btn = (Button) findViewById(R.id.button8);

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
                        webview.evaluateJavascript("javascript:callJS()", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                //此处为 js 返回的结果
                                SharedPreferences sp = getSharedPreferences("SP_Data_List", Context.MODE_PRIVATE);//创建sp对象
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("htmlToString", value); //存入json串
                                editor.commit();//提交
                                Log.e(TAG, "htmlToString: "+ value);
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