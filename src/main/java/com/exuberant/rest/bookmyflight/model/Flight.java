package com.exuberant.rest.bookmyflight.model;

import java.time.LocalDate;

/**
 * Created by rakesh on 23-Oct-2017.
 */
public class Flight {
    private int id;
    private String name;
    /*@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSz")
    private LocalDate date;*/
    private String time;
    private String from;
    private String to;
    private String imageUrl;
    private String onlineUrl;
    private Location location;

    public Flight() {
    }

    public Flight(int id, String name, LocalDate date, String time, String from, String to, String imageUrl, String onlineUrl, Location location) {
        this.id = id;
        this.name = name;
        //  this.date = date;
        this.time = time;
        this.from = from;
        this.to = to;
        this.imageUrl = imageUrl;
        this.onlineUrl = onlineUrl;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
*/
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOnlineUrl() {
        return onlineUrl;
    }

    public void setOnlineUrl(String onlineUrl) {
        this.onlineUrl = onlineUrl;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
