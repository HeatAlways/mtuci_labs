package ru.heatalways.mtuci_labs.lab4;

import ru.heatalways.mtuci_labs.lab4.fractals.BurningShip;
import ru.heatalways.mtuci_labs.lab4.fractals.Mandelbrot;
import ru.heatalways.mtuci_labs.lab4.fractals.Tricorn;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

/**
 * Класс, позволяющий отображать фрактал в окне
 */
public class FractalExplorer extends JFrame {
    /**
     * Расширение поддерживаемых файлов для сохранения
     */
    private static final String SAVE_FILE_EXTENSION = "png";

    /**
     * Описание поддерживаемых файлов для сохранения
     */
    private static final String SAVE_FILE_DESCRIPTION = "PNG Images";

    /**
     * Заголовок диалогового окна, информирующего об ошибке
     */
    private static final String ERROR_DIALOG_TITLE = "Can't save image!";

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
    private FractalGenerator fractalGenerator;

    /**
     * Диапазон комплексной плоскости
     */
    private final Rectangle2D.Double range = new Rectangle2D.Double();

    /**
     * Поле, хранящее в себе кол-во строк, которое необходимо перерисовать
     */
    private int rowsRemaining;


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
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // Настройка элементов интерфейса
        setTitle(WINDOW_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (rowsRemaining == 0) {
                    fractalGenerator.recenterAndZoomRange(
                            range,
                            getFractalX(e.getX()),
                            getFractalY(e.getY()),
                            SCALE_FACTOR
                    );
                    drawFractal();
                }
            }
        });

        // выбор фрактала
        JLabel fractalSelectionLabel = new JLabel("Fractal: ", SwingConstants.RIGHT);

        JComboBox<FractalGenerator> fractalSelectionComboBox = new JComboBox<>();
        fractalSelectionComboBox.addItem(new Mandelbrot());
        fractalSelectionComboBox.addItem(new Tricorn());
        fractalSelectionComboBox.addItem(new BurningShip());
        fractalSelectionComboBox.setSelectedIndex(0);
        fractalSelectionComboBox.addActionListener(e -> {
            fractalGenerator = (FractalGenerator) fractalSelectionComboBox.getSelectedItem();
            assert fractalGenerator != null;
            fractalGenerator.getInitialRange(range);
            drawFractal();
        });

        // Кнопка сохранения изображения фрактала
        JButton buttonSave = new JButton("Save Image");
        buttonSave.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter(
                    SAVE_FILE_DESCRIPTION, SAVE_FILE_EXTENSION
            ));
            fileChooser.setAcceptAllFileFilterUsed(false);
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    File selectedFile = getFileAsPNG(fileChooser.getSelectedFile());
                    ImageIO.write(
                            imageDisplay.getImage(),
                            SAVE_FILE_EXTENSION,
                            selectedFile
                    );
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(
                            this,
                            ioException.getMessage(),
                            ERROR_DIALOG_TITLE,
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });


        // Кнопка сброса изображения
        JButton buttonReset = new JButton("Reset Display");
        buttonReset.addActionListener(e -> {
            fractalGenerator.getInitialRange(range);
            drawFractal();
        });

        // Добавление элементов в лэйаут
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        add(fractalSelectionLabel, constraints);

        constraints.gridwidth = 1;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        add(fractalSelectionComboBox, constraints);

        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(imageDisplay, constraints);

        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 0.5;
        add(buttonSave, constraints);

        constraints.gridwidth = 2;
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weightx = 0.5;
        add(buttonReset, constraints);

        // Отображение элементов
        pack();
        setVisible(true);
        setResizable(false);
    }

    /**
     * Метод, рисующий фракталы в окне
     */
    public void drawFractal() {
        rowsRemaining = windowSize;
        for (int y = 0; y < windowSize; y++) {
            new FractalWorker(y).execute();
        }
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

    private File getFileAsPNG(File file) {
        if (file.getName().toLowerCase().endsWith(SAVE_FILE_EXTENSION)) return file;
        return new File(file.getParent(), file.getName()+ '.' + SAVE_FILE_EXTENSION);
    }

    /**
     * Метод, позволяющий отключить пользовательский интерфейс во время загрузки изображения
     * @param isEnabled true - включить интерфейс, false - выключить интерфейс
     */
    private void setUIEnabled(boolean isEnabled) {
        for (Component component : getContentPane().getComponents()) {
            component.setEnabled(isEnabled);
        }
    }

    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer();
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }

    /**
     * Класс, предназначенный для вычисления цветов пикселей строки фрактала
     */
    private class FractalWorker extends SwingWorker<Object, Object> {
        /**
         * Индекс строки, которую следует перерисовать
         */
        private final int rowIndex;

        /**
         * Строка, хранящая в себе значения цветов пикслей конкретной строки
         */
        private int[] rowRgbs;

        /**
         * Конструктор, принимающий на вход индекс строки, которую следует перерисовать
         * @param rowIndex индекс строки, которую следует перерисовать
         */
        public FractalWorker(int rowIndex) {
            this.rowIndex = rowIndex;
        }

        /**
         * Метод, выполняющийся в другом потоке. Предназначен для вычисления
         * цветов пикселей фрактала конкретной строки
         * @return
         */
        @Override
        protected Object doInBackground() {
            rowRgbs = new int[windowSize];
            double yCoord = getFractalY(rowIndex);

            for (int x = 0; x < windowSize; x++) {
                double xCoord = getFractalX(x);

                int iterationsCount = fractalGenerator.numIterations(xCoord, yCoord);
                int rgbColor = JImageDisplay.BLACK_COLOR;

                if (iterationsCount > -1) {
                    float hue = 0.7f + (float) iterationsCount / 200f;
                    rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                }

                rowRgbs[x] = rgbColor;
            }
            return null;
        }

        /**
         * Метод, вызывающийся при окончании вычислений
         */
        @Override
        protected void done() {
            imageDisplay.drawRow(rowIndex, rowRgbs);
            imageDisplay.repaint(0, 0, rowIndex, windowSize, 1);
            setUIEnabled(--rowsRemaining == 0);
        }
    }
}
