package ru.heatalways.mtuci_labs.lab7;

import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Пул адресов (как и посещённых, так и тех, которые необходимо проверить), которые
 * были найденны в ходе "парсинга" адреса и нахождения внутри него других адресов
 */
public class UrlsPool {
    /**
     * Посещённые и корректные адреса
     */
    private final List<UrlDepthPair> visitedUrls = new LinkedList<>();

    /**
     * Оставшиеся и не проверенные адреса
     */
    private final List<UrlDepthPair> remainingUrls = new LinkedList<>();

    /**
     * Максимальная глубина поиска адресов
     */
    private final int maxDepth;

    /**
     * Кол-во потоков, находящихся в ожидании
     */
    private int waitingThreadsCount = 0;

    /**
     * Конструктор, принимающий на вход адрес, с которого необходимо начать
     * сканирование, а также максимальную глубину, при достижении которой
     * сканер заканчивает свою работу.
     * @param startUrl адрес, с которого необходимо начать сканирование
     * @param maxDepth максимальная глубина, при достижении которой сканер
     * заканчивает свою работу
     */
    public UrlsPool(String startUrl, int maxDepth) {
        this.maxDepth = maxDepth;

        try {
            remainingUrls.add(new UrlDepthPair(startUrl, 0));
        } catch (MalformedURLException ignored) {}
    }

    /**
     * Метод, "достающий" пару адрес-глубина из списка и удаляющий её
     * @return пара глубина-адрес {@link UrlDepthPair}
     */
    public synchronized UrlDepthPair pop() {
        while (remainingUrls.size() == 0) {
            try {
                waitingThreadsCount++;
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        UrlDepthPair url = remainingUrls.get(0);
        remainingUrls.remove(0);
        return url;
    }

    /**
     * Метод, позволяющий положить пару адрес-глубина {@link UrlDepthPair}
     * для будущего сканирования
     * @param url пара адрес-глубинга {@link UrlDepthPair}
     */
    public synchronized void push(UrlDepthPair url) {
        if (!remainingUrls.contains(url)) {
            remainingUrls.add(url);
            notify();
            waitingThreadsCount = Math.max(waitingThreadsCount, 0);
        }
    }

    /**
     * Добавляет пару адрес-глубина {@link UrlDepthPair} в список посещённых
     * @param url пара адрес-глубина {@link UrlDepthPair}
     */
    public synchronized void addVisitedUrl(UrlDepthPair url) {
        System.out.println(url);
        visitedUrls.add(url);
    }

    /**
     * Возвращает все посещённые в ходе сканирования пары адрес-глубина
     * @return все посещённые пары адрес-глубина {@link UrlDepthPair}
     */
    public List<UrlDepthPair> getVisitedUrls() {
        return visitedUrls;
    }

    /**
     * Возвращает кол-во потоков, находящихся в ожидании
     * @return кол-во потоков, находящихся в ожидании
     */
    public int getWaitingThreadsCount() {
        return waitingThreadsCount;
    }

    /**
     * Возвращает максимальную глубину сканирования
     * @return максимальная глубина сканирования
     */
    public int getMaxDepth() {
        return maxDepth;
    }
}
