package components;

public class Thiamine extends Nucleotide {

    public Thiamine() {
        setBase('T');
        setBondStrength(2);
    }

    @Override
    public Nucleotide getComplement() {
        return new Adenine();
    }

}
