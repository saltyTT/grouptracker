package io.github.saltytt.grouptracker.groups.constants.activities.bosses;

import io.github.saltytt.grouptracker.groups.Activity;

public class CFOBoss extends Activity {
    public final static Activity activity = new CFOBoss("Chief Financial Officer", 8);

    private CFOBoss(String name, int maxToons) {
        this.name = name;
        this.maxToons = maxToons;
        this.location = "Cashbot HQ";
    }
}
