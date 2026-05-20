package com.cool.java_core.util;

import com.cool.java_core.three_dimensional_shapes.ThreeDimShape;

import java.util.Comparator;
import java.util.List;

public class ThreeDimShapeUtils {

    private ThreeDimShapeUtils() {
    }

    public static double totalVolume(List<? extends ThreeDimShape> shapes) {
        return shapes.stream()
                .mapToDouble(ThreeDimShape::getVolume)
                .sum();
    }

    public static double totalSurfaceArea(List<? extends ThreeDimShape> shapes) {
        return shapes.stream()
                .mapToDouble(ThreeDimShape::getSurfaceArea)
                .sum();
    }

    public static ThreeDimShape findLargestVolume(List<? extends ThreeDimShape> shapes) {
        return shapes.stream()
                .max(Comparator.comparingDouble(ThreeDimShape::getVolume))
                .orElseThrow(() -> new IllegalArgumentException("Shapes list is empty"));
    }

    public static ThreeDimShape findLargestSurfaceArea(List<? extends ThreeDimShape> shapes) {
        return shapes.stream()
                .max(Comparator.comparingDouble(ThreeDimShape::getSurfaceArea))
                .orElseThrow(() -> new IllegalArgumentException("Shapes list is empty"));
    }

    public static void printShapesInfo(List<? extends ThreeDimShape> shapes) {

        shapes.forEach(shape ->
                System.out.println("""
                        Shape: %s
                        Volume: %.2f
                        Surface Area: %.2f
                        """.formatted(
                        shape.getClass().getSimpleName(),
                        shape.getVolume(),
                        shape.getSurfaceArea()
                ))
        );
    }
}
