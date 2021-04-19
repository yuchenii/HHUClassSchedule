package com.example.hhuclassschedule;

import com.example.hhuclassschedule.MySubject.root;
import com.example.hhuclassschedule.MySubject.root.CourseInfosDTO;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据类，加载课程数据
 * @author zf
 *
 */
public class SubjectRepertory {


    public static List<MySubject> loadDefaultSubjects(){
        //json转义
//        String json="[[\"2017-2018学年秋\", \"\", \"\", \"计算机组成原理\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"1周上\", 1, 1, 2, \"\", \"计算机综合楼106\", \"\"]," +
//                "[\"2017-2018学年秋\", \"\", \"\", \"hahaha\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"2周上\", 1, 1, 4, \"\", \"计算机综合楼106\", \"\"],"+
//                " [\"2017-2018学年秋\", \"\", \"\", \"算法分析与设计\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1周\", 1, 3, 2, \"\", \"计算机综合楼205\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"毛泽东思想和中国特色社会主义理论体系概论\", \"\", \"\", \"\", \"\", \"杨晓军\", \"\", \"\", \"6-12,14-17周上\", 1, 5, 2, \"\", \"3号教学楼3208\", \"\"]," +
//                " [\"2017-2018学年秋\", \"\", \"\", \"高等数学提高\", \"\", \"\", \"\", \"\", \"彭波\", \"\", \"\", \"3-12周\", 1, 9, 2, \"\", \"3号教学楼3101\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"面向对象分析与设计\", \"\", \"\", \"\", \"\", \"马永强\", \"\", \"\", \"1-8周\", 2, 1, 2, \"\", \"计算机综合楼106\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"软件工程\", \"\", \"\", \"\", \"\", \"马永强\", \"\", \"\", \"6-12,14-18周上\", 2, 3, 2, \"\", \"计算机综合楼106\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"Linux原理与应用\", \"\", \"\", \"\", \"\", \"刘永利\", \"\", \"\", \"9-12,14-15周上\", 2, 9, 2, \"\", \"计算机综合楼205\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"计算机组成原理\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"8-12,14-17周上\", 3, 1, 2, \"\", \"计算机综合楼106\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"算法分析与设计\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1-12周\", 3, 3, 2, \"\", \"计算机综合楼205\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"毛泽东思想和中国特色社会主义理论体系概论\", \"\", \"\", \"\", \"\", \"杨晓军\", \"\", \"\", \"6-12,14-17周上\", 3, 5, 2, \"\", \"3号教学楼3208\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"高等数学提高\", \"\", \"\", \"\", \"\", \"彭波\", \"\", \"\", \"3-4周上\", 3, 7, 2, \"\", \"3号教学楼3101\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"数据库高级应用\", \"\", \"\", \"\", \"\", \"李国斌\", \"\", \"\", \"9-12,14-18周上\", 3, 9, 2, \"\", \"计算机综合楼202\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"面向对象分析与设计\", \"\", \"\", \"\", \"\", \"马永强\", \"\", \"\", \"1-8周\", 4, 1, 2, \"\", \"计算机综合楼106\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"数字图像处理\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1-10周\", 4, 3, 2, \"\", \"计算机综合楼102\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"数据库高级应用\", \"\", \"\", \"\", \"\", \"李国斌\", \"\", \"\", \"9-12,14-18周上\", 4, 5, 2, \"\", \"计算机综合楼202\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"高等数学提高\", \"\", \"\", \"\", \"\", \"彭波\", \"\", \"\", \"3-12周\", 4, 7, 2, \"\", \"3号教学楼3101\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"Linux原理与应用\", \"\", \"\", \"\", \"\", \"刘永利\", \"\", \"\", \"9-12,14-15周上\", 4, 9, 2, \"\", \"计算机综合楼205\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"计算机组成原理\", \"\", \"\", \"\", \"\", \"刘静\", \"\", \"\", \"8-12,14-18周上\", 5, 1, 2, \"\", \"计算机综合楼106\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"软件工程\", \"\", \"\", \"\", \"\", \"马永强\", \"\", \"\", \"6-12,14-18周上\", 5, 3, 2, \"\", \"计算机综合楼106\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"毛泽东思想和中国特色社会主义理论体系概论\", \"\", \"\", \"\", \"\", \"杨晓军\", \"\", \"\", \"6-12,14-17周上\", 5, 5, 2, \"\", \"3号教学楼3208\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"高等数学提高\", \"\", \"\", \"\", \"\", \"彭波\", \"\", \"\", \"3-12周\", 5, 7, 2, \"\", \"3号教学楼3101\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"数字图像处理\", \"\", \"\", \"\", \"\", \"王静\", \"\", \"\", \"1-10周\", 5, 9, 2, \"\", \"计算机综合楼102\", \"\"], [\"2017-2018学年秋\", \"\", \"\", \"形势与政策-4\", \"\", \"\", \"\", \"\", \"孔祥增\", \"\", \"\", \"9周上\", 7, 5, 4, \"\", \"3号教学楼3311\", \"\"]]";
        String json = "{\n" +
                "  \"courseInfos\": [\n" +
                "    {\n" +
                "      \"day\": 3,\n" +
                "      \"name\": \"移动互联网应用开发_02\",\n" +
                "      \"position\": \"江宁校区博学楼B505\",\n" +
                "      \"sections\": [\n" +
                "        {\n" +
                "          \"section\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 2\n" +
                "        }\n" +
                "      ],\n" +
                "      \"teacher\": \"傅晓*\",\n" +
                "      \"weeks\": [\n" +
                "        1,\n" +
                "        2,\n" +
                "        3,\n" +
                "        4,\n" +
                "        5,\n" +
                "        6,\n" +
                "        7,\n" +
                "        8\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"day\": 4,\n" +
                "      \"name\": \"数据结构_02\",\n" +
                "      \"position\": \"江宁校区勤学楼5112\",\n" +
                "      \"sections\": [\n" +
                "        {\n" +
                "          \"section\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 2\n" +
                "        }\n" +
                "      ],\n" +
                "      \"teacher\": \"曹敬* 王彦芳\",\n" +
                "      \"weeks\": [\n" +
                "        1,\n" +
                "        2,\n" +
                "        3,\n" +
                "        4,\n" +
                "        5,\n" +
                "        6,\n" +
                "        7,\n" +
                "        8,\n" +
                "        9,\n" +
                "        10,\n" +
                "        11,\n" +
                "        12,\n" +
                "        13,\n" +
                "        14,\n" +
                "        15,\n" +
                "        16\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"day\": 5,\n" +
                "      \"name\": \"软件开发环境（混合式课程）_02\",\n" +
                "      \"position\": \"江宁校区博学楼B508\",\n" +
                "      \"sections\": [\n" +
                "        {\n" +
                "          \"section\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 2\n" +
                "        }\n" +
                "      ],\n" +
                "      \"teacher\": \"刘凡*\",\n" +
                "      \"weeks\": [\n" +
                "        5,\n" +
                "        6,\n" +
                "        7,\n" +
                "        8,\n" +
                "        9,\n" +
                "        10,\n" +
                "        11,\n" +
                "        12,\n" +
                "        13,\n" +
                "        14,\n" +
                "        15,\n" +
                "        16\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"day\": 1,\n" +
                "      \"name\": \"体育Ⅳ_男7\",\n" +
                "      \"position\": \"江宁校区操场操场\",\n" +
                "      \"sections\": [\n" +
                "        {\n" +
                "          \"section\": 3\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 4\n" +
                "        }\n" +
                "      ],\n" +
                "      \"teacher\": \"刘承*\",\n" +
                "      \"weeks\": [\n" +
                "        1,\n" +
                "        2,\n" +
                "        3,\n" +
                "        4,\n" +
                "        5,\n" +
                "        6,\n" +
                "        7,\n" +
                "        8,\n" +
                "        9,\n" +
                "        10,\n" +
                "        11,\n" +
                "        12,\n" +
                "        13,\n" +
                "        14,\n" +
                "        15,\n" +
                "        16\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"day\": 2,\n" +
                "      \"name\": \"数据结构_02\",\n" +
                "      \"position\": \"江宁校区致高楼B401\",\n" +
                "      \"sections\": [\n" +
                "        {\n" +
                "          \"section\": 3\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 4\n" +
                "        }\n" +
                "      ],\n" +
                "      \"teacher\": \"曹敬* 王彦芳\",\n" +
                "      \"weeks\": [\n" +
                "        1,\n" +
                "        2,\n" +
                "        3,\n" +
                "        4,\n" +
                "        5,\n" +
                "        6,\n" +
                "        7,\n" +
                "        8,\n" +
                "        9,\n" +
                "        10,\n" +
                "        11,\n" +
                "        12,\n" +
                "        13,\n" +
                "        14,\n" +
                "        15,\n" +
                "        16\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"day\": 3,\n" +
                "      \"name\": \"软件开发环境（混合式课程）_02\",\n" +
                "      \"position\": \"江宁校区博学楼B407\",\n" +
                "      \"sections\": [\n" +
                "        {\n" +
                "          \"section\": 3\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 4\n" +
                "        }\n" +
                "      ],\n" +
                "      \"teacher\": \"刘凡*\",\n" +
                "      \"weeks\": [\n" +
                "        5,\n" +
                "        6,\n" +
                "        7,\n" +
                "        8,\n" +
                "        9,\n" +
                "        10,\n" +
                "        11,\n" +
                "        12,\n" +
                "        13,\n" +
                "        14,\n" +
                "        15,\n" +
                "        16\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"day\": 4,\n" +
                "      \"name\": \"计算机组成与体系结构_02\",\n" +
                "      \"position\": \"江宁校区博学楼B508\",\n" +
                "      \"sections\": [\n" +
                "        {\n" +
                "          \"section\": 3\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 4\n" +
                "        }\n" +
                "      ],\n" +
                "      \"teacher\": \"廖小平 黄倩* 巫义锐\",\n" +
                "      \"weeks\": [\n" +
                "        2,\n" +
                "        3,\n" +
                "        4,\n" +
                "        5,\n" +
                "        6,\n" +
                "        7,\n" +
                "        8,\n" +
                "        9,\n" +
                "        10,\n" +
                "        11,\n" +
                "        12,\n" +
                "        13\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"day\": 5,\n" +
                "      \"name\": \"移动互联网应用开发_02\",\n" +
                "      \"position\": \"江宁校区博学楼B407\",\n" +
                "      \"sections\": [\n" +
                "        {\n" +
                "          \"section\": 3\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 4\n" +
                "        }\n" +
                "      ],\n" +
                "      \"teacher\": \"傅晓*\",\n" +
                "      \"weeks\": [\n" +
                "        1,\n" +
                "        2,\n" +
                "        3,\n" +
                "        4,\n" +
                "        5,\n" +
                "        6,\n" +
                "        7,\n" +
                "        8\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"day\": 2,\n" +
                "      \"name\": \"计算机组成与体系结构_02\",\n" +
                "      \"position\": \"江宁校区博学楼B505\",\n" +
                "      \"sections\": [\n" +
                "        {\n" +
                "          \"section\": 6\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 7\n" +
                "        }\n" +
                "      ],\n" +
                "      \"teacher\": \"廖小平 黄倩* 巫义锐\",\n" +
                "      \"weeks\": [\n" +
                "        2,\n" +
                "        3,\n" +
                "        4,\n" +
                "        5,\n" +
                "        6,\n" +
                "        7,\n" +
                "        8,\n" +
                "        9,\n" +
                "        10,\n" +
                "        11,\n" +
                "        12,\n" +
                "        13\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"day\": 4,\n" +
                "      \"name\": \"算法与数据结构课程设计_02\",\n" +
                "      \"position\": \"江宁校区计信院实验室勤2507\",\n" +
                "      \"sections\": [\n" +
                "        {\n" +
                "          \"section\": 6\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 7\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 8\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 9\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 10\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 11\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 12\n" +
                "        }\n" +
                "      ],\n" +
                "      \"teacher\": \"施小华*\",\n" +
                "      \"weeks\": [\n" +
                "        7,\n" +
                "        8,\n" +
                "        9,\n" +
                "        10,\n" +
                "        11\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"day\": 5,\n" +
                "      \"name\": \"马克思主义基本原理概论_34\",\n" +
                "      \"position\": \"江宁校区致高楼B404\",\n" +
                "      \"sections\": [\n" +
                "        {\n" +
                "          \"section\": 6\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 7\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 8\n" +
                "        }\n" +
                "      ],\n" +
                "      \"teacher\": \"郑黎明*\",\n" +
                "      \"weeks\": [\n" +
                "        1,\n" +
                "        2,\n" +
                "        3,\n" +
                "        4,\n" +
                "        5,\n" +
                "        6,\n" +
                "        7,\n" +
                "        8,\n" +
                "        9,\n" +
                "        10,\n" +
                "        11,\n" +
                "        12,\n" +
                "        13,\n" +
                "        14,\n" +
                "        15,\n" +
                "        16\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"day\": 3,\n" +
                "      \"name\": \"形势与政策IV_1913\",\n" +
                "      \"position\": \"江宁校区致用楼101\",\n" +
                "      \"sections\": [\n" +
                "        {\n" +
                "          \"section\": 8\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 9\n" +
                "        }\n" +
                "      ],\n" +
                "      \"teacher\": \"朱立琴*\",\n" +
                "      \"weeks\": [\n" +
                "        11,\n" +
                "        12,\n" +
                "        13,\n" +
                "        14\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"day\": 4,\n" +
                "      \"name\": \"移动互联网应用开发实践_02\",\n" +
                "      \"position\": \"江宁校区计信院实验室勤2507\",\n" +
                "      \"sections\": [\n" +
                "        {\n" +
                "          \"section\": 8\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 9\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 10\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 11\n" +
                "        },\n" +
                "        {\n" +
                "          \"section\": 12\n" +
                "        }\n" +
                "      ],\n" +
                "      \"teacher\": \"余宇峰* 侯婷\",\n" +
                "      \"weeks\": [\n" +
                "        12,\n" +
                "        13,\n" +
                "        14,\n" +
                "        15,\n" +
                "        16\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"sectionTimes\": [\n" +
                "    {\n" +
                "      \"endTime\": \"08:45\",\n" +
                "      \"section\": 1,\n" +
                "      \"startTime\": \"08:00\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"endTime\": \"09:35\",\n" +
                "      \"section\": 2,\n" +
                "      \"startTime\": \"08:50\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"endTime\": \"10:35\",\n" +
                "      \"section\": 3,\n" +
                "      \"startTime\": \"09:50\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"endTime\": \"11:25\",\n" +
                "      \"section\": 4,\n" +
                "      \"startTime\": \"10:40\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"endTime\": \"12:15\",\n" +
                "      \"section\": 5,\n" +
                "      \"startTime\": \"11:30\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"endTime\": \"14:45\",\n" +
                "      \"section\": 6,\n" +
                "      \"startTime\": \"14:00\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"endTime\": \"15:35\",\n" +
                "      \"section\": 7,\n" +
                "      \"startTime\": \"14:50\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"endTime\": \"16:35\",\n" +
                "      \"section\": 8,\n" +
                "      \"startTime\": \"15:50\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"endTime\": \"17:25\",\n" +
                "      \"section\": 9,\n" +
                "      \"startTime\": \"16:40\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"endTime\": \"19:15\",\n" +
                "      \"section\": 10,\n" +
                "      \"startTime\": \"18:30\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"endTime\": \"20:05\",\n" +
                "      \"section\": 11,\n" +
                "      \"startTime\": \"19:20\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"endTime\": \"20:55\",\n" +
                "      \"section\": 12,\n" +
                "      \"startTime\": \"20:10\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";
        return parse(json);
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
