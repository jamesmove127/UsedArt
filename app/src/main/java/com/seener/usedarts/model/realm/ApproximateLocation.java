package com.seener.usedarts.model.realm;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class ApproximateLocation extends RealmObject {

    @PrimaryKey
    private long locationId;

    @Required
    private String location;

    private long latitude;
    private long longitude;
    private String nation;
    private String state;
    private String city;
    private String street;
    private String postCode;
    private Date setupDate;

    public ApproximateLocation() {
    }

    public ApproximateLocation(long locationId, String location, long latitude, long longitude, String nation, String state, String city, String street, String postCode, Date setupDate) {
        this.locationId = locationId;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nation = nation;
        this.state = state;
        this.city = city;
        this.street = street;
        this.postCode = postCode;
        this.setupDate = setupDate;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Date getSetupDate() {
        return setupDate;
    }

    public void setSetupDate(Date setupDate) {
        this.setupDate = setupDate;
    }
}
