package components;

import java.io.Serializable;

public class DSDNASequence implements Cloneable, Serializable {
    /**
     * The Donor and GOI are represented by this class
     * The Reverse primer attaches to the  Sense (Upper),
     * and the Forward Primer attaches to the antiSense (lower)
     * strand.
     */
    private SSDNASequence antiSense, sense;


    private boolean isAnnealed = false;


    DSDNASequence() {
        sense = antiSense = null;
    }
    /**
     * The forward primer attaches to the start codon of the
     * template DNA (the anti-sense strand).
     * The reverse primer attaches to the stop codon of the
     * complementary strand of DNA (the sense strand)
     */

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
    }

    public DSDNASequence(String rawDNA) throws CloningAidException {
        if (rawDNA.length() < 20 || rawDNA.length() > 20000)
            throw new IllegalArgumentException("Raw DNA sequence out " +
                    "of limits [ 20 < length < 20000");
        sense = new SSDNASequence(rawDNA);

        antiSense = sense.getComplement().getReversed();
        for (Nucleotide n : sense)
            n.setBound(true);
        for (Nucleotide n : antiSense)
            n.setBound(true);
    }

    public Primer createForwardPrimer() {
        return new Primer(sense);
    }

    public Primer createReversePrimer() {
        return new Primer(antiSense);
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
     * @param primer   The primer to grep for
     * @return index
     */
    private int getBindingIndex(SSDNASequence sequence, SSDNASequence primer)
            throws CloningAidException {
        int bindingIndex;
        SSDNASequence primerComplement = primer.getComplement().getReversed();
        if (sequence.contains(primerComplement)) {
            bindingIndex = sequence.getStartingIndex(primerComplement);
        } else {
            throw new CloningAidException("The Gene Of Interest is not present in the Donor");
        }
        return bindingIndex;
    }


    boolean isAnnealed() {
        return isAnnealed;
    }

    void setAnnealed(boolean annealed) {
        isAnnealed = annealed;
    }

    /**
     * The forward primer binds to the antisense strand of the DNA
     * template, while the reverse primer binds to the sense strand,
     * allowing them to flank the target region that needs to be
     * amplified.
     *
     * @param forwardPrimer drawn from the antiSense
     * @param reversePrimer drawn from the sense
     * @throws CloningAidException If dsDNA is not denatured
     */
    public void annealPrimers(Primer forwardPrimer,
                              Primer reversePrimer)
            throws CloningAidException {
        if (this.isNotDenatured())
            throw new CloningAidException("Not denatured - Anneal failed!");

        int sbi = getBindingIndex(sense, reversePrimer);
        int abi = getBindingIndex(antiSense, forwardPrimer);
        // Anneal primer
        for (int i = 0; i < reversePrimer.getLength(); i++) {
            Nucleotide n = sense.getNucleotide(sbi + i);
            Nucleotide pn = reversePrimer.getReversed().getNucleotide(i).getComplement();
            if (pn.equals(n)) {
                n.setBound(true);
            } else {
                throw new CloningAidException("Can't bind " + n
                        + " to " + pn + " on rev primer");
            }
        }
        for (int i = 0; i < forwardPrimer.getLength(); i++) {
            Nucleotide n = antiSense.getNucleotide(abi + i);
            Nucleotide pn = forwardPrimer.getReversed().getNucleotide(i).getComplement();
            if (pn.equals(n)) {
                n.setBound(true);
            } else {
                throw new CloningAidException("Can't bind " + n
                        + " to " + pn + " on fwd primer");
            }
        }
        isAnnealed = true;
    }



    @Override
    public String toString() {
        String str;
        StringBuilder builder1 = new StringBuilder();
        builder1.append("DSDNA\n");
        int[] index = computeBindingIndices(sense);
        str = String.format("\tUpper bound-range 5'[%d,%d]3'\n", index[0], index[1]);
        builder1.append(str);
        index = computeBindingIndices(antiSense);
        str = String.format("\tLower bound-range 5'[%d,%d]3'\n", index[0], index[1]);
        builder1.append(str);
        str = String.format("%s\n3'[", sense.toString());
        builder1.append(str);
        StringBuilder builder2 = new StringBuilder();
        builder2.append(antiSense.asBindingSensitiveString());
        builder2.reverse();
        builder1.append(builder2);
        builder1.append("]5'");
        return builder1.toString();
    }

    public SSDNASequence getSense() {
        return sense;
    }

    protected void setSense(SSDNASequence sequence) {
        this.sense = sequence;
    }

    protected void setAntiSense(SSDNASequence sequence) {
        this.antiSense = sequence;
    }

    public SSDNASequence getAntiSense() {
        return antiSense;
    }

    @Override
    public DSDNASequence clone() {
        try {
            DSDNASequence clone = (DSDNASequence) super.clone();
            clone.sense = sense.clone();
            clone.antiSense = antiSense.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
