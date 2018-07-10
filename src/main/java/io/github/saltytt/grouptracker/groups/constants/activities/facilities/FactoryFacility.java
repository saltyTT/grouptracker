package io.github.saltytt.grouptracker.groups.constants.activities.facilities;

import io.github.saltytt.grouptracker.groups.Activity;
import io.github.saltytt.grouptracker.groups.constants.activities.Facility;

public class FactoryFacility extends Facility {
    public final static Activity activity = new FactoryFacility("Factory", 4);

    private FactoryFacility(String name, int maxToons) {
        this.name = name;
        this.maxToons = maxToons;
        this.location = "Sellbot HQ";
    }

    @Override
    public String[] getLevels() {
        String[] levels = {
                "Short Factory - Front",
                "Short Factory - Side",
                "Long Factory"
        };
        return levels;
    }
}
