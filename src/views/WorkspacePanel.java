package views;

import components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class WorkspacePanel extends JPanel {

    private static final int WIDTH = 2000, HEIGHT = 2000;

    private final Controller controller;
    private BufferedImage image;

    private final Color background = Color.BLACK;

    public WorkspacePanel(Controller controller) {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.controller = controller;
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(background);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(image, 0, 0, controller);
    }

    public void renderSSDNA(SSDNASequence sequence, int x, int y, int w, int h) {
        SSDNAView ssdnaView = new SSDNAView(sequence);
        Graphics2D gc = image.createGraphics();
        try {
            ssdnaView.draw(x, y, w, h, this, gc);
        } catch (CloningAidException e) {
            throw new RuntimeException(e);
        }
        gc.dispose();
        repaint();
    }

    public void renderPrimer(Primer sequence, int x, int y, int w, int h) {
        PrimerView primerView = new PrimerView(sequence);
        Graphics2D gc = image.createGraphics();
        try {
            primerView.draw(x, y, w, h, this, gc);
        } catch (CloningAidException e) {
            throw new RuntimeException(e);
        }
        gc.dispose();
        repaint();
    }

    public void renderText(String text, int x, int y, int size, Color color) {
        //Font font = new Font("Times New Roman", Font.PLAIN, size);
        Font font = new Font("Helvetica", Font.PLAIN, size);

        Graphics2D gc = image.createGraphics();
        gc.setColor(color);
        gc.setFont(font);
        gc.drawString(text, x, y);
        gc.dispose();
        repaint();
    }

    public void renderText(String text, int x, int y) {
        renderText(text, x, y, 10, Color.WHITE);
    }

    public void renderText(String text, int x, int y, int size) {
        renderText(text, x, y, size, Color.WHITE);
    }

    public void renderText(String text, int x, int y, Color color) {
        renderText(text, x, y, 10, color);
    }

    public void renderDSDNA(DSDNASequence sequence, int x, int y, int w, int h) {
        DSDNAView dsdnaView = new DSDNAView(sequence);
        Graphics2D gc = image.createGraphics();
        try {
            dsdnaView.draw(x, y, w, h, this, gc);
        } catch (CloningAidException e) {
            throw new RuntimeException(e);
        }
        gc.dispose();
        repaint();
    }


    public void clearScreen() {
        Graphics2D gc = (Graphics2D) image.getGraphics();
        gc.setColor(background);
        gc.fillRect(0,0, getWidth(), getHeight());
        gc.dispose();
    }

    public Graphics2D getGraphics2D() {
        return image.createGraphics();
    }

}
