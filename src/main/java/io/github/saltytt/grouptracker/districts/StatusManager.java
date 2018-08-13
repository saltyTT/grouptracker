package io.github.saltytt.grouptracker.districts;

import io.github.saltytt.grouptracker.settings.Settings;
import io.github.saltytt.grouptracker.utilities.NetworkUtils;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.InetSocketAddress;
import java.net.Socket;

public class StatusManager {

    protected static StatusManager standard = new StatusManager().init();

    public boolean loginAPI = false;
    public String loginMsg = "";
    public boolean gameserver = false;
    public boolean website = false;

    private StatusManager init() { return this; }

    private boolean checkLoginAPI() {
        RequestBody header = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", Settings.testUser)
                .addFormDataPart("password", Settings.testPass)
                .addFormDataPart("User-Agent", "Finest Tracker")
                .build();
        String res = NetworkUtils.postResponseFromURL("https://corporateclash.net/api/v1/login", header);
        if (res == null) {
            loginMsg = "The Login API is offline";
            return false;
        }
        try {
            JSONObject r = (JSONObject) (new JSONParser().parse(res));
            if ((boolean) r.get("status")) return true;
            switch ((int) ((long) r.get("reason"))) {
                case 1004:
                    loginMsg = (String) r.get("friendlyreason");
                    return false;
                default:
                    loginMsg = "Bot error. Login API may be up";
                    return false;
            }

        } catch (ParseException e) {
            return false;
        }
    }

    private boolean checkWebsite() {
        return NetworkUtils.getResponseFromURL("https://corporateclash.net/") != null;
    }

    private boolean checkGameserver() {
        try {
            InetSocketAddress sa = new InetSocketAddress("gs.corporateclash.net", 1888);
            Socket ss = new Socket();
            ss.connect(sa, 1000);
            ss.close();
            return true;
        } catch(Exception e) { return false;}
    }

    protected void checkStatus() {
        loginAPI = checkLoginAPI();
        gameserver = checkGameserver();
        website = checkWebsite();
    }

}
