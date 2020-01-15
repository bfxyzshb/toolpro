package com.j8.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RemoveListNull {
    public static void main(String[] args) {
        List list=new ArrayList(){{add(null);add(1);add(2);add(null);}};
        list.removeAll(Collections.singleton(null));
        System.out.println(list);

    }
}
