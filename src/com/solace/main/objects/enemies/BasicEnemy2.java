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

public class BasicEnemy2 extends GameObject
{
    private Handler handler;
    private Game game;
    
    public BasicEnemy2(final float x, final float y, final ID id, final Handler handler, final Game game) {
        super(x, y, id);
        this.handler = handler;
        this.game = game;
        if (game.gameState == Game.STATE.Easy) {
            this.velX = -3.0f;
            this.velY = -3.0f;
        }
        else if (game.gameState == Game.STATE.Medium) {
            this.velX = -5.0f;
            this.velY = -5.0f;
        }
        else if (game.gameState == Game.STATE.Hard) {
            this.velX = -6.0f;
            this.velY = -6.0f;
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
        if (this.y <= 0.0f || this.y >= 448.0f) {
            this.velY *= -1.0f;
        }
        if (this.x <= 0.0f || this.x >= 624.0f) {
            this.velX *= -1.0f;
        }
        this.handler.addObject(new TrailS(this.x, this.y, ID.Trail, new Color(243, 66, 32, 255), 16.0f, 16.0f, 0.09f, this.handler));
    }
    
    @Override
    public void render(final Graphics g) {
        g.setColor(new Color(243, 66, 32, 255));
        g.fillRect((int)this.x, (int)this.y, 16, 16);
    }
}
