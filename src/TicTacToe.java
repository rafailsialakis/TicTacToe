import javax.swing.*;
import java.awt.*;

public class TicTacToe extends JFrame {
    private final JButton[][] Buttons = new JButton[3][3];
    private static final String playerX = "X";
    private static final String playerO = "O";
    private boolean playsX = true; // True if X plays, False if O plays
    private boolean winner = false;
    private boolean draw = false;

    public TicTacToe() {
        JPanel panel = new JPanel(new GridLayout(3, 3));
        panel.setBackground(new Color(220, 220, 220)); // Light gray background
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Buttons[i][j] = new JButton("");
                Buttons[i][j].setFont(new Font("Arial", Font.BOLD, 50)); // Larger and bold font
                Buttons[i][j].setFocusPainted(false); // Remove focus border
                Buttons[i][j].setBackground(Color.WHITE); // Default background
                Buttons[i][j].setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2)); // Blue border
                Buttons[i][j].setForeground(new Color(34, 139, 34)); // Green text color for X
                Buttons[i][j].addActionListener(e -> {
                    if (!winner) {
                        JButton button = (JButton) e.getSource();
                        if (button.getText().isEmpty()) {
                            String currentPlayer = playsX ? playerX : playerO;
                            button.setText(currentPlayer);

                            // Set color based on the player
                            if (currentPlayer.equals(playerX)) {
                                button.setForeground(new Color(34, 139, 34)); // Green for X
                            } else {
                                button.setForeground(new Color(178, 34, 34)); // Red for O
                            }
                            draw = drawGame(Buttons);
                            // Check win for the current player
                            winner = checkWin(Buttons, currentPlayer);

                            if (winner) {
                                showEndGameDialog(currentPlayer);
                                return;
                            }
                            if (draw){
                                showDrawGameDialog();
                            }
                            // Toggle player after checking win
                            playsX = !playsX;
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
        JDialog dialog = new JDialog(this, "Game Over", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel("Player " + winner + " wins!", SwingConstants.CENTER);
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
                button.setForeground(new Color(34, 139, 34)); // Reset text color
            }
        }
        playsX = true; // Reset to X's turn
        winner = false; // Reset winner flag
    }

    private void goToMainMenu() {
        this.dispose(); // Close the current game window
        new WelcomeFrame(); // Open the main menu (assumes you have a WelcomeFrame class)
    }
}
