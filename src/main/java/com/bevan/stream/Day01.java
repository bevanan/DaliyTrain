package com.bevan.stream;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zbf
 * @since 2023/12/13 23:45
 * 这是学习Stream的新特性题库
 */
public class Day01 {
    public static void main(String[] args) {
        // 1.组合多个操作 -- 1.将字符串转换为整数  2.筛选出偶数 3.排序
        List<String> strings = Arrays.asList("1", "2", "3", "4", "5");
        System.out.println(strings.stream().map(Integer::parseInt).filter(n -> n % 2 == 0).sorted().toList());

        // 2.合并流
        List<List<String>> listOfLists = Arrays.asList(
                Arrays.asList("a", "b"),
                Arrays.asList("c", "d", "e")
        );
        System.out.println(listOfLists.stream().flatMap(List::stream).toList());

        // 10.将两个列表中的元素分别相加，然后将结果组合成一个新的列表。  相似题
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(4, 5, 6);
        System.out.println(Stream.concat(list1.stream(), list2.stream()).toList());

        // 3.计算总和
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(numbers.stream().reduce(0, Integer::sum));

        // 4.收集到 Map 中（Map每个字符串的长度）
        List<String> items = Arrays.asList("apple", "banana", "cherry");
        System.out.println(items.stream().reduce("", String::concat));
        System.out.println(items.stream().collect(Collectors.toMap(Function.identity(), String::length)));

        // 5.分组（Map存在个数）
        List<String> items2 = Arrays.asList("apple", "banana", "cherry", "apple", "banana");
        System.out.println(items2.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));

        // 6.并行流(平方后再累加)
        List<Integer> numbers2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println(numbers2.stream().map(n -> n * n).reduce(0, Integer::sum));
        Optional<Integer> reduce = numbers2.parallelStream().map(n -> n * n).reduce(Integer::sum);

        // 7.从一个整数列表中，筛选出所有偶数，并将它们的平方加起来。
        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println(numbers1.stream().filter(n -> n % 2 == 0).map(n -> n * n).reduce(0, Integer::sum));

        // 8.给定一个字符串列表，按照字符串长度从短到长排序，并取前三个字符串。
        List<String> strings1 = Arrays.asList("apple", "banana", "kiwi", "orange", "grape");
        System.out.println(strings1.stream().sorted(Comparator.comparing(String::length)).limit(3).toList());

        // 9.检查一个字符串数组中是否存在包含某特定字符的字符串。
        String[] words = {"java", "python", "ruby", "javascript"};
        System.out.println(Arrays.stream(words).anyMatch(n -> n.contains("jav")));


        // 给定一个包含 Person 对象的列表，筛选出所有年龄大于等于 18 的人，并将他们的姓名放入新的列表中。
        List<Person> people = initPersonData();
        System.out.println(people.stream().filter(n -> n.getAge() >= 18).map(Person::getName).toList());
        // 给定一个包含 Person 对象的列表，按照年龄从低到高排序，并取出前三个产品。
        System.out.println(people.stream().sorted(Comparator.comparing(Person::getAge)).limit(3).toList());
        // 计算一个包含 Person 对象的列表中所有学生的平均分数。
        System.out.println(people.stream().mapToDouble(Person::getPrice).average().orElse(0));
        // 把 Person 的姓名、年龄单独 拎出来后，存进Student内   集合
        System.out.println(people.stream().map(n -> new Student(n.getAge(), n.getName())).toList());
        // 根据这个age将list拆分为map，key为age，value为对应归纳好的list
        // people.stream().collect(Collectors.toMap(Person::getAge, ))

