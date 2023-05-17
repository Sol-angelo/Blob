// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main.util;

import com.solace.main.Game;
import com.solace.main.objects.enemies.BasicEnemy;
import com.solace.main.objects.enemies.BasicEnemy2;
import com.solace.main.objects.enemies.FastEnemy;
import com.solace.main.objects.enemies.TargetEnemy;
import com.solace.main.objects.enemies.bosses.Boss1Enemy;
import com.solace.main.objects.enemies.bosses.Boss2Enemy;

import java.util.Random;

public class Spawn
{
    private Game game;
    private Handler handler;
    private HUD hud;
    private Random r;
    private int ScoreKeep;
    public Difficulty difficulty;
    
    public Spawn(final Handler handler, final HUD hud, final Game game) {
        this.r = new Random();
        this.ScoreKeep = 0;
        this.handler = handler;
        this.hud = hud;
        this.game = game;
    }
    
    public void tick() {
        ++this.ScoreKeep;
        if (this.difficulty == Difficulty.Easy) {
            if (this.hud.getLevel() != 10 || this.hud.getLevel() != 20) {
                if (this.ScoreKeep >= 500) {
                    this.ScoreKeep = 0;
                    HUD.setLevel(this.hud.getLevel() + 1);
                    if (this.hud.getLevel() == 2) {
                        this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 3) {
                        this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 4) {
                        this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 5) {
                        this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 6) {
                        this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 7) {
                        this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 8) {
                        this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 9) {
                        this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 10) {
                        Game.boss1Killed = true;
                        LoadSave.CreateSaveFileAchievements();
                        this.handler.clearEnemies();
                        this.handler.addObject(new Boss1Enemy(270.0f, -120.0f, ID.Boss1Enemy, this.handler));
                    }
                    if (this.hud.getLevel() == 11) {
                        this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 12) {
                        this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 13) {
                        this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 14) {
                        this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 15) {
                        this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 16) {
                        this.handler.addObject(new BasicEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 17) {
                        this.handler.addObject(new BasicEnemy2((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 18) {
                        this.handler.addObject(new FastEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 19) {
                        this.handler.addObject(new TargetEnemy((float) this.r.nextInt(590), (float) this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 20) {
                        this.handler.clearEnemiesB2();
                        this.handler.addObject(new Boss2Enemy(288.0f, -80.0f, ID.Boss2Enemy, this.handler));
                    }
                }
            } else if (this.hud.getLevel() == 10 && this.ScoreKeep >= 2180) {
                this.ScoreKeep = 0;
                this.handler.clearEnemies();
                HUD.setLevel(11);
            } else if (this.hud.getLevel() == 20 && this.ScoreKeep >= 4000) {
                this.ScoreKeep = 0;
                this.handler.clearEnemies();
                HUD.setLevel(21);
            }

            if (this.hud.getLevel() == 10) {
                System.out.println("level 10");
            }
        } else if (this.difficulty == Difficulty.Medium) {
            if (this.hud.getLevel() != 10) {
                if (this.ScoreKeep >= 500) {
                    this.ScoreKeep = 0;
                    this.hud.setLevel(this.hud.getLevel() + 1);
                    if (this.hud.getLevel() == 2) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 3) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 4) {
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 5) {
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 6) {
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 7) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 8) {
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 9) {
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 10) {
                        this.handler.clearEnemies();
                        this.handler.addObject(new Boss1Enemy(270.0f, -120.0f, ID.Boss1Enemy, this.handler));
                    }
                    if (this.hud.getLevel() == 11) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 12) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 13) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 14) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 15) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                }
            }
            else if (this.hud.getLevel() == 10 && this.ScoreKeep >= 2180) {
                this.ScoreKeep = 0;
                this.handler.clearEnemies();
                this.hud.setLevel(11);
            }
        } else {
            if (this.hud.getLevel() != 10) {
                if (this.ScoreKeep >= 500) {
                    this.ScoreKeep = 0;
                    this.hud.setLevel(this.hud.getLevel() + 1);
                    if (this.hud.getLevel() == 2) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 3) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 4) {
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 5) {
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 6) {
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 7) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 8) {
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 9) {
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 10) {
                        this.handler.clearEnemies();
                        this.handler.addObject(new Boss1Enemy(270.0f, -120.0f, ID.Boss1Enemy, this.handler));
                    }
                    if (this.hud.getLevel() == 11) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 12) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new TargetEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.TargetEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 13) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 14) {
                        this.handler.addObject(new BasicEnemy2((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy2, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                    if (this.hud.getLevel() == 15) {
                        this.handler.addObject(new BasicEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.BasicEnemy, this.handler, this.game));
                        this.handler.addObject(new FastEnemy((float)this.r.nextInt(590), (float)this.r.nextInt(430), ID.FastEnemy, this.handler, this.game));
                    }
                }
            }
            else if (this.hud.getLevel() == 10 && this.ScoreKeep >= 2180) {
                this.ScoreKeep = 0;
                this.handler.clearEnemies();
                this.hud.setLevel(11);
            }
        }
    }

    public enum Difficulty {
        Easy,
        Medium,
        Hard
    }
}
