package components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Polymerase {

    private static SSDNASequence createComplementOfBoundStrand(SSDNASequence sequence) {
        List<Nucleotide> list = new ArrayList<>();
        for (var n : sequence) {
            if (n.isBound()) {
                Nucleotide n2 = n.getComplement();
                n2.setBound(true);
                list.add(n2);
            } else {
                list.add(new Empty());
            }
        }
        Collections.reverse(list);
        return new SSDNASequence(list);
    }

    private static SSDNASequence crop(SSDNASequence sequence) {
        if (!(sequence.getNucleotide(0) instanceof Empty))
            return sequence;
        boolean leading = true;
        List<Nucleotide> seq = new ArrayList<>();
        for (Nucleotide n : sequence) {
            // Skip all leading empties
            if (!(n instanceof Empty))
                leading = false;
            if (!leading)
                seq.add(n);
        }
        return new SSDNASequence(seq);
    }

    private static int getPrimerLocation(SSDNASequence sequence) {
        for (int i = sequence.getLength() - 1; i >= 0; i--) {
            if (sequence.getNucleotide(i).isBound()) {
                // Found the primer. Now skip over primer now
                for (int j = i-1; j >= 0; j--) {
                    if (!sequence.getNucleotide(j).isBound()) {
                        return j;
                    }
                }
            }
        }
        return -1;
    }

    public static void extendSense(DSDNASequence dsDNA)
            throws CloningAidException {
        if (dsDNA == null)
            throw new CloningAidException("Could not extend using Sense strand in DNA");
        SSDNASequence seq1 = dsDNA.getSense();
        seq1 = crop(seq1);
        dsDNA.setSense(seq1);
        seq1.bindFrom(getPrimerLocation(seq1));

        SSDNASequence seq2 = createComplementOfBoundStrand(seq1);
        dsDNA.setAntiSense(seq2);
    }

    public static void extendAntiSense(DSDNASequence dsDNA)
            throws CloningAidException {
        if (dsDNA == null)
            throw new CloningAidException("Could not extend using AntiSense strand in DNA");
        SSDNASequence seq1 = dsDNA.getAntiSense();
        seq1 = crop(seq1);
        dsDNA.setAntiSense(seq1);
        seq1.bindFrom(getPrimerLocation(seq1));

        SSDNASequence seq2 = createComplementOfBoundStrand(seq1);
        dsDNA.setSense(seq2);
    }
}
