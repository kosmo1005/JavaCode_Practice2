package com.cool.java_core.three_dimensional_shapes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pyramid implements ThreeDimShape {

    /**
     * Площадь основания
     */
    private double baseArea;

    /**
     * Высота пирамиды
     */
    private double height;

    /**
     * Площадь боковой поверхности
     */
    private double lateralArea;

    @Override
    public double getVolume() {
        return (1.0 / 3.0) * baseArea * height;
    }

    @Override
    public double getSurfaceArea() {
        return baseArea + lateralArea;
    }
}
