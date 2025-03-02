package components;

import java.util.List;

public class Primer extends SSDNASequence{

    public Primer(String nucleotides) throws CloningAidException {
        super(nucleotides);
        if (nucleotides.length() < 18 || nucleotides.length() > 30)
            throw new IllegalArgumentException("Nucleotide sequence out " +
                    "of limits [ 18 <= length <= 30");
    }

    public Primer(List<Nucleotide> nucleotides) {
        super(nucleotides);
        if (nucleotides.size() < 18 || nucleotides.size() > 30)
            throw new IllegalArgumentException("Nucleotide sequence out " +
                    "of limits [ 18 <= length <= 30");
    }

    public Primer(Nucleotide[] nucleotides) {
        super(nucleotides);
        if (nucleotides.length < 18 || nucleotides.length > 30)
            throw new IllegalArgumentException("Nucleotide sequence out " +
                    "of limits [ 18 <= length <= 30");
    }
}
