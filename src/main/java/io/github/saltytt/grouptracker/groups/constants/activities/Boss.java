package io.github.saltytt.grouptracker.groups.constants.activities;

import io.github.saltytt.grouptracker.groups.Activity;

public class Boss extends Activity {
    public final static Activity activity = new Boss();

    private Boss() {
        this.name = "Boss";
    }

}
