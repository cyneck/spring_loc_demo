package com.demo.spring;

import static org.junit.Assert.assertTrue;

import com.demo.spring.bean.ClassRoom;
import com.demo.spring.bean.Student;
import com.demo.spring.core.BeanFactory;
import com.demo.spring.core.ClassXmlApplicationContext;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.io.File;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws DocumentException {
        String dir = System.getProperty("user.dir");
        String currentPath = new File("src/test/java/com/demo/spring/applicationContext.xml").getAbsolutePath();
        BeanFactory bf = new ClassXmlApplicationContext(currentPath);
        Student student = (Student) bf.getBean("Student");
        System.out.println(student.getName());

        ClassRoom classRoom = (ClassRoom) bf.getBean("ClassRoom");
        System.out.println(classRoom.getStudent().getName());
        assertTrue(true);
    }
}
