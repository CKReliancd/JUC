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
            new Student("赵六", 22, 13000.00, 77.00, Student.Status.VOCATION),
            new Student("赵六", 22, 13000.00, 77.00, Student.Status.VOCATION)
    );

    @Test
    public void testCollectGroupBy() {

        Map<String, List<Student>> collect = studentList.stream()
                .sorted(Comparator.comparing(Student::getOld))
                .collect(Collectors.groupingBy((e) -> {
                    if (((Student) e).getOld() < 22) {
                        return "少年";
                    } else if (((Student) e).getOld() == 22) {
                        return "青年";
                    } else if (((Student) e).getOld() > 22) {
                        return "老年";
                    }
                    return null;
                }));
        collect.entrySet().stream().forEach(System.out::println);

    }

    @Test
    public void testCollect() {

        //分组
        Map<Student.Status, List<Student>> collect6 = studentList.stream()
                .collect(Collectors.groupingBy(Student::getStatus));

        collect6.entrySet().stream().forEach(System.out::println);

        Optional<Student> collect5 = studentList.stream()
                .collect(Collectors.minBy(Comparator.comparingDouble(Student::getSalary)));
        System.out.println(collect5);


        Optional<Student> collect4 = studentList.stream()
                .collect(Collectors.maxBy(Comparator.comparingDouble(Student::getSalary)));
        System.out.println(collect4.get());


        Double collect3 = studentList.stream().collect(Collectors.summingDouble(Student::getSalary));
        System.out.println(collect3);


        Double collect2 = studentList.stream()
                .collect(Collectors.averagingDouble(Student::getSalary));
        System.out.println(collect2);

        HashSet<String> collect = studentList.stream()
                .map(Student::getName)
                .collect(Collectors.toCollection(HashSet::new));

        HashSet<Integer> collect1 = studentList.stream()
                .map(Student::getOld)
                .collect(Collectors.toCollection(HashSet::new));
        collect1.forEach(System.out::println);
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

        Optional<Double> salaries = studentList.stream()
                .map(Student::getSalary)
                .reduce(Double::sum);

        System.out.println(salaries.get());

    }

    /**
     * 收集
     * collect——将流转换为其它形式。接受一个Collector接口的实现，用于给Stream中元素做汇总的方法
     */

    @Test
    public void testCollect2() {


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
