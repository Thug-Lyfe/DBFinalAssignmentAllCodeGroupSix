package cphbusiness.group6.controllerTests;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import cphbusiness.group6.Connectors.MongoConnector;
import cphbusiness.group6.Controllers.MongoController;
import cphbusiness.group6.entities.Author;
import cphbusiness.group6.entities.Book;
import cphbusiness.group6.entities.City;
import cphbusiness.group6.entities.Coordinate;
import cphbusiness.group6.exceptions.IncorrectUsrNameOrPasswordException;
import cphbusiness.group6.interfaces.entities.I_Book;
import cphbusiness.group6.interfaces.entities.I_City;
import cphbusiness.group6.interfaces.entities.I_Coordinate;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class MongodbControllerTestss {

    static MongoConnector monCon = null;
    static MongoController yatzi = null;
    static MongoClient mongo = null;

    @BeforeClass
    public static void setup(){
        monCon = new MongoConnector();
        yatzi = new MongoController();
        try {
            mongo = monCon.getMongoDBConnection("167.99.237.199:27017","username","password");
        } catch (IncorrectUsrNameOrPasswordException e) {
            e.printStackTrace();
        }

    }

    @AfterClass
    public static void eliminate(){
        try {
            mongo.close();

        } catch (MongoException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void getMongoConnectionTest(){
        assertThat(mongo, notNullValue());
    }

    @Test
    public void getMongo1(){
        List<I_Book> list = yatzi.getAllBooksThatMentionCity("Oxford");
        Book book = (Book) list.get(0);
        assertThat(list.size(), equalTo(4340));
        assertThat(book.getAuthor().getName(), equalTo("Jefferson, Thomas"));
        assertThat(book.getTitle(), equalTo("The Declaration of Independence of the United States of America"));
        assertThat(book.getReleaseDate(), equalTo("1971-12-01"));
    }

    @Test
    public void getMongoQuery2Test(){
        List list = yatzi.getAllCitiesMentionedInBook("The Magna Carta");
        City t_city = (City) list.get(0);
        assertThat(list.size(), equalTo(92));
        assertThat(t_city.getCityName(),equalTo("Alexander"));
        assertThat(t_city.getGeoLocation().getLat(),equalTo(34.62954));
        assertThat(t_city.getGeoLocation().getLang(),equalTo(-92.44127));

    }
    @Test
    public void getMongoQuery3Test(){
        List<I_Book> list1 = yatzi.getAllBooksWrittenByAuthor("Hayley, William");
        List<I_City> list2 = yatzi.getCitiesFromManyBooks(list1);
        Book book = null;
        City t_city = null;
        if(list1 != null && list2 != null){
            book = (Book) list1.get(0);
            t_city = (City) list2.get(0);
        }
        assertThat(list1.size(), equalTo(3));
        assertThat(list2.size(), equalTo(40));
        assertThat(book.getAuthor().getName(), equalTo("Hayley, William"));
        assertThat(book.getTitle(), equalTo("The Eulogies of Howard: A Vision"));
        assertThat(book.getReleaseDate(), equalTo("2003-11-01"));

        assertThat(t_city.getCityName(),equalTo("Till"));
        assertThat(t_city.getGeoLocation().getLat(),equalTo(32.20068));
        assertThat(t_city.getGeoLocation().getLang(),equalTo(35.21293));

    }
    @Test
    public void getMongoQuery4Test() {
        List<I_Book> list = yatzi.getCitiesCloseToGeoLocation(new Coordinate(52.52437, 13.41053));
        List<I_City> list1 = yatzi.getAllCitiesMentionedInBook(list.get(0).getTitle());
        boolean check = false;
        for (I_City city : list1) {
            if (!check) {
                if (Math.sqrt(Math.pow((51.00755 - city.getGeoLocation().getLat()), 2) + Math.pow((5.58453 - city.getGeoLocation().getLang()), 2)) <= 1) {
                    check = true;
                }
            }

        }
        assertThat(check, equalTo(true));
        assertThat(list.size(), equalTo(2539));
        assertThat(list.get(0).getTitle(), equalTo("My First Years as a Frenchwoman, 1876-1879"));
        assertThat(list.get(0).getReleaseDate(), equalTo("2003-11-01"));
        assertThat(list.get(0).getAuthor().getName(), equalTo("Waddington, Mary King"));
    }
}
