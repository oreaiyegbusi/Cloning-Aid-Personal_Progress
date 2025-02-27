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
        if (rawDNA.getLength() < 20 || rawDNA.getLength() > 20000)
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
        if (boundUpper.getLength() < 20 || boundUpper.getLength() > 20000)
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

    /**
     * Unbinds each nucleotide separating the DSDNA
     */
    public void denature() {
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
                boundIndexEnd = i-1;
                break;
            }
        }
        return new int[] { boundIndexStart, boundIndexEnd};
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
        SSDNASequence primerComplement = primer.getComplement().getReversed();
        if (sequence.contains(primerComplement)) {
            bindingIndex = sequence.getStartingIndex(primerComplement);
        }
        return bindingIndex + primer.getLength() - 1;
    }

    /**
     * Starts binding nucleotides from the index location onwards.
     * @param sequence
     * @param index
     */
    private void bindSequence(SSDNASequence sequence, int index) {
        try {
            // bindFrom(int) runs in the direction of the primer
            // Starting from the index, bind all the way down to zero, the beginning
            sequence.bindFrom(index);
        } catch (CloningAidException e) {
            System.err.println(e.getMessage());
        }
    }

    private SSDNASequence createComplementFromBoundStrand(SSDNASequence parent) {
        List<Nucleotide> list = new ArrayList<>();
        for (var n : parent){
            Nucleotide n2 = n.getComplement();
            if (n.isBound()) {
                n2.setBound(true);
                list.add(n2);
            }
        }
        return new SSDNASequence(list).getReversed();
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
        this.lower = createComplementFromBoundStrand(upper);
        result[0] = this;
        computeBindingIndices();
        SSDNASequence newUpper = createComplementFromBoundStrand(parentLower);
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
        StringBuilder builder = new StringBuilder();
        builder.append("\tUpper bound-range 5'[" + upperBoundIndexStart
                + "," + upperBoundIndexEnd + "]3'\n");
        builder.append("\tLower bound-range 5'[" + lowerBoundIndexStart
                + "," + lowerBoundIndexEnd + "]3'\n");
        String str =  mergeStrands();
        builder.append(str);
        return builder.toString();
    }

    private String mergeStrands() {
        int length = Math.max(upper.getLength(), lower.getLength());
        char[] arrayUpper = new char[length];
        char[] arrayLower = new char[length];
        int gap = upperBoundIndexStart - (lower.getLength() - lowerBoundIndexEnd);
        SSDNASequence reversedLower = lower.getReversed();
        for (int top = 0, bottom = 0, k = 0; k < length; k++) {
            if (gap > 0 && top < gap || bottom >= lower.getLength()) {
                Nucleotide n = upper.getNucleotide(top++);
                char base = n.getBase();
                if (!n.isBound())
                    base = (char)(base+32);
                arrayUpper[k] = base;
                arrayLower[k] = (char)(95);
            } else if (gap < 0 && bottom < -(gap+1) || top >= upper.getLength()) {
                Nucleotide n = reversedLower.getNucleotide(bottom++);
                char base = n.getBase();
                if (!n.isBound())
                    base = (char)(base+32);
                arrayUpper[k] = (char)(95);
                arrayLower[k] = base;
            }
            else {
                Nucleotide n1 = upper.getNucleotide(top++);
                Nucleotide n2 = reversedLower.getNucleotide(bottom++);
                char base1 = n1.getBase();
                char base2 = n2.getBase();
                if (!n1.isBound() && !n2.isBound()) {
                    base1 = (char) (base1 + 32);
                    base2 = (char) (base2 + 32);
                }
                arrayUpper[k] = base1;
                arrayLower[k] = base2;
            }
        }
        return "5'["+String.valueOf(arrayUpper) +
                "]3'\n" + "3'[" +
                String.valueOf(arrayLower) + "]5'";
    }
}
