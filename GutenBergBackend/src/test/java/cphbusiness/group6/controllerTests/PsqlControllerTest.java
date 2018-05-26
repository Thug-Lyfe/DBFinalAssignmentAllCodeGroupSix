package cphbusiness.group6.controllerTests;

import cphbusiness.group6.Connectors.PSQLConnector;
import cphbusiness.group6.Controllers.PSQLController;
import cphbusiness.group6.entities.Book;
import cphbusiness.group6.entities.City;
import cphbusiness.group6.entities.Coordinate;
import cphbusiness.group6.exceptions.IncorrectUsrNameOrPasswordException;
import cphbusiness.group6.interfaces.entities.I_Book;
import cphbusiness.group6.interfaces.entities.I_City;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PsqlControllerTest {

    static Connection con = null;
    static PSQLController pc = null;

    @BeforeClass
    public static void setup(){
        PSQLConnector psql = new PSQLConnector();
        pc = new PSQLController();
        try {
            con = psql.getPSQLConnection("167.99.237.199:5432","username","password");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IncorrectUsrNameOrPasswordException e) {
            e.printStackTrace();
        }

    }

    @AfterClass
    public static void eliminate(){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(con != null && con.isClosed() == true){
                System.out.println("closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getPsqlConnectionTest(){
        assertThat(con, notNullValue());
    }

    @Test
    public void getPsqlQuery1Test(){
        List list = pc.getAllBooksThatMentionCity("Oxford");
        Book book = (Book) list.get(0);
        assertThat(list.size(), equalTo(4340));
        assertThat(book.getAuthor().getName(), equalTo("Abbey, Charles J. (Charles John)"));
        assertThat(book.getTitle(), equalTo("The English Church in the Eighteenth Century"));
        assertThat(book.getReleaseDate(), equalTo("2005-10-02"));
    }
    @Test
    public void getPsqlQuery2Test(){
        List list = pc.getAllCitiesMentionedInBook("The Magna Carta");
        City t_city = (City) list.get(0);
        assertThat(list.size(), equalTo(92));
        assertThat(t_city.getCityName(),equalTo("Alexander"));
        assertThat(t_city.getGeoLocation().getLat(),equalTo(34.62954));
        assertThat(t_city.getGeoLocation().getLang(),equalTo(-92.44127));
    }
    @Test
    public void getPsqlQuery3Test(){
        List<I_Book> list1 = pc.getAllBooksWrittenByAuthor("Hayley, William");
        List<I_City> list2 = pc.getCitiesFromManyBooks(list1);
        Book book = (Book) list1.get(0);
        City t_city = (City) list2.get(0);
        assertThat(list1.size(), equalTo(3));
        assertThat(list2.size(), equalTo(40));
        assertThat(book.getAuthor().getName(), equalTo("Hayley, William"));
        assertThat(book.getTitle(), equalTo("Ballads, Founded on Anecdotes Relating to Animals"));
        assertThat(book.getReleaseDate(), equalTo("2005-10-01"));

        assertThat(t_city.getCityName(),equalTo("As"));
        assertThat(t_city.getGeoLocation().getLat(),equalTo(51.00755));
        assertThat(t_city.getGeoLocation().getLang(),equalTo(5.58453));

    }
    @Test
    public void getPsqlQuery4Test(){
        List<I_Book> list = pc.getCitiesCloseToGeoLocation(new Coordinate(52.52437,13.41053));
        List<I_City> list1 = pc.getAllCitiesMentionedInBook(list.get(0).getTitle());
        boolean check = false;
        for (I_City city: list1) {
            if(!check){
                if(Math.sqrt(Math.pow((51.00755-city.getGeoLocation().getLat()),2)+Math.pow((5.58453-city.getGeoLocation().getLang()),2) ) <=1){
                    check = true;
                }}
        }


        assertThat(check, equalTo(true));
        assertThat(list.size(), equalTo(2546));
        assertThat(list.get(0).getTitle(), equalTo("'Clear the Track!' A Story of To-day"));
        assertThat(list.get(0).getReleaseDate(), equalTo("2011-02-07"));
        assertThat(list.get(0).getAuthor().getName(), equalTo("Smith, Mary Stuart Harrison"));

    }
}
