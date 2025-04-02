package components;

import views.ControllerView;
import views.InputPanel;
import views.SetupPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;

public class Controller extends MouseAdapter implements ActionListener,
        ItemListener,
        WindowListener, ImageObserver {
    private ControllerView view;

    public Controller() {
        view = new ControllerView(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String caller = e.getActionCommand();

        switch (caller) {
            case ControllerView.AC_MENU_TEST:
                System.out.println("Selected " + caller);
                testGraphics();
                break;
            case ControllerView.AC_MENU_OPEN:
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & GIF Images", "jpg", "gif");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(view);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("You chose to open this file: " +
                            chooser.getSelectedFile().getName());
                }
                break;
            case SetupPanel.AC_DONOR_ENTRY:
                new InputPanel(this, view);
                break;
            case SetupPanel.AC_GOI_ENTRY:
                break;
            default:
        }
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


    public static void main(String[] args) {
        SwingUtilities.invokeLater(Controller::new);
    }

}
