
/**
 * ==========================================================
 * Minesweeper Game with Timer Functionality
 * ==========================================================
 * Author: Lukas Krapukaitis
 * Helpers: Tommy Clavin, Jackson Spock, Aakarsh Jain
 * Date Created: 12/08/2024
 * Last Updated: 12/08/2024
 * 
 * Description:
 * -----------
 * This program implements a fully interactive Minesweeper game
 * with an 12x12 (customizable) grid and a 30-second countdown timer. 
 * Players must uncover all tiles without mines to win the game. 
 * 
 * Key features include:
 * - Randomized placement of mines.
 * - Left-click to reveal tiles.
 * - Right-click to flag/unflag suspected mine locations.
 * - Countdown timer that ends the game if it reaches zero.
 * - Clear win/loss messaging in the GUI.
 * - Efficient recursive reveal of non-mine tiles.
 * 
 * Features:
 * ---------------------
 * 1. MineTile:
 *    - Represents each button (tile) on the Minesweeper board.
 *    - Contains the row and column position of the tile.
 * 
 * 2. Timer Functionality:
 *    - Utilizes a Swing Timer to count down from 30 seconds.
 *    - Automatically ends the game when the timer expires.
 * 
 * 3. Minesweeper Game Logic:
 *    - Dynamically generates a grid of buttons.
 *    - Randomly places a fixed number of mines on the board.
 *    - Tracks player progress and ensures clear win/loss conditions.
 * 
 * 
 * Notes:
 * ------
 * - This program was built using Java Swing for GUI rendering.
 * - To modify the grid size, mine count, or timer duration, 
 *   adjust the corresponding variables in the code.
 * 
 * ==========================================================
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;  //used to place mines in random places
import javax.swing.*;



public class Minesweeper {
    
    
    //represents singular tiles on the board
    private class MineTile extends JButton{
        int r; //row position of the tile
        int c; //column position of the tile
        
        public MineTile(int r, int c){
            this.r = r;
            this.c = c;
        }
    }

    // Timer properties for countdown functionality
    int timeLeft = 30; // 30 seconds timer
    Timer gameTimer;    // Swing Timer for game countdown

    int tileSize = 70;
    int numRows = 15;  //for 8x8 grid
    int numCols = numRows;
    int boardWidth = numCols * tileSize;    // 560 pixles
    int boardHeight = numRows * tileSize;   //by 560 pixles
    
    JFrame frame = new JFrame("Timed Minesweeper");
    JLabel textLabel = new JLabel();    // Label for displaying game status
    JPanel textPanel = new JPanel();    // Panel for the status text
    JPanel boardPanel = new JPanel();   // Panel for the game board

    int mineCount = 12; //Total number of mines, defaulr set to 12, can change 
    MineTile[][] board = new MineTile[numRows][numCols]; //2D array for the actual board layout
    ArrayList<MineTile> mineList;   // List of all mine tiles
    Random random = new Random();   // Random object to generate mine positions
    
    int tilesClicked = 0; //click all tiles except the ones containing mines, Counter for non-mine tiles clicked
    boolean gameOver = false;       // Flag to check if the game is over
    
    //constructor for minesweeper
    Minesweeper(){ 
        //Setting the main frame up for user 
        //frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);   // Frame dimensions with Values defined above
        frame.setLocationRelativeTo(null);      // Center the frame on the screen
        frame.setResizable(false);      // Disable resizing
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Close application on exit
        frame.setLayout(new BorderLayout());

        // Configure the status label
        textLabel.setFont(new Font("Arial", Font.BOLD, 25));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Timed Minesweeper: " + Integer.toString(mineCount));
        textLabel.setOpaque(true);

        // Add the label to the text panel and frame
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        // Set up the game board panel
        boardPanel.setLayout(new GridLayout(numRows, numCols)); //grid layout for tiles = 8x8
        boardPanel.setPreferredSize(new Dimension(boardWidth, boardHeight));
        //boardPanel.setBackground(Color.BLUE); //this is only if backgroud is needed
        frame.add(boardPanel);

        /// Create and add tiles to the board 
        int tilecount = 0; //for debugging
        for(int r = 0; r < numRows; r++){
            for (int c  = 0; c < numCols; c++){ //whats funny is i dont think we even learned what a nested for loop is in CNIT255.
                MineTile tile = new MineTile(r, c); //Creates new tile
                board[r][c] = tile;     //adds the new tile to the board
                tilecount++;

                //some properties of the tile - configure appearance and behavior
                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, 45));
                //tile.setText(Integer.toString(tilecount));  //For debugging
                //tile.setText("");

                // Add mouse listener for clicks
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e){
                        if (gameOver){
                            return; //Ignore clicks if the game is over
                        }
                        MineTile tile = (MineTile) e.getSource();
                        
                        // left click
                        if (e.getButton() == MouseEvent.BUTTON1){  //mouse left click = BUTTON1
                            if (tile.getText() == ""){
                                if (mineList.contains(tile)){
                                    revealMines();  // Game over if a mine is clicked
                                }
                                else{
                                    checkMine(tile.r, tile.c);  // Reveal non-mine tiles
                                }
                            }
                        }
                        //FOR THE RIGHT CLICKY FEATURE
                        else if (e.getButton() == MouseEvent.BUTTON3){
                            if (tile.getText() == "" && tile.isEnabled()){
                                tile.setText("X"); // Mark as flagged
                            }
                            else if (tile.getText() == "X"){
                                tile.setText("");   // Unmark tile
                            }
                        }
                    }
                });
                boardPanel.add(tile);   // Add tile to the board panel
                
            }
        }

        // Configure and Initialize the start timer
        gameTimer = new Timer(1000, new ActionListener() {
        @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--; // Decrease time remaining
                textLabel.setText("Time Left: " + timeLeft + " | Mines Remaining: " + mineCount);

                // End game if timer reaches zero
                if (timeLeft <= 0) {
                    gameTimer.stop();
                    gameOver = true;
                    revealMines();
                    textLabel.setText("Time's Up! Game Over!");
                }
            }
        });


        System.out.println("Total tiles added: " + tilecount);//used for debugging

        gameTimer.start(); //start the timer
        frame.setVisible(true); // Display the game frame
        setMines(); // Randomly place mines on the board
    }
    
    void setMines(){
        mineList = new ArrayList<MineTile>();

        // For debugging, testing out
        // mineList.add(board[2][2]);
        // mineList.add(board[5][6]);
        // mineList.add(board[2][3]);
        // mineList.add(board[3][4]);
        // mineList.add(board[1][1]);

        int mineLeft = mineCount;
        while(mineLeft > 0){
            int r = random.nextInt(numRows);    //Random row 0-7
            int c = random.nextInt(numCols);    //Random column

            MineTile tile = board[r][c];
            if (!mineList.contains(tile)){  //check to see if mineList already has this tile
                mineList.add(tile); // Add tile to mine list
                mineLeft -= 1;  // Decrease remaining mines to place
            }       
        }
    }

     // Reveal all mines when the game ends
    void revealMines(){ //displays all the mines
        for (int i = 0; i < mineList.size(); i++){
            MineTile tile = mineList.get(i);
            tile.setText("O"); //mark mines with BOMB (O)
        }
        gameOver = true;
        gameTimer.stop(); //stop the timer
        textLabel.setText("Game Over!");
    }

    // Check a tile and reveal neighboring tiles if safe
    void checkMine(int r, int c){
        if (r < 0 || r >= numRows || c < 0 || c >= numCols){  //Check to see if you are out of bounds of cheking your "neighboring" tiles
            return; //exits the function immediatly 
        }

        MineTile tile = board[r][c];
        if(!tile.isEnabled()){  // Skip already revealed tiles
            return;
        }
        tile.setEnabled(false); // Disable tile
        tilesClicked += 1;  // Increment clicked tile count

        int minesFound = 0;

        // Check surrounding tiles for mines
        //For the top 3 tiles
        minesFound += countMine(r-1, c-1);  //top left of center
        minesFound += countMine(r-1, c);    //top
        minesFound += countMine(r-1, c+1);  //top right

        //For the left and right tiles
        minesFound += countMine(r, c-1);    //left
        minesFound += countMine(r, c+1);    //right

        //for the bottom three
        minesFound += countMine(r+1, c-1);  //bottom left
        minesFound += countMine(r+1, c);    //bottom
        minesFound += countMine(r+1, c+1);  //Bottom right

        if (minesFound > 0){
            tile.setText(Integer.toString(minesFound)); // Display mine count
        } else{
            tile.setText("");

            // Recursively check surrounding tiles
            //top 3
            checkMine(r-1,c-1); //top left
            checkMine(r-1,c);   //top
            checkMine(r-1,c+1); //top right

            //left and right
            checkMine(r,c-1);   //left
            checkMine(r,c+1);   //right

            //bottom 3
            checkMine(r+1,c-1); //bottom left
            checkMine(r+1,c);   //bottom
            checkMine(r+1,c+1); //bottom right
        }
        
        // Check if the player has won
        if (tilesClicked == numRows * numCols - mineList.size()){ //WIN condition
            gameOver = true;
            gameTimer.stop(); //stop the timer
            textLabel.setText("Mines Cleared!");
        }

    }

    // Count the number of mines surrounding a tile
    int countMine(int r, int c){
        if (r < 0 || r >= numRows || c < 0 || c >= numCols){  //Check to see if you are out of bounds of cheking your "neighboring" tiles
            return 0;
        }
        if (mineList.contains(board[r][c])){ //check to see if you contain a mine
            return 1;
        }
        return 0; //If you dont contain a mine
    }

}
