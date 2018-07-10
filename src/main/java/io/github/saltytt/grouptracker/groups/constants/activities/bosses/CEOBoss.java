package io.github.saltytt.grouptracker.groups.constants.activities.bosses;

import io.github.saltytt.grouptracker.groups.Activity;

public class CEOBoss extends Activity {
    public final static Activity activity = new CEOBoss("Chief Executive Officer", 8);

    private CEOBoss(String name, int maxToons) {
        this.name = name;
        this.maxToons = maxToons;
        this.location = "Bossbot HQ";
    }
}
