package views;

import components.CloningAidException;
import components.Empty;
import components.Nucleotide;
import components.SSDNASequence;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class SSDNAView {

    private final SSDNASequence ssDNA;
    private Rectangle bounds;

    public SSDNAView(SSDNASequence ssDNA) {
        this.ssDNA = ssDNA;
    }

    protected Image getImage(int width, int height,
                                   boolean flipped) throws CloningAidException {
        // Create a full-sized image and then scale it down.
        Image image = getSequenceImage(flipped);
        // Now scale image to the correct size
        float xScale, yScale;
        int viewWidth = image.getWidth(null);
        int viewHeight = image.getHeight(null);
        xScale = (float) width/viewWidth;
        yScale = (float) height/viewHeight;
        image = scaleImage(image, xScale, yScale);
        return image;
    }

    public void draw(int x, int y, int width, int height,
                      ImageObserver observer, Graphics2D gc) throws CloningAidException {
        draw(x, y, width, height, null, gc, false);
    }

    public void drawFlipped(int x, int y, int width, int height,
                     ImageObserver observer, Graphics2D gc) throws CloningAidException {
        draw(x, y, width, height, null, gc, true);
    }

    private void draw(int x, int y, int width, int height,
                     ImageObserver observer, Graphics2D gc,
                      boolean flipped)
            throws CloningAidException {
        Image image = getImage(width, height, flipped);
        bounds = new Rectangle(x, y, width, height);
        gc.drawImage(image, x, y, observer);
    }

    private Image getSequenceImage(boolean flipped) throws CloningAidException {
        int offset = 0;
        // Create a full-sized image
        final int widthPerNucleotide = 32;
        int viewHeight = NucleotideView.getHeight(widthPerNucleotide);
        int viewWidth = widthPerNucleotide * ssDNA.getLength();
        BufferedImage image = new BufferedImage(viewWidth, viewHeight,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D gc = image.createGraphics();
        for (Nucleotide n : ssDNA) {
            NucleotideView view = NucleotideView.getInstance(n);
            Image src;
            src = view.getImage(widthPerNucleotide, flipped);
            if (!(n instanceof Empty)) {
                drawBackbone(gc, offset,
                        src.getWidth(null),
                        src.getHeight(null), n,
                        flipped);
                gc.drawImage(src, offset, 0, null);
            }
            offset += widthPerNucleotide;
        }
        gc.dispose();
        return image;
    }


    private BufferedImage scaleImage(Image image, float xScale, float yScale) {
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        int sw = (int) (w * xScale);
        int sh = (int) (h * yScale);
        image = image.getScaledInstance(sw, sh,
                BufferedImage.SCALE_SMOOTH);
        w = image.getWidth(null);
        h = image.getHeight(null);
        BufferedImage bImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gc = bImage.createGraphics();
        gc.drawImage(image, 0, 0, null);
        gc.dispose();
        return bImage;
    }

    private void drawBackbone(Graphics2D gc,
                                int xoff,
                                int width, int height,
                                Nucleotide n,
                                boolean flipped) {
        gc.setColor(getSubstrateColor(n));
        int quarter = (int) (height / 4.0);
        int threeQuarters = (int) (3.0 * height/4.0);
        if (flipped)
            gc.fillRect(xoff, threeQuarters, width,quarter);
        else
            gc.fillRect(xoff, 0, width, quarter);
    }

    protected Color getSubstrateColor(Nucleotide n) {
        if (n.isBound())
            return Palette.annealedBackbone.color();
        else
            return Palette.denaturedBackbone.color();
    }

    public Rectangle getBounds() {
        return new Rectangle(bounds);
    }
}
