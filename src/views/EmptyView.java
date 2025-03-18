package views;

import components.Nucleotide;

import java.awt.*;

public class EmptyView extends NucleotideView{

    protected EmptyView(Nucleotide nucleotide) {
        super(nucleotide);
        nucleotide.setBound(true);
    }

    @Override
    protected Color getColor() {
        return new Color(0x0000);
    }

    @Override
    protected String getImageFileName() {
        return "";
    }
}
