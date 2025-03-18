package views;

import components.Nucleotide;

import java.awt.*;

public class GuanineView extends NucleotideView {

    public GuanineView(Nucleotide nucleotide) {
        super(nucleotide);
    }

    protected Color getColor(){
        return Palette.guanine.color();
    }

    @Override
    protected String getImageFileName() {
        return "g.png";
    }
}
