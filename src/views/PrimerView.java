package views;


import components.Nucleotide;
import components.Primer;

import java.awt.*;

public class PrimerView extends SSDNAView {

    public PrimerView(Primer ssDNA) {
        super(ssDNA);
    }

    @Override
    protected Color getSubstrateColor(Nucleotide n) {
      return Palette.primer.color();
    }

}
