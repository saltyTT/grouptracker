package io.github.saltytt.grouptracker.utilities;

import okhttp3.*;

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
            response.close();
            return res;
        } catch (IOException e) { return null; }
    }

    public static String postResponseFromURL(String url, RequestBody header) {
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .post(header)
                    .build();

            Response response = client.newCall(request).execute();

            String res = (response.code() == 200) ? response.body().string() : null;

            return res;

        } catch(IOException e) { return null; }
    }
}
