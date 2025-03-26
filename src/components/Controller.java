package components;

import views.ControllerView;

import javax.swing.*;
import java.awt.event.*;

public class Controller extends MouseAdapter implements ActionListener,
        ItemListener,
        WindowListener {
    private ControllerView view;

    public Controller() {
        view = new ControllerView(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem item = (JMenuItem) e.getSource();
        System.out.println("Selected " + item.getText());
    }
    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getXOnScreen();
        int y = e.getYOnScreen();
        int ret = e.getModifiers();
        if ( (ret & InputEvent.BUTTON3_MASK)
                == InputEvent.BUTTON3_MASK) {
            System.out.println("Button 3 pressed");
            JPopupMenu popupMenu = new JPopupMenu();
            popupMenu.setLocation(x, y);
            popupMenu.add(new JLabel("Hey!"));
            popupMenu.setVisible(true);
        }


        super.mouseReleased(e);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Controller::new);
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
}
