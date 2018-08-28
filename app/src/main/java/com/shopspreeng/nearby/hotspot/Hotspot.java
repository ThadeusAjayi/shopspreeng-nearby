package com.shopspreeng.nearby.hotspot;

/**
 * Created by Thadeus-APMIS on 8/25/2018.
 */

public class Hotspot {

    private long _id;
    private String hotspotName;
    private String createdAt;
    private int hotspotMembers;

    public Hotspot(long _id, String hotspotName, String createdAt, int hotspotMembers) {
        this._id = _id;
        this.hotspotName = hotspotName;
        this.createdAt = createdAt;
        this.hotspotMembers = hotspotMembers;
    }

    public Hotspot(String hotspotName, String createdAt, int hotspotMembers) {
        this.hotspotName = hotspotName;
        this.createdAt = createdAt;
        this.hotspotMembers = hotspotMembers;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getHotspotName() {
        return hotspotName;
    }

    public void setHotspotName(String hotspotName) {
        this.hotspotName = hotspotName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getHotspotMembers() {
        return hotspotMembers;
    }

    public void setHotspotMembers(int hotspotMembers) {
        this.hotspotMembers = hotspotMembers;
    }

    @Override
    public String toString() {
        return "Hotspot{" +
                "_id=" + _id +
                ", hotspotName='" + hotspotName + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", hotspotMembers=" + hotspotMembers +
                '}';
    }
}
