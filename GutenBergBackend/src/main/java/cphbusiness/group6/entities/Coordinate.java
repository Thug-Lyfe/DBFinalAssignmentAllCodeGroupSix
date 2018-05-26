package cphbusiness.group6.entities;

import cphbusiness.group6.interfaces.entities.I_Coordinate;

public class Coordinate implements I_Coordinate
{
    private double Lat;
    private double lang;

    public Coordinate(double lat, double lang)
    {
        this.Lat = lat;
        this.lang = lang;
    }

    @Override
    public double getLang()
    {
        return this.lang;
    }

    @Override
    public double getLat()
    {
        return this.Lat;
    }

    public void setLat(double lat) { this.Lat = lat; }

    public void setLang(double lang)
    {
        this.lang = lang;
    }
}
