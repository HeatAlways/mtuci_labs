package ru.heatalways.mtuci_labs.lab7;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Lab7 {
    public static void main(String[] args) {

        if (args.length != 2) {
            throwIllegalArgumentException();
        }

        String startUrl = args[0];
        String maxDepthStr = args[1];
        System.out.println(
                "URL: " + startUrl + "\n" +
                "Максиамльная глубина: " + maxDepthStr
        );

        int maxDepth = 0;
        try {
            maxDepth = Integer.parseInt(maxDepthStr);
        } catch (NumberFormatException e) {
            throwIllegalArgumentException();
        }

        if (!isUrlValid(startUrl) || maxDepth <= 0) {
            throwIllegalArgumentException();
        }

        UrlCrawler urlCrawler = new UrlCrawler(startUrl, maxDepth);
        urlCrawler.startScanning();
        System.out.println(urlCrawler.getUrls().stream()
                .map(it -> "Depth: " + it.getDepth() + "; URL: " + it.getUrl())
                .collect(Collectors.joining("\n"))
        );
    }

    private static void throwIllegalArgumentException() {
        throw new IllegalArgumentException(
                "Некорректные входные данные! " +
                        "Использование: Crawler(<URL>, <положительное целое число>)"
        );
    }

    private static boolean isUrlValid(String url) {
        Pattern pattern = Pattern.compile(UrlCrawler.URL_REGEX);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
}
