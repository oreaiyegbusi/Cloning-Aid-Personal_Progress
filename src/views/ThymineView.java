package views;

import components.Nucleotide;

import java.awt.*;

public class ThymineView extends NucleotideView {

    public ThymineView(Nucleotide n) {
        super(n);
    }

    protected Color getColor(){
        return Palette.thymine.color();
    }

    @Override
    protected String getImageFileName() {
        return "t.png";
    }
}
