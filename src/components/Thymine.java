package components;

public class Thymine extends Nucleotide {

    public Thymine() {
        setBase('T');
        setBondStrength(2);
    }

    @Override
    public Nucleotide getComplement() {
        return new Adenine();
    }

}
