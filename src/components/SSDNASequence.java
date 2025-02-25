package components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SSDNASequence  implements Iterable<Nucleotide>{
    /**
     * The SSDNASequence is listed in the forward direction 5'-3'
     */
    private final List<Nucleotide> sequence = new ArrayList<>();

    public SSDNASequence(String seq) throws CloningAidException {
        char[] narr = seq.toCharArray();
        Nucleotide[] nucleotides = new Nucleotide[narr.length];
        for (int i = 0; i < nucleotides.length; i++) {
            switch (narr[i]) {
                case 'A':
                    nucleotides[i] = new Adenine();
                    break;
                case 'G':
                    nucleotides[i] = new Guanine();
                    break;
                case 'T':
                    nucleotides[i] = new Thiamine();
                    break;
                case 'C':
                    nucleotides[i] = new Cytosine();
                    break;
                default:
                    throw new CloningAidException("Illegal char in Nucleotide sequence!");
            }
        }
        this.sequence.addAll(List.of(nucleotides));
    }

    public SSDNASequence(List<Nucleotide> nucleotides) {
        sequence.addAll(nucleotides);
    }
    public SSDNASequence(Nucleotide[] sequence) {
        this.sequence.addAll(List.of(sequence));
    }

    public SSDNASequence addNucleotide(Nucleotide... nucleotides) {
        sequence.addAll(List.of(nucleotides));
        return this;
    }

    protected void addSequence(SSDNASequence that) {
        sequence.addAll(that.getSequence());
    }

    protected List<Nucleotide> getSequence() {
        return sequence;
    }

    public SSDNASequence getComplement() {
        List<Nucleotide> list = new ArrayList<>();
        for (Nucleotide n : sequence) {
           list.add(n.getComplement());
        }
        return new SSDNASequence(list);
    }

    public int getLength() {
        return sequence.size();
    }

    public int getMeltingTemperature() {
        int total = 0;
        for (Nucleotide n : sequence) {
                total += n.getBondStrength();
        }
        return total;
    }

    public SSDNASequence getSubSequence(int start, int end) {
        return new SSDNASequence(sequence.subList(start, end));
    }

    public SSDNASequence getReversed() {
        List<Nucleotide> rev = new ArrayList<>(sequence);
        Collections.reverse(rev);
        return new SSDNASequence(rev);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Nucleotide n : sequence) {
            if (n.isBound())
                builder.append(n.getBase());
            else
                builder.append((char)(n.getBase()+32));
        }
        return builder.toString();
    }

    public String getSequenceAsString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Nucleotide n : this.sequence) {
            stringBuilder.append(n.getBase());
        }
        return stringBuilder.toString();
    }

    public boolean contains(SSDNASequence that) {
        String sequenceAsString = getSequenceAsString();
        String thatSequenceAsString = that.getSequenceAsString();
        return sequenceAsString.contains(thatSequenceAsString);
    }

    public int getStartingIndex(SSDNASequence that) {
            try {
                if (!contains(that))
                    throw new CloningAidException("Sequence does not exist in this strand!");
            } catch (CloningAidException e) {
                System.err.println(e.getMessage());
            }
            return getSequenceAsString().indexOf(that.getSequenceAsString());
    }

    @Override
    public Iterator<Nucleotide> iterator() {
        return sequence.iterator();
    }

    public boolean isFullyBound(){
        for (Nucleotide n : sequence) {
            if (!n.isBound())
                return false;
        }
        return true;
    }

    public void bindFrom(int index) throws CloningAidException {
        for (int i = index; i >= 0 ; i--) {
            Nucleotide n = sequence.get(i);
            if (n.isBound())
                throw  new CloningAidException("Attempt to bind to bound nucleotide");
            n.setBound(true);
        }
    }

    public Nucleotide getNucleotide(int i) {
        return sequence.get(i);
    }
}
