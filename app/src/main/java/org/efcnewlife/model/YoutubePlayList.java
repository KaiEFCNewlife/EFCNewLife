package org.efcnewlife.model;

/**
 * Created by kaiwang on 2016-12-20.
 */

public class YoutubePlayList {
    private int id;
    private String url;

    public YoutubePlayList(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return ""+id+": "+url;
    }
}
