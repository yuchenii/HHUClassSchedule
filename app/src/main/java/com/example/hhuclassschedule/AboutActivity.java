package com.example.hhuclassschedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import es.dmoral.toasty.Toasty;

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
     * @param title toolbar标题
     */
    protected void initToolbar(String title) {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 设置title
        // getSupportActionBar().setTitle(title);
        TextView textView = findViewById(R.id.toolbar_title);
        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) textView.getLayoutParams();
        layoutParams.setMarginStart(160);
        textView.setLayoutParams(layoutParams);
        textView.setText(title);
    }

    private void initLinkTextView() {
        TextView tv_github = findViewById(R.id.tv_github);
        tv_github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://github.com/yuchenii/HHUClassSchedule");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        findViewById(R.id.tv_yuchenii).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(getString(R.string.yuchenii));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

}