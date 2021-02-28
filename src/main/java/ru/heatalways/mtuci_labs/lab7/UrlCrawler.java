package ru.heatalways.mtuci_labs.lab7;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Сканер, получающий все URL адреса внутри веб-сайта вплоть до указанной глубины
 */
public class UrlCrawler {
    /**
     * Шаблон (регулярное выражение) допустимых URL-адресов
     */
    public static final String URL_REGEX = "http://[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.]*[-A-Za-z0-9+&amp;@#/%=~_()|]";

    /**
     * Посещённые и корректные URL адреса (URL-глубина)
     */
    private final List<UrlDepthPair> visitedPairs = new LinkedList<>();

    /**
     * Не проверенные URL-адреса (URL-глубина)
     */
    private final List<UrlDepthPair> remainingPairs = new LinkedList<>();

    /**
     * Стартовый URL-адрес, с которого нужно считать другие URL-адреса
     */
    private final String startUrl;

    /**
     * Максимальная глубина поиска URL-адресов
     */
    private final int maxDepth;

    /**
     * Конструктор сканера. Начинает сканировать с определённого адреса до тех пор, пока глубина
     * не превысит максимальную
     * @param startUrl начальный URL-адрес (сайт, с которого следует считать адреса)
     * @param maxDepth максиамльная глубина (1 - получит адреса только указанного сайта)
     */
    public UrlCrawler(String startUrl, int maxDepth) {
        this.startUrl = startUrl;
        this.maxDepth = maxDepth;
    }

    /**
     * Метод, начинающий сканирование. Работает в одном потоке, следовательно потребляет
     * много ресурсов и ищет контент довольно долго, блокируя поток, в котором был вызван
     */
    public void startScanning() {
        try {
            remainingPairs.add(new UrlDepthPair(startUrl, 0));
            while (!remainingPairs.isEmpty()) {
                for (int i = remainingPairs.size() - 1; i >= 0; i--) {
                    UrlDepthPair pair = remainingPairs.get(i);
                    remainingPairs.remove(i);
                    if (!visitedPairs.contains(pair)) {
                        visitedPairs.add(pair);
                        if (pair.getDepth() + 1 <= maxDepth) {
                            remainingPairs.addAll(getPairsFromUrl(
                                    pair.getUrl(),
                                    pair.getDepth() + 1
                            ));
                        }
                    }
                }
            }
        } catch (IOException ignored) {}
    }

    /**
     * Метод, возвращающий полученные в ходе сканирования адреса
     * @return адреса, полученные в ходе сканирования
     */
    public List<UrlDepthPair> getUrls() {
        return visitedPairs;
    }

    /**
     * Метод, получающий пары URL-глубина из определённого URL-адреса
     * @param url адрес, из которого следует извлечь пары
     * @param depth глубина, на котором происходит сканирование в данный момент
     * @return пары URL-глубина из определённого URL-адреса
     */
    private List<UrlDepthPair> getPairsFromUrl(String url, int depth) {
        List<UrlDepthPair> newPairs = new LinkedList<>();
        try {
            HtmlReader.readLinesFrom(url).forEach(line -> {
                getUrlsFromLine(line).forEach(urlFromLine -> {
                    try {
                        UrlDepthPair newPair = new UrlDepthPair(urlFromLine, depth);
                        newPairs.add(newPair);
                    } catch (MalformedURLException ignore) {}
                });
            });
        } catch (IOException ignored) {}
        return newPairs;
    }

    /**
     * Метод получения адресов из какой-либо строки с помощью шаблона адресов.
     * @param str строка, из которой следует извлечь все URL-адреса
     * @return список URL-адресов
     */
    private List<String> getUrlsFromLine(String str) {
        List<String> urls = new ArrayList<>();
        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher(str);
        while(m.find()) {
            String urlStr = m.group();
            if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }
            urls.add(urlStr);
        }
        return urls;
    }
}
