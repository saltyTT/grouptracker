package io.github.saltytt.grouptracker.groups.constants.activities;

import io.github.saltytt.grouptracker.groups.Activity;
import io.github.saltytt.grouptracker.groups.constants.activities.facilities.FactoryFacility;
import io.github.saltytt.grouptracker.groups.constants.activities.facilities.GolfFacility;
import io.github.saltytt.grouptracker.groups.constants.activities.facilities.MintFacility;
import io.github.saltytt.grouptracker.groups.constants.activities.facilities.OfficeFacility;

public enum FacilityList {
    FACTORY(FactoryFacility.activity),
    MINT(MintFacility.activity),
    OFFICE(OfficeFacility.activity),
    GOLF(GolfFacility.activity);

    public Activity activity;
    FacilityList(Activity activity) {
        this.activity = activity;
    }
}
