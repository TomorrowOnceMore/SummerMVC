package com.summer.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

/**
 * Created by toy on 7/16/16.
 */
public class FreeMarkerTest {

    public class Student {
        private int id;
        private String name;
        private String address;

        public Student(int id, String name, String address) {
            this.id = id;
            this.name = name;
            this.address = address;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    @Test
    public void testFreeMarker() throws Exception {
        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setDirectoryForTemplateLoading(new File("/Users/toy/Projects/JavaProjects/SummerMVC/summer-portal/src/main/webapp/WEB-INF/ftl"));
        configuration.setDefaultEncoding("utf-8");
        Template template = configuration.getTemplate("first.ftl");
        Map root = new HashMap<>();
        root.put("hello", "hello freemarker");
        Writer out = new FileWriter(new File("/Users/toy/Projects/JavaProjects/SummerMVC/summer-portal/html/Hello.html"));
        template.process(root, out);
        out.flush();
        out.close();
    }

    @Test
    public void testPojoFreeMarker() throws Exception {
        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setDirectoryForTemplateLoading(new File("/Users/toy/Projects/JavaProjects/SummerMVC/summer-portal/src/main/webapp/WEB-INF/ftl"));
        configuration.setDefaultEncoding("utf-8");
        Template template = configuration.getTemplate("second.ftl");
        Map root = new HashMap<>();
        //root.put("title", "hello freemarker");
        root.put("student", new Student(1, "张三", "北京"));
        List<Student> stuList = new ArrayList<>();
        stuList.add(new Student(1, "阿迪肌肤", "日本"));
        stuList.add(new Student(2, "密度", "阿方"));
        stuList.add(new Student(3, "可怕", "大发"));
        stuList.add(new Student(4, "叫我", "分"));
        stuList.add(new Student(5, "哦喂", "将诶上课"));
        stuList.add(new Student(6, "卡杜米", "发饿"));
        root.put("students", stuList);
        //root.put("curdate", new Date());
        Writer out = new FileWriter(new File("/Users/toy/Projects/JavaProjects/SummerMVC/summer-portal/html/Second.html"));
        template.process(root, out);
        out.flush();
        out.close();
    }
}
