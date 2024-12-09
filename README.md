# TimedMinesweeper-Java
Version of minesweeper built in Java that is timed

## Overview
This repository contains a fully interactive **Minesweeper game** built in Java using the Swing framework. The game features an customizable grid (default set at 8x8), a customizable countdown timer (default set at 30 seconds), and a modern GUI. Players can enjoy a challenging gameplay experience with clear win/loss mechanics.

---

## Features
- **Interactive Game Board**: 8x8 grid of tiles for a classic Minesweeper experience.
- **Randomized Mines**: Mines are placed randomly on the board at the start of each game.
- **Timer Functionality**: A 30-second countdown timer adds urgency to the gameplay.
- **Tile Actions**:
  - **Left-click** to reveal a tile.
  - **Right-click** to flag or unflag a suspected mine.
- **Win/Loss Conditions**:
  - **Win**: Reveal all tiles without mines.
  - **Loss**: Click on a mine or run out of time.
- **Dynamic Messaging**: Displays remaining time, mine count, and win/loss messages in real-time.

---

## Getting Started

### Prerequisites
- **Java Development Kit (JDK)**: Version 8 or higher.
- **IDE (Optional)**: Any Java IDE (e.g., IntelliJ IDEA, Eclipse) for easier development and debugging.

---

## How to Run
1. Compile the code:
    ```bash
    javac Minesweeper.java App.java
    ```
2. Run the program:
    ```bash
    java App
    ```
3. The game window will appear, and you can start playing!

---

## Gameplay Instructions
1. **Starting the Game**:
   - The game begins with a randomly generated 8x8 grid.
   - Mines are hidden, and the timer starts at 30 seconds.

2. **Controls**:
   - **Left-click**: Reveal a tile.
   - **Right-click**: Flag or unflag a suspected mine.
   
3. **Objective**:
   - Avoid mines and reveal all safe tiles.
   - Use the numbers on tiles to deduce mine locations.

4. **End Conditions**:
   - **Win**: All safe tiles are revealed.
   - **Lose**: Timer runs out or you click on a mine.

---

## Customization
You can modify the following parameters in the `Minesweeper.java` file:
- **Grid Size**: Change the `numRows` and `numCols` variables to adjust the board dimensions.
- **Mine Count**: Modify the `mineCount` variable to increase or decrease the number of mines.
- **Timer Duration**: Adjust the `timeLeft` variable to change the countdown duration.

---

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.
