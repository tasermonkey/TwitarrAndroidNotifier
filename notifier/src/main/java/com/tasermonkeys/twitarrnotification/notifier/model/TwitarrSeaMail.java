package com.tasermonkeys.twitarrnotification.notifier.model;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.tasermonkeys.twitarrnotification.notifier.IsoDateFormatter;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class TwitarrSeaMail extends TwitarrModel {
    private final String subject;
    private final Date timestamp;
    private final List<TwitarrGuiUser> users;
    private final String numberOfMessages;

    public TwitarrSeaMail(String id, String subject, Date timestamp, List<TwitarrGuiUser> users, String numberOfMessages) {
        super(id);
        this.subject = subject;
        this.timestamp = timestamp;
        this.users = users;
        this.numberOfMessages = numberOfMessages;
    }

    public String getSubject() {
        return subject;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public List<TwitarrGuiUser> getUsers() {
        return users;
    }

    public String getNumberOfMessages() {
        return numberOfMessages;
    }

    public static TwitarrSeaMail fromJsonObject(JSONObject input) {
        try {
            List<TwitarrGuiUser> users = jsonArrayOfObjectIterables(input.getJSONArray("users")).transform(
                    new Function<JSONObject, TwitarrGuiUser>() {
                        @Override
                        public TwitarrGuiUser apply(JSONObject guiUser) {
                            return TwitarrGuiUser.fromJsonObject(guiUser);
                        }
                    }
            ).toList();
            return new TwitarrSeaMail(input.getString("id"),
                    input.getString("subject"), IsoDateFormatter.parseDate(input.getString("timestamp")),
                    users, input.getString("messages"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("subject", subject)
                .add("timestamp", timestamp)
                .add("users", users)
                .add("numberOfMessages", numberOfMessages)
                .toString();
    }
}
