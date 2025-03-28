package views;

import components.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class InputPanel extends JDialog {

    private JEditorPane editorPane;

    public InputPanel(Controller controller, ControllerView view) {
        createWindow(controller);
        setLocationRelativeTo(view);
        setVisible(true);
    }

    private void createWindow(Controller controller) {
        JScrollPane editorScrollPane = getjScrollPane();
        add(editorScrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton enterButton = new JButton("Enter");
        buttonPanel.add(enterButton);
        enterButton.addActionListener((e -> {
            String input = editorPane.getText();
            if (controller == null)
                System.out.println(input);
            else
                controller.actionPerformed(new ActionEvent(this, 0, input));
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
