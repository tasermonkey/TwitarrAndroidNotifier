package com.tasermonkeys.twitarrnotification.notifier.model;

import android.util.Log;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.tasermonkeys.twitarrnotification.notifier.model.TwitarrModel.jsonArrayOfObjectIterables;

/**
 * Created by jstapleton on 15/1/20.
 */
public class TwitarrAlert {
    private final List<TwitarrAnnouncement> announcements;
    private final List<TwitarrMessage> mentions;
    private final List<TwitarrForumMessage> forurmMentions;
    private final List<TwitarrSeaMail> unreadSeamail;

    public TwitarrAlert(List<TwitarrAnnouncement> announcements, List<TwitarrMessage> mentions, List<TwitarrForumMessage> forurmMentions, List<TwitarrSeaMail> unreadSeamail) {
        this.announcements = announcements;
        this.mentions = mentions;
        this.forurmMentions = forurmMentions;
        this.unreadSeamail = unreadSeamail;
    }

    public List<TwitarrAnnouncement> getAnnouncements() {
        return announcements;
    }

    public List<TwitarrMessage> getMentions() {
        return mentions;
    }

    public List<TwitarrForumMessage> getForurmMentions() {
        return forurmMentions;
    }

    public List<TwitarrSeaMail> getUnreadSeamail() {
        return unreadSeamail;
    }

    public static TwitarrAlert fromJsonObject(JSONObject object) {

        try {
            List<TwitarrAnnouncement> announcements = jsonArrayOfObjectIterables(object.getJSONArray("announcements"))
                    .transform(new Function<JSONObject, TwitarrAnnouncement>() {
                        @Override
                        public TwitarrAnnouncement apply(JSONObject input) {
                            return TwitarrAnnouncement.fromJsonObject(input);
                        }
                    }).toList();
            List<TwitarrMessage> mentions = jsonArrayOfObjectIterables(object.getJSONArray("tweet_mentions"))
                    .transform(new Function<JSONObject, TwitarrMessage>() {
                        @Override
                        public TwitarrMessage apply(JSONObject input) {
                            return TwitarrMessage.fromJsonObject(input);
                        }
                    }).toList();
            List<TwitarrForumMessage> forum_mentions = jsonArrayOfObjectIterables(object.getJSONArray("forum_mentions"))
                    .transform(new Function<JSONObject, TwitarrForumMessage>() {
                        @Override
                        public TwitarrForumMessage apply(JSONObject input) {
                            return TwitarrForumMessage.fromJsonObject(input);
                        }
                    }).toList();
            List<TwitarrSeaMail> unread_seamail = jsonArrayOfObjectIterables(object.getJSONArray("unread_seamail"))
                    .transform(new Function<JSONObject, TwitarrSeaMail>() {
                        @Override
                        public TwitarrSeaMail apply(JSONObject input) {
                            return TwitarrSeaMail.fromJsonObject(input);
                        }
                    }).toList();
            return new TwitarrAlert(announcements, mentions, forum_mentions, unread_seamail);
        } catch (JSONException e) {
            Log.e("TWA", "Error parsing twitarr-alert json", e);
            return null;
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("announcements", announcements)
                .add("mentions", mentions)
                .add("forurmMentions", forurmMentions)
                .add("unreadSeamail", unreadSeamail).toString();
    }
}
