package com.cool.livecoding;

// Ограничения:
// - Необходимо создать новый Map, где ключи и значения поменяны местами.
// - Исходный Map не должен изменяться.
// - Предполагается, что значения в исходном Map уникальны.
//
// Примеры:
//
// input:
// {1="one", 2="two", 3="three"}
// output:
// {"one"=1, "two"=2, "three"=3}
//
// input:
// {"a"=100, "b"=200}
// output:
// {100="a", 200="b"}
//
// input:
// {"java"="backend", "sql"="database"}
// output:
// {"backend"="java", "database"="sql"}

import java.util.Map;
import java.util.stream.Collectors;

public class ReverseMap {

    public static void main(String[] args) {

        Map <Integer,String> input = Map.of(1,"one", 2,"two", 3,"three");
        System.out.println(reverse(input));
    }

    public static <K, V> Map<V, K> reverse(Map<K, V> source) {

        return source.entrySet().stream()
                .map(x -> Map.entry(x.getValue(),x.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }
}