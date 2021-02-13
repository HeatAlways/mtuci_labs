package ru.heatalways.mtuci_labs.lab4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

/**
 * Класс, позволяющий отображать фрактал в окне
 */
public class FractalExplorer extends JFrame {
    /**
     * Заголовк окна программы
     */
    private static final String WINDOW_TITLE = "Fractal Explorer";

    /**
     * Коэффициент увеличения зума
     */
    private static final double SCALE_FACTOR = 0.5;

    /**
     * Стандартный размер окна
     */
    private static final int DEFAULT_WINDOW_SIZE = 600;

    /**
     * Размер окна
     */
    private final int windowSize;

    /**
     * Объект, позволяющий отображать объекты в окне
     */
    private final JImageDisplay imageDisplay;

    /**
     * Базовый класс для генерации фракталов
     */
    private final FractalGenerator fractalGenerator;

    /**
     * Диапазон комплексной плоскости
     */
    private final Rectangle2D.Double range = new Rectangle2D.Double();


    /**
     * Конструктор по умолчанию, который устанавливает значение размера окна как 800x800 пикселей
     */
    public FractalExplorer() {
        this(DEFAULT_WINDOW_SIZE);
    }

    /**
     * Конструктор, позволяющий пользователю вручную установить размер окна
     * @param windowSize размер окна (размер = высота = ширина)
     */
    public FractalExplorer(int windowSize) {
        this.windowSize = windowSize;
        this.fractalGenerator = new Mandelbrot();
        this.fractalGenerator.getInitialRange(range);
        this.imageDisplay = new JImageDisplay(windowSize, windowSize);
    }

    /**
     * Метод, который необходимо вызвать, чтобы отобразить интерфейс программы
     */
    public void createAndShowGUI() {
        // Установка лэйаута
        setLayout(new BorderLayout());

        // Настройка элементов интерфейса
        setTitle(WINDOW_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fractalGenerator.recenterAndZoomRange(
                        range,
                        getFractalX(e.getX()),
                        getFractalY(e.getY()),
                        SCALE_FACTOR
                );
                drawFractal();
            }
        });

        JButton button = new JButton("Reset display");
        button.addActionListener(e -> {
            fractalGenerator.getInitialRange(range);
            drawFractal();
        });

        // Добавление элементов в лэйаут
        add(imageDisplay, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);

        // Отображение элементов
        pack();
        setVisible(true);
        setResizable(false);
    }

    /**
     * Метод, рисующий фракталы в окне
     */
    public void drawFractal() {
        for (int y = 0; y < windowSize; y++) {
            for (int x = 0; x < windowSize; x++) {
                double xCoord = getFractalX(x);
                double yCoord = getFractalY(y);

                int iterationsCount = fractalGenerator.numIterations(xCoord, yCoord);
                int rgbColor = JImageDisplay.BLACK_COLOR;

                if (iterationsCount > -1) {
                    float hue = 0.7f + (float) iterationsCount / 200f;
                    rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                }

                imageDisplay.drawPixel(x, y, rgbColor);
            }
        }
        imageDisplay.repaint();
    }

    private double getFractalX(int x) {
        return FractalGenerator.getCoord (
                range.x,
                range.x + range.width,
                windowSize,
                x
        );
    }

    private double getFractalY(int y) {
        return FractalGenerator.getCoord (
                range.y,
                range.y + range.height,
                windowSize,
                y
        );
    }

    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer();
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }
}
