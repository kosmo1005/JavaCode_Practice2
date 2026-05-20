package com.cool.java_core.shaps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Triangle implements Shape {

    private double sideA;
    private double sideB;
    private double sideC;

    @Override
    public String getName(){
        return "Triangle";
    }

    @Override
    public double getArea() {
        double semiPerimeter = getPerimeter() / 2;

        return Math.sqrt(
                semiPerimeter
                        * (semiPerimeter - sideA)
                        * (semiPerimeter - sideB)
                        * (semiPerimeter - sideC)
        );
    }

    @Override
    public double getPerimeter() {
        return sideA + sideB + sideC;
    }
}
