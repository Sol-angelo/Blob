// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main.util;

import com.solace.main.Game;
import com.solace.main.objects.Player;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.util.LinkedList;
import java.util.Random;

public class Handler extends MouseAdapter
{
    private Game game;
    private Random r;
    private HUD hud;
    public LinkedList<GameObject> object;
    
    public Handler(final Game game) {
        this.object = new LinkedList<GameObject>();
        this.game = game;
        this.r = new Random();
    }
    
    public void tick() {
        for (int i = 0; i < this.object.size(); ++i) {
            final GameObject tempObject = this.object.get(i);
            tempObject.tick();

        }
    }
    
    public void render(final Graphics g) {
        for (int i = 0; i < this.object.size(); ++i) {
            final GameObject tempObject = this.object.get(i);
            tempObject.render(g);
        }
    }
    
    public void clearEnemies() {
        for (int i = 0; i < this.object.size(); ++i) {
            final GameObject tempObject = this.object.get(i);
            if (tempObject.getId() == ID.Player) {
                this.object.clear();
                this.addObject(new Player((float)(int)tempObject.getX(), (float)(int)tempObject.getY(), ID.Player, this, this.game));

            }
        }
    }

    public void clearEnemiesB2() {
        for (int i = 0; i < this.object.size(); ++i) {
            final GameObject tempObject = this.object.get(i);
            if (tempObject.getId() == ID.Player) {
                this.object.clear();
                this.addObject(new Player(304, 224, ID.Player, this, this.game));
            }
        }
    }
    
    public void clearAll() {
        for (int i = 0; i < this.object.size(); ++i) {
            final GameObject tempObject = this.object.get(i);
            if (tempObject.getId() != ID.MenuParticle) {
                this.object.clear();
            }
        }
    }
    
    public void addObject(final GameObject object) {
        this.object.add(object);
    }
    
    public void removeObject(final GameObject object) {
        this.object.remove(object);
    }
}
