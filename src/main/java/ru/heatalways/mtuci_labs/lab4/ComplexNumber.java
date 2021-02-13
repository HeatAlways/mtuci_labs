package ru.heatalways.mtuci_labs.lab4;

/**
 * Класс, представляющий собой комплексное число с соответствующие ему операции
 */
public class ComplexNumber {
    private double real;
    private double imaginary;

    /**
     * Конструктор по умолчанию, который инициализирует действительную и мнимую части
     * числом 0d (double)
     */
    public ComplexNumber() {
        real = 0.0;
        imaginary = 0.0;
    }

    /**
     * Конструктор, позволяющий самостоятельно задавать действительную и мнимую части
     * комплексного числа
     * @param real дествительная часть
     * @param imaginary мнимая часть
     */
    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * Метод сложения, который ничего не возвращает, но изменяет состояние объекта,
     * у которого был вызван метод
     * @param z комплексное число, которое следует прибавить
     */
    public void add(ComplexNumber z) {
        set(add(this,z));
    }

    /**
     * Метод вычитания, который ничего не возвращает, но изменяет состояние объекта,
     * у которого был вызван метод
     * @param z комплексное число, которое следует вычесть
     */
    public void subtract(ComplexNumber z) {
        set(subtract(this,z));
    }

    /**
     * Метод умножения, который ничего не возвращает, но изменяет состояние объекта,
     * у которого был вызван метод
     * @param z комплексное число, на которое следует умножить
     */
    public void multiply(ComplexNumber z) {
        set(multiply(this,z));
    }

    /**
     * Метод деления, который ничего не возвращает, но изменяет состояние объекта,
     * у которого был вызван метод
     * @param z комплексное число, на которое следует поделить
     */
    public void divide(ComplexNumber z) {
        set(divide(this,z));
    }

    public void set(ComplexNumber z) {
        this.real = z.real;
        this.imaginary = z.imaginary;
    }

    public static ComplexNumber add(ComplexNumber z1, ComplexNumber z2) {
        return new ComplexNumber(z1.real + z2.real, z1.imaginary + z2.imaginary);
    }

    public static ComplexNumber subtract(ComplexNumber z1, ComplexNumber z2) {
        return new ComplexNumber(z1.real - z2.real, z1.imaginary - z2.imaginary);
    }

    public static ComplexNumber multiply(ComplexNumber z1, ComplexNumber z2) {
        double _real = z1.real*z2.real - z1.imaginary*z2.imaginary;
        double _imaginary = z1.real*z2.imaginary + z1.imaginary*z2.real;
        return new ComplexNumber(_real,_imaginary);
    }

    public static ComplexNumber divide(ComplexNumber z1, ComplexNumber z2) {
        ComplexNumber output = multiply(z1,z2.conjugate());
        double div = z2.mod() * z2.mod();
        return new ComplexNumber(output.real/div,output.imaginary/div);
    }

    public ComplexNumber conjugate() {
        return new ComplexNumber(this.real,-this.imaginary);
    }

    public double mod() {
        return Math.sqrt(this.real * this.real + this.imaginary * this.imaginary);
    }

    public ComplexNumber square() {
        double _real = this.real*this.real - this.imaginary*this.imaginary;
        double _imaginary = 2*this.real*this.imaginary;
        return new ComplexNumber(_real,_imaginary);
    }
}
