package com.tasermonkeys.twitarrnotification.notifier.model;

import com.google.common.base.MoreObjects;
import com.google.common.collect.FluentIterable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;

public class TwitarrModel {
    private final String id;

    public TwitarrModel(String id) {
        assert(id != null);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TwitarrModel that = (TwitarrModel) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public static FluentIterable<JSONObject> jsonArrayOfObjectIterables(final JSONArray arr) {
        return new FluentIterable<JSONObject>() {
            @Override
            public Iterator<JSONObject> iterator() {
                return new Iterator<JSONObject>() {
                    int pos = 0;

                    @Override
                    public boolean hasNext() {
                        return pos < arr.length();
                    }

                    @Override
                    public JSONObject next() {
                        try {
                            return arr.getJSONObject(pos);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        } finally {
                            pos++;
                        }
                    }

                    @Override
                    public void remove() {
                        throw new IllegalStateException("Can not remove items with this method");
                    }
                };
            }
        };
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .toString();
    }
}
