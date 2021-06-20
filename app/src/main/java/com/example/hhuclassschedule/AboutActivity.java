package com.example.hhuclassschedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


/**
 * 关于界面
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        initToolbar("关于");
        initLinkTextView();
    }

    /**
     * 初始化 toolbar
     *
     * @param title toolbar标题
     */
    protected void initToolbar(String title) {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 设置title
        // getSupportActionBar().setTitle(title);
        TextView textView = findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// 添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
        textView.setText(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 超链接添加点击事件
     */
    private void initLinkTextView() {
        TextView tv_github = findViewById(R.id.tv_github);
        tv_github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(getString(R.string.github));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        TextView tv_yuchenii = findViewById(R.id.tv_yuchenii);
        tv_yuchenii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(getString(R.string.yuchenii));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        TextView tv_fudi2000 = findViewById(R.id.tv_fudi2000);
        tv_fudi2000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(getString(R.string.fudi2000));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        TextView tv_scandy316 = findViewById(R.id.tv_scandy316);
        tv_scandy316.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(getString(R.string.scandy316));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        TextView tv_chlcoder = findViewById(R.id.tv_chlcoder);
        tv_chlcoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(getString(R.string.chlcoder));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        TextView tv_hhuzyp = findViewById(R.id.tv_hhuzyp);
        tv_hhuzyp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(getString(R.string.hhuzyp));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        TextView tv_xuanipvp = findViewById(R.id.tv_xuanipvp);
        tv_xuanipvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(getString(R.string.xuanipvp));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}
