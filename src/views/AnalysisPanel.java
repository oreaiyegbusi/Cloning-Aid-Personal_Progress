package views;

import components.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class AnalysisPanel extends JPanel {

    public AnalysisPanel(Controller controller) {
        addComponents(controller);
    }

    private void addComponents(Controller controller) {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(400, 300));
        TitledBorder title = BorderFactory.createTitledBorder(BorderFactory
                        .createBevelBorder(1),
                "Analysis");
        title.setTitleColor(Color.BLACK);
        setBorder(title);
    }
}
