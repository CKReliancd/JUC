import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 终止操作
 * 一、查找与匹配
 * allMatch--检查是否匹配所有元素
 * anyMatch--检查是否至少匹配一个元素
 * noneMatch--检查是否没有匹配所有元素
 * findFirst--返回第一个元素
 * findAny--返回当前流种的任意元素
 * count--返回流中元素的总个数
 * max--返回流中最大值
 * min--返回流种最小值
 */

public class TestStreamAPI3 {


    List<Student> studentList = Arrays.asList(
            new Student("张三", 18, 10000.00, 100.00, Student.Status.FREE),
            new Student("李四", 23, 11000.00, 99.00, Student.Status.BUSY),
            new Student("王五", 21, 12000.00, 88.00, Student.Status.FREE),
            new Student("赵六", 22, 13000.00, 77.00, Student.Status.VOCATION)
    );

    /**
     * 收集
     * collect——将流转换为其它形式。接受一个Collector接口的实现，用于给Stream中元素做汇总的方法
     */

    @Test
    public void testCollect2(){


    }


    @Test
    public void testCollect(){
        List<String> list = studentList.stream()
                .map(Student::getName)
                .collect(Collectors.toList());

        list.forEach(System.out::println);

        System.out.println("=====================");
        HashSet<String> hashSet = studentList.stream()
                .map(Student::getName)
                .collect(Collectors.toCollection(HashSet::new));

        hashSet.stream().forEach(System.out::println);

        System.out.println("===========================");

        //总数
        Long count = studentList.stream()
                .collect(Collectors.counting());
        System.out.println(count);
        System.out.println("+================");

        //平均值
        Double avg = studentList.stream()
                .collect(Collectors.averagingDouble(Student::getSalary));
        System.out.println(avg);

        System.out.println("=================");
        //总和
        Double sum = studentList.stream()
                .collect(Collectors.summingDouble(Student::getSalary));
        System.out.println(sum);

        System.out.println("+++++++++++++++++++++++++++");

        //最大值
        Optional<Student> max = studentList.stream()
                .collect(Collectors.maxBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));

        System.out.println(max.get());

        System.out.println("+===========================");
        //最小值
        Optional<Double> min = studentList.stream()
                .map(Student::getSalary)
                .collect(Collectors.minBy(Double::compare));
        System.out.println(min.get());


    }


    /**
     * 归约
     * reduce(T identity, BinaryOperation)/reduce(BinaryOperator)——可以将流中的元素反复结合，得到一个值
     */
    @Test
    public void testReduce() {

        List<Integer> asList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        Integer sum = asList.stream()
                .reduce(0, (x, y) -> x + y);

        System.out.println(sum);

        System.out.println("===========================");

        Optional<Double> optionalR = studentList.stream()
                .map(Student::getSalary)
                .reduce(Double::sum);
        System.out.println(optionalR.get());

    }


    @Test
    public void test() {

        boolean b = studentList.stream()
                .allMatch((e) -> e.getStatus().equals(Student.Status.VOCATION));
        System.out.println(b);

        boolean b1 = studentList.stream()
                .anyMatch(e -> e.getStatus().equals(Student.Status.BUSY));
        System.out.println(b1);

        boolean b2 = studentList.stream()
                .noneMatch(e -> e.getStatus().equals(Student.Status.BUSY));
        System.out.println(b2);

        Optional<Student> op = studentList.stream()
                .sorted((e1, e2) -> -Double.compare(e1.getSalary(), e2.getSalary()))
                .findFirst();
        System.out.println(op.get());

        Optional<Student> any = studentList.parallelStream()
                .filter(e -> e.getStatus().equals(Student.Status.FREE))
                .findAny();
        System.out.println(any.get());

        Optional<Student> max = studentList.stream()
                .max(Comparator.comparingDouble(Student::getSalary));

        System.out.println(max.get());

        Optional<Student> min = studentList.stream()
                .min(Comparator.comparingDouble(Student::getSalary));

        System.out.println(min.get());

        Optional<Double> salaryMin = studentList.stream()
                .map(Student::getSalary)
                .min(Double::compareTo);

        System.out.println(salaryMin.get());

    }

}
