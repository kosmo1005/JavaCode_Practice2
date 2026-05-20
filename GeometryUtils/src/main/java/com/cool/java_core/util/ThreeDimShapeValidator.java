package com.cool.java_core.util;

import com.cool.java_core.three_dimensional_shapes.Cube;
import com.cool.java_core.three_dimensional_shapes.Pyramid;
import com.cool.java_core.three_dimensional_shapes.ThreeDimShape;
import com.cool.java_core.three_dimensional_shapes.Sphere;

public class ThreeDimShapeValidator {

        private ThreeDimShapeValidator() {
        }

        public static boolean isValid(ThreeDimShape shape) {

            if (shape instanceof Cube cube) {
                return cube.getSide() > 0;
            }

            if (shape instanceof Sphere sphere) {
                return sphere.getRadius() > 0;
            }

            if (shape instanceof Pyramid pyramid) {

                return pyramid.getBaseArea() > 0
                        && pyramid.getHeight() > 0
                        && pyramid.getLateralArea() > 0;
            }

            return false;
        }

        public static void validate(ThreeDimShape shape) {

            if (!isValid(shape)) {
                throw new IllegalArgumentException(
                        "Invalid solid shape: " + shape.getClass().getSimpleName()
                );
            }
        }
    }
