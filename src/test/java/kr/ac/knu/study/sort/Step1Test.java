package kr.ac.knu.study.sort;

import kr.ac.knu.study.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by rokim on 2017. 5. 30..
 */
@Slf4j
public class Step1Test {
    @Test
    public void test() {
        List<Student> list = Arrays.asList(
                new Student("Robin", 100),
                new Student("Hobin", 90),
                new Student("Kobin", 80),
                new Student("Tobin", 70),
                new Student("Bobin", 60)
        );

//        List<Student> sortedList = Step1.sort(list, true);
//        System.out.println(sortedList);
    }
}