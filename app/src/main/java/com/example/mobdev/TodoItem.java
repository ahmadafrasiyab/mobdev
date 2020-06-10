package com.example.mobdev;

public class TodoItem {
    public String uid;
    public String name;
    public boolean liked;
    public String lat;
    public String longitude;
    public String imageUrl;

    public TodoItem() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public TodoItem(String uid, String name, boolean liked, String lat, String longitude, String imageUrl) {
        this.uid = uid;
        this.name = name;
        this.liked = liked;
        this.lat = lat;
        this.longitude = longitude;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean getLiked() {
        return liked;
    }

    public String getUid() {
        return uid;
    }

    public void setLike(boolean b) {
        this.liked = b;
    }
}
