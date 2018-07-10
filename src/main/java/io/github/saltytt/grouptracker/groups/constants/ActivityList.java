package io.github.saltytt.grouptracker.groups.constants;

import io.github.saltytt.grouptracker.groups.Activity;
import io.github.saltytt.grouptracker.groups.constants.activities.Boss;
import io.github.saltytt.grouptracker.groups.constants.activities.Facility;

public enum ActivityList {
    BOSS(Boss.activity),
    FACILITY(Facility.activity);

    ActivityList(Activity activity) {
        this.activity = activity;
    }
    public Activity activity;

}
