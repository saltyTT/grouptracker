package io.github.saltytt.grouptracker.utilities;

import io.github.saltytt.grouptracker.Main;
import io.github.saltytt.grouptracker.core.Bot;
import io.github.saltytt.grouptracker.districts.DistrictManager;
import io.github.saltytt.grouptracker.groups.Group;
import io.github.saltytt.grouptracker.groups.GroupManager;
import io.github.saltytt.grouptracker.settings.Settings;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;
import java.util.HashMap;

public class GeneralUtils {
    private static String[] stringVals = {"one", "two", "three", "four", "five"};

    private static HashMap<String, String> cogMap = new HashMap<>();
    private static String[][] cogList = {
            { "Cold Caller", "Telemarketer", "Name Dropper", "Glad Hander", "Mover and Shaker", "Two Face", "The Mingler", "Mr. Hollywood" },
            { "Short Change", "Penny Pincher", "Tightwad", "Bean Counter", "Number Cruncher", "Money Bags", "Loan Shark", "Robber Baron" },
            { "Bottom Feeder", "Bloodsucker", "Double Talker", "Ambulance Chaser", "Backstabber", "Spin Doctor", "Legal Eagle", "Big Wig" },
            { "Flunky", "Pencil Pusher", "Yesman", "Micromanager", "Downsizer", "Head Hunter", "Corporate Raider", "The Big Cheese" },
            { "Con Artists", "Connoisseur", "The Swindler", "Middleman", "Toxic Manager", "Magnate", "Big Fish", "Head Honcho" },
    };

    public static String indexToString(int index) {
        return stringVals[index];
    }

    static {
        for(int i = 0, j = 4; i < cogList.length; i++, j=i-1) {
            for (int k = 0; k < cogList[i].length; k++)
                cogMap.put(cogList[j][k], cogList[i][k]);
        }
    }

    public static String getCorrectCog(String from) {
        return cogMap.get(from);
    }

    public static void restart(boolean shutdown) {

        ArrayList<Group> groups = GroupManager.standard.getGroups();
        for (Group group : groups) group.safeDisband();
        groups.removeAll(groups);

        Main.groupTracker.groupTracker.shutdown();
        if (shutdown) System.exit(0);
        try {
            Thread.sleep(2000);
            Main.groupTracker = new Bot(Settings.token, Settings.prefix, Settings.aList);
            DistrictManager.standard = new DistrictManager();
            DistrictManager.standard.refreshDistricts();
            GroupManager.standard = new GroupManager();
            GroupManager.standard.init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean isBotOwner(User u) {
        return Main.groupTracker.adminList.contains(u.getId());
    }
}
