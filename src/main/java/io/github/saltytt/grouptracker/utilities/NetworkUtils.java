package io.github.saltytt.grouptracker.utilities;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class NetworkUtils {
    public static String getResponseFromURL(String url) {
        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = okHttpClient.newCall(request).execute();
            String res = (response.code() == 200) ? response.body().string() : null;
            //response.close();
            return res;
        } catch(IOException e) { return null; }
    }
}
