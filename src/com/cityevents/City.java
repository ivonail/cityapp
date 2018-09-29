package com.cityevents;


/**
 *
 * @author Ivona
 */
public class City {

    private int id;
    private String cityName;
    private Double lat;
    private Double lon;

    public City(int id, String cityName, Double lat, Double lon) {
        this.id = id;
        this.cityName = cityName;
        this.lat = lat;
        this.lon = lon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

}
