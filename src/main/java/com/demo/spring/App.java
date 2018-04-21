package com.demo.spring;

import com.demo.spring.bean.ClassRoom;
import com.demo.spring.bean.Student;
import com.demo.spring.bean.Teacher;
import com.demo.spring.core.BeanFactory;
import com.demo.spring.core.ClassXmlApplicationContext;
import org.dom4j.DocumentException;

import java.io.File;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws DocumentException {
        String dir = System.getProperty("user.dir");
        String currentPath = new File("src/main/java/com/demo/spring/applicationContext.xml").getAbsolutePath();
        BeanFactory bf = new ClassXmlApplicationContext(currentPath);
        Student student = (Student) bf.getBean("Student");
        Teacher teacher = (Teacher) bf.getBean("Teacher");
        ClassRoom classRoom = (ClassRoom) bf.getBean("ClassRoom");
        System.out.println("student name:" + student.getName());
        System.out.println("teacher course:" + classRoom.getTeacher().getCourse());
    }
}
