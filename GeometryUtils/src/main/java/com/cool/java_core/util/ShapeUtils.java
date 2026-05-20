package com.cool.java_core.util;

import com.cool.java_core.shaps.Shape;
import java.util.Comparator;
import java.util.List;

public final class ShapeUtils {

    private ShapeUtils() {
    }

    public static double totalArea(List<? extends Shape> shapes) {
        return shapes.stream()
                .mapToDouble(Shape::getArea)
                .sum();
    }

    public static double totalPerimeter(List<? extends Shape> shapes) {
        return shapes.stream()
                .mapToDouble(Shape::getPerimeter)
                .sum();
    }

    public static Shape findLargestArea(List<? extends Shape> shapes) {
        return shapes.stream()
                .max(Comparator.comparingDouble(Shape::getArea))
                .orElseThrow(() -> new IllegalArgumentException("Shapes list is empty"));
    }

    public static Shape findLargestPerimeter(List<? extends Shape> shapes) {
        return shapes.stream()
                .max(Comparator.comparingDouble(Shape::getPerimeter))
                .orElseThrow(() -> new IllegalArgumentException("Shapes list is empty"));
    }

    public static void printShapesInfo(List<? extends Shape> shapes) {
        shapes.forEach(shape ->
                System.out.println("""
                        Shape: %s
                        Area: %.2f
                        Perimeter: %.2f
                        """.formatted(
                        shape.getClass().getSimpleName(),
                        shape.getArea(),
                        shape.getPerimeter()
                ))
        );
    }
}
