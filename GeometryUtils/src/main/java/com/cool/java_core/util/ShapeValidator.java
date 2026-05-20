package com.cool.java_core.util;

import com.cool.java_core.shaps.Rectangle;
import com.cool.java_core.shaps.Shape;
import com.cool.java_core.shaps.Circle;
import com.cool.java_core.shaps.Triangle;

public final class ShapeValidator {

    private ShapeValidator() {
    }

    public static boolean isValid(Shape shape) {

        if (shape instanceof Circle circle) {
            return circle.getRadius() > 0;
        }

        if (shape instanceof Rectangle rectangle) {
            return rectangle.getWidth() > 0
                    && rectangle.getHeight() > 0;
        }

        if (shape instanceof Triangle triangle) {

            double a = triangle.getSideA();
            double b = triangle.getSideB();
            double c = triangle.getSideC();

            return a > 0
                    && b > 0
                    && c > 0
                    && a + b > c
                    && a + c > b
                    && b + c > a;
        }

        return false;
    }

    public static void validate(Shape shape) {

        if (!isValid(shape)) {
            throw new IllegalArgumentException(
                    "Invalid shape: " + shape.getClass().getSimpleName()
            );
        }
    }
}
