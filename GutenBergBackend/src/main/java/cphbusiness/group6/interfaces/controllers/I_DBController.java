package cphbusiness.group6.interfaces.controllers;


import cphbusiness.group6.entities.Coordinate;
import cphbusiness.group6.interfaces.entities.I_Book;
import cphbusiness.group6.interfaces.entities.I_City;
import cphbusiness.group6.interfaces.entities.I_Coordinate;

import java.util.List;

public interface I_DBController
{
    public List<I_Book> getAllBooksThatMentionCity(String cityName);

    public List<I_City> getAllCitiesMentionedInBook(String bookTitle);

    public List<I_Book> getAllBooksWrittenByAuthor(String authorName);

    public List<I_City> getCitiesFromManyBooks(List<I_Book> books);

    public List<I_Book> getCitiesCloseToGeoLocation(I_Coordinate geoLocationCoordinate);

}
