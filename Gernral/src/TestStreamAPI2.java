import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TestStreamAPI2 {

    List<Student> list1 = Arrays.asList(
            new Student("张三", 18, 10000.00, 10000.00, Student.Status.BUSY),
            new Student("李四", 18, 10000.00, 10000.00, Student.Status.BUSY),
            new Student("王五", 18, 10000.00, 10000.00, Student.Status.BUSY),
            new Student("赵六", 18, 10000.00, 10000.00, Student.Status.BUSY)
            );

    /**
     * 排序
     * sorted()----自然排序(Comparable)
     * sorted(Comparator com)---定制排序(Comparator)
     */
    @Test
    public void testSorted(){
        List<String> list = Arrays.asList(  "dd","bb","aa", "cc","ee");

        list.stream()
                .sorted()
                .forEach(System.out::println);


    }



    @Test
    public void test5() {
        list1.stream()
                .map(Student::getName)
                .forEach(System.out::println);

        List<String> list = Arrays.asList("aa", "bb", "cc", "dd");

        Stream<Stream<Character>> stream = list.stream()
                .map(TestStreamAPI2::filterCharacter);

        stream.forEach(sm->sm.forEach(System.out::println));

        Stream<Character> stream1 = list.stream().flatMap(TestStreamAPI2::filterCharacter);

        stream1.forEach(System.out::println);

    }

    public static Stream<Character> filterCharacter(String str) {

        List<Character> list = new ArrayList<>();

        for (Character c : str.toCharArray()) {
            list.add(c);
        }
        return list.stream();

    }


}

