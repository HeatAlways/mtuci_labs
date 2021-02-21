package ru.heatalways.mtuci_labs.lab7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
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
     * Полученные строки тела HTML
     */
    private List<String> lines = new ArrayList<>();

    /**
     * Конструктор, позволяющий получить тело HTML из URL-адреса
     * @param url адрес веб-сайта, с которого следует считать информацию. Пример: http://www.example.com
     * @throws IOException выбрасываемое исключение при неудачной попытке получения данных с веб-сайта
     */
    public HtmlReader(String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        connection.setConnectTimeout(RESPONSE_TIMEOUT);
        connection.setReadTimeout(RESPONSE_TIMEOUT);
        try {
            BufferedReader htmlReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            lines = htmlReader.lines().collect(Collectors.toList());
            htmlReader.close();
        } catch (SocketTimeoutException ignored){}
    }

    /**
     * Возвращает считанные строки HTML тела
     * @return строки HTML тела
     */
    public List<String> getLines() {
        return lines;
    }
}
