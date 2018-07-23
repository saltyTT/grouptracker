package io.github.saltytt.grouptracker.districts;

import io.github.saltytt.grouptracker.core.Bot;
import io.github.saltytt.grouptracker.utilities.ChannelUtils;
import io.github.saltytt.grouptracker.utilities.GeneralUtils;
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

    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public ArrayList<District> districts;
    private boolean loginUp = true;
    private boolean gameUp = true;
    public int loginDownCount = 0;
    private int totalPop;

    public DistrictManager() {
        Timer refreshTimer = new Timer();
        refreshTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                DistrictManager.standard.refreshDistricts();
            }
        },1000*20, 1000*20);
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
                        GeneralUtils.getCorrectCog((String) dist.get("cogs_attacking")),
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

        if (!gameUp && pop > 15) {
            loginUp = gameUp = true;
            notifyGameUp();
            loginDownCount = 0;
        } else if (!loginUp && pop > totalPop+20) {
            loginUp = gameUp = true;
            if (loginDownCount > 2) notifyGameUp();
            loginDownCount = 0;
        }

        if (loginDownCount > 0) loginUp = false;
        if (pop == 0) gameUp = false;

        Color col;
        if (pop < 1000) col = Color.green;
        else if (pop < 1250) col = Color.yellow;
        else if (pop < 1750) col = Color.orange;
        else col = Color.red;

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
        Color col;
        String message;
        String imgURL;

        if (!gameUp) {
            col = Color.red;
            message = "The game is currently offline";
            imgURL = "https://github.com/saltyTT/grouptracker/blob/master/src/main/resources/dc0a6320d907631d34e6655dff176295.png?raw=true";
        } else if (!StatusManager.standard.loginAPI) {
            col = Color.orange;
            message = String.format(":x: Login API is down");
            imgURL = "https://github.com/saltyTT/grouptracker/blob/master/src/main/resources/b04ecfe13d61a869b4c47a276b51b634.png?raw=true";
        } else if (loginDownCount > 2) {
            col = Color.orange;
            message = String.format(":warning: Authentication has been reported offline "+ loginDownCount + " times");
            imgURL = "https://github.com/saltyTT/grouptracker/blob/master/src/main/resources/b04ecfe13d61a869b4c47a276b51b634.png?raw=true";
        } else if (loginDownCount > 0) {
            col = Color.yellow;
            message = String.format(":warning: Authentication has been reported offline %s time" + (loginDownCount == 1 ? "" : "s"), loginDownCount);
            imgURL = "https://github.com/saltyTT/grouptracker/blob/master/src/main/resources/b04ecfe13d61a869b4c47a276b51b634.png?raw=true";
        } else {
            col = Color.green;
            message = ":white_check_mark: Authentication has not been reported offline";
            imgURL = "https://github.com/saltyTT/grouptracker/blob/master/src/main/resources/c6b26ba81f44b0c43697852e1e1d1420.png?raw=true";
        }

        StatusManager.standard.checkStatus();

        EmbedBuilder builder = new EmbedBuilder()
            .setTitle("GAME STATUS")
            .setThumbnail(imgURL)
            .addField("Login API", StatusManager.standard.loginAPI ? ":white_check_mark: Online" : ":x:" + StatusManager.standard.loginMsg, false)
            .addField("Gameserver", StatusManager.standard.gameserver ? ":white_check_mark: Online" : ":x: Offline" , false)
            .addField("Authentication", message, false)
            .setFooter("Authentication can not be checked automatically, and thus relies on user reports.\n",null)
            .setColor(col);

        return builder.build();
    }

    private void notifyGameUp() {
        for (Guild g : Bot.groupTracker.getGuilds()) {
            List<Role> roles = g.getRolesByName("gam",false);
            if (roles.isEmpty()) continue;
            Role toPing = roles.get(0);

            MessageChannel chan = ChannelUtils.getTextChannelFromName(g, "gam-up");
            if (chan != null) chan.sendMessage(toPing.getAsMention() + " gam probably up").queue();
        }
    }
}
