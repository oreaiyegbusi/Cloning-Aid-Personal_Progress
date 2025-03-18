package views;

import components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ViewTester extends JFrame {

    private PolymeraseChainReactor pcr;
    private DSDNASequence goi, donor;
    private SSDNASequence seqGoi = null, seqDonor = null;
    DSDNASequence[] result;
    PrimerView prv;
    BufferedImage image;
    public ViewTester(){
        createWindow();
        try {
            addComponents();
        } catch (CloningAidException e) {
            throw new RuntimeException(e);
        }
        pack();
        setVisible(true);
    }

    private void createWindow() {
        setTitle("View Tester");
    }

    private void addComponents() throws CloningAidException {
        MyPanel panel = new MyPanel();
        panel.setPreferredSize(new Dimension(2000,2000));

        setLayout(new FlowLayout());
        // Create a JViewport
        JScrollPane viewport = new JScrollPane(panel);
        viewport.setOpaque(false);
        viewport.setPreferredSize(new Dimension(800,600));
        //viewport.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(viewport);
        addPCR();
        render();
    }

    private void addPCR() throws CloningAidException {
        try {
            seqGoi = new SSDNASequence("ATCCGGATATAGTTCCTGATTACACCTTTC");
            seqDonor = new SSDNASequence("GATTACAATCCGGATATAGTTCCTGATTACACCTTTCAGCAAAAAACCCCTCAAGACCCGTTTAGAGGCCCCAAGGGGTTATGCT");

//            seqGoi = new SSDNASequence("ATCCGGATATAGTTCCTCCTTTCAGCAAAAAA" +
//                    "CCCCTCAAGACCCGTTTAGAGGCCCCAAGGGGTTATGCT");
//            seqDonor = new SSDNASequence("ATCCGGATATAGTTCCT" +
//                    "GATTACACCTTTCAGCAAAAAACCCCTCAAGACCCGTTTAGAGGCCCCAAGGGGTTATGCT" +
//                    "AGTTATTGCTCAGCGGTGGCAGCAGCCAACTCAGCTTCCTTTCGGGCTTTGTTAGCAGCCGGATCTCAGTG" +
//                    "GTGGTGGTGGTGGTGCTCGAGTGCGGCCGCAAGCTTGTCGACGGAGCTCGAATTCGGATCCACAGATATCC" +
//                    "CATGGCCTTGTCGTCGTCGTCGGTACCCAGATCTGGGCTGTCCATGTGCTGGCGTTCGAATTTAGCAGCAG" +
//                    "CGGTTTCTTTCATACCAGAACCGCGTGGCACCAGACCAGAAGAATGATGATGATGATGGTGCATATGTATA" +
//                    "TCTCCTTCTTAAAGTTAAACAAAATTATTTCTAGAGGGGAATTGTTATCCGCTCACAATTCCCCTATAGTG" +
//                    "AGTCGTATTAATTTCGCGGGATCGAGATCGATCTCGATCCTCTACGCCGGACGCATCGTGGCCGGCATCAC" +
//                    "CGGCGCCACAGGTGCGGTTGCTGGCGCCTATATCGCCGACATCACCGATGGGGAAGATCGGGCTCGCCACT" +
//                    "TCGGGCTCATGAGCGCTTGTTTCGGCGTGGGTATGGTGGCAGGCCCCGTGGCCGGGGGACTGTTGGGCGCC" +
//                    "ATCTCCTTGCATGCACCATTCCTTGCGGCGGCGGTGCTCAACGGCCTCAACCTACTACTGGGCTGCTTCCT" +
//                    "AATGCAGGAGTCGCATAAGGGAGAGCGTCGAGATCCCGGACACCATCGAATGGCGCAAAACCTTTCGCGGT" +
//                    "ATGGCATGATAGCGCCCGGAAGAGAGTCAATTCAGGGTGGTGAATGTGAAACCAGTAACGTTATACGATGT" +
//                    "CGCAGAGTATGCCGGTGTCTCTTATCAGACCGTTTCCCGCGTGGTGAACCAGGCCAGCCACGTTTCTGCGA" +
//                    "AAACGCGGGAAAAAGTGGAAGCGGCGATGGCGGAGCTGAATTACATTCCCAACCGCGTGGCACAACAACTG" +
//                    "GCGGGCAAACAGTCGTTGCTGATTGGCGTTGCCACCTCCAGTCTGGCCCTGCACGCGCCGTCGCAAATTGT" +
//                    "CGCGGCGATTAAATCTCGCGCCGATCAACTGGGTGCCAGCGTGGTGGTGTCGATGGTAGAACGAAGCGGCGT" +
//                    "CGAAGCCTGTAAAGCGGCGGTGCACAATCTTCTCGCGCAACGCGTCAGTGGGCTGATCATTAACTATCCGCT" +
//                    "GGATGACCAGGATGCCATTGCTGTGGAAGCTGCCTGCACTAATGTTCCGGCGTTATTTCTTGATGTCTCTGA");
//

        } catch (CloningAidException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }
        goi = new DSDNASequence(seqGoi);
        donor = new DSDNASequence(seqDonor);

        // Create the Reaction
        pcr = new PolymeraseChainReactor(goi, donor);
        pcr.run(2);
        Primer prm = pcr.getForwardPrimer();
        prv = new PrimerView(prm);
        result = pcr.getLevel(2);

    }

    private void render() {
        image = new BufferedImage(1024, 1024, BufferedImage.TYPE_INT_ARGB);
        int offset = 0;
        try {
            Graphics2D g = image.createGraphics();
            offset = 100;
            for (DSDNASequence dsDNA : result) {
                DSDNAView view = new DSDNAView(dsDNA);
                view.draw(10, offset, 500, 25, null, g);
                offset += 100;
            }
            g.dispose();
        } catch (CloningAidException e) {
            throw new RuntimeException(e);
        }
    }

    private class MyPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.drawImage(image, 0, 0, null);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewTester::new);
    }
}
