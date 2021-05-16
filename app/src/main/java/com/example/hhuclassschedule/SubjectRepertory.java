package com.example.hhuclassschedule;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.hhuclassschedule.MySubject.root;
import com.example.hhuclassschedule.MySubject.root.CourseInfosDTO;
import com.example.hhuclassschedule.util.ContextApplication;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据类，加载课程数据
 * @author zf
 *
 */
public class SubjectRepertory  {

    private static final String TAG = "SubjectRepertory";


    public static List<MySubject> loadDefaultSubjects(){
        //json转义
//        String json="[[\"2017-2018学年秋\", \"\", \"\", \"计算机组成原理\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"1周上\", 1, 1, 2, \"\", \"计算机综合楼106\", \"\"]," +
//                "[\"2017-2018学年秋\", \"\", \"\", \"hahaha\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"2周上\", 1, 1, 4, \"\", \"计算机综合楼106\", \"\"],"+
//                " [\"2017-2018学年秋\", \"\", \"\", \"算法分析与设计\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1周\", 1, 3, 2, \"\", \"计算机综合楼205\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"毛泽东思想和中国特色社会主义理论体系概论\", \"\", \"\", \"\", \"\", \"杨晓军\", \"\", \"\", \"6-12,14-17周上\", 1, 5, 2, \"\", \"3号教学楼3208\", \"\"]," +
//                " [\"2017-2018学年秋\", \"\", \"\", \"高等数学提高\", \"\", \"\", \"\", \"\", \"彭波\", \"\", \"\", \"3-12周\", 1, 9, 2, \"\", \"3号教学楼3101\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"面向对象分析与设计\", \"\", \"\", \"\", \"\", \"马永强\", \"\", \"\", \"1-8周\", 2, 1, 2, \"\", \"计算机综合楼106\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"软件工程\", \"\", \"\", \"\", \"\", \"马永强\", \"\", \"\", \"6-12,14-18周上\", 2, 3, 2, \"\", \"计算机综合楼106\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"Linux原理与应用\", \"\", \"\", \"\", \"\", \"刘永利\", \"\", \"\", \"9-12,14-15周上\", 2, 9, 2, \"\", \"计算机综合楼205\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"计算机组成原理\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"8-12,14-17周上\", 3, 1, 2, \"\", \"计算机综合楼106\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"算法分析与设计\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1-12周\", 3, 3, 2, \"\", \"计算机综合楼205\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"毛泽东思想和中国特色社会主义理论体系概论\", \"\", \"\", \"\", \"\", \"杨晓军\", \"\", \"\", \"6-12,14-17周上\", 3, 5, 2, \"\", \"3号教学楼3208\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"高等数学提高\", \"\", \"\", \"\", \"\", \"彭波\", \"\", \"\", \"3-4周上\", 3, 7, 2, \"\", \"3号教学楼3101\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"数据库高级应用\", \"\", \"\", \"\", \"\", \"李国斌\", \"\", \"\", \"9-12,14-18周上\", 3, 9, 2, \"\", \"计算机综合楼202\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"面向对象分析与设计\", \"\", \"\", \"\", \"\", \"马永强\", \"\", \"\", \"1-8周\", 4, 1, 2, \"\", \"计算机综合楼106\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"数字图像处理\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1-10周\", 4, 3, 2, \"\", \"计算机综合楼102\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"数据库高级应用\", \"\", \"\", \"\", \"\", \"李国斌\", \"\", \"\", \"9-12,14-18周上\", 4, 5, 2, \"\", \"计算机综合楼202\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"高等数学提高\", \"\", \"\", \"\", \"\", \"彭波\", \"\", \"\", \"3-12周\", 4, 7, 2, \"\", \"3号教学楼3101\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"Linux原理与应用\", \"\", \"\", \"\", \"\", \"刘永利\", \"\", \"\", \"9-12,14-15周上\", 4, 9, 2, \"\", \"计算机综合楼205\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"计算机组成原理\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"8-12,14-18周上\", 5, 1, 2, \"\", \"计算机综合楼106\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"软件工程\", \"\", \"\", \"\", \"\", \"马永强\", \"\", \"\", \"6-12,14-18周上\", 5, 3, 2, \"\", \"计算机综合楼106\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"毛泽东思想和中国特色社会主义理论体系概论\", \"\", \"\", \"\", \"\", \"杨晓军\", \"\", \"\", \"6-12,14-17周上\", 5, 5, 2, \"\", \"3号教学楼3208\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"高等数学提高\", \"\", \"\", \"\", \"\", \"彭波\", \"\", \"\", \"3-12周\", 5, 7, 2, \"\", \"3号教学楼3101\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"数字图像处理\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1-10周\", 5, 9, 2, \"\", \"计算机综合楼102\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"形势与政策-4\", \"\", \"\", \"\", \"\", \"孔祥增\", \"\", \"\", \"9周上\", 7, 5, 4, \"\", \"3号教学楼3311\", \"\"]]";
        String json = "{\"courseInfos\":[{\"day\":3,\"name\":\"移动互联网应用开发_02\",\"position\":\"江宁校区博学楼B505\",\"sections\":[{\"section\":1},{\"section\":2}],\"teacher\":\"傅晓*\",\"weeks\":[1,2,3,4,5,6,7,8]},{\"day\":4,\"name\":\"数据结构_02\",\"position\":\"江宁校区勤学楼5112\",\"sections\":[{\"section\":1},{\"section\":2}],\"teacher\":\"曹敬* 王彦芳\",\"weeks\":[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16]},{\"day\":5,\"name\":\"软件开发环境（混合式课程）_02\",\"position\":\"江宁校区博学楼B508\",\"sections\":[{\"section\":1},{\"section\":2}],\"teacher\":\"刘凡*\",\"weeks\":[5,6,7,8,9,10,11,12,13,14,15,16]},{\"day\":1,\"name\":\"体育Ⅳ_男7\",\"position\":\"江宁校区操场操场\",\"sections\":[{\"section\":3},{\"section\":4}],\"teacher\":\"刘承*\",\"weeks\":[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16]},{\"day\":2,\"name\":\"数据结构_02\",\"position\":\"江宁校区致高楼B401\",\"sections\":[{\"section\":3},{\"section\":4}],\"teacher\":\"曹敬* 王彦芳\",\"weeks\":[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16]},{\"day\":3,\"name\":\"软件开发环境（混合式课程）_02\",\"position\":\"江宁校区博学楼B407\",\"sections\":[{\"section\":3},{\"section\":4}],\"teacher\":\"刘凡*\",\"weeks\":[5,6,7,8,9,10,11,12,13,14,15,16]},{\"day\":4,\"name\":\"计算机组成与体系结构_02\",\"position\":\"江宁校区博学楼B508\",\"sections\":[{\"section\":3},{\"section\":4}],\"teacher\":\"廖小平 黄倩* 巫义锐\",\"weeks\":[2,3,4,5,6,7,8,9,10,11,12,13]},{\"day\":5,\"name\":\"移动互联网应用开发_02\",\"position\":\"江宁校区博学楼B407\",\"sections\":[{\"section\":3},{\"section\":4}],\"teacher\":\"傅晓*\",\"weeks\":[1,2,3,4,5,6,7,8]},{\"day\":2,\"name\":\"计算机组成与体系结构_02\",\"position\":\"江宁校区博学楼B505\",\"sections\":[{\"section\":6},{\"section\":7}],\"teacher\":\"廖小平 黄倩* 巫义锐\",\"weeks\":[2,3,4,5,6,7,8,9,10,11,12,13]},{\"day\":4,\"name\":\"算法与数据结构课程设计_02\",\"position\":\"江宁校区计信院实验室勤2507\",\"sections\":[{\"section\":6},{\"section\":7},{\"section\":8},{\"section\":9},{\"section\":10},{\"section\":11},{\"section\":12}],\"teacher\":\"施小华*\",\"weeks\":[7,8,9,10,11]},{\"day\":5,\"name\":\"马克思主义基本原理概论_34\",\"position\":\"江宁校区致高楼B404\",\"sections\":[{\"section\":6},{\"section\":7},{\"section\":8}],\"teacher\":\"郑黎明*\",\"weeks\":[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16]},{\"day\":3,\"name\":\"形势与政策IV_1913\",\"position\":\"江宁校区致用楼101\",\"sections\":[{\"section\":8},{\"section\":9}],\"teacher\":\"朱立琴*\",\"weeks\":[11,12,13,14]},{\"day\":4,\"name\":\"移动互联网应用开发实践_02\",\"position\":\"江宁校区计信院实验室勤2507\",\"sections\":[{\"section\":8},{\"section\":9},{\"section\":10},{\"section\":11},{\"section\":12}],\"teacher\":\"余宇峰* 侯婷\",\"weeks\":[12,13,14,15,16]}],\"sectionTimes\":[{\"endTime\":\"08:45\",\"section\":1,\"startTime\":\"08:00\"},{\"endTime\":\"09:35\",\"section\":2,\"startTime\":\"08:50\"},{\"endTime\":\"10:35\",\"section\":3,\"startTime\":\"09:50\"},{\"endTime\":\"11:25\",\"section\":4,\"startTime\":\"10:40\"},{\"endTime\":\"12:15\",\"section\":5,\"startTime\":\"11:30\"},{\"endTime\":\"14:45\",\"section\":6,\"startTime\":\"14:00\"},{\"endTime\":\"15:35\",\"section\":7,\"startTime\":\"14:50\"},{\"endTime\":\"16:35\",\"section\":8,\"startTime\":\"15:50\"},{\"endTime\":\"17:25\",\"section\":9,\"startTime\":\"16:40\"},{\"endTime\":\"19:15\",\"section\":10,\"startTime\":\"18:30\"},{\"endTime\":\"20:05\",\"section\":11,\"startTime\":\"19:20\"},{\"endTime\":\"20:55\",\"section\":12,\"startTime\":\"20:10\"}]}";

        SharedPreferences preferences = ContextApplication.getAppContext().getSharedPreferences("SP_Data_List", Context.MODE_PRIVATE);//创建sp对象
        String htmlToSubject = preferences.getString("HTML_TO_SUBJECT",null);  //取出key为"HTML_TO_SUBJECT"的值，如果值为空，则将第二个参数作为默认值赋值
        Log.e(TAG, "HTML_TO_SUBJECT: "+ htmlToSubject);//HTML_TO_SUBJECT便是取出的数据了
        if(htmlToSubject == null){
            List<MySubject>  mySubject = new ArrayList<>();
            return mySubject;
        }
        return parse(htmlToSubject);
    }

