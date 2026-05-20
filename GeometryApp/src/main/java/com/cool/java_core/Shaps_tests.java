package com.cool.java_core;

import com.cool.java_core.shaps.Circle;
import com.cool.java_core.shaps.Rectangle;
import com.cool.java_core.shaps.Shape;
import com.cool.java_core.shaps.Triangle;
import com.cool.java_core.three_dimensional_shapes.Cube;
import com.cool.java_core.three_dimensional_shapes.Pyramid;
import com.cool.java_core.three_dimensional_shapes.Sphere;
import com.cool.java_core.three_dimensional_shapes.ThreeDimShape;
import com.cool.java_core.util.ShapeUtils;
import com.cool.java_core.util.ShapeValidator;
import com.cool.java_core.util.ThreeDimShapeUtils;
import com.cool.java_core.util.ThreeDimShapeValidator;

import java.util.List;

public class Shaps_tests {
    public static void main(String[] args) {
        List <Shape> shapesList = List.of(new Circle(72),
                new Rectangle(34, 71),
                new Triangle(22,22,17));

        for (Shape shape : shapesList) {
            System.out.println(shape.getName());
            ShapeValidator.validate(shape);
            System.out.println(shape.getArea());
            System.out.println(shape.getPerimeter() + "\n");
        }

        ShapeUtils.printShapesInfo(shapesList);
        System.out.println("totalArea: " + ShapeUtils.totalArea(shapesList));
        System.out.println("totalPerimeter: " + ShapeUtils.totalPerimeter(shapesList));
        System.out.println("LargestArea is " + ShapeUtils.findLargestArea(shapesList).getName());
        System.out.println("LargestPerimeter is " + ShapeUtils.findLargestPerimeter(shapesList) + "\n" + "----------------" + "\n");


        List<ThreeDimShape> threeDimShapeList = List.of(new Cube(38),
                new Pyramid(13, 70, 34),
                new Sphere(66));

        for(ThreeDimShape threeDimShape : threeDimShapeList) {
            ThreeDimShapeValidator.validate(threeDimShape);
        }
        ThreeDimShapeUtils.printShapesInfo(threeDimShapeList);
        System.out.println("totalSurfaceArea: " + ThreeDimShapeUtils.totalSurfaceArea(threeDimShapeList));
        System.out.println("totalVolume: " + ThreeDimShapeUtils.totalVolume(threeDimShapeList));
        System.out.println("LargestVolume is " + ThreeDimShapeUtils.findLargestVolume(threeDimShapeList));
        System.out.println("LargestSurfaceArea is " + ThreeDimShapeUtils.findLargestSurfaceArea(threeDimShapeList));
    }
}
