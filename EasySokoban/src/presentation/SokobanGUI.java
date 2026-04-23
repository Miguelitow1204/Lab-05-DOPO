package presentation;

import domain.Sokoban;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Represents the graphical interface of the Sokoban game.
 * Extends JFrame and handles the visual configuration,
 * menus, and user actions.
 * @author (Murillo-Rubiano)
 * @version (2.0)
 */
public class SokobanGUI extends JFrame {
    //The sokoban model 
    private Sokoban sokoban;

    //Menu components
    private JMenuBar menuBar;
    private JMenu menuGame;
    private JMenuItem myNew, myOpen, mySave, myExit, myColors;
    
    //Ciclo 3
    //Panel components
    private BoardPanel panelBoard;
    private JPanel panelInfo;
    private JPanel panelInstructions;

    //Ciclo 4
    //Colors for the board elements
    private Color boxColor = new Color(139, 90, 43); //Cafe
    private Color targetColor = new Color(255, 182, 193); //Rosado
    private Color boxOnTargetColor = new Color(255, 140, 0); //naranja

    //Label to show boxes  on target count
    private JLabel lblBoxes;

    /**
     * Interface builder.
     * Initializes graphical elements, menus, and their actions.
     */
    public SokobanGUI() {
        super("EasySokoban");
        sokoban = new Sokoban (9, 7);
        prepareElements();
        prepareElementsMenu();
        prepareActions();
        prepareActionsMenu();
    }
    
    /**
     * Configures the main elements of the window,
     * such as size, position, and overall layout.
     */
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

    /**
     * Initialize the main panel where the game board will be displayed.
     */
    private void prepareElementsBoard(){
        panelBoard = new BoardPanel(sokoban, boxColor, targetColor, boxOnTargetColor);
        panelBoard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(panelBoard, BorderLayout.CENTER);
    }

    /**
     * Initializes the side panel that displays game information.
     */
    private void prepareElementsInfo(){
        panelInfo = new JPanel();
        panelInfo.setPreferredSize(new Dimension(150, 0));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Game Info"));
        JLabel lblBoxes = new JLabel("Boxes in place: 0");
        panelInfo.add(lblBoxes);
        add(panelInfo, BorderLayout.EAST);
    }

    /**
     * Initializes the side panel that provides instructions on how to play the game.
     */
    private void prepareElementsInstructions(){
        panelInstructions = new JPanel();
        panelInstructions.setPreferredSize(new Dimension(150, 0));
        panelInstructions.setBorder(BorderFactory.createTitledBorder("How to play"));
        JLabel lblMove = new JLabel("<html>Move:<br>W A S D<br>or arrow keys</html>");
        panelInstructions.add(lblMove);
        add(panelInstructions, BorderLayout.WEST);
    }

    /**
     * Refreshes the game board.
     * This method is called to update the visual representation of the game board.
     */
    public void refresh(){
        if(lblBoxes != null){
            System.out.println("Actualizando label: " + sokoban.getBoxesOnTarget());
            lblBoxes.setText("Boxes in place: " + sokoban.getBoxesOnTarget());
        }
        panelBoard.repaint();

        if(sokoban.isFinished()){
            JOptionPane.showMessageDialog(this, "You Win! All boxes are in place.", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Configures the menu bar and its items, 
     * including "New Game", "Open Game", "Save Game", "Change Colors", and "Exit".
     */
    public void prepareElementsMenu(){
        menuBar = new JMenuBar();
        menuGame = new JMenu("Game");
        myNew = new JMenuItem("New Game");
        myOpen = new JMenuItem("Open Game");
        mySave = new JMenuItem("Save Game");
        myColors = new JMenuItem("Change Colors"); //Ciclo 4
        myExit = new JMenuItem("Exit");

        menuGame.add(myNew);
        menuGame.addSeparator();
        menuGame.add(myOpen);
        menuGame.add(mySave);
        menuGame.addSeparator();
        menuGame.add(myColors);
        menuGame.addSeparator();
        menuGame.add(myExit);

        menuBar.add(menuGame);
        setJMenuBar(menuBar);
    }

    /**
     * Configures the actions for the main window.
     * This method is called to set up event listeners for user interactions.
     */
    public void prepareActions() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

        addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                int key = e.getKeyCode();
                if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP){
                    sokoban.move(-1, 0);
                } else if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN){
                    sokoban.move(1, 0);
                } else if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
                    sokoban.move(0, -1);
                } else if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
                    sokoban.move(0, 1);
                }
                refresh();
            }
        });
        setFocusable(true);
        requestFocus();
    }

    /**
     * Configures the actions for the menu items, such as opening a file dialog for "Open Game" and "Save Game",
     * and showing a color chooser for "Change Colors".
     */
    public void prepareActionsMenu(){
        myExit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                exit();
            }
        });

        myNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sokoban = new Sokoban(9, 7);
                panelBoard = new BoardPanel(sokoban, boxColor, targetColor, boxOnTargetColor);
                refresh();
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
        myColors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newBoxColor = JColorChooser.showDialog(
                    SokobanGUI.this, "Select box color", boxColor);
                if (newBoxColor != null) {
                    boxColor = newBoxColor;
                }
                Color newTargetColor = JColorChooser.showDialog(
                    SokobanGUI.this, "Select target color", targetColor);
                if (newTargetColor != null) {
                    targetColor = newTargetColor;
                }
                Color newBoxOnTargetColor = JColorChooser.showDialog(
                    SokobanGUI.this, "Select box-on-target color", boxOnTargetColor);
                if (newBoxOnTargetColor != null) {
                    boxOnTargetColor = newBoxOnTargetColor;
                }
                panelBoard.updateColors(boxColor, targetColor, boxOnTargetColor);
                refresh();
            }
        });
    }

    /**
     * Exits the game after confirming with the user.
     */
    public void exit() {
        int respuesta = JOptionPane.showConfirmDialog(this, "Do you want to exit the game?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Main method to launch the Sokoban game GUI.
     * @param args
     */
    public static void main(String[] args) {
        SokobanGUI sokoban = new SokobanGUI();
        sokoban.setVisible(true);
    }
}