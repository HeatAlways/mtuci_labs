package ru.heatalways.mtuci_labs.lab7;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, отвечающий за поиск всех внутренних ссылок сайта определённого адреса.
 * Сам адрес достаётся из пула, переданного в конструктор обхекта при создании.
 */
public class CrawlerTask implements Runnable {
    /**
     * Шаблон (регулярное выражение) допустимых URL-адресов
     */
    public static final String URL_REGEX = "http://[-A-Za-z0-9+&amp;@#/%?=~_()|!:,.]*[-A-Za-z0-9+&amp;@#/%=~_()|]";

    /**
     * Ссылка на пул адресов, где хранятся все адреса, которые необходимо обработать
     */
    private final UrlsPool urlsPool;

    /**
     * Конструктор задачи-потока с помощью пула адресов
     * @param urlsPool пул адресов
     */
    public CrawlerTask(UrlsPool urlsPool) {
        this.urlsPool = urlsPool;
    }

    /**
     * Метод, запускающий алгоритм поиска ссылок: забирает доступную ссылку из
     * пула и находит в ней все внутренние ссылки, каждую из которых передаёт в пул.
     */
    @Override
    public void run() {
        while (true) {
            UrlDepthPair pair = urlsPool.pop();
            urlsPool.addVisitedUrl(pair);
            if (pair != null && pair.getDepth() < urlsPool.getMaxDepth()) {
                try {
                    getUrlsFromLines(HtmlReader.readLinesFrom(pair.getUrl())).forEach(url -> {
                        try {
                            urlsPool.push(new UrlDepthPair(url, pair.getDepth() + 1));
                        } catch (MalformedURLException ignored) {}
                    });
                } catch (IOException ignored) {}
            }
        }
    }

    /**
     * Получает все найденные URL-адреса по всем переданным строкам
     * @param lines строки, из которых необходимо достать все ссылки
     * @return все найденные URL-адреса по всем переданным строкам
     */
    private List<String> getUrlsFromLines(List<String> lines) {
        List<String> urls = new ArrayList<>();
        lines.forEach(line -> urls.addAll(getUrlsFromLine(line)));
        return urls;
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
