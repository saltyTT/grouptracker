package io.github.saltytt.grouptracker.utilities;

import io.github.saltytt.grouptracker.Main;
import io.github.saltytt.grouptracker.core.Bot;
import io.github.saltytt.grouptracker.districts.District;
import io.github.saltytt.grouptracker.districts.DistrictManager;
import io.github.saltytt.grouptracker.groups.Group;
import io.github.saltytt.grouptracker.groups.GroupManager;
import io.github.saltytt.grouptracker.settings.Settings;

import java.util.ArrayList;

public class GeneralUtils {
    private static String[] stringVals = {"one", "two", "three", "four", "five"};

    public static String indexToString(int index) {
        return stringVals[index];
    }

    public static void restart(boolean shutdown) {
        ArrayList<Group> groups = GroupManager.standard.getGroups();
        for (Group group : groups) group.safeDisband();
        groups.removeAll(groups);
        Main.groupTracker.groupTracker.shutdown();
        if (shutdown) System.exit(0);
        try {
            Thread.sleep(2000);
            Main.groupTracker = new Bot(Settings.getBotToken());
            DistrictManager.standard = new DistrictManager();
            DistrictManager.standard.refreshDistricts();
            GroupManager.standard = new GroupManager();
            GroupManager.standard.init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
