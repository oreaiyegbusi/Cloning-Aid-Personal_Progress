package components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SSDNASequence  implements Cloneable, Iterable<Nucleotide>{
    /**
     * The SSDNASequence is listed in the forward direction 5'-3'
     */
    private List<Nucleotide> sequence = new ArrayList<>();

    public SSDNASequence(String nucleotides) throws CloningAidException {
        char[] narr = nucleotides.toCharArray();
        Nucleotide[] bases = new Nucleotide[narr.length];
        for (int i = 0; i < bases.length; i++) {
            switch (narr[i]) {
                case 'A':
                    bases[i] = new Adenine();
                    break;
                case 'G':
                    bases[i] = new Guanine();
                    break;
                case 'T':
                    bases[i] = new Thiamine();
                    break;
                case 'C':
                    bases[i] = new Cytosine();
                    break;
                default:
                    throw new CloningAidException("Illegal char in Nucleotide sequence!");
            }
        }
        this.sequence.addAll(List.of(bases));
    }

    public SSDNASequence(List<Nucleotide> nucleotides) {
        sequence.addAll(nucleotides);
    }

    public SSDNASequence(Nucleotide[] nucleotides) {
        this.sequence.addAll(List.of(nucleotides));
    }

    public SSDNASequence getComplement() {
        List<Nucleotide> list = new ArrayList<>();
        for (Nucleotide n : sequence) {
            Nucleotide n2 = n.getComplement();
            n2.setBound(n.isBound());
           list.add(n2);
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

    public List<Nucleotide> getSubSequence(int start, int end) {
        return sequence.subList(start, end);
    }

    public SSDNASequence getReversed() {
        List<Nucleotide> rev = new ArrayList<>(sequence);
        Collections.reverse(rev);
        return new SSDNASequence(rev);
    }

    public String asBindingInsensitiveString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Nucleotide n : this.sequence) {
            stringBuilder.append(n.getBase());
        }
        return stringBuilder.toString();
    }

    public String asBindingSensitiveString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Nucleotide n : this.sequence) {
            if (n.isBound())
                stringBuilder.append(n.getBase());
            else
                stringBuilder.append((char)(n.getBase()+32));
        }
        return stringBuilder.toString();
    }

    public boolean contains(SSDNASequence that) {
        String sequenceAsString = asBindingInsensitiveString();
        String thatSequenceAsString = that.asBindingInsensitiveString();
        return sequenceAsString.contains(thatSequenceAsString);
    }

    public int getStartingIndex(SSDNASequence that) throws CloningAidException {
                if (!contains(that))
                    throw new CloningAidException("Sequence does not exist in this strand!");
            return asBindingInsensitiveString().indexOf(that.asBindingInsensitiveString());
    }

    @Override
    public Iterator<Nucleotide> iterator() {
        return sequence.iterator();
    }

    public boolean isBound(){
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("5'[");
        for (Nucleotide n : sequence) {
                builder.append(n);
        }
        builder.append("]3'");
        return builder.toString();
    }

    @Override
    public SSDNASequence clone() {
        try {
            SSDNASequence clone = (SSDNASequence) super.clone();
            // Duplicate the sequence
            clone.sequence = new ArrayList<>();
            for (Nucleotide n : sequence){
                clone.sequence.add(n.clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
