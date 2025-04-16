package views;

import components.Controller;
import components.DSDNASequence;
import components.Primer;
import components.SSDNASequence;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ControllerView extends JFrame {

    private final Controller controller;
    private WorkspacePanel workspacePanel;
    private AnalysisPanel analysisPanel;
    private SetupPanel setupPanel;

    public static final String AC_OPEN = "open";
    public static final String AC_DONOR_DEF = "donor_input";
    public static final String AC_GOI_DEF = "goi_input";
    public static final String AC_GOI_ENTRY = "goi_entry";
    public static final String AC_DONOR_ENTRY = "donor_entry";
//    public static final String AC_RUN_PCR = "donor_entry";
    public static final String AC_RUN_PCR = "ac_run_pcr";

    public static final String AC_MENU_OPEN = "menu_open";
    public static final String AC_MENU_TEST = "menu_test";


    public ControllerView(Controller controller) {
        this.controller = controller;
        addWindowListener(controller);
        setMinimumSize(new Dimension(800, 600));
        createWindow();
        addComponents();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                centralizeScreen(e);
            }
        });
        setVisible(true);
        pack();
    }

    private void centralizeScreen(WindowEvent e) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int width = e.getWindow().getWidth();
        int height = e.getWindow().getHeight();
        setLocation((dim.width - width) / 2, (dim.height - height) / 2);
    }

    private void createWindow() {
        setTitle("Cloning Aid");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createWorkspacePanel(GridBagConstraints constraints,
                                      Container container) {
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        workspacePanel = new WorkspacePanel(controller);
        // Create a JViewport
        JScrollPane viewport = new JScrollPane(workspacePanel);
        viewport.setOpaque(false);
        viewport.setPreferredSize(new Dimension(800, 600));
        //viewport.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(viewport, constraints);
    }

    private void createSetupPanel(GridBagConstraints constraints,
                                  Container container) {
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        container.add(setupPanel, constraints);
    }

    private void createAnalysisPanel(GridBagConstraints constraints,
                                     Container container) {
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.gridx = 1;
        constraints.gridy = 1;
        container.add(analysisPanel, constraints);
    }

    private void addComponents() {
        createMenu();
        Container container = getContentPane();
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(new GridBagLayout());

        workspacePanel = new WorkspacePanel(controller);
        createWorkspacePanel(constraints, container);
        setupPanel = new SetupPanel(controller);
        createSetupPanel(constraints, container);
        analysisPanel = new AnalysisPanel(controller);
        createAnalysisPanel(constraints, container);
    }

    private void createMenu() {

        JMenuBar menuBar = new JMenuBar();

        // create a menu
        JMenu menu = new JMenu("File");
        JMenu menu2 = new JMenu("Recent Projects");

        // create menuitems
        JMenuItem menuItem1 = new JMenuItem("Open...");
        menuItem1.setActionCommand(AC_MENU_OPEN);

        JMenuItem menuItem2 = new JMenuItem("Close Project");
        JMenuItem menuItem3 = new JMenuItem("Test View");
        menuItem3.setActionCommand(AC_MENU_TEST);

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

    public void renderSSDNA(SSDNASequence sequence, int x, int y, int w, int h) {
        workspacePanel.renderSSDNA(sequence, x, y, w, h);
    }

    public void renderPrimer(Primer sequence, int x, int y, int w, int h) {
        workspacePanel.renderPrimer(sequence, x, y, w, h);
    }

    public void renderDSDNA(DSDNASequence sequence, int x, int y, int w, int h) {
        workspacePanel.renderDSDNA(sequence, x, y, w, h);
    }

    public void renderText(String text, int x, int y, int size) {
        workspacePanel.renderText(text, x, y, size);
    }

}
