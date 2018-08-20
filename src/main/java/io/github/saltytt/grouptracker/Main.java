package io.github.saltytt.grouptracker;

import io.github.saltytt.grouptracker.core.Bot;
import io.github.saltytt.grouptracker.settings.Database;
import io.github.saltytt.grouptracker.settings.Settings;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Main {

    public static Bot bot;

    public static String JAR_PATH = decodeJarPath();

    public static void main(String[] args) {
        Settings.load();
        Database.init();

        bot = new Bot(Settings.token, Settings.prefix, Settings.aList);
    }

    private static String decodeJarPath() {
        String decoded = null;
        try { decoded =  URLDecoder.decode(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8"); }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.exit(0);
        }
        decoded = (new File(decoded)).getParentFile().getPath();
        return decoded;
    }
}
