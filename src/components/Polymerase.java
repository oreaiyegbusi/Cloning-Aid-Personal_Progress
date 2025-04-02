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

    private static DSDNASequence extendSense(SSDNASequence sense)
            throws CloningAidException {
        sense = crop(sense);
        sense.bindFrom(getPrimerLocation(sense));
        SSDNASequence antisense = createComplementOfBoundStrand(sense);
        DSDNASequence dsdna = new DSDNASequence();
        dsdna.setSense(sense);
        dsdna.setAntiSense(antisense);
        return dsdna;
    }

    private static DSDNASequence extendAntiSense(SSDNASequence antiSense)
            throws CloningAidException {
        antiSense = crop(antiSense);
        antiSense.bindFrom(getPrimerLocation(antiSense));
        SSDNASequence sense = createComplementOfBoundStrand(antiSense);
        DSDNASequence dsdna = new DSDNASequence();
        dsdna.setSense(sense);
        dsdna.setAntiSense(antiSense);
        return dsdna;
    }

    /**
     * The DSDNA has to be annealed with primers before this call is made.
     * @return The children after polymerase runs through
     * @throws CloningAidException If primers are not annealed
     */

    public static DSDNASequence[] catalyze(DSDNASequence sequence)
        throws CloningAidException {
        if (!sequence.isAnnealed()) {
            throw new CloningAidException("Primers are not annealed!");
        }
        DSDNASequence[] children = new DSDNASequence[2];
        // Extend the existing strands.
        children[0] = Polymerase.extendSense(sequence.getSense());
        children[1] = Polymerase.extendAntiSense(sequence.getAntiSense());
        sequence.setAnnealed(false);
        return children;
    }
    public static DSDNASequence extendSense2(SSDNASequence sense) {
        return null;
    }

    public static DSDNASequence extendAntiSense2(SSDNASequence antiSense) {
        return null;
    }
}
