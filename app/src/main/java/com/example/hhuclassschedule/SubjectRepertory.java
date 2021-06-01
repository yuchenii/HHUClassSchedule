package com.example.hhuclassschedule;

import android.util.Log;

import com.example.hhuclassschedule.util.ContextApplication;
import com.example.hhuclassschedule.util.SharedPreferencesUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据类，加载课程数据
 * 实例化课程对象
 */
public class SubjectRepertory  {

    private static final String TAG = "SubjectRepertory";

    public static List<MySubject> loadDefaultSubjects(){
        // String json = "{\"courseInfos\":[{\"day\":3,\"name\":\"移动互联网应用开发_02\",\"position\":\"江宁校区博学楼B505\",\"sections\":[{\"section\":1},{\"section\":2}],\"teacher\":\"傅晓*\",\"weeks\":[1,2,3,4,5,6,7,8]},{\"day\":4,\"name\":\"数据结构_02\",\"position\":\"江宁校区勤学楼5112\",\"sections\":[{\"section\":1},{\"section\":2}],\"teacher\":\"曹敬* 王彦芳\",\"weeks\":[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16]},{\"day\":5,\"name\":\"软件开发环境（混合式课程）_02\",\"position\":\"江宁校区博学楼B508\",\"sections\":[{\"section\":1},{\"section\":2}],\"teacher\":\"刘凡*\",\"weeks\":[5,6,7,8,9,10,11,12,13,14,15,16]},{\"day\":1,\"name\":\"体育Ⅳ_男7\",\"position\":\"江宁校区操场操场\",\"sections\":[{\"section\":3},{\"section\":4}],\"teacher\":\"刘承*\",\"weeks\":[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16]},{\"day\":2,\"name\":\"数据结构_02\",\"position\":\"江宁校区致高楼B401\",\"sections\":[{\"section\":3},{\"section\":4}],\"teacher\":\"曹敬* 王彦芳\",\"weeks\":[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16]},{\"day\":3,\"name\":\"软件开发环境（混合式课程）_02\",\"position\":\"江宁校区博学楼B407\",\"sections\":[{\"section\":3},{\"section\":4}],\"teacher\":\"刘凡*\",\"weeks\":[5,6,7,8,9,10,11,12,13,14,15,16]},{\"day\":4,\"name\":\"计算机组成与体系结构_02\",\"position\":\"江宁校区博学楼B508\",\"sections\":[{\"section\":3},{\"section\":4}],\"teacher\":\"廖小平 黄倩* 巫义锐\",\"weeks\":[2,3,4,5,6,7,8,9,10,11,12,13]},{\"day\":5,\"name\":\"移动互联网应用开发_02\",\"position\":\"江宁校区博学楼B407\",\"sections\":[{\"section\":3},{\"section\":4}],\"teacher\":\"傅晓*\",\"weeks\":[1,2,3,4,5,6,7,8]},{\"day\":2,\"name\":\"计算机组成与体系结构_02\",\"position\":\"江宁校区博学楼B505\",\"sections\":[{\"section\":6},{\"section\":7}],\"teacher\":\"廖小平 黄倩* 巫义锐\",\"weeks\":[2,3,4,5,6,7,8,9,10,11,12,13]},{\"day\":4,\"name\":\"算法与数据结构课程设计_02\",\"position\":\"江宁校区计信院实验室勤2507\",\"sections\":[{\"section\":6},{\"section\":7},{\"section\":8},{\"section\":9},{\"section\":10},{\"section\":11},{\"section\":12}],\"teacher\":\"施小华*\",\"weeks\":[7,8,9,10,11]},{\"day\":5,\"name\":\"马克思主义基本原理概论_34\",\"position\":\"江宁校区致高楼B404\",\"sections\":[{\"section\":6},{\"section\":7},{\"section\":8}],\"teacher\":\"郑黎明*\",\"weeks\":[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16]},{\"day\":3,\"name\":\"形势与政策IV_1913\",\"position\":\"江宁校区致用楼101\",\"sections\":[{\"section\":8},{\"section\":9}],\"teacher\":\"朱立琴*\",\"weeks\":[11,12,13,14]},{\"day\":4,\"name\":\"移动互联网应用开发实践_02\",\"position\":\"江宁校区计信院实验室勤2507\",\"sections\":[{\"section\":8},{\"section\":9},{\"section\":10},{\"section\":11},{\"section\":12}],\"teacher\":\"余宇峰* 侯婷\",\"weeks\":[12,13,14,15,16]}],\"sectionTimes\":[{\"endTime\":\"08:45\",\"section\":1,\"startTime\":\"08:00\"},{\"endTime\":\"09:35\",\"section\":2,\"startTime\":\"08:50\"},{\"endTime\":\"10:35\",\"section\":3,\"startTime\":\"09:50\"},{\"endTime\":\"11:25\",\"section\":4,\"startTime\":\"10:40\"},{\"endTime\":\"12:15\",\"section\":5,\"startTime\":\"11:30\"},{\"endTime\":\"14:45\",\"section\":6,\"startTime\":\"14:00\"},{\"endTime\":\"15:35\",\"section\":7,\"startTime\":\"14:50\"},{\"endTime\":\"16:35\",\"section\":8,\"startTime\":\"15:50\"},{\"endTime\":\"17:25\",\"section\":9,\"startTime\":\"16:40\"},{\"endTime\":\"19:15\",\"section\":10,\"startTime\":\"18:30\"},{\"endTime\":\"20:05\",\"section\":11,\"startTime\":\"19:20\"},{\"endTime\":\"20:55\",\"section\":12,\"startTime\":\"20:10\"}]}";

//        SharedPreferences preferences = ContextApplication.getAppContext().getSharedPreferences("SP_Data_List", Context.MODE_PRIVATE);// 创建sp对象
//        String htmlToSubject = preferences.getString("HTML_TO_SUBJECT",null);  // 取出key为"HTML_TO_SUBJECT"的值，如果值为空，则将第二个参数作为默认值赋值
        String htmlToSubject = SharedPreferencesUtil.init(ContextApplication.getAppContext(),"SP_Data_List").getString("HTML_TO_SUBJECT",null);
        Log.e(TAG, "HTML_TO_SUBJECT: "+ htmlToSubject);//HTML_TO_SUBJECT便是取出的数据了
        if(htmlToSubject == null){
            List<MySubject>  mySubject = new ArrayList<>();
            return mySubject;
        }
        return parse(htmlToSubject);
    }


