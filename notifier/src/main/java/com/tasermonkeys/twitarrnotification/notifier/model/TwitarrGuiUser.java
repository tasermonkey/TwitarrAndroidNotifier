package com.tasermonkeys.twitarrnotification.notifier.model;

import com.tasermonkeys.twitarrnotification.notifier.IsoDateFormatter;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jstapleton on 15/1/20.
 */
public class TwitarrGuiUser {
    private final String username;
    private final String display_name;

    public TwitarrGuiUser(String username, String display_name) {
        this.username = username;
        this.display_name = display_name;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public String smartDisplayName() {
        return display_name != null ? display_name : username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TwitarrGuiUser that = (TwitarrGuiUser) o;

        if (display_name != null ? !display_name.equals(that.display_name) : that.display_name != null) return false;
        if (!username.equals(that.username)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + (display_name != null ? display_name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if ( display_name != null && ! display_name.equals(username) )
            return display_name + "(@" + username + ")";
        else
            return "@" + username;
    }

    public static TwitarrGuiUser fromJsonObject(JSONObject input) {
        try {
            return new TwitarrGuiUser(input.getString("username"), input.getString("display_name"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
