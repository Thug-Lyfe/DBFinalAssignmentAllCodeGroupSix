package cphbusiness.group6.entities;

import cphbusiness.group6.interfaces.entities.I_City;
import cphbusiness.group6.interfaces.entities.I_Coordinate;

public class City implements I_City
{
    private String cityName;
    private I_Coordinate geoLocation;

    public City(String cityName, I_Coordinate geoLocation)
    {
        this.cityName = cityName;
        this.geoLocation = geoLocation;
    }

    @Override
    public I_Coordinate getGeoLocation()
    {
        return geoLocation;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public void setGeoLocation(I_Coordinate geoLocation)
    {
        this.geoLocation = geoLocation;
    }

}
