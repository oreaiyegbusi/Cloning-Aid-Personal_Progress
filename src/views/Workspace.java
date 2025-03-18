package views;

import components.Controller;
import components.PolymeraseChainReactor;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Workspace extends Canvas {

    private Image image;
    private final Controller controller;
    private BufferStrategy strategy;

    public Workspace(Controller controller) {
        this.controller = controller;
        setSize(1024, 1024);
        createWorkspace(1024, 1024);
    }

    private void createWorkspace(int width, int height) {
        setSize(width, height);
        setIgnoreRepaint(true);
        setVisible(true);
        // Always call this after the screen is visible on screen
        createBufferStrategy(2);
        strategy = getBufferStrategy();
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void render(PolymeraseChainReactor pcr, int start,
                       int end) {
        do {
            do {
                Graphics2D gc = (Graphics2D) strategy.getDrawGraphics();
                gc.setClip(0, 0, getWidth(), getHeight());
                // draw into the back store
                gc.setColor(Color.GRAY);
                gc.fillRect(0, 0, getWidth(), getHeight());
                gc.drawImage(image, 0, 0, controller);
                gc.dispose();
            } while (strategy.contentsRestored());
            strategy.show();
        } while (strategy.contentsLost());
        getToolkit().sync(); // Avoid flickering
    }

    @Override
    public void repaint() {
        //render();
    }
}
