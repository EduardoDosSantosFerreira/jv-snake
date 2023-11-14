import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;

public class Board extends JPanel implements ActionListener, KeyListener {

    private static final int B_WIDTH = 300;
    private static final int B_HEIGHT = 300;
    private static final int DOT_SIZE = 10;
    private static final int ALL_DOTS = 900;
    private static final int RAND_POS = 29;

    private final LinkedList<Point> snake = new LinkedList<>();
    private Point apple;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        addKeyListener(this);

        initGame();
    }

    private void initGame() {
        snake.clear();
        for (int z = 3; z > 0; z--) {
            snake.add(new Point(z, 1));
        }

        spawnApple();

        timer = new Timer(140, this);
        timer.start();
    }

    private void spawnApple() {
        Random rand = new Random();
        int x = rand.nextInt(RAND_POS);
        int y = rand.nextInt(RAND_POS);

        apple = new Point(x, y);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (inGame) {
            drawApple(g);
            drawSnake(g);

            Toolkit.getDefaultToolkit().sync();

        } else {
            gameOver(g);
        }
    }

    private void drawApple(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(apple.x * DOT_SIZE, apple.y * DOT_SIZE, DOT_SIZE, DOT_SIZE);
    }

    private void drawSnake(Graphics g) {
        g.setColor(Color.GREEN);
        for (Point point : snake) {
            g.fillRect(point.x * DOT_SIZE, point.y * DOT_SIZE, DOT_SIZE, DOT_SIZE);
        }
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    private void checkApple() {
        if (snake.getFirst().equals(apple)) {
            snake.addLast(new Point(-1, -1)); // Add a dummy point to be updated in the next iteration
            spawnApple();
        }
    }

    private void checkCollision() {
        Point head = snake.getFirst();

        if (head.x < 0 || head.x >= B_WIDTH / DOT_SIZE || head.y < 0 || head.y >= B_HEIGHT / DOT_SIZE) {
            inGame = false;
            return;
        }

        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                inGame = false;
                return;
            }
        }
    }

    private void move() {
        Point head = snake.getFirst();
        Point newHead;

        if (leftDirection) {
            newHead = new Point(head.x - 1, head.y);
        } else if (rightDirection) {
            newHead = new Point(head.x + 1, head.y);
        } else if (upDirection) {
            newHead = new Point(head.x, head.y - 1);
        } else {
            newHead = new Point(head.x, head.y + 1);
        }

        snake.addFirst(newHead);
        snake.removeLast(); // Remove the last point of the snake

        checkCollision();
        checkApple();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            move();
            repaint();
        } else {
            timer.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
            leftDirection = true;
            upDirection = false;
            downDirection = false;
        } else if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
            rightDirection = true;
            upDirection = false;
            downDirection = false;
        } else if ((key == KeyEvent.VK_UP) && (!downDirection)) {
            upDirection = true;
            rightDirection = false;
            leftDirection = false;
        } else if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
            downDirection = true;
            rightDirection = false;
            leftDirection = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
