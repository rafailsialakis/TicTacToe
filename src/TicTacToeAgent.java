import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TicTacToeAgent extends JFrame {
    private final JButton[][] Buttons = new JButton[3][3];
    private static final String playerX = "X";
    private static final String playerO = "O";
    private boolean playsX = true; // True if X plays, False if O plays
    private boolean winner = false;
    private boolean draw = false;
    private final String mode; // Game mode: "Easy" or "Impossible"
    private final Random random = new Random();

    public TicTacToeAgent(String mode) {
        this.mode = mode;
        this.playsX = true; // Ensure the game always starts with X's turn
        JPanel panel = new JPanel(new GridLayout(3, 3));
        panel.setBackground(new Color(220, 220, 220)); // Light gray background
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Buttons[i][j] = new JButton("");
                Buttons[i][j].setFont(new Font("Arial", Font.BOLD, 50)); // Larger and bold font
                Buttons[i][j].setFocusPainted(false); // Remove focus border
                Buttons[i][j].setBackground(Color.WHITE); // Default background
                Buttons[i][j].setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2)); // Blue border
                Buttons[i][j].addActionListener(e -> {
                    if (!winner) {
                        JButton button = (JButton) e.getSource();
                        if (button.getText().isEmpty()) {
                            makeMove(button);

                            if (!winner && !draw) {
                                aiMove();
                            }
                        }
                    }
                });
                panel.add(Buttons[i][j]);
            }
        }

        this.setContentPane(panel);
        this.setSize(500, 500);
        this.setVisible(true);
        this.setTitle("Tic Tac Toe");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private void makeMove(JButton button) {
        // Ensure only the user can make a move with "X"
        if (playsX && button.getText().isEmpty()) {
            button.setText(playerX); // User always plays X
            button.setForeground(new Color(34, 139, 34)); // Green for X

            draw = drawGame(Buttons);
            winner = checkWin(Buttons, playerX);

            if (winner) {
                showEndGameDialog(playerX);
                return; // Stop further processing if game is over
            } else if (draw) {
                showDrawGameDialog();
                return; // Stop further processing if game is a draw
            }

            // Toggle turn to AI
            playsX = false;

            // Trigger AI move
            aiMove();
        }
    }



    private void aiMove() {
        if (!playsX && !winner && !draw) { // Only proceed if it's AI's turn
            int i = -1, j = -1;

            if (mode.equals("Easy")) {
                // Find a random move
                do {
                    i = random.nextInt(3);
                    j = random.nextInt(3);
                } while (!Buttons[i][j].getText().isEmpty());
            } else if (mode.equals("Impossible")) {
                // Find the best move
                int[] bestMove = findBestMove();
                if (bestMove != null) {
                    i = bestMove[0];
                    j = bestMove[1];
                }
            }

            // Make the AI's move directly
            if (i != -1 && j != -1) {
                Buttons[i][j].setText(playerO);
                Buttons[i][j].setForeground(new Color(255, 0, 0)); // Red for O

                // Check for game status after AI move
                winner = checkWin(Buttons, playerO);
                draw = drawGame(Buttons);

                if (winner) {
                    showEndGameDialog(playerO);
                } else if (draw) {
                    showDrawGameDialog();
                }

                // Toggle turn back to user
                playsX = true;
            }
        }
    }




    private void easyMove() {
        int i, j;
        do {
            i = random.nextInt(3);
            j = random.nextInt(3);
        } while (!Buttons[i][j].getText().isEmpty());

        makeMove(Buttons[i][j]);
    }

    private void impossibleMove() {
        int[] bestMove = findBestMove();
        if (bestMove != null) {
            makeMove(Buttons[bestMove[0]][bestMove[1]]);
        }
    }

    private int[] findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] move = null;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Buttons[i][j].getText().isEmpty()) {
                    Buttons[i][j].setText(playerO);
                    int score = minimax(false);
                    Buttons[i][j].setText("");

                    if (score > bestScore) {
                        bestScore = score;
                        move = new int[]{i, j};
                    }
                }
            }
        }

        return move;
    }

    private int minimax(boolean isMaximizing) {
        if (checkWin(Buttons, playerO)) return 10;
        if (checkWin(Buttons, playerX)) return -10;
        if (drawGame(Buttons)) return 0;

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Buttons[i][j].getText().isEmpty()) {
                    Buttons[i][j].setText(isMaximizing ? playerO : playerX);
                    int score = minimax(!isMaximizing);
                    Buttons[i][j].setText("");

                    if (isMaximizing) {
                        bestScore = Math.max(bestScore, score);
                    } else {
                        bestScore = Math.min(bestScore, score);
                    }
                }
            }
        }

        return bestScore;
    }

    private boolean checkWin(JButton[][] buttons, String player) {
        boolean win;

        // Check rows
        for (int i = 0; i < 3; i++) {
            win = true;
            for (int j = 0; j < 3; j++) {
                if (!buttons[i][j].getText().equals(player)) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            win = true;
            for (int j = 0; j < 3; j++) {
                if (!buttons[j][i].getText().equals(player)) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        // Check main diagonal
        win = true;
        for (int i = 0; i < 3; i++) {
            if (!buttons[i][i].getText().equals(player)) {
                win = false;
                break;
            }
        }
        if (win) return true;

        // Check secondary diagonal
        win = true;
        for (int i = 0; i < 3; i++) {
            if (!buttons[i][2 - i].getText().equals(player)) {
                win = false;
                break;
            }
        }
        return win;
    }

    private void showDrawGameDialog(){
        // Create a custom dialog
        JDialog dialog = new JDialog(this, "Game Over", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel("The game ended in a draw", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dialog.add(messageLabel, BorderLayout.CENTER);

        // Buttons for restart and main menu
        JPanel buttonPanel = new JPanel();
        JButton restartButton = new JButton("Restart");
        JButton mainMenuButton = new JButton("Main Menu");

        restartButton.addActionListener(_ -> {
            dialog.dispose();
            restartGame();
        });

        mainMenuButton.addActionListener(_ -> {
            dialog.dispose();
            goToMainMenu();
        });

        buttonPanel.add(restartButton);
        buttonPanel.add(mainMenuButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }


    private void showEndGameDialog(String winner) {
        // Create a custom dialog
        JLabel messageLabel = new JLabel();
        JDialog dialog = new JDialog(this, "Game Over", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        if(winner.equals("O")){
            messageLabel = new JLabel("Computer wins!", SwingConstants.CENTER);
        }else{
            messageLabel = new JLabel("Player wins!", SwingConstants.CENTER);
        }
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dialog.add(messageLabel, BorderLayout.CENTER);

        // Buttons for restart and main menu
        JPanel buttonPanel = new JPanel();
        JButton restartButton = new JButton("Restart");
        JButton mainMenuButton = new JButton("Main Menu");

        restartButton.addActionListener(_ -> {
            dialog.dispose();
            restartGame();
        });

        mainMenuButton.addActionListener(_ -> {
            dialog.dispose();
            goToMainMenu();
        });

        buttonPanel.add(restartButton);
        buttonPanel.add(mainMenuButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private boolean drawGame(JButton[][] buttons){
        boolean draw = true;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(buttons[i][j].getText().isEmpty()){
                    draw = false;
                }
            }
        }
        return draw;
    }

    private void restartGame() {
        // Reset all buttons to initial state
        for (JButton[] row : Buttons) {
            for (JButton button : row) {
                button.setText("");
                button.setEnabled(true);
                button.setForeground(new Color(34, 139, 34)); // Reset text color for X
            }
        }
        playsX = true;  // Ensure the player always starts as X
        winner = false; // Reset winner flag
        draw = false;   // Reset draw flag
    }



    private void goToMainMenu() {
        this.dispose(); // Close the current game window
        new WelcomeFrame(); // Open the main menu (assumes you have a WelcomeFrame class)
    }
}
