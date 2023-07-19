package com.urise.webapp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamHomeWork {
    public static void main(String[] args) {
        int[] values = new int[]{9, 1, 8};
        System.out.println(minValue(values));

        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(2);
        System.out.println(oddOrEven(list));
    }

    static int minValue(int[] values) {
        return IntStream.of(values).boxed().collect(Collectors.toCollection(TreeSet::new)).
                stream().reduce(0, (result, element) -> result * 10 + element);
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().reduce(0, (result, element) -> result + element);
        if (sum % 2 == 0) {
            return integers.stream().filter(integer -> (integer % 2 != 0)).toList();
        } else {
            return integers.stream().filter(integer -> (integer % 2 == 0)).toList();
        }
    }
}