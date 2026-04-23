package presentation;

import domain.Sokoban;
import javax.swing.*;
import java.awt.*;

/**
 * A custom JPanel that drwas the Sokoban board.
 * Each cell is drawn as a colored rectangle based on its type.
 * @author (Murillo-Rubiano)
 * @version (1.0)
 */
public class BoardPanel extends JPanel{
    private Sokoban sokoban;
    private Color boxColor;
    private Color targetColor;
    private Color boxOnTargetColor;
    
    /**
     * Creates a new BoardPanel with the given Sokoban model and colors
     * @param sokoban 
     * @param boxColor
     * @param targetColor
     * @param boxOnTargetColor
     */
    public BoardPanel(Sokoban sokoban, Color boxColor, Color targetColor, Color boxOnTargetColor){
        this.sokoban = sokoban;
        this.boxColor = boxColor;
        this.targetColor = targetColor;
        this.boxOnTargetColor = boxOnTargetColor;
        setBackground(Color.LIGHT_GRAY);
    }

    /**
     * Paints the board on the panel, drawing each cell with its color.
     * @param g the Graphics object used for drawing the board.
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int[][] board = sokoban.getBoard();
        int rows = sokoban.getRows();
        int cols = sokoban.getCols();

        int cellWidth = getWidth() / cols;
        int cellHeight = getHeight() / rows;

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                int x = j * cellWidth;
                int y = i * cellHeight;

                if(board[i][j] == Sokoban.WALL){
                    g.setColor(Color.DARK_GRAY);
                } else if(board[i][j] == Sokoban.PLAYER){
                    g.setColor(Color.BLUE);
                } else if(board[i][j] == Sokoban.BOX){
                    g.setColor(boxColor);
                } else if(board[i][j] == Sokoban.TARGET){
                    g.setColor(targetColor);
                } else if(board[i][j] == Sokoban.BOX_ON_TARGET){
                    g.setColor(boxOnTargetColor);
                } else{
                    g.setColor(Color.WHITE);
                }

                g.fillRect(x, y, cellWidth, cellHeight);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, cellWidth, cellHeight);
            }    
        }
    }

    /**
     * Updates the colors used to draw the board.
     * @param boxColor
     * @param targetColor
     * @param boxOnTargetColor
     */
    public void updateColors(Color boxColor, Color targetColor, Color boxOnTargetColor) {
        this.boxColor = boxColor;
        this.targetColor = targetColor;
        this.boxOnTargetColor = boxOnTargetColor;
        repaint();
    }
}