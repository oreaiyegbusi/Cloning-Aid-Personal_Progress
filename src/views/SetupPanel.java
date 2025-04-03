package views;

import components.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Arrays;

public class SetupPanel extends JPanel {

    private final int MAX = 20, MIN = 0;

    public SetupPanel(Controller controller) {
        addComponents(controller);
    }

    private void addComponents(Controller controller) {
        int horizontalGap = 30;
        Font font = new Font("Courier New", Font.BOLD, 24);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension(dim.width/3, dim.height/3));
        setBackground(Color.GRAY);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //Titled borders
        TitledBorder title;
        //title = BorderFactory.createTitledBorder("Setup");
        title = BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Setup");
        title.setTitleColor(Color.WHITE);
        setBorder(title);

        JButton donorButton = new JButton("Define Donor");
        donorButton.setActionCommand(ControllerView.AC_DONOR_ENTRY);
        donorButton.addActionListener(controller);
        JButton goiButton = new JButton("Define GOI");
        goiButton.setActionCommand(ControllerView.AC_GOI_ENTRY);
        goiButton.addActionListener(controller);
        JButton runPCR = new JButton("Run");

        JLabel cyclesLabel = new JLabel(" Cycles:");
        SpinnerModel cyclesModel = new SpinnerNumberModel(3,
                MIN, MAX, 1);
        JSpinner cyclesField = new JSpinner(cyclesModel);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.2;
        constraints.weighty = 0.5;
        add(donorButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        add(goiButton, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel(getPadding(horizontalGap)), constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        cyclesLabel.setForeground(Color.WHITE);
        cyclesLabel.setFont(font);
        add(cyclesLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.ipadx =5;
        add(cyclesField, constraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel(getPadding(horizontalGap)), constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        add(runPCR, constraints);
    }

    private String getPadding(int gap) {
        char[] arr = new char[gap];
        Arrays.fill(arr, ' ');
        return new String(arr);
    }

}
