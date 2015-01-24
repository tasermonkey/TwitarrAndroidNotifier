package com.tasermonkeys.twitarrnotification.notifier.model;

import com.google.common.base.MoreObjects;
import com.tasermonkeys.twitarrnotification.notifier.IsoDateFormatter;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;


public class TwitarrForumMessage extends TwitarrModel {
    private final String subject;
    private final String last_post_username;
    private final String last_post_displayname;
    private final Date timestamp;
    private final String numberOfPosts;

    public TwitarrForumMessage(String id, String subject, String last_post_username, String last_post_displayname, Date timestamp, String numberOfPosts) {
        super(id);
        this.subject = subject;
        this.last_post_username = last_post_username;
        this.last_post_displayname = last_post_displayname;
        this.timestamp = timestamp;
        this.numberOfPosts = numberOfPosts;
    }

    public String getSubject() {
        return subject;
    }

    public String getLast_post_username() {
        return last_post_username;
    }

    public String getLast_post_displayname() {
        return last_post_displayname;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getNumberOfPosts() {
        return numberOfPosts;
    }

    public static TwitarrForumMessage fromJsonObject(JSONObject input) {
        try {
            return new TwitarrForumMessage(input.getString("id"),
                    input.getString("subject"), input.getString("last_post_username"), input.getString("last_post_display_name"),
                    IsoDateFormatter.parseDate(input.getString("timestamp")), input.getString("posts"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("subject", subject)
                .add("last_post_username", last_post_username)
                .add("last_post_displayname", last_post_displayname)
                .add("timestamp", timestamp)
                .add("numberOfPosts", numberOfPosts)
                .toString();
    }
}
