package com.solace.main.util;

import com.solace.main.Game;
import com.solace.main.objects.Player;
import com.solace.main.util.HUD;
import com.sun.tools.javac.Main;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.locks.Lock;


public class LoadSave {
    private HUD hud;
    private static Handler handler;
    public static int level;
    public static int score;
    public static float health;
    public static int state;
    public static boolean regen;
    public static int saveAmount;
    public static final String key = "cayden is slayer";

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    private static Game game;

    public LoadSave(final HUD hud, Game game, Handler handler) {
        this.hud = hud;
        this.game = game;
        this.handler = handler;
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

    public static File getEncryptedByOS(String addpath, String name) {
        String osname = System.getProperty("os.name");
        if (osname.contains("Mac")) {
            Path path = Path.of(System.getProperty("user.home"), "Library", "Application Support", "Solangelo", "Blob");
            File path2 = new File(path + "/" + addpath);
            path2.mkdirs();
            File txt = new File(path2 + "/"+name+".sexinthecorridor");
            return txt;
        } else if (osname.contains("Window")) {
            Path path = Path.of(System.getProperty("user.home"), "AppData", "Solangelo", "Blob");
            File path2 = new File(path + "/" + addpath);
            path2.mkdirs();
            File txt = new File(path2 + "/"+name+".sexinthecorridor");
            return txt;
        }
        return null;
    }

    public static boolean CheckForSaveFile(int saveNumber) {
        File txtFile = getEncryptedByOS("saves", "savedata"+saveNumber);
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
            File eFile = getEncryptedByOS("saves", "savedata"+number);
            PrintWriter pw = new PrintWriter(txtFile);
            pw.println(name);
            pw.println((int)HUD.HEALTH);
            pw.println(HUD.getStaticScore());
            pw.println(HUD.getStaticLevel());
            pw.println(Game.getCurrentGameStateToInt());
            pw.println(Game.regen);
            pw.println(handler.getPlayer().getX());
            pw.println(handler.getPlayer().getY());
            pw.close();
            encrypt(key, txtFile, eFile);
            CreateInfoFile();
        } catch (FileNotFoundException | CryptoException e) {
            e.printStackTrace();
        }
        return number;
    }

    public static void OverwriteSaveFile(String name, int number) {
        try {
            File txtFile = getFileByOS("saves", "savedata"+number);
            File eFile = getEncryptedByOS("saves", "savedata"+number);
            if (eFile.exists()) decrypt(key, eFile, txtFile);
            PrintWriter pw = new PrintWriter(txtFile);
            pw.println(name);
            pw.println((int)HUD.HEALTH);
            pw.println(HUD.getStaticScore());
            pw.println(HUD.getStaticLevel());
            pw.println(Game.getCurrentGameStateToInt());
            pw.println(Game.regen);
            pw.println(handler.getPlayer().getX());
            pw.println(handler.getPlayer().getY());
            pw.close();
            encrypt(key, txtFile, eFile);
        } catch (FileNotFoundException | CryptoException e) {
            e.printStackTrace();
        }
    }

    public static void CreateInfoFile() {
        try {
            File txtFile = getFileByOS("data","info");
            File eFile = getEncryptedByOS("data", "info");
            PrintWriter pw = new PrintWriter(txtFile);
            pw.println(saveAmount);
            pw.close();
            encrypt(key, txtFile, eFile);
        } catch (FileNotFoundException | CryptoException e) {
            e.printStackTrace();
        }
    }

    public static void CreateSettingsFile() {
        try {
            File txtFile = getFileByOS("data","settings");
            File eFile = getEncryptedByOS("data","settings");
            PrintWriter pw = new PrintWriter(txtFile);
            pw.println(Game.ARROWKEYS);
            pw.println(Game.scrollDirection);
            pw.close();
            encrypt(key, txtFile, eFile);
        } catch (FileNotFoundException | CryptoException e) {
            e.printStackTrace();
        }
    }

    public static void CreateAchievementsFile() {
        try {
            File txtFile = getFileByOS("data","achievements");
            File eFile = getEncryptedByOS("data","achievements");
            PrintWriter pw = new PrintWriter(txtFile);
            pw.write(String.valueOf(Game.boss1Killed));
            pw.close();
            encrypt(key, txtFile, eFile);
        } catch (FileNotFoundException | CryptoException e) {
            e.printStackTrace();
        }
    }

