package io.github.saltytt.grouptracker.groups.constants.activities.facilities;

import io.github.saltytt.grouptracker.groups.Activity;
import io.github.saltytt.grouptracker.groups.constants.activities.Facility;

public class GolfFacility extends Facility {
    public final static Activity activity = new GolfFacility("Golf Course", 4);

    private GolfFacility(String name, int maxToons) {
        this.name = name;
        this.maxToons = maxToons;
        this.location = "Bossbot HQ";
    }

    @Override
    public String[] getLevels() {
        String[] levels = {
                "Front 3",
                "Middle 6",
                "Back 9"
        };
        return levels;
    }
}
