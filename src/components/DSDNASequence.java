package components;

import java.util.ArrayList;
import java.util.List;

public class DSDNASequence {
    /**
     * The Donor and GOI are represented by this class
     */
    private SSDNASequence lower, upper, forwardPrimer, reversePrimer;
    private int upperBoundIndexStart = -1, upperBoundIndexEnd = -1;
    private int lowerBoundIndexStart = -1, lowerBoundIndexEnd = -1;

    public DSDNASequence(SSDNASequence rawDNA) {
        if (rawDNA.getLength() < 21 || rawDNA.getLength() > 20000)
            throw new IllegalArgumentException("Raw DNA sequence out " +
                            "of limits [ 20 < length < 20000");
        upper = rawDNA;
        lower = upper.getComplement().getReversed();
        forwardPrimer = upper.getSubSequence(0,20);
        reversePrimer = lower.getSubSequence(0,20);
        for (Nucleotide n : upper)
            n.setBound(true);
        for (Nucleotide n: lower)
            n.setBound(true);
        computeBindingIndices();
    }

    private DSDNASequence(SSDNASequence boundUpper, SSDNASequence boundLower) {
        if (boundUpper.getLength() < 21 || boundUpper.getLength() > 20000)
            throw new IllegalArgumentException("Upper DNA sequence out " +
                    "of limits [ 20 < length < 20000");
        if (boundLower.getLength() < 21 || boundLower.getLength() > 20000)
            throw new IllegalArgumentException("Lower DNA sequence out " +
                    "of limits [ 20 < length < 20000");

        this.upper = boundUpper;
        this.lower = boundLower;
        forwardPrimer = boundUpper.getSubSequence(0,20);
        reversePrimer = boundLower.getSubSequence(0,20);
        computeBindingIndices();

    }

    public SSDNASequence getReversePrimer(){
        return reversePrimer;
    }

    public SSDNASequence getForwardPrimer(){
        return forwardPrimer;
    }

    public void melt() {
        // unbinds all the nucleotides
        for (Nucleotide n: lower) {
            n.setBound(false);
        }
        for (Nucleotide n: upper) {
            n.setBound(false);
        }
        computeBindingIndices();
    }

    private void computeBindingIndices() {
        int[] indices = computeBindingIndices(upper);
        upperBoundIndexStart = indices[0];
        upperBoundIndexEnd = indices[1];
        indices = computeBindingIndices(lower);
        lowerBoundIndexStart = indices[0];
        lowerBoundIndexEnd = indices[1];
    }

    private int[] computeBindingIndices(SSDNASequence sequence) {
        int boundIndexStart = -1;
        int boundIndexEnd = -1;
        boolean inRange = false;
        for (int i = 0; i < sequence.getLength(); i++) {
            if (sequence.getNucleotide(i).isBound() && !inRange) {
                inRange = true;
                boundIndexStart = i;
            }
            if (sequence.getNucleotide(i).isBound() && i == sequence.getLength()-1) {
                boundIndexEnd = i;
                break;
            }
            if (!sequence.getNucleotide(i).isBound() && inRange) {
                boundIndexEnd = i;
                break;
            }
        }
        return new int[] { boundIndexStart, boundIndexEnd};
    }

    public boolean isFullyBound() {
        return upper.isFullyBound() && lower.isFullyBound();
    }
    /**
     * The sequence is the SSDNA to which the primer needs to attach.
     * The sequence is reversed, complemented, and then searched for the pattern
     * provided in the primer. Returns the starting index of the pattern contained
     * in the primer.
     * @param sequence
     * @param primer
     * @return index
     */
    private int getBindingIndex(SSDNASequence sequence, SSDNASequence primer) {
        int bindingIndex = -1;
        SSDNASequence modifiedSequence = sequence.getComplement().getReversed();
        if (modifiedSequence.contains(primer)) {
            int startIndex = modifiedSequence.getStartingIndex(primer);
            bindingIndex = modifiedSequence.getLength() - 1 - startIndex;
        }
        return bindingIndex;
    }

    /**
     * Starts binding nucleotides from the index location onwards.
     * @param sequence
     * @param index
     */
    private void bindSequence(SSDNASequence sequence, int index) {
        try {
            sequence.bindFrom(index);
        } catch (CloningAidException e) {
            System.err.println(e.getMessage());
        }
    }

    private SSDNASequence createProductFromBoundStrand(SSDNASequence parent) {
        List<Nucleotide> list = new ArrayList<>();
        for (var n : parent){
            if (n.isBound())
                list.add(n);
        }
        return new SSDNASequence(list).getComplement().getReversed();
    }

    public DSDNASequence[] polymerize(DSDNASequence goi) {
        SSDNASequence fwdPrimer = goi.getForwardPrimer();
        int upperBindingIndex = getBindingIndex(lower, fwdPrimer);
        bindSequence(lower, upperBindingIndex);
        SSDNASequence revPrimer = goi.getReversePrimer();
        int lowerBindingIndex = getBindingIndex(upper, revPrimer);
        bindSequence(upper, lowerBindingIndex);
        DSDNASequence[] result = new DSDNASequence[2];
        SSDNASequence parentLower = this.lower;
        this.lower = createProductFromBoundStrand(upper);
        result[0] = this;
        SSDNASequence newUpper = createProductFromBoundStrand(parentLower);
        DSDNASequence product = null;
        product = new DSDNASequence(newUpper, parentLower);
        result[1] = product;
        return result;
    }

    public SSDNASequence getUpper() {
        return upper;
    }

    public SSDNASequence getLower() {
        return lower;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append("DSDNA\n");
        builder.append("\tUpper bound-range[" + upperBoundIndexStart + "," + upperBoundIndexEnd + "]\n");
        builder.append("\tLower bound-range[" + lowerBoundIndexStart + "," + lowerBoundIndexEnd + "]\n");
        return builder.toString();
    }
}
