package com.amazon.geo.importer.tigerline.addrfeat;

import com.amazon.geo.importer.tigerline.TLShapefileRecord;

public class AddrfeatRecord implements TLShapefileRecord {
    private double latitude;
    private double longitude;
    private String fullname;
    private String leftFromHouseNumber;
    private String leftToHouseNumber;
    private String rightFromHouseNumber;
    private String rightToHouseNumber;
    private String zipl;
    private String zipr;
    private String plus4l;
    private String plus4r;
    private String city;
    private String county;
    private String state;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLeftFromHouseNumber() {
        return leftFromHouseNumber;
    }

    public void setLeftFromHouseNumber(String leftFromHouseNumber) {
        this.leftFromHouseNumber = leftFromHouseNumber;
    }

    public String getLeftToHouseNumber() {
        return leftToHouseNumber;
    }

    public void setLeftToHouseNumber(String leftToHouseNumber) {
        this.leftToHouseNumber = leftToHouseNumber;
    }

    public String getRightFromHouseNumber() {
        return rightFromHouseNumber;
    }

    public void setRightFromHouseNumber(String rightFromHouseNumber) {
        this.rightFromHouseNumber = rightFromHouseNumber;
    }

    public String getRightToHouseNumber() {
        return rightToHouseNumber;
    }

    public void setRightToHouseNumber(String rightToHouseNumber) {
        this.rightToHouseNumber = rightToHouseNumber;
    }

    public String getZipl() {
        return zipl;
    }

    public void setZipl(String zipl) {
        this.zipl = zipl;
    }

    public String getZipr() {
        return zipr;
    }

    public void setZipr(String zipr) {
        this.zipr = zipr;
    }

    public String getPlus4l() {
        return plus4l;
    }

    public void setPlus4l(String plus4l) {
        this.plus4l = plus4l;
    }

    public String getPlus4r() {
        return plus4r;
    }

    public void setPlus4r(String plus4r) {
        this.plus4r = plus4r;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "AddrfeatRecord [latitude=" + latitude + ", longitude=" + longitude + ", fullname=" + fullname + ", leftFromHouseNumber="
                + leftFromHouseNumber + ", leftToHouseNumber=" + leftToHouseNumber + ", rightFromHouseNumber=" + rightFromHouseNumber
                + ", rightToHouseNumber=" + rightToHouseNumber + ", zipl=" + zipl + ", zipr=" + zipr + ", plus4l=" + plus4l + ", plus4r="
                + plus4r + ", city=" + city + ", county=" + county + ", state=" + state + "]";
    }

}
