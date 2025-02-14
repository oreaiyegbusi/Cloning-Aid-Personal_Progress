package components;

public class Tester {
    public static void main(String[] args) {
        Nucleotide n1 = new Adenine();
        System.out.println(n1.getBase());
        System.out.println(n1);
        System.out.println(n1.isBound());
        n1.setBound(true);
        System.out.println(n1.isBound());
        System.out.println(n1.getComplement());
        Nucleotide[] seq = new Nucleotide[] {
                new Adenine(), new Guanine(),
                new Cytosine(), new Thiamine()
        };
        SSDNASequence dna = new SSDNASequence(seq);
        dna.addNucleotide(new Cytosine()).addNucleotide(new Adenine());
        System.out.println(dna);
    }
}
