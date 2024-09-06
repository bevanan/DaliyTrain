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
public class LambdaTitle {
    public static void main(String[] args) {
        // 调试 .peek  -- 不做数据处理，通常都是看流内数据的处理情况
        // .peek(n -> System.out.println(n))

        // 1.组合多个操作 -- 1.将字符串转换为整数  2.筛选出偶数 3.排序
        List<String> strings = Arrays.asList("1", "2", "3", "4", "5");


        // 2.合并流
        List<List<String>> listOfLists = Arrays.asList(
                Arrays.asList("a", "b"),
                Arrays.asList("c", "d", "e")
        );

        // .flatMap() 相关题 组合与拆分
        // 将这些字符串按逗号拆分成单词，并找出所有长度大于3的单词，去重后 按字母顺序排序并返回一个列表。
        List<String> phrases = Arrays.asList("hello world", "java stream", "code with fun", "hello java");


        // 要的结果为 ["a1", "a2", "a3", "b1", "b2", "b3"]
        List<String> list13 = Arrays.asList("a", "b");
        List<String> list23 = Arrays.asList("1", "2", "3");


        // 10.将两个列表中的元素分别相加，然后将结果组合成一个新的列表。  相似题
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(4, 5, 6);


        // 3.计算总和
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);


        // 4.获取字符串集合中所有单词的长度，并生成Map
        List<String> items = Arrays.asList("apple", "banana", "cherry");


        // 5.分组（Map存在个数）
        List<String> items2 = Arrays.asList("apple", "banana", "cherry", "apple", "banana");


        // 拓展一 求每个字母的出现次数
        // 例：[a=4, p=2, r=2, e=2, n=2, b=1, c=1, h=1, y=1, l=1]


        // 拓展二
        /*
        {
            "hello" -> {h=1, e=1, l=2, o=1},
            "world" -> {w=1, o=1, r=1, l=1, d=1}
         } 生成这样的map，结构为 Map<String, Map<Character, Long>>
         */


