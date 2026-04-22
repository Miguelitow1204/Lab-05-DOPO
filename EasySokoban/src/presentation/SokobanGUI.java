package presentation;

import java.io.File;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SokobanGUI extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuGame;
    private JMenuItem myNew, myOpen, mySave, myExit;
    
    //Ciclo 3
    private JPanel panelBoard;
    private JPanel panelInfo;
    private JPanel panelInstructions;

    //Ciclo 4
    private JMenuItem myColors;
    private Color boxColor = new Color(139, 90, 43); //Cafe
    private Color targetColor = new Color(255, 182, 193); //Rosado
    private Color boxOnTargetColor = new Color(255, 140, 0); //naranja

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

        //Ciclo 3
        setLayout(new BorderLayout());
        prepareElementsBoard();
        prepareElementsInfo();
        prepareElementsInstructions();
    }

    private void prepareElementsBoard(){
        panelBoard = new JPanel();
        panelBoard.setBackground(Color.WHITE);
        panelBoard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(panelBoard, BorderLayout.CENTER);
    }

    private void prepareElementsInfo(){
        panelInfo = new JPanel();
        panelInfo.setPreferredSize(new Dimension(150, 0));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Game Info"));
        JLabel lblBoxes = new JLabel("Boxes in place: 0");
        panelInfo.add(lblBoxes);
        add(panelInfo, BorderLayout.EAST);
    }

    private void prepareElementsInstructions(){
        panelInstructions = new JPanel();
        panelInstructions.setPreferredSize(new Dimension(150, 0));
        panelInstructions.setBorder(BorderFactory.createTitledBorder("How to play"));
        JLabel lblMove = new JLabel("<html>Move:<br>W A S D<br>or arrow keys</html>");
        panelInstructions.add(lblMove);
        add(panelInstructions, BorderLayout.WEST);
    }

    public void refresh(){
        panelBoard.repaint();
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

        myColors = new JMenuItem("Change Colors"); //Ciclo 4
        menuGame.add(myColors);
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

        myOpen.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser chooser = new JFileChooser();
                int result = chooser.showOpenDialog(SokobanGUI.this);
                if(result == JFileChooser.APPROVE_OPTION){
                    File file = chooser.getSelectedFile();
                    JOptionPane.showMessageDialog(SokobanGUI.this, "Opening file: " + file.getName());
                }
            }
        });

        mySave.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser chooser = new JFileChooser();
                int result = chooser.showSaveDialog(SokobanGUI.this);
                if(result == JFileChooser.APPROVE_OPTION){
                    File file = chooser.getSelectedFile();
                    JOptionPane.showMessageDialog(SokobanGUI.this, "Saving file: " + file.getName());
                }
            }
        });

        //Oyente ciclo 4
        myColors.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Color newColor = JColorChooser.showDialog(SokobanGUI.this, "Select box color", boxColor);
                if(newColor != null){
                    boxColor = newColor;
                    panelBoard.setBackground(newColor);
                    refresh();
                }
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