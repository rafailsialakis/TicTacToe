import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DifficultyFrame extends JFrame {

    private JPanel panel = new JPanel();
    private JLabel label = new JLabel("Choose Difficulty");
    private JLabel subtitle = new JLabel("Select your game difficulty:");
    private JButton easyButton = new JButton("Easy");
    private JButton impossibleButton = new JButton("Impossible");

    public DifficultyFrame() {
        // Set up the panel with a vertical layout
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 248, 255)); // Light blue background

        // Style the main label
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); // Top padding

        // Style the subtitle
        subtitle.setFont(new Font("Arial", Font.ITALIC, 16));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // Style buttons
        customizeButton(easyButton, new Color(60, 179, 113), new Color(34, 139, 34)); // Green shades
        customizeButton(impossibleButton, new Color(255, 69, 0), new Color(255, 0, 0)); // Red shades

        // Add components to the panel
        panel.add(label);
        panel.add(subtitle);
        panel.add(easyButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing between buttons
        panel.add(impossibleButton);

        // Button actions
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                // Start game in Easy mode
                new TicTacToeAgent("Easy");
            }
        });

        impossibleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                // Start game in Impossible mode
                new TicTacToeAgent("Impossible");
            }
        });

        // Frame properties
        this.setContentPane(panel);
        this.setSize(400, 400);
        this.setTitle("Choose Difficulty");
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