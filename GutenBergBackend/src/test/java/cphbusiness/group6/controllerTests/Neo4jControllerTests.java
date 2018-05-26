package cphbusiness.group6.controllerTests;

import cphbusiness.group6.Connectors.Neo4JConnector;
import cphbusiness.group6.Controllers.Neo4JController;
import cphbusiness.group6.entities.Book;
import cphbusiness.group6.entities.City;
import cphbusiness.group6.entities.Coordinate;
import cphbusiness.group6.interfaces.entities.I_Book;
import cphbusiness.group6.interfaces.entities.I_City;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.exceptions.Neo4jException;

import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class Neo4jControllerTests {

    static Neo4JConnector morpheus = null;
    static Neo4JController nazi = null;
    static Session con = null;


    @BeforeClass
    public static void setup(){
        morpheus = new Neo4JConnector();
        nazi = new Neo4JController();
        con = morpheus.getNeo4JConnection("bolt://167.99.237.199:7687", "username","password");

    }

    @AfterClass
    public static void eliminate(){
        try {
            con.close();
            morpheus.closeDriver();
        } catch (Neo4jException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getPsqlConnectionTest(){
        assertThat(con, notNullValue());
    }

    @Test
    public void getTheOne1(){
        List<I_Book> list = nazi.getAllBooksThatMentionCity("Oxford");
        Book book = (Book) list.get(0);
        assertThat(list.size(), equalTo(4340));
        assertThat(book.getAuthor().getName(), equalTo("Tolstoy, Leo, graf"));
        assertThat(book.getTitle(), equalTo("What to Do? Thoughts Evoked By the Census of Moscow"));
        assertThat(book.getReleaseDate(), equalTo("2002-11-01"));

    }

    @Test
    public void getTheOneQuery2Test(){
        List list = nazi.getAllCitiesMentionedInBook("The Magna Carta");
        City t_city = (City) list.get(0);
        assertThat(list.size(), equalTo(92));
        assertThat(t_city.getCityName(),equalTo("Worcester"));
        assertThat(t_city.getGeoLocation().getLat(),equalTo(-33.64651));
        assertThat(t_city.getGeoLocation().getLang(),equalTo(19.44852));
        String str = "";
    }
    @Test
    public void getTheOneQuery3Test(){
        List<I_Book> list1 = nazi.getAllBooksWrittenByAuthor("Hayley, William");
        List<I_City> list2 = nazi.getCitiesFromManyBooks(list1);
        Book book = (Book) list1.get(0);
        City t_city = (City) list2.get(0);
        assertThat(list1.size(), equalTo(3));
        assertThat(list2.size(), equalTo(40));
        assertThat(book.getAuthor().getName(), equalTo("Hayley, William"));
        assertThat(book.getTitle(), equalTo("Ballads, Founded on Anecdotes Relating to Animals"));
        assertThat(book.getReleaseDate(), equalTo("2005-10-01"));

        assertThat(t_city.getCityName(),equalTo("Commerce"));
        assertThat(t_city.getGeoLocation().getLat(),equalTo(34.00057));
        assertThat(t_city.getGeoLocation().getLang(),equalTo(-118.15979));
        String str = "";
    }
    @Test
    public void getTheOneQuery4Test(){
        List<I_Book> list = nazi.getCitiesCloseToGeoLocation(new Coordinate(52.52437,13.41053));
        List<I_City> list1 = nazi.getAllCitiesMentionedInBook(list.get(0).getTitle());
        boolean check = false;
        for (I_City city: list1) {
            if(!check){
                if(Math.sqrt(Math.pow((51.00755-city.getGeoLocation().getLat()),2)+Math.pow((5.58453-city.getGeoLocation().getLang()),2) ) <=1){
                    check = true;
                }}

        }
        assertThat(check, equalTo(true));
        assertThat(list.size(), equalTo(2546));
        assertThat(list.get(0).getTitle(), equalTo("Unbeaten Tracks in Japan"));
        assertThat(list.get(0).getReleaseDate(), equalTo("2000-05-01"));
        assertThat(list.get(0).getAuthor().getName(), equalTo("Bird, Isabella L. (Isabella Lucy)"));
        String str = "";
    }

}
