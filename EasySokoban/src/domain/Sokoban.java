package domain;

import java.util.Random;


/**
 * Represents the core logic of the Sokoban game.
 * Handles the game board, player position, box movements, and game state.
 * @author (Murillo-Rubiano)
 * @version (2.5)
 */
public class Sokoban{
    private int cellUnderPlayer;
    //Tipos de casilla - constantes
    public static final int EMPTY = 0;
    public static final int WALL = 1;
    public static final int BOX = 2;
    public static final int TARGET = 3;
    public static final int BOX_ON_TARGET = 4;
    public static final int PLAYER = 5;

    //Atributes
    private int[][] board;
    private int rows;
    private int cols;
    private int pRow;
    private int pCol;
    private int boxesOnTarget;
    private int [][] initialBoard;
    private int initialPlayerRow;
    private int initialPlayerCol;

    /**
     * Constructor for the Sokoban class.
     * Initializes the game board with the specified number of rows and columns,
     */
    public Sokoban(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.boxesOnTarget = 0;
        this.cellUnderPlayer = EMPTY;
        board = new int[rows][cols];
        generateBoard();
    }

    /**
     * Returns the current state of the game board.
     * @return A 2D array representing the game board.
     */
    public int[][] getBoard(){
        return board;
    }

    /**
     * Returns the number of rows in the game board.
     * @return The number of rows.
     */
    public int getRows(){
        return rows;
    }

    /**
     * Returns the number of columns in the game board.
     * @return The number of columns.
     */
    public int getCols(){
        return cols;
    }

    /**
     * Returns the number of boxes currently on target positions.
     * @return The number of boxes on target.
     */
    //Requisito 4. Informar el número de cajas en destino
    public int getBoxesOnTarget(){
        return boxesOnTarget;
    }

    /**
     * Generates the board with random walls, targets and boxes.
     * The number of each element is 10% of the total area
     * The border of the board is always a wall
     * The player is placed in the center of the board.
     */
    //Requisito 1. Crear un tablero dado su tamaño  [h,w]
    protected void generateBoard(){
        int area = rows * cols;
        int numObjects = (int)(area * 0.1);

        //Set border walls
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(i == 0 || i == rows - 1 || j == 0 || j == cols - 1){
                    board[i][j] = WALL;
                } else{
                    board[i][j] = EMPTY;
                }
            }
        }

        //Place player in the center
        pRow = rows / 2;
        pCol = cols / 2;
        board[pRow][pCol] = PLAYER;

        //Randomly place walls, targets and boxes
        placeRandom(WALL, numObjects);
        placeRandom(TARGET, numObjects);
        placeRandom(BOX, numObjects);

        //Save initial state for restart
        initialBoard = new int[rows][cols];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                initialBoard[i][j] = board[i][j];
            }
        }
        initialPlayerRow = pRow;
        initialPlayerCol = pCol;
    }

    /**
     * Places a given number of elements randomly on empty cells of the board.
     * @param element the type of element to place
     * @param count the number of elements to place
     */
    //Requisito 2. Generar las paredes internas, los destinos y las cajas de manera aleatoria. El número de cada uno es el 10% del área.
    private void placeRandom(int element, int count){
        Random random = new Random();
        int placed = 0;
        while(placed < count){
            int r = 1 + random.nextInt(rows - 2);
            int c = 1 + random.nextInt(cols - 2);
            if(board[r][c] == EMPTY){
                board[r][c] = element;
                placed ++;
            }
        }
    }

    /**
     * Moves the player in the given direction
     * The player can move to an empty cell or a target cell
     * The player can push a box to an empty or target cell
     * @param dRow the row direction (-1 up, 1 down, 0 no vertical movement)
     * @param dCol the column direction (-1 left, 1 right, 0 no horizontal movement)
     */
    //Requisito 3. Permitir mover al jugador
    public void move(int dRow, int dCol){
        int newRow = pRow + dRow;
        int newCol = pCol + dCol;

        if(!inBounds(newRow, newCol)) return;

        int newCell = board[newRow][newCol];

        //Move to empty or target cell
        if(newCell == EMPTY || newCell == TARGET){
            updatePlayerPosition(newRow, newCol);
        //Push a box
        } else if (newCell == BOX || newCell == BOX_ON_TARGET){
            int boxNewRow = newRow + dRow;
            int boxNewCol = newCol + dCol;

            if(!inBounds(boxNewRow, boxNewCol)) return;

            int boxNewCell = board[boxNewRow][boxNewCol];

            if(boxNewCell == EMPTY || boxNewCell == TARGET){
                //Update box position
                if(boxNewCell == TARGET){
                    board[boxNewRow][boxNewCol] = BOX_ON_TARGET;
                    boxesOnTarget ++;
                    System.out.println("boxesOnTarget: " + boxesOnTarget);
                } else{
                    board[boxNewRow][boxNewCol] = BOX;
                }

                if(newCell == BOX_ON_TARGET){
                    board[newRow][newCol] = TARGET;
                    boxesOnTarget --;
                } else{
                    board[newRow][newCol] = EMPTY;
                }

                updatePlayerPosition(newRow, newCol);
            }
        }
    }

    /**
     * Checks if a given cell is within the board bounds.
     * @param row the row to check
     * @param col the col to check
     * @return true if the cell is within bounds, false otherwise
     */
    private boolean inBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    /**
     * Updates the player position on the board.
     * @param newRow the new row of the player
     * @param newCol the new col of the player
     */
    private void updatePlayerPosition(int newRow, int newCol) {
        //Restore what was under the player
        board[pRow][pCol] = cellUnderPlayer;
        //Remember what is under the new position
        cellUnderPlayer = board[newRow][newCol];
        //Place player
        pRow = newRow;
        pCol = newCol;
        board[pRow][pCol] = PLAYER;
    }

    /**
     * Sets a cell value directly. Used for testing purposes.
     * @param row the row of the cell
     * @param col the col of the cell
     * @param value the value to set
     */
    public void setCell(int row, int col, int value) {
        board[row][col] = value;
    }

    /**
     * Sets the player position directly. Used for testing purposes.
     * @param row the row of the player
     * @param col the col of the player
     */
    public void setPlayerPosition(int row, int col) {
        board[pRow][pCol] = EMPTY;
        pRow = row;
        pCol = col;
        board[pRow][pCol] = PLAYER;
    }

    /**
     * Checks if the game is finished.
     * the game is finished when all boxes are on target
     * @return true if all boxes are on their targets, false if not
     */
    public boolean isFinished(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(board[i][j] == BOX){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Clears the board setting all inner cells to empty. Used for testing purposes.
     */
    public void clearBoard() {
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                board[i][j] = EMPTY;
            }
        }
        boxesOnTarget = 0;
    }

    /**
     * Resizes the board to the given dimensions and generates a new board
     * @param rows the new number of rows
     * @param cols the new number of columns
     */
    //Requisito 7. Modificar el tamaño del tablero.
    public void  resize(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.boxesOnTarget = 0;
        board = new int[rows][cols];
        generateBoard();
    }

    //Ciclo 7: Reiniciar
    /**
     * Restarts the game by generating the board with the same dimensions
     */
    public void restart() {
        boxesOnTarget = 0;
        cellUnderPlayer = EMPTY;
        pRow = initialPlayerRow;
        pCol = initialPlayerCol;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = initialBoard[i][j];
            }
        }
    }

}