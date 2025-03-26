package views;

import components.Controller;
import components.PolymeraseChainReactor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Workspace extends JScrollPane {

    private Image image;
    private final int WIDTH = 2000, HEIGHT = 2000;

    private final Controller controller;

    private MyPanel panel;

    private static class MyPanel extends JPanel{
        Image image;
        MyPanel() {
            setSize(new Dimension(WIDTH, HEIGHT));
        }

        void setImage(Image image) {
            this.image = image;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0,0,null);
        }
    }
    public Workspace(Controller controller) {
        super();
        this.controller = controller;
        createWorkspace();
    }

    private void createWorkspace() {
        panel = new MyPanel();
        // Create a JViewport
        setViewportView(panel);
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void render(Graphics2D gc) {
                gc.setClip(0, 0, getWidth(), getHeight());
                // draw into the back store
                gc.setColor(Color.GRAY);
                gc.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        render((Graphics2D) g);
    }
}
