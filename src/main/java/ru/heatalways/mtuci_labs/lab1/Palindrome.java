package ru.heatalways.mtuci_labs.lab1;

public class Palindrome {
    public static void main(String[] args) {
        for (String s : args) {
            System.out.println(s + " is palindrome? " + isPalindrome(s));
        }
    }

    /**
     * Метод, переворачивающий строку. Пример: "song" -> "gnos"
     * @param str строка, котору нужно перевернуть
     * @return перевёрнутая строка
     */
    public static String reverseString(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char symbol: str.toCharArray()) {
            stringBuilder.insert(0, symbol);
        }
        return stringBuilder.toString();
    }

    /**
     * Метод, проверяющий, является ли данная строка палиндромом. Пример "noon" -> true, "song" -> false
     * @param str строка, которую нужно проверить
     * @return true - если строка является палиндромом, иначе - false
     */
    public static boolean isPalindrome(String str) {
        return str.equals(reverseString(str));
    }
}
