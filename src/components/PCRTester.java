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
        DSDNASequence goi, donor;
        SSDNASequence seqGoi, seqDonor;
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
            throw new RuntimeException(e);
        }

        donor = new DSDNASequence(seqDonor);
        goi = new DSDNASequence(seqGoi);
        donor.denature();
        System.out.println("DENATURED DONOR:\n"+donor);
        DSDNASequence[] cycle1 = donor.polymerize(goi);
        System.out.println("Child[0]\n"+cycle1[0]);
        System.out.println("Child[1]\n"+cycle1[1]);
        System.out.println("Fwd Primer:" + goi.getForwardPrimer());
        System.out.println("Rev Primer:" + goi.getReversePrimer());
        System.out.println("GOI:\n"+goi);
    }
}
