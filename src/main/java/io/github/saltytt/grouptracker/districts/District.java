package io.github.saltytt.grouptracker.districts;

public class District {
    String name;
    boolean online;
    long population;
    boolean invasionOnline;
    long lastUpdate;
    String cogsAttacking;
    long countDefeated;
    long countTotal;
    long remainingTime;

    public District(String name, boolean online, long population, boolean invasionOnline, long lastUpdate,
                    String cogsAttacking, long countDefeated, long countTotal, long remainingTime) {
        this.name = name;
        this.online = online;
        this.population = population;
        this.invasionOnline = invasionOnline;
        this.lastUpdate = lastUpdate;
        this.cogsAttacking = cogsAttacking;
        this.countDefeated = countDefeated;
        this.countTotal = countTotal;
        this.remainingTime = remainingTime;
    }

}
