package com.tasermonkeys.twitarrnotification.notifier;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.tasermonkeys.twitarrnotification.notifier.alarmservice.AlarmReceiver;
import com.tasermonkeys.twitarrnotification.notifier.alarmservice.JsonSchedulingService;

public class TwitarrNotificationReceiver extends AlarmReceiver {
    private static final int SECONDS = 1000;
    public static final String POLLING_INTERVAL = "polling_interval";
    private long polling_interval = 20;

    public TwitarrNotificationReceiver() {
    }

    @Override
    public Class<? extends JsonSchedulingService> getSchedulingService() {
        return TwitarrNotificationService.class;
    }

    @Override
    public void configureSchedulingServiceIntent(Intent service) {
    }

    @Override
    public long pollingInterval() {
        return polling_interval;
    }

    @Override
    public void configureAlarm(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        polling_interval = Integer.parseInt(sharedPrefs.getString(POLLING_INTERVAL, "20")) * SECONDS;
    }
}
