package io.github.saltytt.grouptracker;

import io.github.saltytt.grouptracker.core.Bot;
import io.github.saltytt.grouptracker.settings.Database;
import io.github.saltytt.grouptracker.settings.Settings;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Main {

    public static Bot bot;

    private static final String JAR_PATH = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    private static final String TEST_PATH = "/Users/adam/Desktop/dt/Other/discord/Bots/grouptrackerconfig";
    public static String FILE_PATH;

    public static void main(String[] args) {
        FILE_PATH = TEST_PATH;//decodePath();
        // swap paths before building jar
        Settings.load();
        Database.init();

        bot = new Bot(Settings.token, Settings.prefix, Settings.aList);
    }

    private static String decodePath() {
        String decoded = null;
        try { decoded =  URLDecoder.decode(JAR_PATH, "UTF-8"); }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.exit(0);
        }
        decoded = (new File(decoded)).getParentFile().getPath();
        return decoded;
    }
}
