import sun.nio.ch.ThreadPool;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyUserTest {


    public static void userTest(Long id,LineFilter filter){

    }

    public static void main(String[] args) {

        Function<String,Integer> f=Integer::parseInt;
        System.out.println(f.apply("123"));
        BiFunction<String,Integer,Character> f1=String::charAt;
        System.out.println(f1.apply("abcdef",2));
        User user=new User();
        Supplier c= user::getAge;
        String[] stringArray = { "Barbara", "James", "Mary", "John", "Patricia", "Robert", "Michael", "Linda" };
        Arrays.sort(stringArray, String::compareToIgnoreCase);
        final Car car = Car.create( Car::new );
        final List< Car > cars = Arrays.asList( car );
        cars.forEach( Car::repair );
        System.out.println("===============================");
        List<String> list=Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        System.out.println(list.stream().distinct().collect(Collectors.toList()));
        Stream<String> stream=list.stream();
        List list1=stream.filter(str->str.length()>=3).map(s->{
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s.substring(0,2);}).sorted(Comparator.comparing(String::length)).collect(Collectors.toList());
        list1.stream().forEach(System.out::println);
        System.out.println("===============================");
        System.out.println(list1);
        System.out.println("===============================");
        Stream stream1=Stream.of("aa","bb");
        System.out.println(stream1.collect(Collectors.toMap(s->s,String::length)));
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.put("key4", 4);
        map.put("key5", 5);
        map.put("key5", 'h');
        map.forEach((k,v)-> System.out.println(k+"="+v));

        List<Integer> a=new ArrayList<>();
        a.add(1);
        a.add(2);
        List<Integer> b=new ArrayList<>();
        b.add(3);
        b.add(4);
        List<Integer> figures=Stream.of(a,b).flatMap(u->u.stream()).collect(Collectors.toList());
        figures.forEach(System.out::println);
        Optional optional= Stream.of(a,b).flatMap(u->u.stream()).findAny();
        System.out.println("============-------------");
        System.out.println(optional.orElse(-1));
        Integer result=Stream.of(a,b).flatMap(u->u.stream()).reduce(0,Integer::sum);
        System.out.println(result);

        List<String> words = new ArrayList<String>();
        words.add("hello");
        words.add("word");

        //将words数组中的元素再按照字符拆分，然后字符去重，最终达到["h", "e", "l", "o", "w", "r", "d"]
        //如果使用map，是达不到直接转化成List<String>的结果
        List<String> stringList = words.stream()
                .flatMap(word -> Arrays.stream(word.split("")))
                .distinct()
                .collect(Collectors.toList());

        stringList.forEach(e -> System.out.println(e));
        ExecutorService executorService= Executors.newFixedThreadPool(2);
        CompletableFuture future=CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;}).whenComplete((value,throwable)-> System.out.println("whenComplete="+value));


        try {
            System.out.println("========get=======");
            System.out.println(future.whenComplete((value,t)-> System.out.println(value)));
        } catch (Exception e) {
            e.printStackTrace();
        }


        userTest(1L, new LineFilter() {
            @Override
            public Boolean checkUser(User user) {
                return true;
            }

            @Override
            public User getUser(Long id) {
                User user=new User();
                user.setId(id);
                return user;
            }
        });
    }

}
