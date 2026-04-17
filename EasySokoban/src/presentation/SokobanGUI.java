package presentation;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SokobanGUI extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuGame;
    private JMenuItem myNew, myOpen, mySave, myExit;

    public SokobanGUI() {

        super("EasySokoban");
        prepareElements();
        prepareElementsMenu();
        prepareActions();
        prepareActionsMenu();
    }

    public void prepareElements() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 2;
        int height = screenSize.height / 2;
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    public void prepareElementsMenu(){
        menuBar = new JMenuBar();
        menuGame = new JMenu("Game");
        myNew = new JMenuItem("New Game");
        myOpen = new JMenuItem("Open Game");
        mySave = new JMenuItem("Save Game");
        myExit = new JMenuItem("Exit");

        menuGame.add(myNew);
        menuGame.addSeparator();
        menuGame.add(myOpen);
        menuGame.add(mySave);
        menuGame.addSeparator();
        menuGame.add(myExit);

        menuBar.add(menuGame);
        setJMenuBar(menuBar);
    }

    public void prepareActions() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
    }

    public void prepareActionsMenu(){
        myExit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                exit();
            }
        });
    }

    public void exit() {
        int respuesta = JOptionPane.showConfirmDialog(this, "Do you want to exit the game?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SokobanGUI sokoban = new SokobanGUI();
        sokoban.setVisible(true);
    }
}