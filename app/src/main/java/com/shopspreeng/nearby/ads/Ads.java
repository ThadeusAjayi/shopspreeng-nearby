package com.shopspreeng.nearby.ads;

/**
 * Created by Thadeus-APMIS on 8/25/2018.
 */

public class Ads {

    private long _id;
    private String adTitle;
    private String adBody;
    private int adImage;

    public Ads(String adTitle, String adBody, int adImage) {
        this.adTitle = adTitle;
        this.adBody = adBody;
        this.adImage = adImage;
    }

    public Ads(String adTitle, String adBody) {
        this.adTitle = adTitle;
        this.adBody = adBody;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public String getAdBody() {
        return adBody;
    }

    public void setAdBody(String adBody) {
        this.adBody = adBody;
    }

    public int getAdImage() {
        return adImage;
    }

    public void setAdImage(int adImage) {
        this.adImage = adImage;
    }

    @Override
    public String toString() {
        return "Ads{" +
                "_id=" + _id +
                ", adTitle='" + adTitle + '\'' +
                ", adBody='" + adBody + '\'' +
                ", adImage=" + adImage +
                '}';
    }
}
