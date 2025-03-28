package views;

import components.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Arrays;

public class AnalysisPanel extends JPanel {

    public AnalysisPanel(Controller controller) {
        addComponents(controller);
    }

    private void addComponents(Controller controller) {
        int horizontalGap = 30;
        Font font = new Font("Courier New", Font.BOLD, 24);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension(dim.width/3, dim.height/3));
        setBackground(Color.LIGHT_GRAY);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //Titled borders
        TitledBorder title;
        title = BorderFactory.createTitledBorder(BorderFactory
                .createBevelBorder(1), "Analysis");
        title.setTitleColor(Color.BLACK);
        setBorder(title);

        JButton saveButton = new JButton("Save PCR");
        JButton loadButton = new JButton("Load PCR");
        JButton displayButton = new JButton("Display");

        JLabel levelLabel = new JLabel(" Level:");
        JTextField levelField = new JTextField("0");
        levelField.setColumns(7);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.2;
        constraints.weighty = 0.5;
        add(saveButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        add(loadButton, constraints);

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
        levelLabel.setForeground(Color.BLACK);
        levelLabel.setFont(font);
        add(levelLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.ipadx =5;
        add(levelField, constraints);

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
        add(displayButton, constraints);
    }

    private String getPadding(int gap) {
        char[] arr = new char[gap];
        Arrays.fill(arr, ' ');
        return new String(arr);
    }
}
