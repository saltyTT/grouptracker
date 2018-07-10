package io.github.saltytt.grouptracker.groups.constants.activities;

import io.github.saltytt.grouptracker.groups.Activity;
import io.github.saltytt.grouptracker.groups.constants.activities.bosses.CEOBoss;
import io.github.saltytt.grouptracker.groups.constants.activities.bosses.CJBoss;
import io.github.saltytt.grouptracker.groups.constants.activities.bosses.VPBoss;

public enum BossList {
    CEO(CEOBoss.activity),
    CFO(CEOBoss.activity),
    VP(VPBoss.activity),
    CJ(CJBoss.activity);

    public Activity activity;
    BossList(Activity activity) {
        this.activity = activity;
    }
}
