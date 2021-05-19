package com.example.hhuclassschedule;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuangfei.timetable.model.Schedule;

import java.util.List;

import javax.security.auth.Subject;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class AddCourseActivity extends AppCompatActivity {

    private static final String TAG = "AddCourseActivity";

    String title;
    List<Schedule> scheduletList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        title = (String) getIntent().getExtras().get("title");
        initToolbar(title);
        if (title.equals("编辑课程")) {
            scheduletList = (List<Schedule>) getIntent().getSerializableExtra("scheduleList");
            editSubject(scheduletList);
        } else {
            int day = (int) getIntent().getExtras().get("day");
            int start = (int) getIntent().getExtras().get("start");
            addSubject(day, start);
        }
    }

    protected void initToolbar(String title) {

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 设置title
        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.savemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.btn_save) {
            //finish();
            Toast.makeText(AddCourseActivity.this, "保存", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 编辑课程
    protected void editSubject(List<Schedule> beans) {
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
        String[] arrayday = {"一", "二", "三", "四", "五", "六", "日"};
        TextView et_time = rl_indlude_detail.findViewById(R.id.et_time);
        String str_time = "周" + arrayday[beans.get(0).getDay() - 1] + "   第" + beans.get(0).getStart() + "-" + (beans.get(0).getStart() + beans.get(0).getStep() - 1) + "节";
        et_time.setText(str_time);
        // 老师
        EditText et_teacher = rl_indlude_detail.findViewById(R.id.et_teacher);
        et_teacher.setText(beans.get(0).getTeacher());
        // 教室
        EditText et_room = rl_indlude_detail.findViewById(R.id.et_room);
        et_room.setText(beans.get(0).getRoom());

        // 删除时间段
        TextView tv_ib_delete = rl_indlude_detail.findViewById(R.id.ib_delete);
        tv_ib_delete.setClickable(true);
        tv_ib_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "至少要保留一个时间段";
                Toast.makeText(AddCourseActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 添加课程
    protected void addSubject(int day, int start) {

        LinearLayout linearLayout = findViewById(R.id.ll_add_course_detail);
        // 课程名
        EditText et_courseName = findViewById(R.id.et_name);
        //     et_courseName.setText(beans.get(0).getName());
        RelativeLayout rl_indlude_detail = linearLayout.findViewById(R.id.include_add_course_detail);
        // 周数
        TextView et_weeks = rl_indlude_detail.findViewById(R.id.et_weeks);
        //     String str_weeks = "第" + beans.get(0).getWeekList().get(0) + "-" + beans.get(0).getWeekList().get(beans.get(0).getWeekList().size() - 1) + "周";
        //     et_weeks.setText(str_weeks);
        et_weeks.setClickable(true);
        et_weeks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectWeek();
            }
        });
        // 节数
        TextView et_time = rl_indlude_detail.findViewById(R.id.et_time);
        String[] arrayday = {"一", "二", "三", "四", "五", "六", "日"};
        start = start % 2 == 0 ? start - 1 : start;
        String str_time = "周" + arrayday[day] + "   第" + start + "-" + (start + 1) + "节";
        et_time.setText(str_time);
        et_time.setClickable(true);
        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime();
            }
        });
        // 老师
        EditText et_teacher = rl_indlude_detail.findViewById(R.id.et_teacher);

        // 教室
        EditText et_room = rl_indlude_detail.findViewById(R.id.et_room);

        // 删除时间段
        TextView tv_ib_delete = rl_indlude_detail.findViewById(R.id.ib_delete);
        tv_ib_delete.setClickable(true);
        tv_ib_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "至少要保留一个时间段";
                Toast.makeText(AddCourseActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 选择时间
    protected void selectTime(){
        View selectTimeDetail = getLayoutInflater().inflate(R.layout.fragment_select_time,null);
        initTimePicker(selectTimeDetail);
        // 设置自定义布局
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(selectTimeDetail);
        final AlertDialog dialog = builder.show();
    }

    protected  void initTimePicker(View selectTimeDetail){
        NumberPickerView dayPicker = selectTimeDetail.findViewById(R.id.time_day);
        String[] week = {"周一","周二","周三","周四","周五","周六","周日"};
        dayPicker.setDisplayedValues(week);
        //设置最大值
        dayPicker.setMaxValue(week.length-1);
        //设置最小值
        dayPicker.setMinValue(0);
        //设置当前值
        dayPicker.setValue(1);
        //设置滑动监听
        dayPicker.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            //当NunberPicker的值发生改变时，将会激发该方法
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                String toast = "oldVal：" + oldVal + "   newVal：" + newVal;
             //   Toast.makeText(AddCourseActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });

        NumberPickerView sectionStartPicker = selectTimeDetail.findViewById(R.id.time_start);
        String[] sectionStart = {"第1节","第2节","第3节","第4节","第5节","第6节","第7节","第8节","第9节","第10节","第11节","第12节","第13节","第14节","第15节"};
        sectionStartPicker.setDisplayedValues(sectionStart);
        //设置最大值
        sectionStartPicker.setMaxValue(sectionStart.length-1);
        //设置最小值
        sectionStartPicker.setMinValue(0);
        //设置当前值
        sectionStartPicker.setValue(1);

        NumberPickerView sectionEndPicker = selectTimeDetail.findViewById(R.id.time_end);
        String[] sectionEnd = {"第1节","第2节","第3节","第4节","第5节","第6节","第7节","第8节","第9节","第10节","第11节","第12节","第13节","第14节","第15节"};
        sectionEndPicker.setDisplayedValues(sectionEnd);
        //设置最大值
        sectionEndPicker.setMaxValue(sectionEnd.length-1);
        //设置最小值
        sectionEndPicker.setMinValue(0);
        //设置当前值
        sectionEndPicker.setValue(1);
    }

    // 选择周数
    protected void selectWeek(){
        View selectWeekDetail = getLayoutInflater().inflate(R.layout.fragment_select_week,null);
        initWeekPicker(selectWeekDetail);
        // 设置自定义布局
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(selectWeekDetail);
        final AlertDialog dialog = builder.show();
    }

    protected  void initWeekPicker(View selectWeekDetail){

        NumberPickerView weekStartPicker = selectWeekDetail.findViewById(R.id.week_start);
        String[] weekStart = {"第1周","第2周","第3周","第4周","第5周","第6周","第7周","第8周","第9周","第10周","第11周","第12周","第13周","第14周","第15周",
                "第16周","第17周","第18周","第19周","第20周","第21周","第22周","第23周","第24周","第25周","第26周","第27周","第28周","第29周","第30周"};
        weekStartPicker.setDisplayedValues(weekStart);
        //设置最大值
        weekStartPicker.setMaxValue(weekStart.length-1);
        //设置最小值
        weekStartPicker.setMinValue(0);
        //设置当前值
        weekStartPicker.setValue(1);

        NumberPickerView weekEndPicker = selectWeekDetail.findViewById(R.id.week_end);
        String[] weekEnd = {"第1周","第2周","第3周","第4周","第5周","第6周","第7周","第8周","第9周","第10周","第11周","第12周","第13周","第14周","第15周",
                "第16周","第17周","第18周","第19周","第20周","第21周","第22周","第23周","第24周","第25周","第26周","第27周","第28周","第29周","第30周"};
        weekEndPicker.setDisplayedValues(weekEnd);
        //设置最大值
        weekEndPicker.setMaxValue(weekEnd.length-1);
        //设置最小值
        weekEndPicker.setMinValue(0);
        //设置当前值
        weekEndPicker.setValue(1);
    }
}