    /**
     * 对json字符串进行解析
     * @param parseString 带解析的json字符串
     * @return 解析后的课程列表
     */
    public static List<MySubject> parse(String parseString){
        List<MySubject> course = new ArrayList<>();
        Gson gson = new Gson();
        MysubjectDTO jsonObject = gson.fromJson(parseString, MysubjectDTO.class);
        try {
            List<MysubjectDTO.CourseInfosDTO> courseinfo = jsonObject.getCourseInfos();
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

    /**
     * 实例化课程对象
     */
    public class MysubjectDTO {
        private List<CourseInfosDTO> courseInfos;
        private List<SectionTimesDTO> sectionTimes;

        public List<CourseInfosDTO> getCourseInfos() {
            return courseInfos;
        }

        public void setCourseInfos(List<CourseInfosDTO> courseInfos) {
            this.courseInfos = courseInfos;
        }

        public List<SectionTimesDTO> getSectionTimes() {
            return sectionTimes;
        }

        public void setSectionTimes(List<SectionTimesDTO> sectionTimes) {
            this.sectionTimes = sectionTimes;
        }

        public class CourseInfosDTO {
            private Integer day;
            private String name;
            private String position;
            private List<SectionsDTO> sections;
            private String teacher;
            private List<Integer> weeks;

            public Integer getDay() {
                return day;
            }

            public void setDay(Integer day) {
                this.day = day;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public List<SectionsDTO> getSections() {
                return sections;
            }

            public void setSections(List<SectionsDTO> sections) {
                this.sections = sections;
            }

            public String getTeacher() {
                return teacher;
            }

            public void setTeacher(String teacher) {
                this.teacher = teacher;
            }

            public List<Integer> getWeeks() {
                return weeks;
            }

            public void setWeeks(List<Integer> weeks) {
                this.weeks = weeks;
            }

            public class SectionsDTO {
                private Integer section;

                public Integer getSection() {
                    return section;
                }

                public void setSection(Integer section) {
                    this.section = section;
                }
            }

        }

        public  class SectionTimesDTO {
            private String endTime;
            private Integer section;
            private String startTime;

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public Integer getSection() {
                return section;
            }

            public void setSection(Integer section) {
                this.section = section;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }
        }
    }
}
