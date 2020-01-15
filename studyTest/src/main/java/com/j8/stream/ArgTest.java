package com.j8.stream;

public class ArgTest {

    public static void main(String[] args) {
        User user = new User();
        user.setAge(1);
        test(user);
        System.out.println(user.getAge());
    }

    private static void test(User user) {
        user=new User();
        user.setAge(10);
    }

    static class User {
        String name = "";
        int age = 0;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}
