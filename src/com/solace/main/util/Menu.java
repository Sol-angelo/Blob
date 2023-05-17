// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main.util;

import com.solace.main.Game;
import com.solace.main.objects.MenuParticle;
import com.solace.main.objects.Player;
import com.solace.main.objects.enemies.BasicEnemy;
import com.solace.main.objects.enemies.BasicEnemy2;
import com.solace.main.objects.enemies.FastEnemy;
import com.solace.main.objects.enemies.TargetEnemy;
import com.solace.main.objects.enemies.bosses.Boss1Enemy;
import com.solace.main.objects.enemies.bosses.Boss2Enemy;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Random;
import java.awt.event.MouseAdapter;

public class Menu extends MouseAdapter
{
    private Game game;
    private Handler handler;
    private Random r;
    private HUD hud;

    public int scrollAmount;
    
    public Menu(final Game game, final Handler handler, final HUD hud) {
        this.r = new Random();
        this.game = game;
        this.handler = handler;
        this.hud = hud;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println("mouse wheel moved");
        if (Game.gameState == Game.STATE.SelectGame) {
            System.out.println("select game mouse wheel");
            if (Game.scrollDirection) {
                scrollAmount -= e.getWheelRotation();
            } else {
                scrollAmount += e.getWheelRotation();
            }
        }
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        final int mx = e.getX();
        final int my = e.getY();
        if (Game.gameState == Game.STATE.Menu) {
            if (this.mouseOver(mx, my, 210, 150, 200, 64)) {
                if (LoadSave.CheckForSaveFile(0)) {
                    Game.gameState = Game.STATE.SelectGame;
                } else {
                    Game.gameState = Game.STATE.GameCreation;
                }
                System.out.println(Game.gameState);
            }
            if (this.mouseOver(mx, my, 210, 250, 200, 64)) {
                Game.gameState = Game.STATE.Help;
            }
            if (this.mouseOver(mx, my, 210, 350, 200, 64)) {
                System.exit(2);
            }
            if (this.mouseOver(mx, my, 50, 350, 64, 64)) {
                Game.gameState = Game.STATE.Settings;
            }
        }
        else if (Game.gameState == Game.STATE.Settings) {
            if (this.mouseOver(mx, my, 350, 174, 32, 16)) {
                if (this.game.ARROWKEYS) {
                    this.game.ARROWKEYS = false;
                } else {
                    this.game.ARROWKEYS = true;
                }
            }
            if (this.mouseOver(mx, my, 350, 274, 32, 16)) {
                if (Game.scrollDirection) {
                    Game.scrollDirection = false;
                } else {
                    Game.scrollDirection = true;
                }
            }
            if (this.mouseOver(mx, my, 50, 50, 200, 64)) {
                LoadSave.CreateSettingsFile();
                Game.gameState = Game.STATE.Menu;
            }
        }
        else if (Game.gameState == Game.STATE.Help) {
            if (this.mouseOver(mx, my, 50, 50, 200, 64)) {
                Game.gameState = Game.STATE.Menu;
            }
        }
        else if (Game.gameState == Game.STATE.Death) {
            if (this.mouseOver(mx, my, 210, 250, 200, 64)) {
                Game.gameState = Game.STATE.Menu;
            }
            if (this.mouseOver(mx, my, 210, 350, 200, 64)) {
                Game.gameState = Game.STATE.Difficulty;
            }
        }
        else if (Game.gameState == Game.STATE.Difficulty) {
            if (this.mouseOver(mx, my, 210, 150, 200, 64)) {
                Game.gameState = Game.STATE.Easy;
                this.handler.addObject(new Player(304.0f, 208.0f, ID.Player, this.handler, this.game));
                this.handler.clearEnemies();
                this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
            }
            if (this.mouseOver(mx, my, 210, 250, 200, 64)) {
                Game.gameState = Game.STATE.Medium;
                this.handler.addObject(new Player(304.0f, 208.0f, ID.Player, this.handler, this.game));
                this.handler.clearEnemies();
                this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
            }
            if (this.mouseOver(mx, my, 210, 350, 200, 64)) {
                Game.gameState = Game.STATE.Hard;
                this.handler.addObject(new Player(304.0f, 208.0f, ID.Player, this.handler, this.game));
                this.handler.clearEnemies();
                this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
            }
            if (this.mouseOver(mx, my, 50, 350, 64, 64)) {
                Game.gameState = Game.STATE.Saveload;
            }
        }
        else if (Game.gameState == Game.STATE.Saveload) {
            if (this.mouseOver(mx, my, 50, 50, 200, 64)) {
                Game.gameState = Game.STATE.Difficulty;
            }
            if (this.mouseOver(mx, my, 210, 150, 200, 64)) {
                System.out.println("load 1");
                LoadSave.ReadFromSaveFile(0);
                if (LoadSave.state == 1) {
                    Game.gameState = Game.STATE.Easy;
                    this.handler.addObject(new Player(304.0f, 208.0f, ID.Player, this.handler, this.game));
                    this.handler.clearEnemies();
                    this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                    if (this.hud.getLevel() >= 2) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 3) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 4) {
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 5) {
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 6) {
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 7) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 8) {
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 9) {
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 10) {
                        this.handler.clearEnemies();
                        this.handler.addObject(new Boss1Enemy(270.0f, -120.0f, ID.Boss1Enemy, this.handler));
                    }
                    if (this.hud.getLevel() >= 11) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 12) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 13) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 14) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 15) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 16) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 17) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 18) {
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 19) {
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 20) {
                        this.handler.clearEnemiesB2();
                        this.handler.addObject(new Boss2Enemy(288.0f, -80.0f, ID.Boss2Enemy, this.handler));
                    }

                }
                if (LoadSave.state == 2) {
                    Game.gameState = Game.STATE.Medium;
                    this.handler.addObject(new Player(304.0f, 208.0f, ID.Player, this.handler, this.game));
                    this.handler.clearEnemies();
                    this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                    if (this.hud.getLevel() >= 2) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 3) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 4) {
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 5) {
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 6) {
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 7) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 8) {
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 9) {
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 10) {
                        this.handler.clearEnemies();
                        this.handler.addObject(new Boss1Enemy(270.0f, -120.0f, ID.Boss1Enemy, this.handler));
                    }
                    if (this.hud.getLevel() >= 11) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 12) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 13) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 14) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 15) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                }
                if (LoadSave.state == 3) {
                    Game.gameState = Game.STATE.Hard;
                    this.handler.addObject(new Player(304.0f, 208.0f, ID.Player, this.handler, this.game));
                    this.handler.clearEnemies();
                    this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                    if (this.hud.getLevel() >= 2) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 3) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 4) {
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 5) {
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 6) {
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 7) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 8) {
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 9) {
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 10) {
                        this.handler.clearEnemies();
                        this.handler.addObject(new Boss1Enemy(270.0f, -120.0f, ID.Boss1Enemy, this.handler));
                    }
                    if (this.hud.getLevel() >= 11) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 12) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 13) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 14) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() >= 15) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                }
            }
            if (this.mouseOver(mx, my, 210, 250, 200, 64)) {
                System.out.println("load 2");
            }
            if (this.mouseOver(mx, my, 210, 350, 200, 64)) {
                System.out.println("load 3");
            }
        }
        else if (Game.gameState == Game.STATE.Easy || Game.gameState == Game.STATE.Medium || Game.gameState == Game.STATE.Hard) {
            final Game game = this.game;
            if (Game.paused = true) {
                if (this.mouseOver(mx, my, 210, 150, 200, 64)) {
                    Game.paused = false;
                }
                if (this.mouseOver(mx, my, 210, 250, 200, 64)) {
                    this.handler.clearAll();
                    Game.gameState = Game.STATE.Menu;
                    for (int i = 0; i < 20; ++i) {
                        this.handler.addObject(new MenuParticle((float)this.r.nextInt(640), (float)this.r.nextInt(480), ID.MenuParticle, this.handler));
                    }
                    Game.paused = false;
                }
                if (this.mouseOver(mx, my, 210, 350, 200, 64)) {
                    Game.gameState = Game.STATE.SaveloadIG;
                    System.out.println("went to saveloadig");
                    //LoadSave.CreateSaveFile();
                    //System.exit(2);
                }
            }
        }
        if (Game.gameState == Game.STATE.SaveloadIG) {
            /*if (this.mouseOver(mx, my, 50, 50, 200, 64)) {
                if (Game.getCurrentGameStateToInt() == 1) {
                    Game.gameState = Game.STATE.Easy;
                    System.out.println("returned to game easy");
                }
                if (Game.getCurrentGameStateToInt() == 2) {
                    Game.gameState = Game.STATE.Medium;
                }
                if (Game.getCurrentGameStateToInt() == 3) {
                    Game.gameState = Game.STATE.Hard;
                }
            }
            if (this.mouseOver(mx, my, 210, 150, 200, 64)) {
                if (LoadSave.CheckForSaveFile(0)) {
                    Game.gameState = Game.STATE.Save1;
                    System.out.println("found 0");
                } else {
                    LoadSave.CreateSaveFile(0);
                    System.out.println("saved 0");
                }
            }
            if (this.mouseOver(mx, my, 210, 250, 200, 64)) {
                if (LoadSave.CheckForSaveFile(1)) {
                    Game.gameState = Game.STATE.Save2;
                    System.out.println("found 1");
                } else {
                    LoadSave.CreateSaveFile(1);
                    System.out.println("saved 1");
                }
            }
            /*if (this.mouseOver(mx, my, 210, 350, 200, 64)) {
                if (LoadSave.CheckForSaveFile(2)) {
                    Game.gameState = Game.STATE.Save3;
                    System.out.println("found 2");
                } else {
                    LoadSave.CreateSaveFile(2);
                    System.out.println("saved 2");
                }
            }*/
        }
    }
    
    @Override
    public void mouseReleased(final MouseEvent e) {
    }
    
    private boolean mouseOver(final int mx, final int my, final int x, final int y, final int width, final int height) {
        return mx > x && mx < x + width && (my > y && my < y + height);
    }
    
    public void tick() {
    }
    
    public void render(final Graphics g) {
        final Font fnt = new Font("arial", 1, 50);
        final Font fnt2 = new Font("arial", 1, 30);
        final Font fnt3 = new Font("arial", 1, 20);
        if (Game.gameState == Game.STATE.Menu) {
            g.setColor(new Color(17, 17, 17, 100));
            g.fillRect(210, 150, 200, 64);
            g.fillRect(210, 250, 200, 64);
            g.fillRect(210, 350, 200, 64);
            g.fillRect(50, 350, 64, 64);
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Blob", 250, 75);
            g.setFont(fnt2);
            g.drawString("Play", 275, 190);
            g.drawString("Help", 275, 290);
            g.drawString("Quit", 275, 390);
            g.drawRect(210, 150, 200, 64);
            g.drawRect(210, 250, 200, 64);
            g.drawRect(210, 350, 200, 64);
            g.drawRect(50, 350, 64, 64);
        }
        if (Game.gameState == Game.STATE.Settings) {
            g.setColor(new Color(17, 17, 17, 100));
            g.fillRect(210, 150, 200, 64);
            g.fillRect(210, 250, 200, 64);
            g.setColor(Color.white);
            g.setFont(fnt3);
            g.drawString("Arrow Keys", 220, 190);
            g.drawRect(350, 174, 32, 16);
            g.drawRect(210, 150, 200, 64);
            if (Game.ARROWKEYS) {
                g.setColor(new Color(45, 225, 50));
                g.fillRect(351, 175, 15, 14);
            } else {
                g.setColor(new Color(215, 65, 31));
                g.fillRect(366, 175, 15, 14);
            }
            g.setColor(Color.white);
            g.setFont(fnt3);
            g.drawString("Normal Scroll", 220, 290);
            g.drawRect(350, 274, 32, 16);
            g.drawRect(210, 250, 200, 64);
            if (Game.scrollDirection) {
                g.setColor(new Color(45, 225, 50));
                g.fillRect(351, 275, 15, 14);
            } else {
                g.setColor(new Color(215, 65, 31));
                g.fillRect(366, 275, 15, 14);
            }
            g.setFont(fnt2);
            g.setColor(Color.white);
            g.drawString("Back", 130, 90);
            g.setColor(Color.WHITE);
            g.drawRect(50, 50, 200, 64);
        }
        else if (Game.gameState == Game.STATE.Saveload) {
            g.setColor(new Color(17, 17, 17, 100));
            g.fillRect(0, 0, 640, 480);
            g.setFont(fnt2);
            g.setColor(Color.white);
            g.drawString("Back", 130, 90);
            g.drawRect(50, 50, 200, 64);
            g.drawString("Save 1", 265, 190);
            g.drawString("Save 2", 265, 290);
            g.drawString("Save 3", 265, 390);
            g.drawRect(210, 150, 200, 64);
            g.drawRect(210, 250, 200, 64);
            g.drawRect(210, 350, 200, 64);
        }
        else if (Game.gameState == Game.STATE.SaveloadIG) {
            g.setColor(new Color(17, 17, 17, 100));
            g.fillRect(0, 0, 640, 480);
            g.setFont(fnt2);
            g.setColor(Color.white);
            g.drawString("Back", 130, 90);
            g.drawRect(50, 50, 200, 64);
            g.drawString("Save 1", 265, 190);
            g.drawString("Save 2", 265, 290);
            g.drawString("Save 3", 265, 390);
            g.drawRect(210, 150, 200, 64);
            g.drawRect(210, 250, 200, 64);
            g.drawRect(210, 350, 200, 64);
        }
        else if (Game.gameState == Game.STATE.Help) {
            g.setColor(new Color(17, 17, 17, 100));
            g.fillRect(0, 0, 640, 480);
            g.setFont(fnt2);
            g.setColor(Color.white);
            g.drawString("Back", 130, 90);
            g.setColor(Color.WHITE);
            g.drawRect(50, 50, 200, 64);
        }
        else if (Game.gameState == Game.STATE.Death) {
            g.setColor(new Color(17, 17, 17, 100));
            g.fillRect(0, 0, 640, 480);
            g.setFont(fnt);
            g.setColor(Color.red);
            g.drawString("GAME OVER", 160, 170);
            g.setFont(fnt3);
            g.setColor(Color.white);
            g.drawString(String.valueOf("Your Score was: "+(int)hud.getScore()), 210, 230);
            g.setFont(fnt2);
            g.setColor(Color.white);
            g.drawString("Main Menu", 230, 290);
            g.drawString("Retry", 275, 390);
            g.drawRect(210, 250, 200, 64);
            g.drawRect(210, 350, 200, 64);
        }
        else if (Game.gameState == Game.STATE.SelectGame) {
            g.setColor(new Color(17, 17, 17, 100));
            g.fillRect(0, 0, 640, 480);

            g.setColor(new Color(255, 0, 0));
            g.fillRect(100, 100+scrollAmount, 100, 100);
        }
        else if (Game.gameState == Game.STATE.GameCreation) {
            g.setColor(new Color(17, 17, 17, 100));
            g.fillRect(0, 0, 640, 480);

            g.setColor(new Color(255, 255, 255));
            g.fillRect(100, 100+scrollAmount, 100, 100);
        }
        else if (Game.gameState == Game.STATE.Difficulty) {
            g.setColor(new Color(17, 17, 17, 100));
            g.fillRect(0, 0, 640, 480);
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Blob", 250, 75);
            g.setFont(fnt2);
            g.drawString("Easy", 275, 190);
            g.drawString("Medium", 265, 290);
            g.drawString("Hard", 275, 390);
            g.drawRect(210, 150, 200, 64);
            g.drawRect(210, 250, 200, 64);
            g.drawRect(210, 350, 200, 64);
            g.drawRect(50, 350, 64, 64);
        } else {
            if (Game.paused) {
                g.setFont(fnt2);
                g.setColor(new Color(17, 17, 17, 200));
                g.fillRect(210, 100, 200, 64);
                g.fillRect(210, 200, 200, 64);
                g.fillRect(210, 300, 200, 64);
                g.setColor(new Color(17, 17, 17, 100));
                g.fillRect(0, 0, 640, 480);
                g.setColor(Color.white);
                g.drawString("Play", 275, 140);
                g.drawString("Menu", 275, 240);
                g.drawString("Quit", 275, 340);
                g.drawRect(210, 100, 200, 64);
                g.drawRect(210, 200, 200, 64);
                g.drawRect(210, 300, 200, 64);
            }
        }
    }
}
