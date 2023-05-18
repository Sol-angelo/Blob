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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.awt.event.MouseAdapter;

public class Menu extends MouseAdapter
{
    private Game game;
    private Handler handler;
    private Random r;
    private HUD hud;
    public int scrollAmount;
    public int page;
    public int pageAmount;
    public Selected selected;

    public Menu(final Game game, final Handler handler, final HUD hud) {
        this.r = new Random();
        this.game = game;
        this.handler = handler;
        this.hud = hud;
        LoadSave.ReadOnLoad();
        selected = Selected.None;
        if (LoadSave.saveAmount % 3 == 0) {
            pageAmount = (LoadSave.saveAmount + 1) / 3;
        } else {
            if (LoadSave.CheckForSaveFile(14) || LoadSave.CheckForSaveFile(13) ||LoadSave.CheckForSaveFile(12)) {
                pageAmount = 5;
            } else if (LoadSave.CheckForSaveFile(11) ||LoadSave.CheckForSaveFile(10) ||LoadSave.CheckForSaveFile(9)) {
                pageAmount = 4;
            } else if (LoadSave.CheckForSaveFile(8) || LoadSave.CheckForSaveFile(7) || LoadSave.CheckForSaveFile(6)) {
                pageAmount = 3;
            } else if (LoadSave.CheckForSaveFile(5) || LoadSave.CheckForSaveFile(4) || LoadSave.CheckForSaveFile(3)){
                pageAmount = 2;
            } else if (LoadSave.CheckForSaveFile(2) || LoadSave.CheckForSaveFile(1) || LoadSave.CheckForSaveFile(0)) {
                pageAmount = 1;
            } else {
                pageAmount = 0;
            }
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println("mouse wheel moved");
        if (Game.gameState == Game.STATE.SelectGame) {
            System.out.println("select game mouse wheel");
            /*if (Game.scrollDirection) {
                scrollAmount -= e.getWheelRotation();
            } else {
                scrollAmount += e.getWheelRotation();
            }*/
            String message;
            int notches = e.getWheelRotation();
            if (notches < 0) {
                message = "Mouse wheel moved UP " + -notches + " notch(es)";
            } else {
                message = "Mouse wheel moved DOWN " + notches + " notch(es)";
            }
            if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
                message += "    Scroll type: WHEEL_UNIT_SCROLL";
                message += "    Scroll amount: " + e.getScrollAmount()
                        + " unit increments per notch" ;
                message += "    Units to scroll: " + e.getUnitsToScroll()
                        + " unit increments" ;
                //message += "    Vertical unit increment: " + scrollPane.getVerticalScrollBar().getUnitIncrement(1) + " pixels" ;
            } else { //scroll type == MouseWheelEvent.WHEEL_BLOCK_SCROLL
                message += "    Scroll type: WHEEL_BLOCK_SCROLL" ;
                //message += "    Vertical block increment: " + scrollPane.getVerticalScrollBar().getBlockIncrement(1) + " pixels" ;
            }
            System.out.println(message + " " + e);
        }
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        final int mx = e.getX();
        final int my = e.getY();
        if (Game.gameState == Game.STATE.Menu) {
            if (this.mouseOver(mx, my, 210, 150, 200, 64)) {
                if (LoadSave.CheckForSaveFile(0)) {
                    page = 1;
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
        } else if (Game.gameState == Game.STATE.SelectGame) {
            if (page >= pageAmount && pageAmount != 0) {

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
            if (Game.paused) {
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
        if (Game.gameState == Game.STATE.SelectGame) {
            if (mouseOver(mx, my, 50, 50, 540, 100)) {
                selected = Selected.Game1;
            }
            if (selected == Selected.Game1) {
                if (mouseOver(mx, my, 450, 70, 64, 60)) {
                    LoadSave.ReadFromSaveFile((page-1) * 3);
                    if (LoadSave.state == 1) {
                        Game.gameState = Game.STATE.Easy;
                        this.handler.addObject(new Player(304.0f, 208.0f, ID.Player, this.handler, this.game));
                        this.handler.clearEnemies();
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        LoadSave.ReadFromSaveFile((page-1) * 3);
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
                        LoadSave.ReadFromSaveFile((page-1) * 3);
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
                        LoadSave.ReadFromSaveFile((page-1) * 3);
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
                        LoadSave.ReadFromSaveFile((page-1) * 3);
                    }
                }
            }
            if (LoadSave.saveAmount >= 1) {
                if (mouseOver(mx, my, 50, 170, 540, 100)) {
                    selected = Selected.Game2;
                }
                if (selected == Selected.Game2) {
                    if (mouseOver(mx, my, 450, 190, 64, 60)) {
                        LoadSave.ReadFromSaveFile((page - 1) * 3 + 1);
                        if (LoadSave.state == 1) {
                            Game.gameState = Game.STATE.Easy;
                            this.handler.addObject(new Player(304.0f, 208.0f, ID.Player, this.handler, this.game));
                            this.handler.clearEnemies();
                            this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                            LoadSave.ReadFromSaveFile((page - 1) * 3 + 1);
                            if (this.hud.getLevel() >= 2) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 3) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 4) {
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 5) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 6) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 7) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 8) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 9) {
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() == 10) {
                                this.handler.clearEnemies();
                                this.handler.addObject(new Boss1Enemy(270.0f, -120.0f, ID.Boss1Enemy, this.handler));
                            }
                            if (this.hud.getLevel() >= 11) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 12) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 13) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 14) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 15) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 16) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 17) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 18) {
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 19) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() == 20) {
                                this.handler.clearEnemiesB2();
                                this.handler.addObject(new Boss2Enemy(288.0f, -80.0f, ID.Boss2Enemy, this.handler));
                            }
                            LoadSave.ReadFromSaveFile((page - 1) * 3 + 1);
                        }
                        if (LoadSave.state == 2) {
                            Game.gameState = Game.STATE.Medium;
                            this.handler.addObject(new Player(304.0f, 208.0f, ID.Player, this.handler, this.game));
                            this.handler.clearEnemies();
                            this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                            if (this.hud.getLevel() >= 2) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 3) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 4) {
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 5) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 6) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 7) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 8) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 9) {
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 10) {
                                this.handler.clearEnemies();
                                this.handler.addObject(new Boss1Enemy(270.0f, -120.0f, ID.Boss1Enemy, this.handler));
                            }
                            if (this.hud.getLevel() >= 11) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 12) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 13) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 14) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 15) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            LoadSave.ReadFromSaveFile((page - 1) * 3 + 1);
                        }
                        if (LoadSave.state == 3) {
                            Game.gameState = Game.STATE.Hard;
                            this.handler.addObject(new Player(304.0f, 208.0f, ID.Player, this.handler, this.game));
                            this.handler.clearEnemies();
                            this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                            if (this.hud.getLevel() >= 2) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 3) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 4) {
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 5) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 6) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 7) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 8) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 9) {
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 10) {
                                this.handler.clearEnemies();
                                this.handler.addObject(new Boss1Enemy(270.0f, -120.0f, ID.Boss1Enemy, this.handler));
                            }
                            if (this.hud.getLevel() >= 11) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 12) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 13) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 14) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 15) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            LoadSave.ReadFromSaveFile((page - 1) * 3 + 1);
                        }
                    }
                }
            }
            if (LoadSave.saveAmount >=2) {
                if (mouseOver(mx, my, 50, 290, 540, 100)) {
                    selected = Selected.Game3;
                }
                if (selected == Selected.Game3) {
                    if (mouseOver(mx, my, 450, 190, 64, 60)) {
                        LoadSave.ReadFromSaveFile((page - 1) * 3 + 2);
                        if (LoadSave.state == 1) {
                            Game.gameState = Game.STATE.Easy;
                            this.handler.addObject(new Player(304.0f, 208.0f, ID.Player, this.handler, this.game));
                            this.handler.clearEnemies();
                            this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                            LoadSave.ReadFromSaveFile((page - 1) * 3 + 2);
                            if (this.hud.getLevel() >= 2) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 3) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 4) {
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 5) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 6) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 7) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 8) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 9) {
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() == 10) {
                                this.handler.clearEnemies();
                                this.handler.addObject(new Boss1Enemy(270.0f, -120.0f, ID.Boss1Enemy, this.handler));
                            }
                            if (this.hud.getLevel() >= 11) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 12) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 13) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 14) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 15) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 16) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 17) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 18) {
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 19) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() == 20) {
                                this.handler.clearEnemiesB2();
                                this.handler.addObject(new Boss2Enemy(288.0f, -80.0f, ID.Boss2Enemy, this.handler));
                            }
                            LoadSave.ReadFromSaveFile((page - 1) * 3 + 2);
                        }
                        if (LoadSave.state == 2) {
                            Game.gameState = Game.STATE.Medium;
                            this.handler.addObject(new Player(304.0f, 208.0f, ID.Player, this.handler, this.game));
                            this.handler.clearEnemies();
                            this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                            if (this.hud.getLevel() >= 2) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 3) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 4) {
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 5) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 6) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 7) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 8) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 9) {
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 10) {
                                this.handler.clearEnemies();
                                this.handler.addObject(new Boss1Enemy(270.0f, -120.0f, ID.Boss1Enemy, this.handler));
                            }
                            if (this.hud.getLevel() >= 11) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 12) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 13) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 14) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 15) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            LoadSave.ReadFromSaveFile((page - 1) * 3 + 2);
                        }
                        if (LoadSave.state == 3) {
                            Game.gameState = Game.STATE.Hard;
                            this.handler.addObject(new Player(304.0f, 208.0f, ID.Player, this.handler, this.game));
                            this.handler.clearEnemies();
                            this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                            if (this.hud.getLevel() >= 2) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 3) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 4) {
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 5) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 6) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 7) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 8) {
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 9) {
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 10) {
                                this.handler.clearEnemies();
                                this.handler.addObject(new Boss1Enemy(270.0f, -120.0f, ID.Boss1Enemy, this.handler));
                            }
                            if (this.hud.getLevel() >= 11) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 12) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 13) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 14) {
                                this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            if (this.hud.getLevel() >= 15) {
                                this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                                this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                            }
                            LoadSave.ReadFromSaveFile((page - 1) * 3 + 2);
                        }
                    }
                }
            }
            if (mouseOver(mx, my, 0, 0, 640, 480)) {
                if (!mouseOver(mx, my, 50, 50, 540, 100)) {
                    if (LoadSave.saveAmount >= 2) {
                        if (!mouseOver(mx, my, 50, 290, 540, 100) && !mouseOver(mx, my, 50, 170, 540, 100)) {
                            selected = Selected.None;
                        }
                    } else if (LoadSave.saveAmount >= 1) {
                        if (!mouseOver(mx, my, 50, 170, 540, 100)) {
                            selected = Selected.None;
                        }
                    } else {
                        selected = Selected.None;
                    }
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
        final Font fnt4 = new Font("arial", 1, 14);
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
            g.drawString("Your Score was: "+hud.getScore(), 210, 230);
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
            g.setColor(new Color(17, 17, 17, 200));
            g.fillRect(50, 50, 540, 100);
            g.setColor(Color.white);
            g.drawRect(50, 50, 540, 100);
            if (selected == Selected.Game1) {
                g.drawRect(49, 49, 542, 102);
            } else if (selected == Selected.Game2) {
                g.drawRect(49, 169, 542, 102);
            } else if (selected == Selected.Game3) {
                g.drawRect(49, 289, 542, 102);
            }
            g.drawRect(450, 70, 64, 60);
            g.setFont(fnt2);
            int i = (page-1) * 3;
            g.drawString(LoadSave.ReadFromSaveFileName(i), 70, 90);
            g.setFont(fnt4);
            g.drawString("Level: "+LoadSave.ReadFromSaveFileLevel(i), 70, 110);
            g.drawString("Score: "+LoadSave.ReadFromSaveFileScore(i), 70, 125);
            g.drawString("Difficulty: "+Game.getStateIntToString(LoadSave.ReadFromSaveFileState(i)), 70, 140);
            if (LoadSave.saveAmount >= 1) {
                g.setColor(new Color(17, 17, 17, 200));
                g.fillRect(50, 170, 540, 100);
                g.setColor(Color.white);
                g.drawRect(50, 170, 540, 100);
                g.setFont(fnt2);
                int n = (page-1) * 3 + 1;
                g.drawString(LoadSave.ReadFromSaveFileName(n), 70, 210);
                g.setFont(fnt4);
                g.drawString("Level: "+LoadSave.ReadFromSaveFileLevel(n), 70, 230);
                g.drawString("Score: "+LoadSave.ReadFromSaveFileScore(n), 70, 245);
                g.drawString("Difficulty: "+Game.getStateIntToString(LoadSave.ReadFromSaveFileState(n)), 70, 260);
                g.drawRect(450, 190, 64, 60);
            }
            if (LoadSave.saveAmount >= 2) {
                g.setColor(new Color(17, 17, 17, 200));
                g.fillRect(50, 290, 540, 100);
                g.setColor(Color.white);
                g.drawRect(50, 290, 540, 100);
                g.setFont(fnt2);
                int m = (page-1) * 3 + 2;
                g.drawString(LoadSave.ReadFromSaveFileName(m), 70, 90+ 240);
                g.setFont(fnt4);
                g.drawString("Level: "+LoadSave.ReadFromSaveFileLevel(m), 70, 110+ 240);
                g.drawString("Score: "+LoadSave.ReadFromSaveFileScore(m), 70, 125+ 240);
                g.drawString("Difficulty: "+Game.getStateIntToString(LoadSave.ReadFromSaveFileState(m)), 70, 140+ 240);
                g.drawRect(450, 310, 64, 60);
            }
        }
        else if (Game.gameState == Game.STATE.GameCreation) {
            g.setColor(new Color(17, 17, 17, 100));
            g.fillRect(0, 0, 640, 480);
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
