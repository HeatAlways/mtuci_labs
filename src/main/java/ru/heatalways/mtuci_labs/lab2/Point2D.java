package ru.heatalways.mtuci_labs.lab2;

public class Point2D {
    /** координата X **/
    protected double xCoord;

    /** координата Y **/
    protected double yCoord;

    /** Конструктор инициализации **/
    public Point2D( double x, double y) {
        xCoord = x;
        yCoord = y;
    }

    /** Конструктор по умолчанию. **/
    public Point2D() {
        //Вызовите конструктор с двумя параметрами и определите источник.
        this(0, 0);
    }

    /** Возвращение координаты X **/
    public double getX () {
        return xCoord;
    }

    /** Возвращение координаты Y **/
    public double getY () {
        return yCoord;
    }

    /** Установка значения координаты X. **/
    public void setX ( double val) {
        xCoord = val;
    }

    /** Установка значения координаты Y. **/
    public void setY ( double val) {
        yCoord = val;
    }
}
