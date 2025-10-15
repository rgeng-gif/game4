package game;

import javax.swing.*;

import game.Point;

import java.awt.*;
import java.awt.event.*;

public class tankDuel extends JPanel implements KeyListener {

    private tank player1;   // Green tank
    private tank2 player2;  // Red tank
    private Walls walls; //walls restricting movement of tanks and bullets
    private boolean running = true;
    private boolean gameOver = false;

    public tankDuel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);

        startNewGame();


        Timer timer = new Timer(16, e -> {
            if (!gameOver) {
                if (player1 != null && player1.isAlive()) player1.update();
                if (player2 != null && player2.isAlive()) player2.update();

                if (player1 != null && player2 != null) {
                    player1.checkBulletCollision(player2);
                    player2.checkBulletCollision(player1);
                }

                if (player1 != null && !player1.isAlive()) player1 = null;
                if (player2 != null && !player2.isAlive()) player2 = null;

                // Check if game ended
                if (player1 == null || player2 == null) {
                    gameOver = true;
                }
            }

            repaint();
        });
        timer.start();
    }

    /** Resets both tanks and starts a new game */
    private void startNewGame() {
    	walls = new Walls();
        player1 = new tank(new Point(100, 500), Color.GREEN, walls);
        player2 = new tank2(new Point(700, 100), Color.RED, walls);
        gameOver = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Draw alive tanks
        if (player1 != null && player2 != null) {
        	player1.draw(g2);
        	player2.draw(g2);
        	walls.draw(g2);
        }
        // Draw endgame text
        if (gameOver) {
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 28));

            if (player1 == null && player2 == null) {
                g2.drawString("Both tanks destroyed!", 270, 280);
            } else if (player1 == null) {
                g2.drawString("Red Wins!", 340, 280);
            } else if (player2 == null) {
                g2.drawString("Green Wins!", 330, 280);
            }

            g2.setFont(new Font("Arial", Font.PLAIN, 22));
            g2.drawString("Press R to Replay", 310, 320);
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gameOver && code == KeyEvent.VK_R) {
            startNewGame();
            return;
        }

        if (!gameOver) {
            if (player1 != null) player1.keyPressed(code);
            if (player2 != null) player2.keyPressed(code);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (!gameOver) {
            if (player1 != null) player1.keyReleased(code);
            if (player2 != null) player2.keyReleased(code);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}


    public static void main(String[] args) {
        JFrame frame = new JFrame("Tank Duel");
        tankDuel gamePanel = new tankDuel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