        // （难） 对这些整数进行分组，按整数的奇偶性分成两组，并统计每组中整数的个数，
        // 返回一个Map，其中key为"odd"或"even"，value为对应组中的整数个数。 结果：{"odd": 5, "even": 5}
        List<Integer> numbers1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);


        // 6.并行流(平方后再累加)
        List<Integer> numbers2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);


        // 7.从一个整数列表中，筛选出所有偶数，并将它们的平方加起来。
        List<Integer> numbers3 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);


        // 8.给定一个字符串列表，按照字符串长度从短到长排序，并取前三个字符串。
        List<String> strings1 = Arrays.asList("apple", "banana", "kiwi", "orange", "grape");
        // 那从长到短呢？

        // 9.检查一个字符串数组中是否存在包含某特定字符的字符串。
        String[] words = {"java", "python", "ruby", "javascript"};

        // 要求：输出结果为 输出: name1/name2/name3
        List<String> names = Arrays.asList("name1", "name2", "name3");

        // 给定一个包含 Person 对象的列表，筛选出所有年龄大于等于 18 的人，并将他们的姓名放入新的列表中。
        List<Person> persons = initPersonData();

        // sout 每个person的年龄

        // 给定一个包含 Person 对象的列表，按照年龄从低到高排序，并取出前三人。

        // 计算一个包含 Person 对象的列表中所有学生的平均分数。

        // 每个年龄段分别有多少钱

        // 统计每个年龄段分别有多少钱

        // 把 Person 的姓名、年龄单独 拎出来后，存进Student内   集合

        // 根据这个age将list拆分为map，key为age，value为对应归纳好的list

        // 检查一个包含 Book 对象的数组，是否存在至少一本书的作者是特定的作者。
        Person[] person = null; // people集合转换成数组
        String targetAuthor = "bevan";
    }

    public static void answer() {
        // 组合多个操作 1.将字符串转换为整数  2.筛选出偶数 3.排序
        List<String> strings = Arrays.asList("1", "2", "3", "4", "5");
        List<Integer> intList = strings.stream()
                .map(Integer::parseInt)
                .filter(n -> n % 2 == 0)
                .sorted()
                .collect(Collectors.toList());


        /*
          flatMap(List::stream)将流中的每个元素转换为另一个流，然后将这些流连接起来形成一个单一的流
         */
        // 合并流
        List<List<String>> listOfLists = Arrays.asList(
                Arrays.asList("a", "b"),
                Arrays.asList("c", "d", "e")
        );
        List<String> mergedList = listOfLists.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        // .flatMap() 相关题 组合与拆分
        // 将这些字符串按逗号拆分成单词，并找出所有长度大于3的单词，去重后 按字母顺序排序并返回一个列表。
        List<String> phrases = Arrays.asList("hello world", "java stream", "code with fun", "hello java");
        List<String> result = phrases.stream()
                .flatMap(phrase -> Arrays.stream(phrase.split(" "))) // 将每个短语按空格拆分成单词，并合并成一个流
                .filter(word -> word.length() > 3) // 筛选出长度大于3的单词
                .distinct() // 去重
                .sorted() // 按字母顺序排序
                .collect(Collectors.toList()); // 收集到列表中

        // 要的结果为 ["a1", "a2", "a3", "b1", "b2", "b3"]
        List<String> list1 = Arrays.asList("a", "b");
        List<String> list2 = Arrays.asList("1", "2", "3");
        List<String> answer = list1.stream()
                .flatMap(s1 -> list2.stream().map(s2 -> s1 + s2))
                .collect(Collectors.toList());


        /*
          .reduce()用于将流中的元素组合成一个单一的结果。
          单一参数（无初始值），则返回Optional
          双参数，则放回对应类型的值 例如.reduce(0, Integer::sum); 返回int
         */
        // 计算总和
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int sum = numbers.stream()
                .reduce(0, Integer::sum);

        // 4.获取字符串集合中所有单词的长度，并生成Map
        List<String> items = Arrays.asList("apple", "banana", "cherry");
        Map<String, Integer> map = items.stream()
                .collect(Collectors.toMap(Function.identity(), String::length));
        // Function.identity()这个函数的输出就是其输入；经常用于那些需要一个函数作为参数的场合，但实际上你并不希望改变元素

        // 分组（Map存在个数）
        List<String> items2 = Arrays.asList("apple", "banana", "cherry", "apple", "banana");
        Map<String, Long> itemCount = items2.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));


        // 拓展一 求每个字母的出现次数, 由大到小排序
        // 例：[a=4, p=2, r=2, e=2, n=2, b=1, c=1, h=1, y=1, l=1]
        List<Map.Entry<Character, Long>> result111 = items.stream()
                .flatMap(word -> word.chars().mapToObj(c -> (char) c))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .collect(Collectors.toList());


        /* 拓展二
        {
            "hello" -> {h=1, e=1, l=2, o=1},
            "world" -> {w=1, o=1, r=1, l=1, d=1}
         } 生成这样的map，结构为 Map<String, Map<Character, Long>>
         */
        Map<String, Map<Character, Long>> res = items.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        word -> word.chars()
                                .mapToObj(c -> (char) c)
                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                ));



        /*
          对这些整数进行分组，按整数的奇偶性分成两组，并统计每组中整数的个数，
          返回一个Map，其中key为"odd"或"even"，value为对应组中的整数个数。 结果：{"odd": 5, "even": 5}

          Collectors.partitioningBy是Java Stream API中的一个特殊的收集器，
          它用于根据给定的谓词（一个布尔条件）将流中的元素分成两组，分别对应谓词为true和false的元素。
          结果是一个Map，键是布尔值true或false，值是满足对应布尔条件的元素列表。
         */
        List<Integer> numbers3 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Map<String, Long> result1 = numbers.stream()
                .collect(Collectors.partitioningBy(
                        n -> n % 2 == 0, // 按奇偶性分组
                        Collectors.counting() // 统计每组中的整数个数
                )).entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey() ? "even" : "odd", // 将key转换为"even"或"odd"
                        Map.Entry::getValue // 取对应的value
                ));


        /*
          .stream()：这创建了一个顺序流。在顺序流中，元素是按顺序一个接一个地处理的。
          当你调用 .stream() 时，你得到的是一个单线程执行的流，其中的操作（如 map, filter, forEach 等）在单个线程中按顺序执行。
          这种类型的流适用于数据量不大或操作不需要并行处理的场景。

          .parallelStream()：相比之下，parallelStream() 创建的是一个并行流。
          在并行流中，数据被分割成多个部分，每个部分在不同的线程中处理，然后将结果合并在一起。这种方式类似于 Fork/Join 框架。
          并行流试图利用多核处理器的优势，通过多线程执行来提高性能。它适用于处理大量数据或计算密集型任务。
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
        // 那从长到短呢？.sorted(Comparator.comparing(String::length).reversed()) 即可


        // 检查一个字符串数组中是否存在包含某特定字符的字符串。
        String[] words = {"java", "python", "ruby", "javascript"};
        boolean containsJava = Arrays.stream(words)
                .anyMatch(s -> s.contains("java"));


        // 将两个列表中的元素分别相加，然后将结果组合成一个新的列表。
        List<Integer> list123 = Arrays.asList(1, 2, 3);
        List<Integer> list232 = Arrays.asList(4, 5, 6);
        List<Integer> combinedList = Stream.concat(list123.stream(), list232.stream())
                .map(n -> n * 2)
                .collect(Collectors.toList());


        // 要求：输出结果为 输出: name1/name2/name3
        List<String> names = Arrays.asList("name1", "name2", "name3");
        String join1 = names.stream().collect(Collectors.joining("/"));
        String join2 = String.join("/", names);


        // 给定一个包含 Person 对象的列表，筛选出所有年龄大于等于 18 的人，并将他们的姓名放入新的列表中。
        List<Person> persons = initPersonData();
        List<String> adultsNames = persons.stream()
                .filter(person -> person.getAge() >= 18)
                .map(Person::getName)
                .collect(Collectors.toList());

        // sout 每个people的年龄
        // 并行流支持：在使用并行流（parallelStream()）时，forEach() 可以自动并行处理集合中的元素，从而提高性能。
        // 返回 void，即没有返回值。执行完操作后，流就被消耗掉了，无法再使用该流。
        persons.stream().map(Person::getAge).forEach(System.out::println);

        // 给定一个包含 Product 对象的列表，按照年龄从低到高排序，并取出前三人。
        List<Person> products1 = persons.stream()
                .sorted(Comparator.comparing(Person::getAge))
                .limit(3)
                .collect(Collectors.toList());

        // 检查一个包含 person 对象的数组，是否存在至少一本书的作者是特定的作者。
        Person[] person = persons.toArray(new Person[0]);
        String targetAuthor = "bevan";
        boolean hasBookByAuthor = Arrays.stream(person)
                .anyMatch(book -> book.getName().equals(targetAuthor));

        // 计算一个包含 Student 对象的列表中所有学生的平均分数。
        double averageScore = persons.stream()
                .mapToDouble(Person::getPrice)
                .average()
                .orElse(0.0);

        // .summingDouble --加法
        // 每个年龄段分别有多少钱
        Map<Integer, Double> collect1 = persons.stream().collect(Collectors.groupingBy(Person::getAge, Collectors.summingDouble(Person::getPrice)));
        // .summarizingDouble -- 不仅包含加法，里面额外包含统计，最大值最小值等
        Map<Integer, DoubleSummaryStatistics> collect2 = persons.stream().collect(Collectors.groupingBy(Person::getAge, Collectors.summarizingDouble(Person::getPrice)));


        // 现在有一个类为学生集合list，学生类中有个字段为age。
        // 现在我想根据这个age将list拆分为map，key为age，value为对应归纳好的list
        List<Person> personList = null;
        Map<Integer, List<Person>> collect = persons.stream().collect(Collectors.groupingBy(Person::getAge));


        List<String> items21 = Arrays.asList("apple", "banana", "cherry", "apple", "banana");
        // 结果： {banana=[banana, banana], cherry=[cherry], apple=[apple, apple]}
        Map<String, List<String>> collect11 = items2.stream().collect(Collectors.groupingBy(Function.identity()));
        // 结果： {banana=2, cherry=1, apple=2}
        Map<String, Long> coll1ect121 = items2.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));


        // 把 Person 的姓名、年龄单独 拎出来后，存进Student内  集合
        List<Person> personList1 = null;
        List<Student> studentList = personList1.stream().map(person1 -> new Student(person1.getAge(), person1.getName()))
                .collect(Collectors.toList());

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
