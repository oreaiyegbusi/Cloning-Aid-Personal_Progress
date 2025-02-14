package components;

public class Guanine extends Nucleotide {

    public Guanine() {
        setBase('G');
        setBondStrength(4);
    }

    @Override
    public Nucleotide getComplement() {
        return new Cytosine();
    }

}
