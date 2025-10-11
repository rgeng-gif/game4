package game;

import java.awt.*;
import java.awt.geom.*;

public interface CollectableItem {
    // Called every frame to draw the collectable
    void draw(Graphics2D g);

    // Returns the collision bounds for the collectable
    Shape getBounds();

    // Applies the effect to the tank
    void applyEffect(tank Tank);

    // Returns whether the collectable is still active
    boolean isActive();

    // Deactivates the collectable (after collected)
    void deactivate();
}