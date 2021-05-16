package com.example.hhuclassschedule;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuangfei.timetable.model.Schedule;

import java.util.List;

import javax.security.auth.Subject;

public class AddCourseActivity extends AppCompatActivity {

    private static final String TAG = "AddCourseActivity";

    String title;
    List<Schedule> scheduletList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        title = (String)getIntent().getExtras().get("title");
        scheduletList = (List<Schedule>)getIntent().getSerializableExtra("scheduleList");

        initTollbar(title);

        editSubject(scheduletList);
    }

    protected void initTollbar(String title){

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void editSubject(List<Schedule> beans){
        LinearLayout linearLayout = findViewById(R.id.ll_add_course_detail);
        // 课程名
        EditText et_courseName = findViewById(R.id.et_name);
        et_courseName.setText(beans.get(0).getName());
        RelativeLayout rl_indlude_detail = linearLayout.findViewById(R.id.include_add_course_detail);
        // 周数
        TextView et_weeks = rl_indlude_detail.findViewById(R.id.et_weeks);
        String str_weeks = "第" + beans.get(0).getWeekList().get(0) + "-" + beans.get(0).getWeekList().get(beans.get(0).getWeekList().size() - 1) + "周";
        et_weeks.setText(str_weeks);
        // 节数
        String[] arrayday = {"一","二","三","四","五","六","日"};
        TextView et_time = rl_indlude_detail.findViewById(R.id.et_time);
        String str_time = "周" + arrayday[beans.get(0).getDay()-1] + "   第" + beans.get(0).getStart() + "-" + (beans.get(0).getStart() + beans.get(0).getStep() - 1) + "节";
        et_time.setText(str_time);
        // 老师
        TextView et_teacher = rl_indlude_detail.findViewById(R.id.et_teacher);
        et_teacher.setText(beans.get(0).getTeacher());
        // 教室
        TextView et_room = rl_indlude_detail.findViewById(R.id.et_room);
        et_room.setText(beans.get(0).getRoom());

        // 删除时间段
        TextView tv_ib_delete = rl_indlude_detail.findViewById(R.id.ib_delete);
        tv_ib_delete.setClickable(true);
        tv_ib_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "至少要保留一个时间段";
                Toast.makeText(AddCourseActivity.this,str,Toast.LENGTH_SHORT).show();
            }
        });
    }
}