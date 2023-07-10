package com.solace.main.util;

import com.solace.main.Game;
import com.solace.main.util.HUD;
import com.sun.tools.javac.Main;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;
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

    public static int CreateSaveFile(String name) {
        ReadOnLoad();
        int number = 0;
        for (int i = 0; i <= saveAmount; i++) {
            if (!CheckForSaveFile(i)) {
                number = i;
                saveAmount = i+1;
                break;
            }
        }
        try {
            FileOutputStream outputStream = new FileOutputStream("res/data/saves/savedata"+number+".txt");
            PrintWriter pw = new PrintWriter(outputStream);
            pw.println(name);
            pw.println((int)HUD.HEALTH);
            pw.println(HUD.getStaticScore());
            pw.println(HUD.getStaticLevel());
            pw.println(Game.getCurrentGameStateToInt());
            pw.println(Game.regen);
            pw.close();
            CreateInfoFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return number;
    }

    public static void OverwriteSaveFile(String name, int number) {
        try {
            FileOutputStream outputStream = new FileOutputStream("res/data/saves/savedata"+number+".txt");
            PrintWriter pw = new PrintWriter(outputStream);
            pw.println(name);
            pw.println((int)HUD.HEALTH);
            pw.println(HUD.getStaticScore());
            pw.println(HUD.getStaticLevel());
            pw.println(Game.getCurrentGameStateToInt());
            pw.println(Game.regen);
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void CreateInfoFile() {
        try {
            FileOutputStream outputStream = new FileOutputStream("res/data/info.txt");
            PrintWriter pw = new PrintWriter(outputStream);
            pw.println(saveAmount);
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void CreateSettingsFile() {
        try {
            FileOutputStream outputStream = new FileOutputStream("res/data/settings.txt");
            PrintWriter pw = new PrintWriter(outputStream);
            pw.println(Game.ARROWKEYS);
            pw.println(Game.scrollDirection);
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void CreateAchievementsFile() {
        try {
            FileOutputStream outputStream = new FileOutputStream("res/data/achievements.txt");
            PrintWriter pw = new PrintWriter(outputStream);
            pw.write(String.valueOf(Game.boss1Killed));
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void ReadFromSaveFile(int saveNumber) {
        InputStream txtFile = ClassLoader.getSystemClassLoader().getResourceAsStream("data/saves/savedata"+saveNumber+".txt");
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(txtFile));

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
        InputStream txtFile = ClassLoader.getSystemClassLoader().getResourceAsStream("data/saves/savedata"+saveNumber+".txt");
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(txtFile));
            String name = br.readLine();
            br.close();
            return name;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Float ReadFromSaveFileHealth(int saveNumber) {
        InputStream txtFile = ClassLoader.getSystemClassLoader().getResourceAsStream("data/saves/savedata"+saveNumber+".txt");
        try{
            assert txtFile != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(txtFile));

            br.readLine();
            health = Integer.parseInt(br.readLine());

            br.close();
            return health;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer ReadFromSaveFileScore(int saveNumber) {
        InputStream txtFile = ClassLoader.getSystemClassLoader().getResourceAsStream("data/saves/savedata"+saveNumber+".txt");
        try{
            assert txtFile != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(txtFile));

            br.readLine();
            br.readLine();
            score = Integer.parseInt(br.readLine());

            br.close();
            return score;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer ReadFromSaveFileLevel(int saveNumber) {
        InputStream txtFile = ClassLoader.getSystemClassLoader().getResourceAsStream("data/saves/savedata"+saveNumber+".txt");
        try{
            assert txtFile != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(txtFile));

            br.readLine();
            br.readLine();
            br.readLine();
            level = Integer.parseInt(br.readLine());

            br.close();
            return level;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer ReadFromSaveFileState(int saveNumber) {
        InputStream txtFile = ClassLoader.getSystemClassLoader().getResourceAsStream("data/saves/savedata"+saveNumber+".txt");
        try{
            assert txtFile != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(txtFile));

            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            state = Integer.parseInt(br.readLine());

            br.close();
            return state;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void ReadOnLoad() {
        InputStream txtFilea = ClassLoader.getSystemClassLoader().getResourceAsStream("data/achievements.txt");
        InputStream txtFiles = ClassLoader.getSystemClassLoader().getResourceAsStream("data/settings.txt");
        InputStream txtFilei = ClassLoader.getSystemClassLoader().getResourceAsStream("data/info.txt");
        try{
            BufferedReader bra = new BufferedReader(new InputStreamReader(txtFilea));
            BufferedReader brs = new BufferedReader(new InputStreamReader(txtFiles));
            BufferedReader bri = new BufferedReader(new InputStreamReader(txtFilei));

            Game.boss1Killed = Boolean.parseBoolean(bra.readLine());

            Game.ARROWKEYS = Boolean.parseBoolean(brs.readLine());
            Game.scrollDirection = Boolean.parseBoolean(brs.readLine());

            saveAmount = Integer.parseInt(bri.readLine());

            bra.close();
            brs.close();
            bri.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void DeleteFile(String name) {
        File txtFile = new File("res/data/"+name+".txt");
        if (txtFile.exists()) {
            txtFile.delete();
        }
    }
}
