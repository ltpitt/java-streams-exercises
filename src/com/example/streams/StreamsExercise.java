package com.example.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamsExercise {
    public static void main(String[] args) throws IOException {

        System.out.println("Integer Stream:");
        IntStream
                .range(1, 10)
                .forEach(System.out::print);

        System.out.println("\n\nInteger Stream with skip:");
        IntStream
                .range(1, 10)
                .skip(5)
                .forEach(x -> System.out.println(x));

        System.out.println("\n\nInteger Stream with sum:");
        System.out.println(
                IntStream
                        .range(1, 5)
                        .sum()
        );

        System.out.println("\n\nStream.of, sorted and findFirst:");
        Stream.of("Vu", "Kevin", "Farhad", "Eimert", "Davide")
                .sorted()
                .findFirst()
                .ifPresent(System.out::println);

        System.out.println("\n\nStream from Array, sort, filter and print:");
        String[] names = {"Vu", "Kevin", "Farhad", "Eimert", "Davide", "Daniel"};
        Arrays.stream(names)
                .filter(x -> x.startsWith("D"))
                .sorted()
                .forEach(System.out::println);

        System.out.println("\n\nAverage of squares of an int array:");
        Arrays.stream(new int[]{2, 4, 6, 8, 10})
                .map(x -> x * x)
                .average()
                .ifPresent(System.out::println);

        System.out.println("\n\nStream from List, filter and print:");
        List<String> people = Arrays.asList("Vu", "Kevin", "Farhad", "Eimert", "Davide", "Daniel");
        people
                .stream()
                .map(String::toLowerCase)
                .filter(x -> x.startsWith("d"))
                .forEach(System.out::println);

        System.out.println("\n\nStream rows from text file, sort, filter and print:");
        Stream<String> bands = Files.lines(Paths.get("C:\\Java\\gitlab\\learn\\streams\\src\\com\\example\\streams\\bands.txt"));
        bands
                .sorted()
                .filter(x -> x.length() > 11)
                .forEach(System.out::println);
        bands.close(); // Good people use .close() in order to prevent memory leaks

        System.out.println("\n\nStream rows from text file and save to List:");
        List<String> bands2 = Files.lines(Paths.get("C:\\Java\\gitlab\\learn\\streams\\src\\com\\example\\streams\\bands.txt"))
                .filter(x -> x.contains("The"))
                .collect(Collectors.toList());
        bands2.forEach(x -> System.out.println(x));

        System.out.println("\n\nStream rows from CSV file and count rows containing 3 items:");
        Stream<String> rows = Files.lines(Paths.get("C:\\Java\\gitlab\\learn\\streams\\src\\com\\example\\streams\\data.txt"));
        int rowCount = (int) rows
                .map(x -> x.split(","))
                .filter(x -> x.length == 3)
                .count();
        rows.close();
        System.out.println(rowCount + " rows");

        System.out.println("\n\nStream rows from CSV file, parse data from rows:");
        Stream<String> rows2 = Files.lines(Paths.get("C:\\Java\\gitlab\\learn\\streams\\src\\com\\example\\streams\\data.txt"));
        rows2
                .map(x -> x.split(","))
                .filter(x -> x.length == 3)
                .filter(x -> Integer.parseInt(x[1]) > 15)
                .forEach(x -> System.out.println(x[0] + " " + x[1] + " " + x[2]));
        rows2.close();

        System.out.println("\n\nStream rows from CSV file, store fields in HashMap:");
        Stream<String> rows3 = Files.lines(Paths.get("C:\\Java\\gitlab\\learn\\streams\\src\\com\\example\\streams\\data.txt"));
        Map<String, Integer> map = new HashMap<>();
        map = rows3
                .map(x -> x.split(","))
                .filter(x -> x.length == 3)
                .filter(x -> Integer.parseInt(x[1]) > 15)
                .collect(Collectors.toMap(
                        x -> x[0],
                        x -> Integer.parseInt(x[1])
                ));
        for (String key : map.keySet()) {
            System.out.println(key + " " + map.get(key));
        }

        System.out.println("\n\nReduction - sum:");
        // .sum() only works for integers
        // This is why here we use reduce in order to sum floats
        // 0.0 is the starting value, a will be the running total and
        // b will be the new item to be added
        double total = Stream.of(7.3, 1.5, 4.8)
                .reduce(0.0, (Double a, Double b) -> a + b);
        System.out.println("Total = " + total);

        System.out.println("\n\nReduction - summary statistics:");
        IntSummaryStatistics summary = IntStream.of(7, 2, 19, 88, 73, 4, 10)
                .summaryStatistics();
        System.out.println(summary);


        System.out.println("\n\nPrinting values that can be divided by 3 in specified range:");
        List<Integer> evenInts = IntStream.rangeClosed(1, 10)
                .filter(i -> i % 3 == 0)
                .boxed() // There’re times when we need to convert primitive values to their wrapper equivalents.
                .collect(Collectors.toList());
        evenInts.stream()
                .forEach(System.out::println);


        System.out.println("\n\nFizzBuzz ver 1:");
        IntStream.rangeClosed(0, 100).mapToObj(
                i -> i % 3 == 0 ?
                        (i % 5 == 0 ? "FizzBuzz" : "Fizz") :
                        (i % 5 == 0 ? "Buzz" : i))
                .forEach(System.out::println);


        System.out.println("\n\nFizzBuzz ver 2:");
        IntStream fizzBuzzInts = IntStream.rangeClosed(1, 100);
        fizzBuzzInts
                .forEach(i -> {
                    if (i % 15 == 0) {
                        System.out.println("FizzBuzz");
                    } else if (i % 3 == 0) {
                        System.out.println("Fizz");
                    } else if (i % 5 == 0) {
                        System.out.println("Buzz");
                    } else {
                        System.out.println(i);
                    }
                });

        System.out.println("\nDone :)");
    }
}
