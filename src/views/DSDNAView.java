package views;

import components.CloningAidException;
import components.DSDNASequence;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class DSDNAView  {

    private final DSDNASequence dsDNA;
    private Rectangle bounds;

    public DSDNAView(DSDNASequence dsDNA) {
        this.dsDNA = dsDNA;
    }

    public void draw(int x, int y, int width, int height,
                     ImageObserver observer, Graphics2D gc)
            throws CloningAidException {
        Image image = getImage(width, height);
        bounds = new Rectangle(x, y, width, height);
        gc.drawImage(image, x, y, observer);
    }

    private Image getImage(int width, int height) throws CloningAidException {

        int gap = (int) (height/12.0);

        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D gc = image.createGraphics();

        Image img = getSenseImage(width, height/2);
        gc.drawImage(img,0,0 + gap, null);

        img = getAntiSenseImage(width, height/2);
        gc.drawImage(img,0,height/2-1 - gap, null);
        gc.dispose();
        return image;
    }

    private Image getSenseImage(int width, int height) throws CloningAidException {
        SSDNAView view = new SSDNAView(dsDNA.getSense());
        return view.getImage(width, height, false);
    }

    private Image getAntiSenseImage(int width, int height) throws CloningAidException {
        SSDNAView view = new SSDNAView(dsDNA.getAntiSense().getReversed());
        return view.getImage(width, height, true);
    }

    public Rectangle getBounds() {
        return new Rectangle(bounds);
    }
}
