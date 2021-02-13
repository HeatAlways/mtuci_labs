package ru.heatalways.mtuci_labs.lab4;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends JComponent {
    public static final int BLACK_COLOR = 0;

    private BufferedImage image;

    public JImageDisplay(int width, int height) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(width, height));
    }

    /**
     * Метод, очищающий окно (в чёрный цвет)
     */
    public void clearImage() {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                image.setRGB(x, y, BLACK_COLOR);
            }
        }
    }

    /**
     * Метод, рисующий пискель определённого цвета на экране
     * @param x координата X в окне
     * @param y координата Y в окне
     * @param rgbColor цвет пикселя
     */
    public void drawPixel(int x, int y, int rgbColor) {
        image.setRGB(x, y, rgbColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }
}