        // 检查一个包含 Book 对象的数组，是否存在至少一本书的作者是特定的作者。
        Person[] books = people.toArray(new Person[0]); // 集合转换成数组
        String targetAuthor = "bevan";
        System.out.println(Arrays.stream(books).anyMatch(n -> n.getName().contains(targetAuthor)));
    }


    public static void answer() {
        // 组合多个操作 1.将字符串转换为整数  2.筛选出偶数 3.排序
        List<String> strings = Arrays.asList("1", "2", "3", "4", "5");
        List<Integer> intList = strings.stream()
                .map(Integer::parseInt)
                .filter(n -> n % 2 == 0)
                .sorted()
                .collect(Collectors.toList());


        /**
         * flatMap(List::stream)将流中的每个元素转换为另一个流，然后将这些流连接起来形成一个单一的流
         */
        // 合并流
        List<List<String>> listOfLists = Arrays.asList(
                Arrays.asList("a", "b"),
                Arrays.asList("c", "d", "e")
        );
        List<String> mergedList = listOfLists.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        /**
         * .reduce()用于将流中的元素组合成一个单一的结果。
         * 单一参数（无初始值），则返回Optional
         * 双参数，则放回对应类型的值 例如.reduce(0, Integer::sum); 返回int
         */
        // 计算总和
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int sum = numbers.stream()
                .reduce(0, Integer::sum);

        // 收集到 Map 中
        List<String> items = Arrays.asList("apple", "banana", "cherry");
        Map<String, Integer> map = items.stream()
                .collect(Collectors.toMap(Function.identity(), String::length));
        // Function.identity()这个函数的输出就是其输入；经常用于那些需要一个函数作为参数的场合，但实际上你并不希望改变元素


        // 分组
        List<String> items2 = Arrays.asList("apple", "banana", "cherry", "apple", "banana");
        Map<String, Long> itemCount = items2.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));


        /**
         * .stream()：这创建了一个顺序流。在顺序流中，元素是按顺序一个接一个地处理的。
         * 当你调用 .stream() 时，你得到的是一个单线程执行的流，其中的操作（如 map, filter, forEach 等）在单个线程中按顺序执行。
         * 这种类型的流适用于数据量不大或操作不需要并行处理的场景。
         *
         * .parallelStream()：相比之下，parallelStream() 创建的是一个并行流。
         * 在并行流中，数据被分割成多个部分，每个部分在不同的线程中处理，然后将结果合并在一起。这种方式类似于 Fork/Join 框架。
         * 并行流试图利用多核处理器的优势，通过多线程执行来提高性能。它适用于处理大量数据或计算密集型任务。
         */
        // 并行流
        List<Integer> numbers2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int sumOfSquares = numbers2.parallelStream()
                .map(n -> n * n)
                .reduce(0, Integer::sum);


        // 从一个整数列表中，筛选出所有偶数，并将它们的平方加起来。
        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int sumOfSquares1 = numbers1.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .reduce(0, Integer::sum);

        // 给定一个字符串列表，按照字符串长度从短到长排序，并取前三个字符串。
        List<String> strings1 = Arrays.asList("apple", "banana", "kiwi", "orange", "grape");
        List<String> shortStrings = strings1.stream()
                .sorted(Comparator.comparing(String::length))
                .limit(3)
                .collect(Collectors.toList());


        // 检查一个字符串数组中是否存在包含某特定字符的字符串。
        String[] words = {"java", "python", "ruby", "javascript"};
        boolean containsJava = Arrays.stream(words)
                .anyMatch(s -> s.contains("java"));


        // 将两个列表中的元素分别相加，然后将结果组合成一个新的列表。
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(4, 5, 6);
        List<Integer> combinedList = Stream.concat(list1.stream(), list2.stream())
                .map(n -> n * 2)
                .collect(Collectors.toList());


        // 给定一个包含 Person 对象的列表，筛选出所有年龄大于等于 18 的人，并将他们的 姓名 放入新的列表中。
        List<Person> people = null;
        List<String> adultsNames = people.stream()
                .filter(person -> person.getAge() >= 18)
                .map(Person::getName)
                .collect(Collectors.toList());


        // 给定一个包含 Product 对象的列表，按照价格从低到高排序，并取出前三个产品。
        List<Person> products = null;
        List<Person> products1 = products.stream()
                .sorted(Comparator.comparing(Person::getPrice))
                .limit(3)
                .collect(Collectors.toList());


        // 检查一个包含 Book 对象的数组，是否存在至少一本书的作者是特定的作者。
        Person[] books = null;
        String targetAuthor = "bevan";
        boolean hasBookByAuthor = Arrays.stream(books)
                .anyMatch(book -> book.getAuthor().equals(targetAuthor));


        // 计算一个包含 Student 对象的列表中所有学生的平均分数。
        List<Person> students = null;
        double averageScore = students.stream()
                .mapToDouble(Person::getPrice)
                .average()
                .orElse(0.0);


        // 把 Person 的姓名、年龄单独 拎出来后，存进Student内   集合
        List<Person> personList = null;
        List<Student> studentList = personList.stream().map(person -> new Student(person.getAge(), person.getName()))
                .collect(Collectors.toList());

        // 现在有一个类为学生集合list，学生类中有个字段为age。
        // 现在我想根据这个age将list拆分为map，key为age，value为对应归纳好的list
        List<Person> persons = null;
        Map<Integer, List<Person>> collect = persons.stream().collect(Collectors.groupingBy(Person::getAge));
    }

    public static List<Person> initPersonData() {
        List<Person> peoples = new ArrayList<>();
        peoples.add(new Person(17, "lili", 50));
        peoples.add(new Person(18, "bevan", 90));
        peoples.add(new Person(18, "lisa", 60));
        peoples.add(new Person(19, "kangkang", 80));
        peoples.add(new Person(16, "mj", 100));
        return peoples;
    }
}
