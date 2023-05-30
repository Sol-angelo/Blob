package com.solace.main.objects.powerups;

import com.solace.main.util.GameObject;
import com.solace.main.util.Handler;
import com.solace.main.util.enums.ID;

import java.awt.*;

public class PU extends GameObject {

    private Handler handler;

    public PU(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        if (id == ID.SpeedPU) {
            g.setColor(new Color(0, 121, 255));
        } else if (id == ID.KillPU) {
            g.setColor(new Color(255, 156, 27));
        }
        g.fillRect((int)this.x, (int)this.y, 16, 16);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)this.x, (int)this.y, 16, 16);
    }
}
