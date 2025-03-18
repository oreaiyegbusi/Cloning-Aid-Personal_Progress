package components;

import views.ControllerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

public class Controller implements ActionListener, ImageObserver {

    private PolymeraseChainReactor pcr;

    public Controller() {
        ControllerView controllerView = new ControllerView(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO
        JMenuItem source = (JMenuItem)(e.getSource());
        System.out.println(source.getText());
    }

    @Override
    public boolean imageUpdate(Image img,
                               int infoflags,
                               int x, int y, int width,
                               int height) {
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Controller::new);
    }

}
