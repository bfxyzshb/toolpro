package com.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class CompareAndSetTest {
    public static void main(String[] args) {
        //compareAndSet();
        AtomicIntegerFieldUpdaterTest();
    }

    /**
     * expect 期望值(上一次的执行结果)，update 准备更新的值
     */
    private static void compareAndSet() {
        AtomicInteger atomicInteger=new AtomicInteger(0);
        atomicInteger.compareAndSet(0,10);
        System.out.println(atomicInteger.intValue());
    }

    /**
     1）例子中，对于字段ID的修改，其中id的修饰必须是基本类型数据，用volatile修饰，不能是包装类型，int,long就可以，但是不可以是Integer和Long；
     2） 必须是实例变量，不可以是类变量；
     3） 必须是可变的变量，不能是final修饰的变量
     */
    private static void AtomicIntegerFieldUpdaterTest(){
        AtomicIntegerFieldUpdater<Person> mAtoLong = AtomicIntegerFieldUpdater.newUpdater(Person.class, "id");
        Person person = new Person(12345);
        mAtoLong.compareAndSet(person, 12345, 1000);
        System.out.println("id="+person.getId());

    }
    static class Person {
        volatile int id;
        public Person(int id) {
            this.id = id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public int getId() {
            return id;
        }
    }


}



