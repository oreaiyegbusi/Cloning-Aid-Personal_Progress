package views;

import components.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public abstract class NucleotideView {

    protected int width;
    private Nucleotide nucleotide;

    protected NucleotideView(Nucleotide nucleotide) {
        this.nucleotide = nucleotide;
    }

    public BufferedImage getImage(int width, boolean flipped)
            throws CloningAidException {
        this.width = width;
        int height = (int) (3.0 * width / 2.0);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Graphics2D gc2d = (Graphics2D) image.getGraphics();
        gc2d.setRenderingHints(rh);
        // Draw profile
        gc2d.setColor(getColor());
        Rectangle rect = new Rectangle((int) (width / 4.0), (int) (height / 8.0),
                (int) (3.0 * width / 4.0),
                (int) (7.0 * height / 8.0));
        drawImageFromFile(gc2d, rect);
        gc2d.dispose();
        if (flipped) {
             image = rotateImage(image, 180.0);
        }
        return image;
    }

    private BufferedImage rotateImage(Image image, double angle) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        BufferedImage rotated = new BufferedImage(width,
                height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gc = (Graphics2D) rotated.getGraphics();
        gc.rotate(Math.toRadians(angle), width / 2.0, height / 2.0);
        gc.drawRenderedImage((BufferedImage) image, null);
        gc.dispose();
        return rotated;
    }

    public static int getHeight(int width) {
        return (int) (3.0 * width / 2.0);
    }

    public static NucleotideView getInstance(Nucleotide nucleotide) {
        return switch (nucleotide) {
            case Adenine adenine -> new AdenineView(nucleotide);
            case Guanine guanine -> new GuanineView(nucleotide);
            case Thymine thymine -> new ThymineView(nucleotide);
            case Cytosine cytosine -> new CytosineView(nucleotide);
            case null, default -> new EmptyView(nucleotide);
        };
    }

    protected void drawImageFromFile(Graphics2D gc, Rectangle rectangle) throws CloningAidException {
        String fileName = getImageFileName();
        if (fileName.isEmpty())
            return;
        URL url = getClass().getResource("/images/" + fileName);
        BufferedImage image;
        try {
            assert url != null;
            image = ImageIO.read(url);
            gc.drawImage(image, rectangle.x, rectangle.y,
                    rectangle.width, rectangle.height,
                    0, 0,
                    image.getWidth(), image.getHeight(),
                    null
            );
        } catch (IOException e) {
            throw new CloningAidException("Cannot find image " + fileName + ".");
        }
    }

    public void setNucleotide(Nucleotide nucleotide) {
        this.nucleotide = nucleotide;
    }

    public Nucleotide getNucleotide() {
        return nucleotide;
    }

    protected abstract Color getColor();

    protected abstract String getImageFileName();
}
