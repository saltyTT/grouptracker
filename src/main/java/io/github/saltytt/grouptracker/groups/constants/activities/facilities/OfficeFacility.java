package io.github.saltytt.grouptracker.groups.constants.activities.facilities;

import io.github.saltytt.grouptracker.groups.Activity;
import io.github.saltytt.grouptracker.groups.constants.activities.Facility;

public class OfficeFacility extends Facility {
    public final static Activity activity = new OfficeFacility("Office", 4);

    private OfficeFacility(String name, int maxToons) {
        this.name = name;
        this.maxToons = maxToons;
        this.location = "Lawbot HQ";
    }

    @Override
    public String[] getLevels() {
        String[] levels = {
                "Office A",
                "Office B",
                "Office C",
                "Office D"
        };
        return levels;
    }
}
