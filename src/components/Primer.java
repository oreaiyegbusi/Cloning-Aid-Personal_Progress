package components;

import java.util.List;

public class Primer extends SSDNASequence {

    private final int lowerLimit = 18;
    private final int upperLimit = 20;
    private final double annealingTemperature;
    private final SSDNASequence goi;

    public Primer(SSDNASequence goi) {
        if (goi.getLength() < 20)
            throw new IllegalArgumentException("GOI is too short!");
        SSDNASequence subSequence = goi.getSubSequence(0,20);
        addSequence(subSequence);
        this.annealingTemperature = getAnnealingTemperature(goi);
        this.goi = goi;
    }

    public Primer getReversePrimer(){
        SSDNASequence revGOI = goi.getReversed();
        SSDNASequence subSequence = revGOI.getSubSequence(0,20);
        return new Primer(subSequence.getComplement());
    }

    public Primer getForwardPrimer() {
        return this;
    }

    private double getAnnealingTemperature(SSDNASequence goi) {
        return (0.3 * getMeltingTemperature() + 0.7 * goi.getMeltingTemperature());
    }

    public double getAnnealingTemperature() {
        return annealingTemperature;
    }
}
