import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class streamTest {

    public static void main(String[] args) {
        IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);

        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.of(new int[]{0,1,2,3}).forEach(System.out::println);

        Arrays.asList( "a", "b", "d" ).forEach(e -> {
            System.out.println( e );
        } );
        /*String separator = ",";
        Arrays.asList( "a", "b", "d" ).forEach(
                ( String e ) -> System.out.print( e + separator ) );*/
       /*String str="sss";
        Consumer c= s-> System.out.println(str+"aaaa");
        Consumer c1= s-> System.out.println(str+"bbbb");
        Consumer c2= s-> System.out.println(str+"cccc");
        c.andThen(c1).andThen(c2).accept("");*/

        /*Arrays.asList("a","b","c").sort((e1,e2)->e1.compareTo(e2));
        int x=2,y=1;
        System.out.println("result:"+(Runnable)()-> System.out.println(x-y));

        User user=User.create(User::new);
        user.setUserName("shb");
        user.setAge(10);
        System.out.println(user);
        ConcurrentHashMap map=new ConcurrentHashMap();
        map.computeIfAbsent(user,u1->getAge((User) u1));
        System.out.println(map.containsKey(user));*/

        distinctPrimary("1","2","3","6","7","8","8");
        Comparator<Integer> c2 = (x, y) -> Integer.compare(x, y);
        Comparator<Integer> c1 = Integer::compare;

    }
    private static Integer getAge(User user){
        return user.getAge();
    }

    public static void distinctPrimary(String... numbers) {
        List<String> l = Arrays.asList(numbers);
        Map<Object, Object> r = l.stream()
                .map(e -> new Integer(e))
                .filter(e -> e>5)
                .distinct()
                .collect(Collectors.toMap(a->a,a->a));
        System.out.println("distinctPrimary result is: " + r);
    }

    public void distinctPrimarySum(String... numbers) {
        List<String> l = Arrays.asList(numbers);
        int sum = l.stream()
                .map(e -> new Integer(e))
                .filter(e -> e>5)
                .distinct()
                .reduce(0, (x,y) -> x+y); // equivalent to .sum()
        System.out.println("distinctPrimarySum result is: " + sum);
    }
}
