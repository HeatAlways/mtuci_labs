package ru.heatalways.mtuci_labs.lab7;

import java.net.MalformedURLException;
import java.util.Objects;

/**
 * Класс, хранящий в себе определённый URL-адрес и глубину, на котором находится данный адрес
 * @see UrlCrawler
 */
public class UrlDepthPair {
    /**
     * Поддерживаемый протокл для URL-адресов
     */
    public static final String URL_PROTOCOL_PREFIX = "http://";

    /**
     * URL-адрес
     */
    private String url;

    /**
     * Глубина, на котором находится URL-адрес
     * @see UrlCrawler
     */
    private int depth;

    /**
     * Конструктор, создающий пару URL-глубина
     * @param url URL-адрес
     * @param depth глубина, на котором находится URL-адрес
     * @throws MalformedURLException исключение, выбрасываемое, если адрес имеет
     * не верный протокол или не имеет префикс-протокола вообще
     */
    public UrlDepthPair(String url, int depth) throws MalformedURLException {
        this.url = checkUrl(url);
        this.depth = depth;
    }

    /**
     * Метод, возвращающий URL-адрес
     * @return URL-адрес
     */
    public String getUrl() {
        return url;
    }

    /**
     * Устанавливает URL-адрес
     * @param url URL-адрес
     * @throws MalformedURLException URL-адрес исключение, выбрасываемое, если адрес имеет
     * не верный протокол или не имеет префикс-протокола вообще
     */
    public void setUrl(String url) throws MalformedURLException {
        this.url = checkUrl(url);
    }

    /**
     * Возвращает глубину URL-адреса
     * @return глубина URL-адреса
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Устанавливает глубину URL-адреса
     * @param depth глубина URL-адреса
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * Проверяет, является ли URL-адрес корректным
     * @param url URL-адрес, который следует проверить
     * @return этот-же URL-адрес, если он корректен
     * @throws MalformedURLException URL-адрес исключение, выбрасываемое, если адрес имеет
     * не верный протокол или не имеет префикс-протокола вообще
     */
    private String checkUrl(String url) throws MalformedURLException {
        if (!url.startsWith(URL_PROTOCOL_PREFIX)) throw new MalformedURLException(
                "Не верный протокол URL! Необходим \"" +
                URL_PROTOCOL_PREFIX +
                "\". URL: " + url
        );
        return url;
    }

    @Override
    public String toString() {
        return "UrlDepthPair{" +
                "url='" + url + '\'' +
                ", depth=" + depth +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlDepthPair that = (UrlDepthPair) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
