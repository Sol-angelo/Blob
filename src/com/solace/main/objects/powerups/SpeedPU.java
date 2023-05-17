package com.solace.main.objects.powerups;

import com.solace.main.Game;
import com.solace.main.util.GameObject;
import com.solace.main.util.HUD;
import com.solace.main.util.Handler;
import com.solace.main.util.ID;

import java.awt.*;

public class SpeedPU extends GameObject {

    private Handler handler;

    public SpeedPU(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    @Override
    public void tick() {
        collision();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(0, 121, 255));
        g.fillRect((int)this.x, (int)this.y, 16, 16);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)this.x, (int)this.y, 16, 16);
    }

    private void collision() {
        for (int i = 0; i < this.handler.object.size(); ++i) {
            final GameObject tempObject = this.handler.object.get(i);
            if ((tempObject.getId() == ID.Player) && this.getBounds().intersects(tempObject.getBounds())) {
                this.handler.removeObject(this);
            }
        }
    }
}
