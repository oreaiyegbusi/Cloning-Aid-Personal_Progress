package views;

import components.Nucleotide;

import java.awt.*;

public class AdenineView extends NucleotideView {

    public AdenineView(Nucleotide nucleotide) {
        super(nucleotide);
    }

    protected Color getColor(){
        return Palette.adenine.color();
    }

    @Override
    protected String getImageFileName() {
        return "a.png";
    }
}
