package ru.heatalways.mtuci_labs.lab1;

public class Primes {
    public static void main(String[] args) {
        for (int number = 2; number <= 100; number++) {
            if (isPrime(number)) System.out.print(number + " ");
        }
    }

    /**
     * Метод, проверяющий, является ли данное число простым
     * @param number число, которое нужно проверить
     * @return true - если число простое, иначе - false
     */
    public static boolean isPrime(int number) {
        for (int i = 2; i < number; i++) {
            if (number % i == 0) return false;
        }
        return true;
    }
}
