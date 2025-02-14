package components;

public class Cytosine extends Nucleotide {

    public Cytosine() {
        setBase('C');
        setBondStrength(4);
    }

    @Override
    public Nucleotide getComplement() {
        return new Guanine();
    }

}
