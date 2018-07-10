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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DistrictManager {
    private String districtURL = "https://corporateclash.net/api/v1/districts/";
    public static DistrictManager standard = new DistrictManager();

    DateFormat dateFormat = new SimpleDateFormat("HH:mm");

    public ArrayList<District> districts;

    public void refreshDistricts() {
        String res = NetworkUtils.getResponseFromURL(districtURL);


        final JSONParser jsonParser = new JSONParser();
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
        String[] dists = new String[districts.size()];
        int count = 0;
        for(District d : districts) dists[count++] = d.name;
        return dists;
    }

    public void postDistricts() {
        MessageEmbed districtInfo = buildMessage();

        for (Guild g : Bot.groupTracker.getGuilds()) {
            MessageChannel chan = ChannelUtils.getTextChannelFromName(g, "district-info");
            if (chan == null) continue;
            List<Message> msg = chan.getHistory().retrievePast(1).complete();
            if (msg.isEmpty())
                chan.sendMessage(districtInfo).queue();
            else
                msg.get(0).editMessage(districtInfo).queue();
        }
    }

    private MessageEmbed buildMessage() {

        int totalPop = 0;

        EmbedBuilder builder = new EmbedBuilder()
            .setTitle("Online Districts")
            .addBlankField(false);

        for (District d : districts) {
            if (!d.online || d.invasionOnline) continue;
            totalPop+=d.population;
            final String info = "Population: " + d.population;
            builder.addField(d.name, info, true);
        }
        builder.addBlankField(false);

        for (District d : districts) {
            if (!d.online || !d.invasionOnline) continue;
            totalPop+=d.population;
            final String info =
                    "Population: " + d.population + "\n" +
                    "Invasion: " + d.cogsAttacking + "\n" +
                    "Time Left: " + d.remainingTime/60 + ":" + d.remainingTime%60;
            builder.addField(d.name, info, true);
        }

        (builder)
            .addBlankField(false)
            .addField("Total Population", String.valueOf(totalPop), false)
            .addBlankField(false)
            .setFooter("Last Update: "+dateFormat.format(new Date()), null);

        return builder.build();
    }


}
