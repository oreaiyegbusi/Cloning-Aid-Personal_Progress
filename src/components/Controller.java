package components;

import views.ControllerView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

    public Controller() {
        ControllerView controllerView = new ControllerView(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO
        JMenuItem source = (JMenuItem)(e.getSource());
        System.out.println(source.getText());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Controller::new);
    }
}
