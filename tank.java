package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class tank extends Polygon {
    private double speed;
    private int health;
    private Color color;

    protected boolean upPressed, downPressed, leftPressed, rightPressed, shootPressed;
    private ArrayList<Bullet> bullets;
    private long lastShotTime;
    private long fireCooldown = 300; // milliseconds between shots
    private Point barrelTipLocal = new Point(35, 0);

    // === Constructor ===
    public tank(Point position, Color color) {
        super(createTankShape(), position, 0); // use static method below
        this.speed = 2.5;
        this.health = 1;
        this.color = color;
        this.bullets = new ArrayList<>();
    }
    
    public tank(Point position, Color color, double rotation) {
        super(createTankShape(), position, rotation); // use static method below
        this.speed = 2.5;
        this.health = 1;
        this.color = color;
        this.bullets = new ArrayList<>();
    }

    /**
     * Defines the tank shape as a polygon: a rectangle with a small barrel.
     */
    private static Point[] createTankShape() {
        // The body is a 40x30 rectangle and the barrel extends from the front
        return new Point[]{
            new Point(-20, -15),
            new Point(20, -15),
            new Point(20, -5),     // start of barrel base
            new Point(35, -5), 
            new Point(35, 5),
            new Point(20, 5),
            new Point(20, 15),
            new Point(-20, 15)
        };
    }


    public void keyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
            case KeyEvent.VK_SPACE -> shootPressed = true;
        }
    }

    public void keyReleased(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
            case KeyEvent.VK_SPACE -> shootPressed = false;
        }
    }


    public void update() {
        if (leftPressed) rotate(-3);
        
        if (rightPressed) rotate(3);
        
        double radians = Math.toRadians(rotation);
        
        
        if (upPressed) {
            position.x += Math.cos(radians) * speed;
            position.y += Math.sin(radians) * speed;
        }
        if (downPressed) {
            position.x -= Math.cos(radians) * speed;
            position.y -= Math.sin(radians) * speed;
        }


        if (shootPressed && System.currentTimeMillis() - lastShotTime > fireCooldown) {
        	
            bullets.add(new Bullet(new Point(position.x, position.y), rotation));
            lastShotTime = System.currentTimeMillis();
            
        }

        // update bullets
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            b.update();
            if (!b.isAlive()) {
                bullets.remove(i);
                i--;
            }
        }
    }
    
    public Rectangle getBound() {
    	return new Rectangle((int) position.x - 20, (int) position.y - 20, 40, 40);
    }
    // === Draw ===
    public void draw(Graphics2D g) {
        Point[] pts = getPoints();
        int[] xs = new int[pts.length];
        int[] ys = new int[pts.length];
        for (int i = 0; i < pts.length; i++) {
            xs[i] = (int) pts[i].x;
            ys[i] = (int) pts[i].y;
        }

        g.setColor(color);
        g.fillPolygon(xs, ys, pts.length);
        g.setColor(Color.BLACK);
        g.drawPolygon(xs, ys, pts.length);

        // Draw bullets
        for (Bullet b : bullets) {
            b.draw(g);
        }
    }


    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }

    public boolean isAlive() {
        return health > 0;
    }

    // === Getters ===
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public int getHealth() {
        return health;
    }

    public Point getPosition() {
        return position;
    }
    
    public void checkBulletCollision(tank other) {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            if (other.isAlive() && other.getBound().intersects(b.getBounds())) {
                other.takeDamage(10);
                bullets.remove(i);
                i--;
            }
        }
    }

    public class Bullet {
        private Point position;
        private double rotation;
        private double speed = 15;
        private boolean alive = true;
        private final int SIZE = 5;

        public Bullet(Point start, double rotationDegrees) {
            this.rotation = rotationDegrees;
            double radians = Math.toRadians(rotation);
            // start bullet at the tip of the barrel
            this.position = new Point(start.x,
                                      start.y);
            
        }


        public void update() {
            double radians = Math.toRadians(rotation);
            position.x += Math.cos(radians)*speed;
            position.y += Math.sin(radians)*speed;

            // basic boundary removal
            if (position.x < 0 || position.x > 800 || position.y < 0 || position.y > 600) {
                alive = false;
            }
        }

        public void draw(Graphics2D g) {
            g.setColor(Color.BLACK);
            g.fillOval((int) position.x - SIZE / 2, (int) position.y - SIZE / 2, SIZE, SIZE);
        }

        public boolean isAlive() {
            return alive;
        }

        public Rectangle getBounds() {
            return new Rectangle((int) position.x - SIZE / 2, (int) position.y - SIZE / 2, SIZE, SIZE);
        }
    }
}
