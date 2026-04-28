package com.cool.java_core;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamCollectorsExample {
    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order("Laptop", 1200.0),
                new Order("Smartphone", 800.0),
                new Order("Laptop", 1500.0),
                new Order("Tablet", 500.0),
                new Order("Smartphone", 900.0)
        );

        orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getProduct,
                        Collectors.summingDouble(Order::getCost)
                ))
                .entrySet()
                .stream()
                .sorted((Comparator<? super Map.Entry<String, Double>>) Map.Entry.comparingByValue().reversed())
                .limit(3)
                .forEach(result -> System.out.println(result.getKey() + " " + result.getValue()));

    }
}

class Order {
    private String product;
    private double cost;

    public Order(String product, double cost) {
        this.product = product;
        this.cost = cost;
    }

    public String getProduct() {
        return product;
    }

    public double getCost() {
        HashSet set = new HashSet();
        
        return cost;
    }


}