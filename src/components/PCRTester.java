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
        System.out.println("DENATURED DONOR:\n" + donor);


        System.out.println("GOI:\n" + goi);

        System.out.println("Fwd Primer:" + fwdPrimer);
        System.out.println("Rev Primer:" + revPrimer);
            // Run the polymerase chain reaction
        DSDNASequence[] cycle1 = new DSDNASequence[2];
        int count = 0;
        donor.denature();
        try {
            donor.annealPrimers(fwdPrimer, revPrimer);
            DSDNASequence[] tmp = Polymerase.catalyze(donor);
            cycle1[count++] = tmp[0];
            cycle1[count] = tmp[1];
        } catch (CloningAidException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Child[L1][0]\n" + cycle1[0]);
        System.out.println("Child[L1][1]\n" + cycle1[1]);

        DSDNASequence[] cycle2 = new DSDNASequence[4];
        count = 0;
        for (int i = 0; i < cycle1.length; i++) {
            cycle1[i].denature();
            try {
                cycle1[i].annealPrimers(fwdPrimer, revPrimer);
                DSDNASequence[] tmp = Polymerase.catalyze(cycle1[i]);
                cycle2[count++] = tmp[0];
                cycle2[count++] = tmp[1];
            } catch (CloningAidException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Child[L2][0]\n" + cycle2[0]);
        System.out.println("Child[L2][1]\n" + cycle2[1]);
        System.out.println("Child[L2][2]\n" + cycle2[2]);
        System.out.println("Child[L2][3]\n" + cycle2[3]);

        DSDNASequence[] cycle3 = new DSDNASequence[8];
        count = 0;
        for (int i = 0; i < cycle2.length; i++) {
            cycle2[i].denature();
            try {
                cycle2[i].annealPrimers(fwdPrimer, revPrimer);
                DSDNASequence[] tmp = Polymerase.catalyze(cycle2[i]);
                cycle3[count++] = tmp[0];
                cycle3[count++] = tmp[1];
            } catch (CloningAidException e) {
                throw new RuntimeException(e);
            }
        }
        for (int i = 0; i < cycle3.length; i++) {
            System.out.println("Child[L3][" + i + "]\n" + cycle3[i]);
        }
    }
}
