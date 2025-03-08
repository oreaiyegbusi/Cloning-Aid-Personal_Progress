package components;

public class Empty extends Nucleotide {

    public Empty() {
        setBase('X');
        setBondStrength(0);
    }

    @Override
    public Nucleotide getComplement() {
        return new Empty();
    }

}
