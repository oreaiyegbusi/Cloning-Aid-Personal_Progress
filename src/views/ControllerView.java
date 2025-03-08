package views;

import components.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerView extends JFrame {

    private final Controller controller;

    public ControllerView(Controller controller) {
        this.controller = controller;
        createWindow();
        addComponents();
        setVisible(true);
    }

    private void createWindow() {
        setTitle("Cloning Aid");
        setSize(new Dimension(800,600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addComponents() {
        //TODO
        createMenu();
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();

        // create a menu
        JMenu menu = new JMenu("Menu");
        JMenu menu2 = new JMenu("Menu2");

        // create menuitems
        JMenuItem menuItem1 = new JMenuItem("Menu Item1");
        JMenuItem menuItem2 = new JMenuItem("Menu Item2");
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
