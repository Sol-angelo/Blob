// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main.objects.enemies;

import com.solace.main.*;
import com.solace.main.util.GameObject;
import com.solace.main.util.TrailS;
import com.solace.main.util.Handler;
import com.solace.main.util.ID;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class TargetEnemy extends GameObject
{
    private Handler handler;
    private GameObject player;
    private Game game;
    
    public TargetEnemy(final float x, final float y, final ID id, final Handler handler, final Game game) {
        super(x, y, id);
        this.handler = handler;
        this.game = game;
        for (int i = 0; i < handler.object.size(); ++i) {
            if (handler.object.get(i).getId() == ID.Player) {
                this.player = handler.object.get(i);
            }
        }
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)this.x, (int)this.y, 16, 16);
    }
    
    @Override
    public void tick() {
        this.x += this.velX;
        this.y += this.velY;
        final float diffX = this.x - this.player.getX() - 8.0f;
        final float diffY = this.y - this.player.getY() - 8.0f;
        final float distance = (float)Math.sqrt((this.x - this.player.getX()) * (this.x - this.player.getX()) + (this.y - this.player.getY()) * (this.y - this.player.getY()));
        this.velX = (float)(-1.0 / distance * diffX);
        this.velY = (float)(-1.0 / distance * diffY);
        this.handler.addObject(new TrailS(this.x, this.y, ID.Trail, new Color(229, 243, 32, 255), 16.0f, 16.0f, 0.09f, this.handler));
    }
    
    @Override
    public void render(final Graphics g) {
        g.setColor(new Color(243, 239, 32, 255));
        g.fillRect((int)this.x, (int)this.y, 16, 16);
    }
}
