package ru.heatalways.mtuci_labs.lab2;

/**
 * Класс, представляющий собос некую обёртку трёхмерных координат (x, y, z).
 * По умолчанию конструктор инициализирует данные три переменные значением 0.
 */
public class Point3D extends Point2D {
    private double z;

    public Point3D() { this(0, 0, 0); }

    public Point3D(double x, double y, double z) {
        super(x, y);
        this.z = z;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Метод, находящий расстояние между двумя точками в трёхмерном пространстве.
     * @param point точка, до которой нужно найти расстояние.
     * @return расстояние между текущей точкой и указанной в параметре.
     */
    public double distanceTo(Point3D point) {
        return Math.round(Math.sqrt(
                    Math.pow(point.getX() - getX(), 2) +
                    Math.pow(point.getY() - getY(), 2) +
                    Math.pow(point.getZ() - getZ(), 2)
        ) * 100d) / 100d;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return  Double.compare(point3D.getX(), getX()) == 0 &&
                Double.compare(point3D.getY(), getY()) == 0 &&
                Double.compare(point3D.getZ(), getZ()) == 0;
    }
}
