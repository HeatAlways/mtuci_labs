package ru.heatalways.mtuci_labs.lab7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, предназначенный для считывания тела HTML по заданному URL-адресу
 */
public class HtmlReader {
    /**
     * Максимальное время ожидания ответа от веб-сайта в миллисекундах
     */
    public static final int RESPONSE_TIMEOUT = 5000; // in milliseconds

    /**
     * Метод, получающий HTML-тело определённого адрес
     * @param url адрес веб-сайта, с которого следует считать информацию. Пример: http://www.example.com
     * @return строки HTML-тела
     * @throws IOException выбрасываемое исключение при неудачной попытке получения данных с веб-сайта
     */
    public static List<String> readLinesFrom(String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        connection.setConnectTimeout(RESPONSE_TIMEOUT);
        connection.setReadTimeout(RESPONSE_TIMEOUT);
        BufferedReader htmlReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        List<String> result = htmlReader.lines().collect(Collectors.toList());
        htmlReader.close();
        return result;
    }
}
