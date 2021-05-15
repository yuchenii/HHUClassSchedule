package com.example.hhuclassschedule;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hhuclassschedule.adapter.OnDateDelayAdapter;
import com.example.hhuclassschedule.adapter.OnDateDelayAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.IWeekView;
import com.zhuangfei.timetable.listener.OnItemBuildAdapter;
import com.zhuangfei.timetable.listener.OnSlideBuildAdapter;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.view.WeekView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "MainActivity";

    TimetableView mTimetableView;
    WeekView mWeekView;

    Button moreButton;
    LinearLayout layout;
    TextView titleTextView;
    List<MySubject> mySubjects;


    //记录切换的周次，不一定是当前周
    int target = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moreButton = findViewById(R.id.id_more);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopmenu();
            }
        });

//       mySubjects = SubjectRepertory.loadDefaultSubjects2();
//       mySubjects.addAll(SubjectRepertory.loadDefaultSubjects());

        SharedPreferences sp = getSharedPreferences("SP_Data_List", Activity.MODE_PRIVATE);//创建sp对象
        String peopleListJson = sp.getString("KEY_Data_List_DATA", null);
        if (peopleListJson == null) {
            mySubjects = SubjectRepertory.loadDefaultSubjects();
            if (!mySubjects.isEmpty()) {
                toSaveSubjects(mySubjects);
            }
        } else {
            mySubjects = toGetSubjects();
        }


        titleTextView = findViewById(R.id.id_title);
        layout = findViewById(R.id.id_layout);
        layout.setOnClickListener(this);
        initTimetableView();
    }


    /**
     * 更新一下，防止因程序在后台时间过长（超过一天）而导致的日期或高亮不准确问题。
     */
    @Override
    protected void onStart() {
        super.onStart();

//        mTimetableView.onDateBuildListener()
//                .onHighLight();

        //用于更正日期的显示
        int cur = mTimetableView.curWeek();
        mTimetableView.onDateBuildListener().onUpdateDate(cur, cur);
//        mTimetableView.onDateBuildListener().onHighLight();

        //更新文本
        OnDateDelayAdapter adapter = (OnDateDelayAdapter) mTimetableView.onDateBuildListener();
        long when = adapter.whenBeginSchool();
        if (when > 0) {
            titleTextView.setText("距离开学还有" + when + "天");
        }
    }


    /**
     * 初始化课程控件
     */
    protected void initTimetableView() {
        //获取控件
        mWeekView = findViewById(R.id.id_weekview);
        mTimetableView = findViewById(R.id.id_timetableView);

        //设置周次选择属性
        mWeekView.source(mySubjects)
                //     .curWeek(1)
                .callback(new IWeekView.OnWeekItemClickedListener() {
                    @Override
                    public void onWeekClicked(int week) {
                        int cur = mTimetableView.curWeek();
                        //更新切换后的日期，从当前周cur->切换的周week
                        mTimetableView.onDateBuildListener()
                                .onUpdateDate(cur, week);
                        mTimetableView.changeWeekOnly(week);
                    }
                })
                .callback(new IWeekView.OnWeekLeftClickedListener() {
                    @Override
                    public void onWeekLeftClicked() {
                        onWeekLeftLayoutClicked();
                    }
                })
                .isShow(false)//设置隐藏，默认显示
                .showView();

        mTimetableView.source(mySubjects)
                //      .curWeek(1)
                .curTerm(null)
                .maxSlideItem(12)
                .monthWidthDp(30)
                .cornerAll(20)
                .marTop(10)
                .marLeft(10)
                //透明度
                //日期栏0.1f、侧边栏0.1f，周次选择栏0.6f
                //透明度范围为0->1，0为全透明，1为不透明
//                .alpha(0.1f, 0.1f, 0.6f)
                .callback(new ISchedule.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, List<Schedule> scheduleList) {
                        // display(scheduleList);
                        showCourseDetail(scheduleList);
                    }
                })
                .callback(new ISchedule.OnItemLongClickListener() {
                    @Override
                    public void onLongClick(View v, int day, int start, int id) {
                        Toast.makeText(MainActivity.this,
                                "长按:周" + day + ",第" + start + "节" + ",id: " + id,
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .callback(getDateDelayAdapter())//这行要放在下行的前边
                .callback(new ISchedule.OnWeekChangedListener() {
                    @Override
                    public void onWeekChanged(int curWeek) {
                        if (mTimetableView.onDateBuildListener() instanceof OnDateDelayAdapter) {
                            OnDateDelayAdapter adapter = (OnDateDelayAdapter) mTimetableView.onDateBuildListener();
                            long when = adapter.whenBeginSchool();
                            if (when > 0) {
                                titleTextView.setText("距离开学还有" + when + "天");
                            } else {
                                titleTextView.setText("第" + curWeek + "周");
                            }
                        }
                    }
                })
                //旗标布局点击监听
                .callback(new ISchedule.OnFlaglayoutClickListener() {
                    @Override
                    public void onFlaglayoutClick(int day, int start) {
                        mTimetableView.hideFlaglayout();
                        Toast.makeText(MainActivity.this,
                                "点击了旗标:周" + (day + 1) + ",第" + start + "节",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .callback(new OnItemBuildAdapter() {
                    @Override
                    public void onItemUpdate(FrameLayout layout, TextView textView, TextView countTextView, Schedule schedule, GradientDrawable gd) {
                        super.onItemUpdate(layout, textView, countTextView, schedule, gd);
                        //可见说明重叠，取消角标，添加角度
                        if (countTextView.getVisibility() == View.VISIBLE) {
                            countTextView.setVisibility(View.GONE);
                            //       gd.setCornerRadii(new float[]{0, 0, 20, 20, 0, 0, 0, 0});
                        }
                    }
                })
                .showView();
    }


    /**
     * 配置OnDateDelayAdapter
     */
    public OnDateDelayAdapter getDateDelayAdapter() {
        OnDateDelayAdapter onDateDelayAdapter = new OnDateDelayAdapter();

        //计算开学时间戳
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long startTime = 0;
        try {
            startTime = sdf.parse("2021-04-05 00:00").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //计算开学时的一周日期，我这里模拟一下
        List<String> dateList = Arrays.asList("9", "03", "04", "05", "06", "07", "08", "09");

        //设置
        onDateDelayAdapter.setStartTime(startTime);
        onDateDelayAdapter.setDateList(dateList);
        return onDateDelayAdapter;
    }


    /**
     * 周次选择布局的左侧被点击时回调<br/>
     * 对话框修改当前周次
     */
    protected void onWeekLeftLayoutClicked() {
        final String items[] = new String[20];
        int itemCount = mWeekView.itemCount();
        for (int i = 0; i < itemCount; i++) {
            items[i] = "第" + (i + 1) + "周";
        }
        target = -1;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置当前周");
        builder.setSingleChoiceItems(items, mTimetableView.curWeek() - 1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        target = i;
                    }
                });
        builder.setPositiveButton("设置为当前周", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (target != -1) {
                    mWeekView.curWeek(target + 1).updateView();
                    mTimetableView.changeWeekForce(target + 1);
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }


    protected void showCourseDetail(List<Schedule> beans) {

        View courseDetail = getLayoutInflater().inflate(R.layout.fragment_course_detail, null);
        RelativeLayout rl_indlude_detail = courseDetail.findViewById(R.id.include_detail);
        // 课程名
        TextView tv_item = rl_indlude_detail.findViewById(R.id.tv_item);
        tv_item.setText(beans.get(0).getName());
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

        // 设置自定义布局
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(courseDetail);
        final AlertDialog dialog = builder.show();

        // 关闭dialog
        TextView tv_ib_delete = rl_indlude_detail.findViewById(R.id.ib_delete);
        tv_ib_delete.setClickable(true);
        tv_ib_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 删除课程
        TextView tv_delete_course = courseDetail.findViewById(R.id.ib_delete_course);
        tv_delete_course.setClickable(true);

        tv_delete_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv_tips = courseDetail.findViewById(R.id.tv_tips);
                if(tv_tips.getVisibility()==View.GONE){
                    tv_tips.setVisibility(View.VISIBLE);
                }else{
                    int delete_id = (int)beans.get(0).getExtras().get("extras_id");
                    deleteSubject(delete_id);
                    dialog.dismiss();
                }
            }
        });

        // 编辑课程
        TextView tv_edit_course = courseDetail.findViewById(R.id.ib_edit);
        tv_edit_course.setClickable(true);
        tv_edit_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String str = "编辑课程";
//                Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, addCourseActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
    }

    /**
     * 显示内容
     *
     * @param beans
     */
    protected void display(List<Schedule> beans) {
        String str = "";
        for (Schedule bean : beans) {
            str += bean.getName() + "," + bean.getWeekList().toString() + "," + bean.getStart() + "," + bean.getStep() + "," + bean.getExtras() + "\n";
        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_layout:
                //如果周次选择已经显示了，那么将它隐藏，更新课程、日期
                //否则，显示
                if (mWeekView.isShowing()) hideWeekView();
                else showWeekView();
                break;
        }
    }


    /**
     * 显示弹出菜单
     */
    public void showPopmenu() {
        PopupMenu popup = new PopupMenu(this, moreButton);
        popup.getMenuInflater().inflate(R.menu.popmenu_base_func, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.top1:
                        addSubject();
                        break;
                    case R.id.top2:
                        deleteSubject(18312);
                        break;

                    case R.id.top4:
                        hideNonThisWeek();
                        break;
                    case R.id.top5:
                        showNonThisWeek();
                        break;
                    case R.id.top6:
                        setMaxItem(8);
                        break;
                    case R.id.top7:
                        setMaxItem(10);
                        break;
                    case R.id.top8:
                        setMaxItem(12);
                        break;
                    case R.id.top9:
                        showTime();
                        break;
                    case R.id.top10:
                        hideTime();
                        break;
                    case R.id.top11:
                        showWeekView();
                        break;
                    case R.id.top12:
                        hideWeekView();
                        break;
                    case R.id.top13:
                        setMonthWidth();
                        break;
                    case R.id.top16:
                        resetMonthWidth();
                        break;
                    case R.id.top14:
                        hideWeekends();
                        break;
                    case R.id.top15:
                        showWeekends();
                        break;
                    case R.id.top17:
                        setMarginAndCorner();
                        break;
                    case R.id.top18:
                        modifyOverlayStyle();
                        break;
                    case R.id.top19:
                        Intent intent = new Intent(MainActivity.this, parseHtmlActivity.class);
                        startActivity(intent);
                        break;

                    default:
                        break;
                }
                return true;
            }
        });

        popup.show();
    }


    /**
     * 删除课程
     * 内部使用集合维护课程数据，操作集合的方法来操作它即可
     * 最后更新一下视图（全局更新）
     */
    protected void deleteSubject(int delete_id) {
//        int size = mTimetableView.dataSource().size();
//        int pos = (int) (Math.random() * size);
//        if (size > 0) {
//            mTimetableView.dataSource().remove(pos);
//            mTimetableView.updateView();
//            Log.e("MainActivity","delteDataSource: "+mTimetableView.dataSource().toString());
//        }
        List<Schedule> ds = mTimetableView.dataSource();
        Iterator<Schedule> it = ds.iterator();
        while (it.hasNext()) {
            Schedule next = it.next();
            int id = (int) next.getExtras().get("extras_id");
            if (id == delete_id) {
                it.remove();
                break;
            }
        }
        mTimetableView.updateView();

        List<MySubject> ms = mySubjects;
        Iterator<MySubject> iterator = ms.iterator();
        while (iterator.hasNext()) {
            MySubject next = iterator.next();
            int id = next.getId();
            if (id == delete_id) {
                iterator.remove();
                break;
            }
        }
        toSaveSubjects(mySubjects);

    }

    /**
     * 添加课程
     * 内部使用集合维护课程数据，操作集合的方法来操作它即可
     * 最后更新一下视图（全局更新）
     */
    protected void addSubject() {
//        List<Schedule> dataSource = mTimetableView.dataSource();
//        int size = dataSource.size();
//        if (size > 0) {
//            Schedule schedule = dataSource.get(0);
//            dataSource.add(schedule);
//            mTimetableView.updateView();
//        }
        List<Integer> weeks = Arrays.asList(1, 2, 3, 4);
        MySubject mysubject = new MySubject(null, "Test", "寝室", "张三", weeks, 1, 2, 1, -1, null);
        Schedule schedule = new Schedule("Test", "寝室", "张三", weeks, 1, 2, 1, -1);
        mySubjects.add(mysubject);


        mTimetableView.dataSource().add(schedule);
        mTimetableView.updateView();
        toSaveSubjects(mySubjects);
        initTimetableView();
    }

    /**
     * 隐藏非本周课程
     * 修改了内容的显示，所以必须更新全部（性能不高）
     * 建议：在初始化时设置该属性
     * updateView()被调用后，会重新构建课程，课程会回到当前周
     */
    protected void hideNonThisWeek() {
        mTimetableView.isShowNotCurWeek(false).updateView();
    }

    /**
     * 显示非本周课程
     * 修改了内容的显示，所以必须更新全部（性能不高）
     * 建议：在初始化时设置该属性
     */
    protected void showNonThisWeek() {
        mTimetableView.isShowNotCurWeek(true).updateView();
    }

    /**
     * 设置侧边栏最大节次，只影响侧边栏的绘制，对课程内容无影响
     *
     * @param num
     */
    protected void setMaxItem(int num) {
        mTimetableView.maxSlideItem(num).updateSlideView();
    }

    /**
     * 显示时间
     * 设置侧边栏构建监听，TimeSlideAdapter是控件实现的可显示时间的侧边栏
     */
    protected void showTime() {
        String[] times = new String[]{
                "8:00", "9:00", "10:10", "11:00",
                "15:00", "16:00", "17:00", "18:00",
                "19:30", "20:30", "21:30", "22:30"
        };
        OnSlideBuildAdapter listener = (OnSlideBuildAdapter) mTimetableView.onSlideBuildListener();
        listener.setTimes(times)
                .setTimeTextColor(Color.BLACK);
        mTimetableView.updateSlideView();
    }

    /**
     * 隐藏时间
     * 将侧边栏监听置Null后，会默认使用默认的构建方法，即不显示时间
     * 只修改了侧边栏的属性，所以只更新侧边栏即可（性能高），没有必要更新全部（性能低）
     */
    protected void hideTime() {
        mTimetableView.callback((ISchedule.OnSlideBuildListener) null);
        mTimetableView.updateSlideView();
    }


    /**
     * 隐藏周次选择，此时需要将课表的日期恢复到本周并将课表切换到当前周
     */
    public void hideWeekView() {
        mWeekView.isShow(false);
        titleTextView.setTextColor(getResources().getColor(R.color.app_course_textcolor_blue));
        int cur = mTimetableView.curWeek();
        mTimetableView.onDateBuildListener()
                .onUpdateDate(cur, cur);
        mTimetableView.changeWeekOnly(cur);
    }

    /**
     * 隐藏WeekView
     */
    public void showWeekView() {
        mWeekView.isShow(true);
        titleTextView.setTextColor(getResources().getColor(R.color.app_red));
    }

    /**
     * 设置月份宽度
     */
    private void setMonthWidth() {
        mTimetableView.monthWidthDp(50).updateView();
    }

    /**
     * 设置月份宽度,默认40dp
     */
    private void resetMonthWidth() {
        mTimetableView.monthWidthDp(40).updateView();
    }

    /**
     * 隐藏周末
     */
    private void hideWeekends() {
        mTimetableView.isShowWeekends(false).updateView();
    }

    /**
     * 显示周末
     */
    private void showWeekends() {
        mTimetableView.isShowWeekends(true).updateView();
    }


    /**
     * 设置间距以及弧度
     * 该方法只能同时设置四个角的弧度，设置单个角的弧度可参考下文
     */
    protected void setMarginAndCorner() {
        mTimetableView.cornerAll(20)
                .marTop(10)
                .marLeft(10)
                .updateView();
    }


    /**
     * 修改课程重叠的样式，在该接口中，你可以自定义出很多的效果
     */
    protected void modifyOverlayStyle() {
        mTimetableView.callback(new OnItemBuildAdapter() {
            @Override
            public void onItemUpdate(FrameLayout layout, TextView textView, TextView countTextView, Schedule schedule, GradientDrawable gd) {
                super.onItemUpdate(layout, textView, countTextView, schedule, gd);
                //可见说明重叠，取消角标，添加角度
                if (countTextView.getVisibility() == View.VISIBLE) {
                    countTextView.setVisibility(View.GONE);
                    //       gd.setCornerRadii(new float[]{0, 0, 20, 20, 0, 0, 0, 0});
                }
            }
        });
        mTimetableView.updateView();
    }


    public void toSaveSubjects(List<MySubject> course) {

        Gson gson = new Gson();
        String str = gson.toJson(course);
        SharedPreferences sp = getSharedPreferences("SP_Data_List", Activity.MODE_PRIVATE);//创建sp对象
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("KEY_Data_List_DATA", str); //存入json串
        editor.commit();//提交
        Log.e(TAG, "toSaveSubjects: " + str);//peopleListJson便是取出的数据了

    }

    public List<MySubject> toGetSubjects() {

        SharedPreferences sp = getSharedPreferences("SP_Data_List", Activity.MODE_PRIVATE);//创建sp对象
        String subjectJSON = sp.getString("KEY_Data_List_DATA", null);  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
        Log.e(TAG, "toGetSubjects: " + subjectJSON);//peopleListJson便是取出的数据了
        Gson gson = new Gson();
        List<MySubject> subjectList = gson.fromJson(subjectJSON, new TypeToken<List<MySubject>>() {
        }.getType());
        return subjectList;
    }

}