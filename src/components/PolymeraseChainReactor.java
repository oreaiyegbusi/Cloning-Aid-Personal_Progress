package components;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

public class PolymeraseChainReactor implements Serializable {
    private DSDNASequence[] result;
    private final int INITIAL_CAPACITY = 8;
    private int size = 0;
    private final DSDNASequence goi;
    private final DSDNASequence donor;
    private final Primer forwardPrimer;
    private final Primer reversePrimer;

    @Serial
    private static final long serialVersionUID = 7L;
    public static final Logger PCRLOGGER = Logger.getLogger("PCR:" + serialVersionUID);

    public PolymeraseChainReactor(DSDNASequence goi, DSDNASequence donor) {
        this.goi = goi;
        this.donor = donor;
        // Create the Primers
        forwardPrimer = goi.createForwardPrimer();
        reversePrimer = goi.createReversePrimer();
    }

    private void put(DSDNASequence dsDNA) {
        if (size == result.length) resize(2 * result.length);    // double size of array if necessary
        result[size++] = dsDNA;                            // add item
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= size;
        result = Arrays.copyOf(result, capacity);
    }

    public void run(int iterations) {
        result = new DSDNASequence[INITIAL_CAPACITY];
        Queue<DSDNASequence> inputs = new LinkedList<>();
        put(donor);
        inputs.add(donor);
        int range = 1 + 2 * (int) (Math.pow(2, iterations) - 1.0);
        try {
            while (range > 0) {
                range--;
                // Denature the dsdna
                DSDNASequence dsDNA = inputs.remove().clone();
                dsDNA.denature();
                //Anneal the primers to the denatured donor
                dsDNA.annealPrimers(forwardPrimer, reversePrimer);
                DSDNASequence[] tmp = Polymerase.catalyze(dsDNA);
                for (DSDNASequence d : tmp) {
                    put(d);
                    inputs.add(d);
                }
            }

        } catch (CloningAidException e) {
            PCRLOGGER.warning(e.getMessage());
            System.exit(0);
        }
    }

    public DSDNASequence getGoi() {
        return goi;
    }

    public DSDNASequence getDonor() {
        return donor;
    }

    public Primer getForwardPrimer() {
        return forwardPrimer;
    }

    public Primer getReversePrimer() {
        return reversePrimer;
    }

    public DSDNASequence[] getLevel(int level) throws CloningAidException {
        int l = getLevels();
        if (level > l)
            throw new CloningAidException("Maximum level " +
                    "available is " + l + ". Asking " + level + "!");
        int index = (int) (1 + 2 * (Math.pow(2, level - 1) - 1));
        int range = (int) (Math.pow(2, level)) - 1;

        return Arrays.copyOfRange(result, index, index + range + 1);
    }

    public void commit(String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this);
            PCRLOGGER.info("PCR run has been saved to " + filename + ".");
        } catch (IOException e) {
            PCRLOGGER.warning(e.getMessage());
        }
    }

    public static PolymeraseChainReactor restore(String filename) {
        PolymeraseChainReactor pcr = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            // Deserializing an object from the file
            pcr = (PolymeraseChainReactor) in.readObject();
            // Using the deserialized object
            PCRLOGGER.info("Deserialized PCR object: " + pcr);
        } catch (IOException | ClassNotFoundException e) {
            PCRLOGGER.warning(e.getMessage());
        }
        return pcr;
    }

    public double getPotentialYield(double pcrVolume, double primerConcentration) {
        double length =  goi.getSense().getLength();
        return primerConcentration * pcrVolume * length * 660.0;
    }

    public double getRequiredPrimerConcentration(double pcrVolume, double desiredYield) {
        double length =  goi.getSense().getLength();
        return desiredYield / (pcrVolume * 660.0 * length);
    }

    public int getLevels() {
        return (int)(Math.log10(size)/Math.log10(2.0));
    }
}