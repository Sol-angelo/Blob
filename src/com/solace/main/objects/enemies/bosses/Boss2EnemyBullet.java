// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main.objects.enemies.bosses;

import com.solace.main.util.GameObject;
import com.solace.main.util.Handler;
import com.solace.main.util.ID;
import com.solace.main.util.TrailS;

import java.awt.*;
import java.util.Random;

public class Boss2EnemyBullet extends GameObject
{
    private Handler handler;
    Random r;

    public Boss2EnemyBullet(final float x, final float y, final ID id, final Handler handler) {
        super(x, y, id);
        this.r = new Random();
        this.handler = handler;
        this.velX = (float)(this.r.nextInt(10) - 5);
        this.velY = 5.0f;
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)this.x, (int)this.y, 16, 16);
    }
    
    @Override
    public void tick() {
        this.x += this.velX;
        this.y += this.velY;
        if (this.y >= 480.0f) {
            this.handler.removeObject(this);
        }
        this.handler.addObject(new TrailS(this.x, this.y, ID.Trail, new Color(32, 243, 43, 255), 16.0f, 16.0f, 0.09f, this.handler));
    }
    
    @Override
    public void render(final Graphics g) {
        g.setColor(new Color(32, 243, 43, 255));
        g.fillRect((int)this.x, (int)this.y, 16, 16);
    }
}
