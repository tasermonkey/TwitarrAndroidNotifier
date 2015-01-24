package com.tasermonkeys.twitarrnotification.notifier;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import com.tasermonkeys.twitarrnotification.notifier.alarmservice.JsonSchedulingService;
import com.tasermonkeys.twitarrnotification.notifier.model.TwitarrAlert;
import com.tasermonkeys.twitarrnotification.notifier.model.TwitarrGuiUser;
import com.tasermonkeys.twitarrnotification.notifier.model.TwitarrSeaMail;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicInteger;

public class TwitarrNotificationService extends JsonSchedulingService {
    public static final String TAG = "TNS";
    public static AtomicInteger mNotId = new AtomicInteger(0);
    public static final String BASE_URL = "base_url";
    public static final String KEY = "user_key";
    private String base_url;
    private String key;


    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        this.base_url = sharedPrefs.getString(TwitarrNotificationService.BASE_URL, null);
        this.key = sharedPrefs.getString(TwitarrNotificationService.KEY, null);
    }

    @Override
    public void handleJsonWork(JSONObject jsonObject) {
        TwitarrAlert alert = TwitarrAlert.fromJsonObject(jsonObject);
        //Log.i(TAG, "work object => " + alert);

        NotificationManager mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        String fromUsers = Joiner.on(',').join(FluentIterable.from(alert.getUnreadSeamail()).transformAndConcat(
                new Function<TwitarrSeaMail, Iterable<String>>() {
                    @Override
                    public Iterable<String> apply(TwitarrSeaMail input) {
                        return Iterables.transform(input.getUsers(), new Function<TwitarrGuiUser, String>() {
                            @Override
                            public String apply(TwitarrGuiUser input) {
                                return input.smartDisplayName();
                            }
                        });
                    }
                }).toSortedSet(Ordering.natural()));

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("Unread Seamail: " + alert.getUnreadSeamail().size())
                        .setContentText(" from: " + fromUsers);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(mNotId.getAndAdd(1), mBuilder.build());
    }

    @Override
    public String getServiceUrl() {
        return base_url + "/api/v2/alerts?key=" + key + "&no_reset=true&skip_markup=true";
    }

    @Override
    protected void loadConfiguration(Intent intent) {
    }
}
