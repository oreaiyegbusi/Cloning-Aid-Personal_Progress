package components;

import views.ControllerView;
import views.InputPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;

public class Controller extends MouseAdapter implements ActionListener,
        ItemListener,
        WindowListener, ImageObserver {
    private ControllerView view;
    private DSDNASequence donor, goi;
    private boolean goiDefined = false, donorDefined = false;

    public Controller() {
        view = new ControllerView(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String caller = e.getActionCommand();
        CloningAidActionEvent cae = null;
        String sequence = null;
        Object param = null;

        if (e instanceof CloningAidActionEvent) {
            cae = (CloningAidActionEvent) e;
            param = cae.getParameter();
            if (param instanceof String) {
                sequence = (String) param;
            }
        }

        switch (caller) {
            case ControllerView.AC_MENU_TEST:
                System.out.println("Selected " + caller);
                testGraphics();
                break;

            case ControllerView.AC_MENU_OPEN:
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(view);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
                }
                break;

            case ControllerView.AC_DONOR_ENTRY:
                new InputPanel(this, view, "donor");
                break;

            case ControllerView.AC_DONOR_DEF:
                if (sequence != null && sequence.matches("[ACTG]+")) {
                    try {
                        donor = new DSDNASequence(sequence);
                        donorDefined = true;
                        showDonor();
                    } catch (CloningAidException | IllegalArgumentException ex) {
                        showErrorPanel(ex.getMessage());
                    }
                }
                System.out.println(sequence);
                break;

            case ControllerView.AC_GOI_ENTRY:
                new InputPanel(this, view, "goi");
                break;

            case ControllerView.AC_GOI_DEF:
                if (sequence != null && sequence.matches("[ACTG]+")) {
                    try {
                        goi = new DSDNASequence(sequence);
                        goiDefined = true;
                        showGOI();
                    } catch (CloningAidException | IllegalArgumentException ex) {
                        showErrorPanel(ex.getMessage());
                    }
                }
                System.out.println(sequence);
                break;

            case ControllerView.AC_RUN_PCR:
                if (donorDefined && goiDefined) {
                    PolymeraseChainReactor reactor = new PolymeraseChainReactor(donor, goi);
                    int cycles = 1;
                    if (param instanceof Integer) {
                        cycles = (Integer) param;
                    }
                    reactor.run(cycles);

                    //To display all products from all cycles.
                    for (int i = 0; i <= cycles; i++) {
                        try {
                            DSDNASequence[] cycleProducts = reactor.getLevel(i);
                            if (cycleProducts != null && cycleProducts.length > 0 && cycleProducts[0] != null) {
                               // for (int j = 0; j < cycleProducts.length; j++) {
                                    DSDNASequence product = cycleProducts[0];
                                   // if (product != null) {
                                        int yOffset = 100 + (i * 100);
                                        int length = product.getSense().getLength();

                                        view.renderText("Cycle " + i + "Product ", 20, yOffset, 14);
                                        view.renderDSDNA(product, 20, yOffset + 15, length * 5, 10);
                                    }
                        } catch (CloningAidException ex) {
                            showErrorPanel("Error retrieving level " + i + ": " + ex.getMessage());
                        }
                    }

//                    DSDNASequence amplified = reactor.getDonor();
//                    int length = amplified.getSense().getLength();
//                    view.renderText("PCR Product", 10, 160, 12);
//                    view.renderDSDNA(amplified, 10, 170, length * 5, 10);
                } else {
                    showErrorPanel("Define both donor and GOI before Running PCR.");
                }
                break;
        }
    }


    private void showDonor() {
        view.renderText("Donor", 10, 40, 12);
        int length = donor.getSense().getLength();
        view.renderDSDNA(donor, 10, 50, length * 5, 10);
    }

    private void showGOI() {
        view.renderText("Gene of Interest", 10, 100, 12);
        int lengthGOI = goi.getSense().getLength();
        view.renderDSDNA(goi, 10, 110, lengthGOI * 5, 10);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        //TODO
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getXOnScreen();
        int y = e.getYOnScreen();
        int ret = e.getModifiers();
        if ((ret & InputEvent.BUTTON3_MASK)
                == InputEvent.BUTTON3_MASK) {
            System.out.println("Button 3 pressed");
            JPopupMenu popupMenu = new JPopupMenu();
            popupMenu.setLocation(x, y);
            popupMenu.add(new JLabel("Hey!"));
            popupMenu.setVisible(true);
        }


        super.mouseReleased(e);
    }

    private void showSupportedFonts() {
        String availableFonts[] =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        for (String fontFamilyName : availableFonts) {
            System.out.println(fontFamilyName);
        }
    }

    public void testGraphics() {
        SSDNASequence seqGoi, seqDonor;
        try {
            seqGoi = new SSDNASequence("ATCCGGATATAGTTCCTGATTACACCTTTC");
            seqDonor = new SSDNASequence("GATTACAATCCGGATATAGTTCCTGATTACACCTTTCAGCAAAAAACCCCTCAAGACCCGTTTAGAGGCCCCAAGGGGTTATGCT");
            DSDNASequence goi = new DSDNASequence(seqGoi);
            DSDNASequence donor = new DSDNASequence(seqDonor);
            // Create the Reaction
            PolymeraseChainReactor pcr = new PolymeraseChainReactor(goi, donor);
            pcr.run(4);
            Primer prm = pcr.getForwardPrimer();
            DSDNASequence[] result = pcr.getLevel(4);
            int offset = 50;
            view.renderText("Primer", 100, 40, 12);
            view.renderPrimer(prm, 100, offset, 200, 25);
            offset = 100;
            for (DSDNASequence dsDNA : result) {
                view.renderDSDNA(dsDNA, 10, offset, 500, 20);
                offset += 100;
            }
        } catch (CloningAidException e) {
            System.err.println(e.getMessage());
            System.exit(0);
        }

    }

    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println("Window opened!");
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Window closing!");

    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.out.println("Window closed!");

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
    }


    private void showErrorPanel(String message) {
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.OK_CANCEL_OPTION);
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Controller::new);
    }

}
