package io.github.saltytt.grouptracker.districts;

import io.github.saltytt.grouptracker.core.Bot;
import io.github.saltytt.grouptracker.utilities.ChannelUtils;
import io.github.saltytt.grouptracker.utilities.NetworkUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class DistrictManager {
    private static String[] staticDistricts = {
        "Jellybean Junction",
        "Rake River",
        "Hypno Harbor",
        "Splatville",
        "Quicksand Quarry",
        "Piano Peaks",
        "Lazy Lagoon",
        "Feather Field",
        "High-Dive Hills",
        "Boulderbury",
        "Marble Mountains",
        "Jollywood",
        "Whistle Woods",
        "Seltzer Summit",
        "Geyser Gulch",
        "Kazoo Kanyon",
        "Opera Oasis",
        "Comical Canyon",
        "Bamboozle Bay",
        "Foghorn Falls",
        "Bugle Bay"
    };

    private String districtURL = "https://corporateclash.net/api/v1/districts/";
    public static DistrictManager standard = new DistrictManager();
    final JSONParser jsonParser = new JSONParser();
    final String TFN_ID = "384650260439629836";

    DateFormat dateFormat = new SimpleDateFormat("HH:mm");

    public ArrayList<District> districts;
    private boolean authUp = true;
    public int authDownCount = 0;
    private int totalPop;
    private Role role;

    public DistrictManager() {
        Timer refreshTimer = new Timer();
        refreshTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                DistrictManager.standard.refreshDistricts();
            }
        },1000*20, 1000*20);

        role = Bot.groupTracker.getGuildById(TFN_ID).getRolesByName("gam",false).get(0);
    }


    public void refreshDistricts() {
        String res = NetworkUtils.getResponseFromURL(districtURL);
        if (res==null) return;

        Object object;

        try {

            object = jsonParser.parse(res);
            final JSONArray dis = (JSONArray) object;

            ArrayList<District> temp = new ArrayList<>();

            for (Object d : dis) {
                JSONObject dist = (JSONObject) d;
                temp.add(
                    new District(
                        (String) dist.get("name"),
                        (boolean) dist.get("online"),
                        (long) dist.get("population"),
                        (boolean) dist.get("invasion_online"),
                        (long) dist.get("last_update"),
                        (String) dist.get("cogs_attacking"),
                        (long) dist.get("count_defeated"),
                        (long) dist.get("count_total"),
                        (long) dist.get("remaining_time")
                    )
                );
            }
            districts = temp;
            postDistricts();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String[] getDistrictNames() {
        if (districts == null) return staticDistricts;
        String[] dists = new String[districts.size()];
        int count = 0;
        for(District d : districts) dists[count++] = d.name;
        return dists;
    }

    public void postDistricts() {
        MessageEmbed districtInfo = buildMessage();
        MessageEmbed authInfo = buildAuth();

        for (Guild g : Bot.groupTracker.getGuilds()) {
            MessageChannel chan = ChannelUtils.getTextChannelFromName(g, "district-info");
            if (chan == null) continue;
            List<Message> msg = chan.getHistory().retrievePast(2).complete();
            if (msg.isEmpty()) {
                chan.sendMessage(authInfo).queue();
                chan.sendMessage(districtInfo).queue();
            } else {
                msg.get(1).editMessage(authInfo).queue();
                msg.get(0).editMessage(districtInfo).queue();
            }
        }
    }

    private MessageEmbed buildMessage() {

        int pop = 0;

        EmbedBuilder builder = new EmbedBuilder()
            .setTitle("Online Districts")
            .addBlankField(false);

        for (District d : districts) {
            if (!d.online || d.invasionOnline) continue;
            pop+=d.population;
            final String info = "Population: " + d.population;
            builder.addField(d.name, info, true);
        }

        builder.addBlankField(false);

        for (District d : districts) {
            if (!d.online || !d.invasionOnline) continue;
            pop+=d.population;
            final String info =
                    "Population: " + d.population + "\n" +
                    "Invasion: " + d.cogsAttacking + "\n" +
                    "Time Left: " + d.remainingTime/60 + ":" + (d.remainingTime%60 > 9 ? "" : "0") + d.remainingTime%60;
            builder.addField(d.name, info, true);
        }

        if (pop > totalPop+20 && !authUp) {
            authUp = true;
            if (authDownCount > 2) pingNoobs();
            authDownCount = 0;
        }

        if (authDownCount > 0) authUp = false;

        Color col;
        if (pop < 800) col = Color.green;
        else if (pop < 1200) col = Color.yellow;
        else col = Color.orange;

        (builder)
            .addBlankField(false)
            .addField("Total Population", String.valueOf(pop), false)
            .addBlankField(false)
            .setFooter("Last Update: " + dateFormat.format(new Date()), null)
            .setColor(col);

        totalPop = pop;

        return builder.build();
    }

    private MessageEmbed buildAuth() {

        Color col = authUp? Color.green : Color.red;

        String message = authUp?
                "Log in servers are likely online"
                :
                String.format("Log in servers have been reported down %s time" + (authDownCount==1 ? "" : "s"), authDownCount);

        EmbedBuilder builder = new EmbedBuilder()
            .setTitle("LOG IN STATUS")
            .addField("", message, false)
            .setColor(col);

        return builder.build();
    }

    private void pingNoobs() {
        try {
            ChannelUtils.getTextChannelFromName(Bot.groupTracker.getGuildById(TFN_ID), "gam-up")
                    .sendMessage(role.getAsMention() + " gam probably up").queue();
        } catch (NullPointerException e) { e.printStackTrace(); }
    }


}
