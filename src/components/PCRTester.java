package components;

public class PCRTester {

    /*
    Strand
    ATCCGGATATAGTTCCTCCTTTCAGCAAAAAACCCCTCAAGACCCGTTTAGAGGCCCCAAGGGGTTATGCT
    AGTTATTGCTCAGCGGTGGCAGCAGCCAACTCAGCTTCCTTTCGGGCTTTGTTAGCAGCCGGATCTCAGTG
    GTGGTGGTGGTGGTGCTCGAGTGCGGCCGCAAGCTTGTCGACGGAGCTCGAATTCGGATCCACAGATATCC
    CATGGCCTTGTCGTCGTCGTCGGTACCCAGATCTGGGCTGTCCATGTGCTGGCGTTCGAATTTAGCAGCAG
    CGGTTTCTTTCATACCAGAACCGCGTGGCACCAGACCAGAAGAATGATGATGATGATGGTGCATATGTATA
    TCTCCTTCTTAAAGTTAAACAAAATTATTTCTAGAGGGGAATTGTTATCCGCTCACAATTCCCCTATAGTG
    AGTCGTATTAATTTCGCGGGATCGAGATCGATCTCGATCCTCTACGCCGGACGCATCGTGGCCGGCATCAC
    CGGCGCCACAGGTGCGGTTGCTGGCGCCTATATCGCCGACATCACCGATGGGGAAGATCGGGCTCGCCACT
    TCGGGCTCATGAGCGCTTGTTTCGGCGTGGGTATGGTGGCAGGCCCCGTGGCCGGGGGACTGTTGGGCGCC
    ATCTCCTTGCATGCACCATTCCTTGCGGCGGCGGTGCTCAACGGCCTCAACCTACTACTGGGCTGCTTCCT
    AATGCAGGAGTCGCATAAGGGAGAGCGTCGAGATCCCGGACACCATCGAATGGCGCAAAACCTTTCGCGGT
    ATGGCATGATAGCGCCCGGAAGAGAGTCAATTCAGGGTGGTGAATGTGAAACCAGTAACGTTATACGATGT
    CGCAGAGTATGCCGGTGTCTCTTATCAGACCGTTTCCCGCGTGGTGAACCAGGCCAGCCACGTTTCTGCGA
    AAACGCGGGAAAAAGTGGAAGCGGCGATGGCGGAGCTGAATTACATTCCCAACCGCGTGGCACAACAACTG
    GCGGGCAAACAGTCGTTGCTGATTGGCGTTGCCACCTCCAGTCTGGCCCTGCACGCGCCGTCGCAAATTGT
    CGCGGCGATTAAATCTCGCGCCGATCAACTGGGTGCCAGCGTGGTGGTGTCGATGGTAGAACGAAGCGGCGT
    CGAAGCCTGTAAAGCGGCGGTGCACAATCTTCTCGCGCAACGCGTCAGTGGGCTGATCATTAACTATCCGCT
    GGATGACCAGGATGCCATTGCTGTGGAAGCTGCCTGCACTAATGTTCCGGCGTTATTTCTTGATGTCTCTGA
    */
    public static void main(String[] args) {
        DSDNASequence goi = null, donor = null;
        SSDNASequence seqGoi = null, seqDonor = null;
          try {

            seqGoi = new SSDNASequence("ATCCGGATATAGTTCCTCCTTTCAG");
            seqDonor = new SSDNASequence("GATTACAATCCGGATATAGTTCCTCCTTTCAG" +
                                         "CAAAAAACCCCTCAAGACCCGTTTA");

            /*seqGoi = new SSDNASequence("ATCCGGATATAGTTCCTCCTTTCAGCAAAAAA" +
                    "CCCCTCAAGACCCGTTTAGAGGCCCCAAGGGGTTATGCT");
            seqDonor = new SSDNASequence("ATCCGGATATAGTTCCT" +
                    "GATTACACCTTTCAGCAAAAAACCCCTCAAGACCCGTTTAGAGGCCCCAAGGGGTTATGCT" +
                    "AGTTATTGCTCAGCGGTGGCAGCAGCCAACTCAGCTTCCTTTCGGGCTTTGTTAGCAGCCGGATCTCAGTG" +
                    "GTGGTGGTGGTGGTGCTCGAGTGCGGCCGCAAGCTTGTCGACGGAGCTCGAATTCGGATCCACAGATATCC" +
                    "CATGGCCTTGTCGTCGTCGTCGGTACCCAGATCTGGGCTGTCCATGTGCTGGCGTTCGAATTTAGCAGCAG" +
                    "CGGTTTCTTTCATACCAGAACCGCGTGGCACCAGACCAGAAGAATGATGATGATGATGGTGCATATGTATA" +
                    "TCTCCTTCTTAAAGTTAAACAAAATTATTTCTAGAGGGGAATTGTTATCCGCTCACAATTCCCCTATAGTG" +
                    "AGTCGTATTAATTTCGCGGGATCGAGATCGATCTCGATCCTCTACGCCGGACGCATCGTGGCCGGCATCAC" +
                    "CGGCGCCACAGGTGCGGTTGCTGGCGCCTATATCGCCGACATCACCGATGGGGAAGATCGGGCTCGCCACT" +
                    "TCGGGCTCATGAGCGCTTGTTTCGGCGTGGGTATGGTGGCAGGCCCCGTGGCCGGGGGACTGTTGGGCGCC" +
                    "ATCTCCTTGCATGCACCATTCCTTGCGGCGGCGGTGCTCAACGGCCTCAACCTACTACTGGGCTGCTTCCT" +
                    "AATGCAGGAGTCGCATAAGGGAGAGCGTCGAGATCCCGGACACCATCGAATGGCGCAAAACCTTTCGCGGT" +
                    "ATGGCATGATAGCGCCCGGAAGAGAGTCAATTCAGGGTGGTGAATGTGAAACCAGTAACGTTATACGATGT" +
                    "CGCAGAGTATGCCGGTGTCTCTTATCAGACCGTTTCCCGCGTGGTGAACCAGGCCAGCCACGTTTCTGCGA" +
                    "AAACGCGGGAAAAAGTGGAAGCGGCGATGGCGGAGCTGAATTACATTCCCAACCGCGTGGCACAACAACTG" +
                    "GCGGGCAAACAGTCGTTGCTGATTGGCGTTGCCACCTCCAGTCTGGCCCTGCACGCGCCGTCGCAAATTGT" +
                    "CGCGGCGATTAAATCTCGCGCCGATCAACTGGGTGCCAGCGTGGTGGTGTCGATGGTAGAACGAAGCGGCGT" +
                    "CGAAGCCTGTAAAGCGGCGGTGCACAATCTTCTCGCGCAACGCGTCAGTGGGCTGATCATTAACTATCCGCT" +
                    "GGATGACCAGGATGCCATTGCTGTGGAAGCTGCCTGCACTAATGTTCCGGCGTTATTTCTTGATGTCTCTGA");

    */

          } catch (CloningAidException e) {
              System.err.println(e.getMessage());
              System.exit(0);
        }
        goi = new DSDNASequence(seqGoi);
        donor = new DSDNASequence(seqDonor);

          // Create the Primers
        Primer fwdPrimer = goi.createForwardPrimer();
        Primer revPrimer = goi.createReversePrimer();

        // Create and Denature the GOI
        donor.denature();
        System.out.println("DENATURED DONOR:\n"+donor);

        //Anneal the primers to the denatured donor
        try {
            donor.annealPrimers(fwdPrimer, revPrimer);
        } catch (CloningAidException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

        System.out.println("GOI:\n"+goi);

        System.out.println("Fwd Primer:" + fwdPrimer);
        System.out.println("Rev Primer:" + revPrimer);

        // Run the polymerase chain reaction
        DSDNASequence[] cycle1 = null;
        try {
            cycle1 = donor.runPolymerase();
        } catch (CloningAidException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        System.out.println("Child[L0][0]\n"+cycle1[0]);
        System.out.println("Child[L0][1]\n"+cycle1[1]);

        // Denature the children
        cycle1[0].denature();
        cycle1[1].denature();

        DSDNASequence[] cycle1_top = null;
        DSDNASequence[] cycle1_bottom = null;

        try {
            // Anneal the children with primers
            cycle1[0].annealPrimers(fwdPrimer, revPrimer);
            cycle1[1].annealPrimers(fwdPrimer, revPrimer);
            // Run Polymerase chain reaction
            cycle1_top = cycle1[0].runPolymerase();
            cycle1_bottom = cycle1[1].runPolymerase();
        } catch (CloningAidException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Child[L1][0]\n"+cycle1_top[0]);
        System.out.println("Child[L1][1]\n"+cycle1_top[1]);
        System.out.println("Child[L1][2]\n"+cycle1_bottom[0]);
        System.out.println("Child[L1][3]\n"+cycle1_bottom[1]);
    }
}
