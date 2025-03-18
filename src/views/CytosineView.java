package views;

import components.Nucleotide;

import java.awt.*;

public class CytosineView extends NucleotideView {

    public CytosineView(Nucleotide nucleotide) {
        super(nucleotide);
    }

    protected Color getColor(){
        return Palette.cytosine.color();
    }

    @Override
    protected String getImageFileName() {
        return "c.png";
    }
}
