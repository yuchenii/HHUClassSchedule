package com.example.hhuclassschedule;

import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.model.ScheduleEnable;

import java.util.List;

/**
 * 自定义实体类需要实现ScheduleEnable接口并实现getSchedule()
 *
 * @see ScheduleEnable#getSchedule()
 */
public class MySubject implements ScheduleEnable {

    public static final String EXTRAS_ID="extras_id";
    public static final String EXTRAS_AD_URL="extras_ad_url";

    private int id=0;

    /**
     * 课程名
     */
    private String name;

    //无用数据
    private String time;

    /**
     * 教室
     */
    private String room;

    /**
     * 教师
     */
    private String teacher;

    /**
     * 第几周至第几周上
     */
    private List<Integer> weekList;

    /**
     * 开始上课的节次
     */
    private int start;

    /**
     * 上课节数
     */
    private int step;

    /**
     * 周几上
     */
    private int day;

    private String term;

    /**
     *  一个随机数，用于对应课程的颜色
     */
    private int colorRandom = 0;

    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public MySubject() {
        // TODO Auto-generated constructor stub
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public MySubject(String term, String name, String room, String teacher, List<Integer> weekList, int start, int step, int day, int colorRandom, String time) {
        super();
        this.term=term;
        this.name = name;
        this.room = room;
        this.teacher = teacher;
        this.weekList=weekList;
        this.start = start;
        this.step = step;
        this.day = day;
        this.colorRandom = colorRandom;
        this.time=time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setWeekList(List<Integer> weekList) {
        this.weekList = weekList;
    }

    public List<Integer> getWeekList() {
        return weekList;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getColorRandom() {
        return colorRandom;
    }

    public void setColorRandom(int colorRandom) {
        this.colorRandom = colorRandom;
    }

    @Override
    public Schedule getSchedule() {
        Schedule schedule=new Schedule();
        schedule.setDay(getDay());
        schedule.setName(getName());
        schedule.setRoom(getRoom());
        schedule.setStart(getStart());
        schedule.setStep(getStep());
        schedule.setTeacher(getTeacher());
        schedule.setWeekList(getWeekList());
        schedule.setColorRandom(2);
        schedule.putExtras(EXTRAS_ID,getId());
        schedule.putExtras(EXTRAS_AD_URL,getUrl());
        return schedule;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ssSSS");
//        String timeStamp = simpleDateFormat.format(new Date());
//      //  id = (int) System.currentTimeMillis();
//        long timestamp = System.currentTimeMillis() * 1000000L + System.nanoTime() % 1000000L;
//        id = timestamp % 1000000000L;
        String str = ""+getWeekList().get(0)+getWeekList().get(getWeekList().size()-1)+getDay()+getStart()+getStep();
        id = Integer.parseInt(str);
        return id;
    }

    public class root {
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
