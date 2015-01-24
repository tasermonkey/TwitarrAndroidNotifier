package com.tasermonkeys.twitarrnotification.notifier.alarmservice;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class JsonSchedulingService extends IntentService {
    public static final String TAG = "TSS";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    public JsonSchedulingService() {
        super("JsonSchedulingService");
    }

    public abstract void handleJsonWork(JSONObject jsonObject);
    public abstract String getServiceUrl();
    protected abstract void loadConfiguration(Intent intent);

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Log.i(TAG, "JSS:onHandleIntent");
            loadConfiguration(intent);
            String result = loadFromNetwork(getServiceUrl());
            handleJsonWork(new JSONObject(result));
        } catch (IOException e) {
            Log.i(TAG, "Error loading from network", e);
        } catch (JSONException e) {
            Log.i(TAG, "Error parsing json object", e);
        } finally {
            // Release the wake lock provided by the BroadcastReceiver.
            AlarmReceiver.completeWakefulIntent(intent);
        }
    }

    /** Given a URL string, initiate a fetch operation. */
    private String loadFromNetwork(String urlString) throws IOException {
        InputStream stream = null;
        String str ="";

        try {
            stream = downloadUrl(urlString);
            str = readIt(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return str;
    }

    /**
     * Given a string representation of a URL, sets up a connection and gets
     * an input stream.
     * @param urlString A string representation of a URL.
     * @return An InputStream retrieved from a successful HttpURLConnection.
     * @throws IOException
     */
    private InputStream downloadUrl(String urlString) throws IOException {

        java.net.URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Start the query
        conn.connect();
        if ( conn.getResponseCode() / 100 != 2 ) {
            throw new IOException("Invalid response code from the server: " + conn.getResponseCode() + ": " + conn.getResponseMessage());
        }
        return conn.getInputStream();
    }
    /**
     * Reads an InputStream and converts it to a String.
     * @param stream InputStream containing HTML from www.google.com.
     * @return String version of InputStream.
     * @throws IOException
     */
    private String readIt(InputStream stream) throws IOException {

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        for(String line = reader.readLine(); line != null; line = reader.readLine())
            builder.append(line);
        reader.close();
        return builder.toString();
    }
}