    public static List<MySubject> loadDefaultSubjects2() {
        //json转义
//        String json = "[[\"2017-2018学年秋\", \"\", \"\", \"计算机组成原理\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"1,2,3,4,5\", 1, 1, 4, \"\", \"计算机综合楼106\", \"\"]," +
//                "[\"2017-2018学年秋\", \"\", \"\", \"高等数学\", \"\", \"\", \"\", \"\", \"壮飞\", \"\", \"\", \"1,2,3,7,8\", 1, 2, 2, \"\", \"计算机综合楼106\", \"\"]," +
//                "[\"2017-2018学年秋\", \"\", \"\", \"算法分析与设计\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1,3,5,9,10\", 1, 5, 2, \"\", \"计算机综合楼205\", \"\"]]";
        List<MySubject>  sdf = new ArrayList<>();
        return sdf;
    }



    /**
     * 对json字符串进行解析
     * @param parseString
     * @return
     */
//    public static List<MySubject> parse(String parseString) {
//        List<MySubject> courses = new ArrayList<>();
//        try {
//            JSONArray array = new JSONArray(parseString);
//            for(int i=0;i<array.length();i++){
//                JSONArray array2=array.getJSONArray(i);
//                String term=array2.getString(0);
//                String name=array2.getString(3);
//                String teacher=array2.getString(8);
//                if(array2.length()<=10){
//                    courses.add(new MySubject(term,name,null, teacher, null, -1, -1, -1, -1,null));
//                    continue;
//                }
//                String string=array2.getString(17);
//                if(string!=null){
//                    string=string.replaceAll("\\(.*?\\)", "");
//                }
//                String room=array2.getString(16)+string;
//                String weeks=array2.getString(11);
//                int day,start,step;
//                try {
//                    day=Integer.parseInt(array2.getString(12));
//                    start=Integer.parseInt(array2.getString(13));
//                    step=Integer.parseInt(array2.getString(14));
//                } catch (Exception e) {
//                    day=-1;
//                    start=-1;
//                    step=-1;
//                }
//                courses.add(new MySubject(term,name, room, teacher, getWeekList(weeks), start, step, day, -1,null));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return courses;
//    }

