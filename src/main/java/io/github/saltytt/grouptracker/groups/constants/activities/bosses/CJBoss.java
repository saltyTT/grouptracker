package io.github.saltytt.grouptracker.groups.constants.activities.bosses;

import io.github.saltytt.grouptracker.groups.Activity;

public class CJBoss extends Activity {
    public final static Activity activity = new CJBoss("Chief Justice", 8);

    private CJBoss(String name, int maxToons) {
        this.name = name;
        this.maxToons = maxToons;
        this.location = "Lawbot HQ";
    }
}
