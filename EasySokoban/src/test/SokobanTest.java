package test;

import domain.Sokoban;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static domain.Sokoban.*;

/**
 * Unit tests for the Sokoban class to verify correct functionality.
 * @author (Murillo-Rubiano with support of GPT-4o)
 * @version (1.0)
 */
public class SokobanTest {

    private Sokoban sokoban;

    @Before
    public void setUp() {
        sokoban = new Sokoban(9, 7);
    }

    /**
     * Tests that the board is created with the correct dimensions.
     */
    @Test
    public void testBoardDimensions() {
        assertEquals(9, sokoban.getRows());
        assertEquals(7, sokoban.getCols());
    }

    /**
     * Tests that the border cells are all walls.
     */
    @Test
    public void testBorderWalls() {
        int[][] board = sokoban.getBoard();
        for (int i = 0; i < sokoban.getRows(); i++) {
            assertEquals(Sokoban.WALL, board[i][0]);
            assertEquals(Sokoban.WALL, board[i][sokoban.getCols() - 1]);
        }
        for (int j = 0; j < sokoban.getCols(); j++) {
            assertEquals(Sokoban.WALL, board[0][j]);
            assertEquals(Sokoban.WALL, board[sokoban.getRows() - 1][j]);
        }
    }

    /**
     * Tests that the player is placed in the center of the board.
     */
    @Test
    public void testPlayerInCenter() {
        int[][] board = sokoban.getBoard();
        assertEquals(Sokoban.PLAYER, board[9/2][7/2]);
    }

    /**
     * Tests that the number of boxes equals the number of targets (10% of area).
     */
    @Test
    public void testElementCount() {
        int[][] board = sokoban.getBoard();
        int boxes = 0;
        int targets = 0;
        int walls = 0;
        for (int i = 1; i < sokoban.getRows() - 1; i++) {
            for (int j = 1; j < sokoban.getCols() - 1; j++) {
                if (board[i][j] == Sokoban.BOX) boxes++;
                if (board[i][j] == Sokoban.TARGET) targets++;
                if (board[i][j] == Sokoban.WALL) walls++;
            }
        }
        int expected = (int)(9 * 7 * 0.10);
        assertEquals(expected, boxes);
        assertEquals(expected, targets);
        assertEquals(expected, walls);
    }

    /**
     * Tests that the player moves up to an empty cell correctly.
     */
    @Test
    public void testPlayerMovesUp() {
        sokoban.setCell(sokoban.getRows()/2, sokoban.getCols()/2, EMPTY);
        sokoban.setPlayerPosition(3, 3);
        sokoban.move(-1, 0);
        assertEquals(PLAYER, sokoban.getBoard()[2][3]);
        assertEquals(EMPTY, sokoban.getBoard()[3][3]);
    }

    /**
     * Tests that the player cannot move into a wall.
     */
    @Test
    public void testPlayerCannotMoveIntoWall() {
        sokoban.setCell(sokoban.getRows()/2, sokoban.getCols()/2, EMPTY);
        sokoban.setPlayerPosition(3, 3);
        sokoban.setCell(2, 3, WALL);
        sokoban.move(-1, 0);
        assertEquals(PLAYER, sokoban.getBoard()[3][3]);
    }

    /**
     * Tests that the player cannot push a box into a wall.
     */
    @Test
    public void testPlayerCannotPushBoxIntoWall() {
        sokoban.setCell(sokoban.getRows()/2, sokoban.getCols()/2, EMPTY);
        sokoban.setPlayerPosition(3, 3);
        sokoban.setCell(2, 3, BOX);
        sokoban.setCell(1, 3, WALL);
        sokoban.move(-1, 0);
        assertEquals(PLAYER, sokoban.getBoard()[3][3]);
        assertEquals(BOX, sokoban.getBoard()[2][3]);
    }

    /**
     * Tests that boxesOnTarget increases when a box is pushed onto a target.
     */
    @Test
    public void testBoxOnTargetCount() {
        sokoban.setCell(sokoban.getRows()/2, sokoban.getCols()/2, EMPTY);
        sokoban.setPlayerPosition(3, 3);
        sokoban.setCell(2, 3, BOX);
        sokoban.setCell(1, 3, TARGET);
        sokoban.move(-1, 0);
        sokoban.move(-1, 0);
        assertEquals(1, sokoban.getBoxesOnTarget());
        assertEquals(BOX_ON_TARGET, sokoban.getBoard()[1][3]);
    }

    /**
     * Tests that the game is not finished when there are boxes not on targets.
     */
    @Test
    public void testGameNotFinished() {
        sokoban.clearBoard();
        sokoban.setPlayerPosition(3, 3);
        sokoban.setCell(2, 3, BOX);
        assertFalse(sokoban.isFinished());
    }

    /**
     * Tests that the game is finished when all boxes are on their targets.
     */
    @Test
    public void testGameFinished() {
        sokoban.clearBoard();
        sokoban.setPlayerPosition(3, 3);
        sokoban.setCell(2, 3, BOX);
        sokoban.setCell(1, 3, TARGET);
        sokoban.move(-1, 0);
        sokoban.move(-1, 0);
        assertTrue(sokoban.isFinished());
    }

    /**
     * Tests that the board is resized correctly.
     */
    @Test
    public void testResize() {
        sokoban.resize(7, 5);
        assertEquals(7, sokoban.getRows());
        assertEquals(5, sokoban.getCols());
    }

    /**
     * Tests that after resizing the border walls are still correct.
     */
    @Test
    public void testResizeBorderWalls() {
        sokoban.resize(7, 5);
        int[][] board = sokoban.getBoard();
        for (int i = 0; i < sokoban.getRows(); i++) {
            assertEquals(WALL, board[i][0]);
            assertEquals(WALL, board[i][sokoban.getCols() - 1]);
        }
        for (int j = 0; j < sokoban.getCols(); j++) {
            assertEquals(WALL, board[0][j]);
            assertEquals(WALL, board[sokoban.getRows() - 1][j]);
        }
    }
}