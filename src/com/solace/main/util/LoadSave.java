package com.solace.main.util;

import com.solace.main.Game;
import com.solace.main.util.HUD;

import java.io.*;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

public class LoadSave {
    private HUD hud;
    public static int level;
    public static int score;
    public static float health;

    public static int state;
    public static boolean regen;
    public static int saveAmount;

    private static Game game;

    public LoadSave(final HUD hud, Game game) {
        this.hud = hud;
        this.game = game;
    }

    public static boolean CheckForSaveFile(int saveNumber) {
        File txtFile = new File("res/data/saves/savedata"+saveNumber+".txt");
        return txtFile.exists();
    }

    public static void CreateSaveFile(String name) {
        ReadOnLoad();
        File txtFile = new File("res/data/saves/savedata"+saveAmount+".txt");
        try {
            txtFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PrintWriter pw = new PrintWriter(txtFile);
            pw.println(name);
            pw.println((int)HUD.HEALTH);
            pw.println(HUD.getStaticScore());
            pw.println(HUD.getStaticLevel());
            pw.print(Game.getCurrentGameStateToInt());
            pw.close();
            saveAmount++;
            CreateInfoFile();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

    public static void CreateSettingsFile() {
        File txtFile = new File("res/data/settings.txt");
        try {
            txtFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PrintWriter pw = new PrintWriter(txtFile);
            pw.println(Game.ARROWKEYS);
            pw.println(Game.scrollDirection);
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void CreateAchievementsFile() {
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

    public static void ReadFromSaveFile(int saveNumber) {
        File txtFile = new File("res/data/saves/savedata"+saveNumber+".txt");
        try{
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

            String name = br.readLine();
            health = Integer.parseInt(br.readLine());
            score = Integer.parseInt(br.readLine());
            level = Integer.parseInt(br.readLine());
            state = Integer.parseInt(br.readLine());
            regen = Boolean.parseBoolean(br.readLine());

            br.close();

            HUD.HEALTH = health;
            HUD.setScore(score);
            HUD.setLevel(level);
            Game.regen = regen;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String ReadFromSaveFileName(int saveNumber) {
        File txtFile = new File("res/data/saves/savedata"+saveNumber+".txt");
        try{
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

            String name = br.readLine();
            health = Integer.parseInt(br.readLine());
            score = Integer.parseInt(br.readLine());
            level = Integer.parseInt(br.readLine());
            state = Integer.parseInt(br.readLine());

            br.close();
            return name;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Float ReadFromSaveFileHealth(int saveNumber) {
        File txtFile = new File("res/data/saves/savedata"+saveNumber+".txt");
        try{
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

            String name = br.readLine();
            health = Integer.parseInt(br.readLine());
            score = Integer.parseInt(br.readLine());
            level = Integer.parseInt(br.readLine());
            state = Integer.parseInt(br.readLine());

            br.close();
            return health;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer ReadFromSaveFileScore(int saveNumber) {
        File txtFile = new File("res/data/saves/savedata"+saveNumber+".txt");
        try{
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

            String name = br.readLine();
            health = Integer.parseInt(br.readLine());
            score = Integer.parseInt(br.readLine());
            level = Integer.parseInt(br.readLine());
            state = Integer.parseInt(br.readLine());

            br.close();
            return score;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer ReadFromSaveFileLevel(int saveNumber) {
        File txtFile = new File("res/data/saves/savedata"+saveNumber+".txt");
        try{
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

            String name = br.readLine();
            health = Integer.parseInt(br.readLine());
            score = Integer.parseInt(br.readLine());
            level = Integer.parseInt(br.readLine());
            state = Integer.parseInt(br.readLine());

            br.close();
            return level;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer ReadFromSaveFileState(int saveNumber) {
        File txtFile = new File("res/data/saves/savedata"+saveNumber+".txt");
        try{
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

            String name = br.readLine();
            health = Integer.parseInt(br.readLine());
            score = Integer.parseInt(br.readLine());
            level = Integer.parseInt(br.readLine());
            state = Integer.parseInt(br.readLine());

            br.close();
            return state;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void ReadOnLoad() {
        File txtFilea = new File("res/data/achievements.txt");
        File txtFiles = new File("res/data/settings.txt");
        File txtFilei = new File("res/data/info.txt");
        try{
            BufferedReader bra = new BufferedReader(new FileReader(txtFilea));

            Game.boss1Killed = Boolean.parseBoolean(bra.readLine());
            bra.close();

            BufferedReader brs = new BufferedReader(new FileReader(txtFiles));

            Game.ARROWKEYS = Boolean.parseBoolean(brs.readLine());
            Game.scrollDirection = Boolean.parseBoolean(brs.readLine());
            brs.close();

            BufferedReader bri = new BufferedReader(new FileReader(txtFilei));
            saveAmount = Integer.parseInt(bri.readLine());
            bri.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
