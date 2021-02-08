package ru.heatalways.mtuci_labs.lab2;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Lab2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Point3D[] points = new Point3D[3];
        for (int i = 0; i < points.length; i++) {
            System.out.println("Введите координаты для точки №" + (i + 1) + ". Пример: 12 -36.2 2.445");

            List<Double> coords = Arrays
                    .stream(scanner.nextLine().split(" "))
                    .map(Double::parseDouble)
                    .collect(Collectors.toList());

            points[i] = new Point3D(coords.get(0), coords.get(1), coords.get(2));
        }

        if (!points[0].equals(points[1]) &&
            !points[1].equals(points[2]) &&
            !points[2].equals(points[0])) {

            System.out.println("Площадь тругольника равна: " + computeArea(
                    points[0],
                    points[1],
                    points[2]
            ));
        }
    }

    /**
     * Метод, находящий площадь треугольника в трёхмерном пространстве по 3 точкам
     * @param point1 первая точка
     * @param point2 вторая точка
     * @param point3 третья точка
     * @return площадь треугольника, сформированная данными тремя точками
     */
    public static double computeArea(Point3D point1, Point3D point2, Point3D point3) {
        // Вычисляем стороны треугольника
        double a = point1.distanceTo(point2);
        double b = point2.distanceTo(point3);
        double c = point3.distanceTo(point1);

        // Находим полупериметр треугольника
        double p = (a + b + c) / 2d;

        // Находим площадь треугольника по формуле Герона
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }
}
