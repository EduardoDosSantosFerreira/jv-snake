import javax.swing.*;
import java.awt.*;

public class SnakeGame extends JFrame {

    public SnakeGame() {
        initUI();
    }

    private void initUI() {
        Board board = new Board();
        add(board);

        setResizable(false);
        pack();

        setTitle("Snake Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame snakeGame = new SnakeGame();
            snakeGame.setVisible(true);
        });
    }
}
