package com.solace.main.util;

import com.solace.main.Game;
import com.solace.main.util.HUD;
import com.sun.tools.javac.Main;

import java.io.*;
import java.nio.file.Path;
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

    public static File getFileByOS(String addpath, String name) {
        String osname = System.getProperty("os.name");
        if (osname.contains("Mac")) {
            Path path = Path.of(System.getProperty("user.home"), "Library", "Application Support", "Solangelo", "Blob");
            File path2 = new File(path + "/" + addpath);
            path2.mkdirs();
            File txt = new File(path2 + "/"+name+".txt");
            return txt;
        } else if (osname.contains("Window")) {
            Path path = Path.of(System.getProperty("user.home"), "AppData", "Solangelo", "Blob");
            File path2 = new File(path + "/" + addpath);
            path2.mkdirs();
            File txt = new File(path2 + "/"+name+".txt");
            return txt;
        }
        return null;
    }

    public static boolean CheckForSaveFile(int saveNumber) {
        File txtFile = getFileByOS("saves", "savedata"+saveNumber);
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
            File txtFile = getFileByOS("saves", "savedata"+number);
            PrintWriter pw = new PrintWriter(txtFile);
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
            File txtFile = getFileByOS("saves", "savedata"+number);
            PrintWriter pw = new PrintWriter(txtFile);
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
            File txtFile = getFileByOS("data","info");
            PrintWriter pw = new PrintWriter(txtFile);
            pw.println(saveAmount);
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void CreateSettingsFile() {
        try {
            File txtFile = getFileByOS("data","settings");
            PrintWriter pw = new PrintWriter(txtFile);
            pw.println(Game.ARROWKEYS);
            pw.println(Game.scrollDirection);
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void CreateAchievementsFile() {
        try {
            File txtFile = getFileByOS("data","achievements");
            PrintWriter pw = new PrintWriter(txtFile);
            pw.write(String.valueOf(Game.boss1Killed));
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void ReadFromSaveFile(int saveNumber) {
        File txtFile = getFileByOS("saves","savedata"+saveNumber);
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
        File txtFile = getFileByOS("saves", "savedata"+saveNumber);
        try{
            BufferedReader br = new BufferedReader(new FileReader(txtFile));
            return br.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Float ReadFromSaveFileHealth(int saveNumber) {
        File txtFile = getFileByOS("saves","savedata"+saveNumber);
        try{
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

            br.readLine();
            health = Integer.parseInt(br.readLine());

            br.close();
            return health;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer ReadFromSaveFileScore(int saveNumber) {
        File txtFile = getFileByOS("saves","savedata"+saveNumber);
        try{
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

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
        File txtFile = getFileByOS("saves","savedata"+saveNumber);
        try{
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

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
        File txtFile = getFileByOS("saves","savedata"+saveNumber);
        try{
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

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
        File txtFilea = getFileByOS("data","achievements");
        File txtFiles = getFileByOS("data","settings");
        File txtFilei = getFileByOS("data","info");
        if (txtFilea.exists() && txtFilei.exists() && txtFiles.exists()) {
            try {
                BufferedReader bra = new BufferedReader(new FileReader(txtFilea));
                BufferedReader brs = new BufferedReader(new FileReader(txtFiles));
                BufferedReader bri = new BufferedReader(new FileReader(txtFilei));

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
        } else {
            CreateAchievementsFile();
            CreateSettingsFile();
            CreateInfoFile();
        }
    }

    public static void DeleteFile(String addpath, String name) {
        File txtFile = getFileByOS(addpath, name);
        if (txtFile.exists()) {
            System.out.println("file exists");
            txtFile.delete();
        }
    }

    public static void clearDirectory(String path) {
        String osname = System.getProperty("os.name");
        if (osname.contains("Mac")) {
            Path path1 = Path.of(System.getProperty("user.home"), "Library", "Application Support", "Solangelo", "Blob");
            File path2 = new File(path1 + "/" + path);
            path2.mkdirs();
            for (File file : path2.listFiles()) {
                if (!file.isDirectory()) {
                    file.delete();
                }
            }
            saveAmount = 0;
            CreateInfoFile();
        } else if (osname.contains("Window")) {
            Path path1 = Path.of(System.getProperty("user.home"), "AppData", "Solangelo", "Blob");
            File path2 = new File(path1 + "/" + path);
            path2.mkdirs();
            for (File file : path2.listFiles()) {
                if (!file.isDirectory()) {
                    file.delete();
                }
            }
            saveAmount = 0;
            CreateInfoFile();
        }
    }
}
