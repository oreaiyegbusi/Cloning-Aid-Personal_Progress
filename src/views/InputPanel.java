package views;

import components.CloningAidActionEvent;
import components.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class InputPanel extends JDialog {

    private JEditorPane editorPane;

    public InputPanel(Controller controller, ControllerView view,
                      String type) {
        createWindow(controller, type);
        setLocationRelativeTo(view);
        setVisible(true);
    }

    private void createWindow(Controller controller, String type) {
        JScrollPane editorScrollPane = getjScrollPane();
        add(editorScrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton enterButton = new JButton("Enter");
        enterButton.setActionCommand(ControllerView.AC_DONOR_DEF);
        buttonPanel.add(enterButton);
        enterButton.addActionListener((e -> {
            String input = editorPane.getText();
            if (controller == null)
                System.out.println(input);
            else {
                if (type.equalsIgnoreCase("donor"))
                    controller.actionPerformed(new CloningAidActionEvent(e.getSource(), e.getID(), ControllerView.AC_DONOR_DEF, input));
                else if (type.equalsIgnoreCase("goi"))
                    controller.actionPerformed(new CloningAidActionEvent(e.getSource(), e.getID(), ControllerView.AC_GOI_DEF, input));
            }
            dispose();
        }));
        buttonPanel.add(new JButton("Clear"));
        add(buttonPanel, BorderLayout.SOUTH);
        setSize(new Dimension(800,600));
    }

    private JScrollPane getjScrollPane() {
        editorPane = new JEditorPane();

        editorPane.setEditable(true);
        editorPane.setText("Copy and paste raw DNA sequence. Length should be between 21 and 20,000...");
        editorPane.selectAll();
        //Put the editor pane in a scroll pane.
        JScrollPane editorScrollPane = new JScrollPane(editorPane);
        editorScrollPane.setViewportBorder(BorderFactory.createBevelBorder(1));
        editorScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setPreferredSize(new Dimension(250, 145));
        editorScrollPane.setMinimumSize(new Dimension(10, 10));
        return editorScrollPane;
    }


}