    public static List<MySubject> parse(String parseString){
        List<MySubject> course = new ArrayList<>();
        Gson gson = new Gson();
        root jsonObject = gson.fromJson(parseString,root.class);
        try {
            List<CourseInfosDTO> courseinfo = jsonObject.getCourseInfos();
            for(int i=0; i<courseinfo.size(); i++){
                int day = courseinfo.get(i).getDay();
                String name = courseinfo.get(i).getName();
                String position = courseinfo.get(i).getPosition();
                String teacher = courseinfo.get(i).getTeacher();
                List<Integer> weeks = courseinfo.get(i).getWeeks();
                int start = courseinfo.get(i).getSections().get(0).getSection();
                int step = courseinfo.get(i).getSections().size();
                course.add(new MySubject(null,name,position,teacher,weeks,start,step,day,-1,null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  course;
    }



    public static List<Integer> getWeekList(String weeksString){
        List<Integer> weekList=new ArrayList<>();
        if(weeksString==null||weeksString.length()==0) return weekList;

        weeksString=weeksString.replaceAll("[^\\d\\-\\,]", "");
        if(weeksString.indexOf(",")!=-1){
            String[] arr=weeksString.split(",");
            for(int i=0;i<arr.length;i++){
                weekList.addAll(getWeekList2(arr[i]));
            }
        }else{
            weekList.addAll(getWeekList2(weeksString));
        }
        return weekList;
    }

    public static List<Integer> getWeekList2(String weeksString){
        List<Integer> weekList=new ArrayList<>();
        int first=-1,end=-1,index=-1;
        if((index=weeksString.indexOf("-"))!=-1){
            first=Integer.parseInt(weeksString.substring(0,index));
            end=Integer.parseInt(weeksString.substring(index+1));
        }else{
            first=Integer.parseInt(weeksString);
            end=first;
        }
        for(int i=first;i<=end;i++)
            weekList.add(i);
        return weekList;
    }
}
