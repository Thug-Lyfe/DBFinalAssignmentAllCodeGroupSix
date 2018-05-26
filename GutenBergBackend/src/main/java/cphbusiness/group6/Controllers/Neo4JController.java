package cphbusiness.group6.Controllers;

import cphbusiness.group6.Connectors.Neo4JConnector;
import cphbusiness.group6.entities.Author;
import cphbusiness.group6.entities.Book;
import cphbusiness.group6.entities.City;
import cphbusiness.group6.entities.Coordinate;
import cphbusiness.group6.interfaces.controllers.I_DBController;
import cphbusiness.group6.interfaces.entities.I_Book;
import cphbusiness.group6.interfaces.entities.I_City;
import cphbusiness.group6.interfaces.entities.I_Coordinate;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.*;

import java.util.ArrayList;
import java.util.List;

public class Neo4JController implements I_DBController
{
    @Override
    public List<I_Book> getAllBooksThatMentionCity(String cityName)
    {

        Neo4JConnector theOne = new Neo4JConnector();

        Session session = theOne.getNeo4JConnection("bolt://167.99.237.199:7687", "username","password");


        try {
            StatementResult result;

            result = session.run("MATCH (:city {name : '"+cityName+"'})<-[:Mentions]-(a :book)<-[:Written_by]-(b :author) return distinct a, b");

            ArrayList<I_Book> books = new ArrayList();

            while(result.hasNext()){
                Record record = result.next();
                books.add(new Book(record.get("a").get("name").asString(), record.get("a").get("release_date").asString(), new Author(record.get("b").get("name").asString())));
            }
            return books;
        }
        finally {
            session.close();
            theOne.closeDriver();
        }



    }

    @Override
    public List<I_City> getAllCitiesMentionedInBook(String bookTitle)
    {
        Neo4JConnector theOne = new Neo4JConnector();

        Session session = theOne.getNeo4JConnection("bolt://167.99.237.199:7687", "username","password");


        try {
            StatementResult result;

            result = session.run("MATCH (:book {name : '"+bookTitle+"'})-[:Mentions]->(a :city) return distinct a");

            ArrayList<I_City> city = new ArrayList();

            while(result.hasNext()){
                Record record = result.next();
                city.add(new City(record.get("a").get("name").asString(),new Coordinate(Double.parseDouble(record.get("a").get("latitude").asString()), Double.parseDouble(record.get("a").get("longitude").asString()))));
            }
            return city;
        }
        finally {
            session.close();
            theOne.closeDriver();
        }

    }

    @Override
    public List<I_Book> getAllBooksWrittenByAuthor(String authorName)
    {
        Neo4JConnector theOne = new Neo4JConnector();

        Session session = theOne.getNeo4JConnection("bolt://167.99.237.199:7687", "username","password");


        try {
            StatementResult result;

            result = session.run("MATCH (:author {name : '"+authorName+"'})-[:Written_by]->(a :book) return distinct a");

            ArrayList<I_Book> books = new ArrayList();

            while(result.hasNext()){
                Record record = result.next();
                books.add(new Book(record.get("a").get("name").asString(), record.get("a").get("release_date").asString(), new Author(authorName)));
            }
            return books;
        }
        finally {
            session.close();
            theOne.closeDriver();
        }
    }

    @Override
    public List<I_City> getCitiesFromManyBooks(List<I_Book> books)
    {
        Neo4JConnector theOne = new Neo4JConnector();

        Session session = theOne.getNeo4JConnection("bolt://167.99.237.199:7687", "username","password");

        String arr = "";
        for (I_Book book :books) {
            String title = book.getTitle().replaceAll(" ", "&nbsp");
            if(arr != "") {
                arr += ",'" + book.getTitle() + "'";
            }else{
                arr += "'" + book.getTitle() + "'";
            }
        }

        try {
            StatementResult result;

            result = session.run("MATCH (c:author {name:'"+books.get(0).getAuthor().getName()+"'})-[:Written_by]->(b:book)-[:Mentions]->(a :city) return distinct a");

            ArrayList<I_City> city = new ArrayList();

            while(result.hasNext()){
                Record record = result.next();
                city.add(new City(record.get("a").get("name").asString(),new Coordinate(Double.parseDouble(record.get("a").get("latitude").asString()), Double.parseDouble(record.get("a").get("longitude").asString()))));
            }
            return city;
        }
        finally {
            session.close();
            theOne.closeDriver();
        }
    }

    @Override
    public List<I_Book> getCitiesCloseToGeoLocation(I_Coordinate geoLocationCoordinate)
    {
        Neo4JConnector theOne = new Neo4JConnector();

        Session session = theOne.getNeo4JConnection("bolt://167.99.237.199:7687","username","password");


        try {
            StatementResult result;

            result = session.run("MATCH (b :city )<-[:Mentions]-(a :book)<-[:Written_by]-(c:author) Where asin(sqrt(sin(radians("+geoLocationCoordinate.getLat()+" - toFloat(b.latitude))/2)^2 + sin(radians("+geoLocationCoordinate.getLang()+" - toFloat(b.longitude))/2)^2 * cos(radians(toFloat(b.latitude))) * cos(radians("+geoLocationCoordinate.getLat()+"))))*12756.2 < 100 return distinct a, c");

            ArrayList<I_Book> books = new ArrayList();

            while(result.hasNext()){
                Record record = result.next();
                books.add(new Book(record.get("a").get("name").asString(), record.get("a").get("release_date").asString(), new Author(record.get("c").get("name").asString())));
            }
            return books;
        }
        finally {
            session.close();
            theOne.closeDriver();
        }
    }
}
