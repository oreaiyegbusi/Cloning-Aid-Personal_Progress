package components;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class DSDNAView extends Image {

    private DSDNASequence sequence;
    private BufferedImage image;
    private int WIDTH = 256, HEIGHT = 24;

    public DSDNAView(DSDNASequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public int getWidth(ImageObserver observer) {
        return 0;
    }

    @Override
    public int getHeight(ImageObserver observer) {
        return 0;
    }

    @Override
    public ImageProducer getSource() {
        return null;
    }

    @Override
    public Graphics getGraphics() {
        return null;
    }

    @Override
    public Object getProperty(String name, ImageObserver observer) {
        return null;
    }
}