    public static void ReadFromSaveFile(int saveNumber) {
        File txtFile = getFileByOS("saves","savedata"+saveNumber);
        File eFile = getEncryptedByOS("saves", "savedata"+saveNumber);
        try{
            if (!txtFile.exists()) {
                if (eFile.exists()) decrypt(key, eFile, txtFile);
            }
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

            encrypt(key, txtFile, eFile);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String ReadFromSaveFileName(int saveNumber) {
        File txtFile = getFileByOS("saves", "savedata"+saveNumber);
        File eFile = getEncryptedByOS("saves", "savedata"+saveNumber);
        try{
            if (eFile.exists()) decrypt(key, eFile, txtFile);
            BufferedReader br = new BufferedReader(new FileReader(txtFile));
            String string = br.readLine();
            encrypt(key, txtFile, eFile);
            return string;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Float ReadFromSaveFileHealth(int saveNumber) {
        File txtFile = getFileByOS("saves","savedata"+saveNumber);
        File eFile = getEncryptedByOS("saves", "savedata"+saveNumber);
        try{
            if (eFile.exists()) decrypt(key, eFile, txtFile);
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

            br.readLine();
            health = Integer.parseInt(br.readLine());

            br.close();
            encrypt(key, txtFile, eFile);
            return health;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer ReadFromSaveFileScore(int saveNumber) {
        File txtFile = getFileByOS("saves","savedata"+saveNumber);
        File eFile = getEncryptedByOS("saves", "savedata"+saveNumber);
        try{
            if (eFile.exists()) decrypt(key, eFile, txtFile);
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

            br.readLine();
            br.readLine();
            score = Integer.parseInt(br.readLine());

            br.close();
            encrypt(key, txtFile, eFile);
            return score;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer ReadFromSaveFileLevel(int saveNumber) {
        File txtFile = getFileByOS("saves","savedata"+saveNumber);
        File eFile = getEncryptedByOS("saves", "savedata"+saveNumber);
        try{
            if (eFile.exists()) decrypt(key, eFile, txtFile);
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

            br.readLine();
            br.readLine();
            br.readLine();
            level = Integer.parseInt(br.readLine());
            br.close();

            encrypt(key, txtFile, eFile);
            return level;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer ReadFromSaveFileState(int saveNumber) {
        File txtFile = getFileByOS("saves","savedata"+saveNumber);
        File eFile = getEncryptedByOS("saves", "savedata"+saveNumber);
        try{
            if (eFile.exists()) decrypt(key, eFile, txtFile);
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            state = Integer.parseInt(br.readLine());

            br.close();
            encrypt(key, txtFile, eFile);
            return state;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static float ReadFromSaveFilePlayerX(int saveNumber) {
        File txtFile = getFileByOS("saves","savedata"+saveNumber);
        File eFile = getEncryptedByOS("saves", "savedata"+saveNumber);
        try{
            if (eFile.exists()) decrypt(key, eFile, txtFile);
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            float x = Float.parseFloat(br.readLine());

            br.close();
            encrypt(key, txtFile, eFile);
            return x;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static float ReadFromSaveFilePlayerY(int saveNumber) {
        File txtFile = getFileByOS("saves","savedata"+saveNumber);
        File eFile = getEncryptedByOS("saves", "savedata"+saveNumber);
        try{
            if (eFile.exists()) decrypt(key, eFile, txtFile);
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            br.readLine();
            float y = Float.parseFloat(br.readLine());

            br.close();
            encrypt(key, txtFile, eFile);
            return y;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void ReadOnLoad() {
        File txtFilea = getFileByOS("data","achievements");
        File txtFiles = getFileByOS("data","settings");
        File txtFilei = getFileByOS("data","info");
        File eFilea = getEncryptedByOS("data","achievements");
        File eFiles = getEncryptedByOS("data","settings");
        File eFilei = getEncryptedByOS("data","info");
        if (eFilea.exists() && eFilei.exists() && eFiles.exists()) {
            try {
                decrypt(key, eFilea, txtFilea);
                decrypt(key, eFilei, txtFilei);
                decrypt(key, eFiles, txtFiles);

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

                encrypt(key, txtFilea, eFilea);
                encrypt(key, txtFilei, eFilei);
                encrypt(key, txtFiles, eFiles);
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

    public static void DeleteEncryptedFile(String addpath, String name) {
        File txtFile = getEncryptedByOS(addpath, name);
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

    public static void encrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    public static void decrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }

    private static void doCrypto(int cipherMode, String key, File inputFile, File outputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();
            inputFile.delete();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }
}
