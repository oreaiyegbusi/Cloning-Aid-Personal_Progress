package views;

import components.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SetupPanel extends JPanel {

    public SetupPanel(Controller controller) {
        addComponents(controller);
    }

    private void addComponents(Controller controller) {
        setPreferredSize(new Dimension(400, 300));
        setBackground(Color.GRAY);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //Titled borders
        TitledBorder title;
        //title = BorderFactory.createTitledBorder("Setup");
        title = BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Setup");
        title.setTitleColor(Color.WHITE);
        setBorder(title);
        JButton donorButton = new JButton("Donor");
        JButton goiButton = new JButton("GOI");
        JButton runPCR = new JButton("Run");
        JLabel runLabel = new JLabel("Cycles:");
        JTextField cyclesField = new JTextField("3");

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        add(donorButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        add(goiButton, constraints);
    }


}
