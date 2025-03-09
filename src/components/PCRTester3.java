package components;

public class PCRTester3 {

    public static void main(String[] args) throws CloningAidException {
        // Create the Reaction
        PolymeraseChainReactor pcr = PolymeraseChainReactor.restore("Somesuch.dat");
        DSDNASequence donor = pcr.getDonor();
        DSDNASequence goi = pcr.getGoi();
        // Create and Denature the GOI
        System.out.println("DONOR:\n" + donor);
        System.out.println("GOI:\n" + goi);

        System.out.println("Fwd Primer:" + pcr.getForwardPrimer());
        System.out.println("Rev Primer:" + pcr.getReversePrimer());
        //Anneal the primers to the denatured donor
        int count = 0;
        int n = 0;
        DSDNASequence[] tmp = pcr.getLevel(n);
       for (DSDNASequence dsdna : tmp) {
           String str = String.format("L[%d][%d]:\n%s", n, count++, dsdna);
            System.out.println(str);
        }
        pcr.commit("Somesuch.dat");
    }
}
