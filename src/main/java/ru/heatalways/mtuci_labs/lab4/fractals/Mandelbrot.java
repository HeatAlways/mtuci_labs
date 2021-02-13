package ru.heatalways.mtuci_labs.lab4.fractals;

import ru.heatalways.mtuci_labs.lab4.ComplexNumber;
import ru.heatalways.mtuci_labs.lab4.FractalGenerator;

import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator {
    public static final int MAX_ITERATIONS = 2000;

    public static final double INITIAL_X = -2;
    public static final double INITIAL_Y = -1.5;
    public static final double INITIAL_WIDTH = 3;
    public static final double INITIAL_HEIGHT = 3;

    /**
     * Установка начального диапазона для корректного отображения фракталов
     * @param range "интересная" область для генератора фракталов
     */
    @Override
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = INITIAL_X;
        range.y = INITIAL_Y;
        range.width = INITIAL_WIDTH;
        range.height = INITIAL_HEIGHT;
    }

    /**
     * Возвращает кол-во итераций для конкретной точки фракатала
     */
    @Override
    public int numIterations(double x, double y) {
        ComplexNumber c = new ComplexNumber(x, y);
        ComplexNumber z = ComplexNumber.add(new ComplexNumber(0, 0), c);

        int iteration = 0;

        while ((z.mod())*(z.mod()) <= 4) {
            z = ComplexNumber.add(z.square(), c);
            iteration++;

            if (iteration >= MAX_ITERATIONS) return -1;
        }

        return iteration;
    }

    @Override
    public String toString() {
        return "Mandelbrot";
    }
}
