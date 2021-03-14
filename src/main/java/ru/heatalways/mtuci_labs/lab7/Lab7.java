package ru.heatalways.mtuci_labs.lab7;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lab7 {
    public static void main(String[] args) {

        if (args.length != 3) {
            throwIllegalArgumentException();
        }

        String startUrl = args[0];
        String maxDepthStr = args[1];
        String threadsCountStr = args[2];

        System.out.println(
                "URL: " + startUrl + "\n" +
                "Максиамльная глубина: " + maxDepthStr + "\n" +
                "Кол-во потоков: " + threadsCountStr
        );

        int maxDepth = 0;
        try {
            maxDepth = Integer.parseInt(maxDepthStr);
        } catch (NumberFormatException e) {
            throwIllegalArgumentException();
        }

        int threadsCount = 0;
        try {
            threadsCount = Integer.parseInt(threadsCountStr);
        } catch (NumberFormatException e) {
            throwIllegalArgumentException();
        }

        if (!isUrlValid(startUrl) || maxDepth <= 0) {
            throwIllegalArgumentException();
        }



        UrlsPool urlsPool = new UrlsPool(startUrl, maxDepth);

        for (int i = 0; i < threadsCount; i++) {
            new Thread(new CrawlerTask(urlsPool)).start();
        }

        int finalThreadsCount = threadsCount;
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (urlsPool.getWaitingThreadsCount() == finalThreadsCount) {
                    return;
                }
            }
        }).start();
    }

    private static void throwIllegalArgumentException() {
        throw new IllegalArgumentException(
                "Некорректные входные данные! " +
                        "Использование: Crawler(<URL>, <положительное целое число>, <кол-во потоков>)"
        );
    }

    private static boolean isUrlValid(String url) {
        Pattern pattern = Pattern.compile(CrawlerTask.URL_REGEX);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
}
