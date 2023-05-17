package com.solace.main.util;

import com.solace.main.Game;
import com.solace.main.util.HUD;

import java.io.*;

public class LoadSave {
    private HUD hud;
    public static int level;
    public static int score;
    public static float health;

    public static int state;
    public static int saveAmount;

    private static Game game;

    public LoadSave(final HUD hud, Game game) {
        this.hud = hud;
        this.game = game;
    }

    public static void CreateSaveFile() {
        ReadFromInfoFile();
        File txtFile = new File("res/data/saves/savedata"+saveAmount+".txt");
        try {
            txtFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PrintWriter pw = new PrintWriter(txtFile);
            pw.println((int)HUD.HEALTH);
            pw.println((int)HUD.getStaticScore());
            pw.println(HUD.getStaticLevel());
            pw.print(Game.getCurrentGameStateToInt());
            pw.close();
            saveAmount++;
            CreateInfoFile();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean CheckForSaveFile(int saveNumber) {
        File txtFile = new File("res/data/saves/savedata"+saveNumber+".txt");
        return txtFile.exists();
    }

    public static void ReadFromSaveFile(int saveNumber) {
        File txtFile = new File("res/data/saves/savedata"+saveNumber+".txt");
        try{
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

            health = Integer.parseInt(br.readLine());
            score = Integer.parseInt(br.readLine());
            level = Integer.parseInt(br.readLine());
            state = Integer.parseInt(br.readLine());

            br.close();

            HUD.HEALTH = health;
            HUD.setLevel(level);
            HUD.setScore(score);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void CreateInfoFile() {
        File txtFile = new File("res/data/info.txt");
        try {
            txtFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PrintWriter pw = new PrintWriter(txtFile);
            pw.println(saveAmount);
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void ReadFromInfoFile() {
        File txtFile = new File("res/data/info.txt");
        try{
            BufferedReader br = new BufferedReader(new FileReader(txtFile));
            saveAmount = Integer.parseInt(br.readLine());
            br.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void CreateSettingsFile() {
        File txtFile = new File("res/data/settings.txt");
        try {
            txtFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PrintWriter pw = new PrintWriter(txtFile);
            pw.write(String.valueOf(Game.ARROWKEYS));
            pw.write(String.valueOf(Game.scrollDirection));
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void ReadFromSettingsFile() {
        File txtFile = new File("res/data/settings.txt");
        try{
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

            Game.ARROWKEYS = Boolean.parseBoolean(br.readLine());
            Game.scrollDirection = Boolean.parseBoolean(br.readLine());
            br.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void CreateSaveFileAchievements() {
        File txtFile = new File("res/data/achievements.txt");
        try {
            txtFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PrintWriter pw = new PrintWriter(txtFile);
            pw.write(String.valueOf(Game.boss1Killed));
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void ReadFromSaveFileAchievements() {
        File txtFile = new File("res/data/achievements.txt");
        try{
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

            Game.boss1Killed = Boolean.parseBoolean(br.readLine());
            br.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
