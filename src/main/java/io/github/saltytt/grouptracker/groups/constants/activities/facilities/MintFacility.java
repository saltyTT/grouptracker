package io.github.saltytt.grouptracker.groups.constants.activities.facilities;

import io.github.saltytt.grouptracker.groups.Activity;
import io.github.saltytt.grouptracker.groups.constants.activities.Facility;

public class MintFacility extends Facility {
    public final static Activity activity = new MintFacility("Mint", 4);

    private MintFacility(String name, int maxToons) {
        this.name = name;
        this.maxToons = maxToons;
        this.location = "Cashbot HQ";
    }

    @Override
    public String[] getLevels() {
        String[] levels = {
                "Coin Mint",
                "Dollar Mint",
                "Bullion Mint"
        };
        return levels;
    }
}
