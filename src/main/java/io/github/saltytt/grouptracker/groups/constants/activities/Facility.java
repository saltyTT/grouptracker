package io.github.saltytt.grouptracker.groups.constants.activities;

import io.github.saltytt.grouptracker.groups.Activity;

public class Facility extends Activity {
    public final static Activity activity = new Facility();

    public Facility() {
        this.name = "Facility";
    }

    public String[] getLevels() {
        return null;
    }
}
