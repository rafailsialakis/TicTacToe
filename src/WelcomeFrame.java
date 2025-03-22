import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WelcomeFrame extends JFrame {

    public WelcomeFrame() {
        // Set up the panel with a vertical layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 248, 255)); // Light blue background

        // Style the main label
        JLabel label = new JLabel("Welcome to TicTacToe");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); // Top padding

        // Style the subtitle
        JLabel subtitle = new JLabel("Choose your opponent wisely:");
        subtitle.setFont(new Font("Arial", Font.ITALIC, 16));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // Style buttons
        JButton playerButton = new JButton("Player");
        customizeButton(playerButton, new Color(60, 179, 113), new Color(34, 139, 34)); // Green shades
        JButton computerButton = new JButton("Computer");
        customizeButton(computerButton, new Color(70, 130, 180), new Color(25, 25, 112)); // Blue shades

        // Add components to the panel
        panel.add(label);
        panel.add(subtitle);
        panel.add(playerButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between buttons
        panel.add(computerButton);

        playerButton.addActionListener(_ -> {
            dispose();
            new TicTacToe();
        });
        computerButton.addActionListener(_ -> {
            dispose();
            new DifficultyFrame();
        });
        // Frame properties
        this.setContentPane(panel);
        this.setSize(400, 400);
        this.setTitle("Tic Tac Toe");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    // Custom button styling method
    private void customizeButton(JButton button, Color normalColor, Color hoverColor) {
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(normalColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
            }
        });
    }
}