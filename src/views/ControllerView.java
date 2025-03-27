package views;

import components.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ControllerView extends JFrame {

    private final Controller controller;
    private JScrollPane workspacePanel;
    private AnalysisPanel analysisPanel;
    private SetupPanel setupPanel;


    public ControllerView(Controller controller) {
        this.controller = controller;
        addWindowListener(controller);
        createWindow();
        addComponents();
        //setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                int width = e.getWindow().getWidth();
                int height = e.getWindow().getHeight();
                setLocation((dim.width - width)/2, (dim.height - height)/2);
            }
        });
        setVisible(true);
        pack();
    }

    private void createWindow() {
        setTitle("Cloning Aid");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addComponents() {
        Container container = getContentPane();
        createMenu();
        setLayout(new GridBagLayout());
        workspacePanel = new JScrollPane();
        workspacePanel.setPreferredSize(new Dimension(800, 600));
        workspacePanel.setBackground(Color.DARK_GRAY);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        container.add(workspacePanel, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        setupPanel = new SetupPanel(controller);
        container.add(setupPanel, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.gridx = 1;
        constraints.gridy = 1;
        analysisPanel = new AnalysisPanel(controller);
        container.add(analysisPanel, constraints);
    }


    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();

        // create a menu
        JMenu menu = new JMenu("File");
        JMenu menu2 = new JMenu("Recent Projects");

        // create menuitems
        JMenuItem menuItem1 = new JMenuItem("Open...");
        JMenuItem menuItem2 = new JMenuItem("Close Project");
        JMenuItem menuItem3 = new JMenuItem("Menu Item3");
        menuItem1.addActionListener(controller);
        menuItem2.addActionListener(controller);
        menuItem3.addActionListener(controller);
        // add menu items to menu
        menu.add(menuItem1);
        menu.add(menuItem2);
        menu.add(menuItem3);

        JMenu subMenu = new JMenu("Sub Menu");
        JMenuItem subMenuItem1 = new JMenuItem("SubMenu Item1");
        JMenuItem subMenuItem2 = new JMenuItem("SubMenu Item2");
        subMenuItem1.addActionListener(controller);
        subMenuItem2.addActionListener(controller);

        subMenu.add(subMenuItem1);
        subMenu.add(subMenuItem2);
        menu.add(subMenu);

        // add menu to menu bar
        menuBar.add(menu);
        menuBar.add(menu2);
        // add menubar to frame
        setJMenuBar(menuBar);
    }

}
