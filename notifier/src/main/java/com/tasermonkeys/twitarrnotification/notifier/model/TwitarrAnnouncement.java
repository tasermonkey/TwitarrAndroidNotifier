package com.tasermonkeys.twitarrnotification.notifier.model;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.tasermonkeys.twitarrnotification.notifier.IsoDateFormatter;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class TwitarrAnnouncement extends TwitarrModel {
    private final String author;
    private final String displayName;
    private final String text;
    private final Date timestamp;


    public TwitarrAnnouncement(String id, String author, String displayName, String text, Date timestamp) {
        super(id);
        this.author = author;
        this.displayName = displayName;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getAuthor() {
        return author;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getText() {
        return text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public static TwitarrAnnouncement fromJsonObject(JSONObject input) {
        try {
            return new TwitarrAnnouncement(input.getString("id"),
                    input.getString("author"), input.getString("display_name"), input.getString("text"),
                    IsoDateFormatter.parseDate(input.getString("timestamp")));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("author", author)
                .add("displayName", displayName)
                .add("text", text)
                .add("timestamp", timestamp)
                .toString();
    }
}
