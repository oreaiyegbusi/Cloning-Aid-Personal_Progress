package components;

public abstract class Nucleotide implements Cloneable{
    private boolean bound = false;
    private char base = 0x0000;
    private int bondStrength = 0;

    public abstract Nucleotide getComplement();

    public void setBound(boolean state) {
        bound = state;
    }

    protected void setBase(char base) {
        this.base = base;
    }

    public boolean isBound() {
        return bound;
    }

    public char getBase() {
        return base;
    }

    public void setBondStrength(int bondStrength) {
        this.bondStrength = bondStrength;
    }

    public  int getBondStrength() {
        return bondStrength;
    }

    @Override
    public String toString() {
        if (isBound())
        return String.valueOf(base);
        else
            return String.valueOf((char)(base+32));
    }

    @Override
    public Nucleotide clone() {
        try {
            return (Nucleotide) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
