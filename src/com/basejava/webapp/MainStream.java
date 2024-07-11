package com.basejava.webapp;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStream {
    public static void main(String[] args) {
        int[] values = {9, 8, 7, 4, 5, 6, 1, 2, 3, 9, 6, 5, 3};
        System.out.println(minValue(values));

        List<Integer> integers = List.of(1, 2, 3, 4, 5,6,7);
        System.out.println(oddOrEven(integers));

    }

    private static int minValue(int[] values) {
        return IntStream.of(values)
                .distinct()
                .sorted()
                .reduce(0, (acc, value) -> acc * 10 + value);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream()
                .sorted()
                .reduce(0, Integer::sum);
        return integers.stream()
                .filter(n -> (sum % 2 == 0) == (n % 2 != 0))
                .collect(Collectors.toList());
    }
}
