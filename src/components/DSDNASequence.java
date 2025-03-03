package components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DSDNASequence implements Cloneable {
    /**
     * The Donor and GOI are represented by this class
     * The Reverse primer attaches to the  Sense (Upper),
     * and the Forward Primer attaches to the antiSense (lower)
     * strand.
     */
    private SSDNASequence antiSense, sense;

    /**
     *  The forward primer attaches to the start codon of the
     *  template DNA (the anti-sense strand).
     *  The reverse primer attaches to the stop codon of the
     *  complementary strand of DNA (the sense strand)
     */
    private int upperBoundIndexStart = -1, upperBoundIndexEnd = -1;
    private int lowerBoundIndexStart = -1, lowerBoundIndexEnd = -1;
    private Primer annealedFwdPrimer, annealedRevPrimer;

    public DSDNASequence(SSDNASequence rawDNA) {
        if (rawDNA.getLength() < 20 || rawDNA.getLength() > 20000)
            throw new IllegalArgumentException("Raw DNA sequence out " +
                    "of limits [ 20 < length < 20000");
        sense = rawDNA;
        antiSense = sense.getComplement().getReversed();
        for (Nucleotide n : sense)
            n.setBound(true);
        for (Nucleotide n : antiSense)
            n.setBound(true);
        computeBindingIndices();
    }

    public Primer createForwardPrimer(){
        return new Primer(sense.getSubSequence(0, 20));
    }

    public Primer createReversePrimer(){
        return new Primer(antiSense.getSubSequence(0, 20));
    }

    /**
     * Unbinds each nucleotide separating the DSDNA
     */
    public void denature() {
        for (Nucleotide n : antiSense) {
            n.setBound(false);
        }
        for (Nucleotide n : sense) {
            n.setBound(false);
        }
        computeBindingIndices();
    }

    private void computeBindingIndices() {
        int[] indices = computeBindingIndices(sense);
        upperBoundIndexStart = indices[0];
        upperBoundIndexEnd = indices[1];
        indices = computeBindingIndices(antiSense);
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
            if (sequence.getNucleotide(i).isBound() && i == sequence.getLength() - 1) {
                boundIndexEnd = i;
                break;
            }
            if (!sequence.getNucleotide(i).isBound() && inRange) {
                boundIndexEnd = i - 1; // Get the previous value
                break;
            }
        }
        return new int[]{boundIndexStart, boundIndexEnd};
    }

    public boolean isNotDenatured() {
        return sense.isBound() || antiSense.isBound();
    }

    /**
     * The sequence is the SSDNA to which the primer needs to attach.
     * The sequence is reversed, complemented, and then searched for the pattern
     * provided in the primer. Returns the starting index of the pattern contained
     * in the primer.
     *
     * @param sequence DNA sequence
     * @param primer The primer to grep for
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
     *
     * @param sequence DNA sequence
     * @param index location from where binding needs to start
     */
    private void bindSequence(SSDNASequence sequence, int index) {
        try {
            sequence.bindFrom(index);
        } catch (CloningAidException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     *  The forward primer binds to the antisense strand of the DNA
     *  template, while the reverse primer binds to the sense strand,
     *  allowing them to flank the target region that needs to be
     *  amplified.
     * @param fwdPrimer
     * @param revPrimer
     * @throws CloningAidException
     */
    public void annealPrimers(Primer fwdPrimer,
                              Primer revPrimer)
            throws CloningAidException {
        if (this.isNotDenatured())
            throw new CloningAidException("Not denatured - Anneal failed!");
        this.annealedFwdPrimer = fwdPrimer;
        this.annealedRevPrimer = revPrimer;
    }

    /**
     * The DSDNA has to be annealed with primers before this call is made.
     * @return The children after polymerase runs through
     * @throws CloningAidException
     */
    public DSDNASequence[] runPolymerase() throws CloningAidException {
       if (annealedFwdPrimer == null || annealedRevPrimer == null) {
           throw new CloningAidException("Primers are not annealed!");
       }
       // The clones are created denatured.
        DSDNASequence duplicate0 = this.clone();
        int bindingIndex = getBindingIndex(duplicate0.sense, annealedRevPrimer);
        bindSequence(duplicate0.sense, bindingIndex);
        duplicate0.antiSense = createComplementOfBoundStrand(duplicate0.sense);
        duplicate0.computeBindingIndices();

        DSDNASequence duplicate1 = this.clone();
        bindingIndex = getBindingIndex(duplicate1.antiSense, annealedFwdPrimer);
        bindSequence(duplicate1.antiSense, bindingIndex);
        duplicate1.sense = createComplementOfBoundStrand(duplicate1.antiSense);
        duplicate1.computeBindingIndices();

        DSDNASequence[] result = new DSDNASequence[2];
        result[0] = duplicate0;
        result[1] = duplicate1;
        return result;
    }

    private SSDNASequence createComplementOfBoundStrand(SSDNASequence sequence) {
        List<Nucleotide> list = new ArrayList<>();
        for (var n : sequence) {
            if (n.isBound()) {
                Nucleotide n2 = n.getComplement();
                n2.setBound(true);
                list.add(n2);
            }
        }
        Collections.reverse(list);
        return new SSDNASequence(list);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DSDNA\n");
        builder.append("\tUpper bound-range 5'[" + upperBoundIndexStart
                + "," + upperBoundIndexEnd + "]3'\n");
        builder.append("\tLower bound-range 5'[" + lowerBoundIndexStart
                + "," + lowerBoundIndexEnd + "]3'\n");
        String str = mergeStrands();
        builder.append(str);
        return builder.toString();
    }

    private String mergeStrands() {
        StringBuilder uBuilder = new StringBuilder();
        StringBuilder lBuilder = new StringBuilder();

        uBuilder.append(sense.asBindingSensitiveString());
        lBuilder.append(antiSense.asBindingSensitiveString()).reverse();
        if (isNotDenatured()) {
          addPadding(lBuilder, uBuilder);
        }
        uBuilder.insert(0, "5'[");
        uBuilder.append("]3'\n3'[");
        uBuilder.append(lBuilder);
        uBuilder.append("]5'");
        return uBuilder.toString();
    }

    private void addPadding(StringBuilder lowerBuilder, StringBuilder upperBuilder) {
        int ts = upperBoundIndexStart;
        int be = lowerBoundIndexEnd;
        int ue = upperBoundIndexEnd;
        int ls = lowerBoundIndexStart;
        int ul = sense.getLength();
        int bl = antiSense.getLength();
        int leftGap = Math.abs(ts - (bl - be - 1));
        int rightGap = Math.abs((ul - ue - 1) - ls);
        String leftPadding = "";
        String rightPadding = "";

        if (leftGap > 0) {
            leftPadding = getPadding(leftGap);
        }
        if (rightGap > 0) {
            rightPadding = getPadding(rightGap);
        }

        if (upperBuilder.length() < lowerBuilder.length()) {
            if (!leftPadding.isEmpty())
                upperBuilder.insert(0, leftPadding);
            if (!rightPadding.isEmpty())
                upperBuilder.append(rightPadding);
        } else if (lowerBuilder.length() < upperBuilder.length()) {
            if (!leftPadding.isEmpty())
                lowerBuilder.insert(0, leftPadding);
            if (!rightPadding.isEmpty())
                lowerBuilder.append(rightPadding);
        }
    }

    private String getPadding(int number) {
        if (number == 0)
            return "";
        StringBuilder gapBuilder = new StringBuilder();
        while (number > 0) {
            gapBuilder.append("_");
            number--;
        }
        return gapBuilder.toString();
    }


    @Override
    public DSDNASequence clone() {
        try {
            DSDNASequence clone = (DSDNASequence) super.clone();
            clone.antiSense = antiSense.clone();
            clone.sense = sense.clone();
            clone.annealedFwdPrimer = null;
            clone.annealedRevPrimer = null;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
