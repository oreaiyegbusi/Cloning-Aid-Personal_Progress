package components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SSDNASequence {
    private final List<Nucleotide> sequence = new ArrayList<>();

    protected SSDNASequence() {

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

    public SSDNASequence getReversePrimer(){
        SSDNASequence revGOI = this.getReversed();
        return revGOI.getSubSequence(0,20).getComplement();
    }

    public SSDNASequence getForwardPrimer(){
        return this.getSubSequence(0,20);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Nucleotide n : sequence)
            builder.append(n.getBase());
        return builder.toString();
    }

    public DSDNASequence polymerize(SSDNASequence primer) {
        //TODO
        String parent = toString();
        String primerComplement =
                primer.getComplement().toString();

        return null;
    }
}
