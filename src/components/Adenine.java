package components;

public class Adenine extends Nucleotide {

    public Adenine() {
        setBase('A');
        setBondStrength(2);
    }

    @Override
    public Nucleotide getComplement() {
        return new Thymine();
    }

}
