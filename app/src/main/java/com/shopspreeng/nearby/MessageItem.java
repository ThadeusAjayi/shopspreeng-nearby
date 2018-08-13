package com.shopspreeng.nearby;

/**
 * Created by Thadeus-APMIS on 8/12/2018.
 */

public class MessageItem {

    private String message;
    private long time;

    public MessageItem(String message, long time) {
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "MessageItem{" +
                "message='" + message + '\'' +
                ", time=" + time +
                '}';
    }
}
