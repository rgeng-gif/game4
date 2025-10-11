package game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class tank2 extends tank {
	
	

    public tank2(Point position, Color color) {
        super(position, color, 180);
    }

    @Override
    public void keyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_DOWN -> downPressed = true;
            case KeyEvent.VK_LEFT -> leftPressed = true;
            case KeyEvent.VK_RIGHT -> rightPressed = true;
            case KeyEvent.VK_ENTER -> shootPressed = true;
        }
    }

    @Override
    public void keyReleased(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_DOWN -> downPressed = false;
            case KeyEvent.VK_LEFT -> leftPressed = false;
            case KeyEvent.VK_RIGHT -> rightPressed = false;
            case KeyEvent.VK_ENTER -> shootPressed = false;
        }
    }
}
