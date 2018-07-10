package io.github.saltytt.grouptracker.groups.constants.activities.bosses;

import io.github.saltytt.grouptracker.groups.Activity;

public class VPBoss extends Activity {
    public final static Activity activity = new VPBoss("Vice President", 8);

    private VPBoss(String name, int maxToons) {
        this.name = name;
        this.maxToons = maxToons;
        this.location = "Sellbot HQ";
    }
}
