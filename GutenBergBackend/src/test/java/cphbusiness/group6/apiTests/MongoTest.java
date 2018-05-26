package cphbusiness.group6.apiTests;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.ValidatableResponse;
import cphbusiness.group6.api.APIMAINTEST;
import cphbusiness.group6.interfaces.entities.I_Coordinate;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {APIMAINTEST.class})
public class MongoTest
{

    @LocalServerPort
    private int port;


    //status tests
    @Test
    public void checkMongoBooksStatus()
    {

        String city = "Oxford";
        given().port(port).basePath("/api/mongo/books/Copenhagen").get("").then().statusCode(200);
//        given().when().get("/api/mongo/books/{city}", city).then().statusCode(200);
    }

    @Test
    public void checkMongoPlotStatus()
    {
        String book = "The Magna Carta";
//        given().when().get("/api/mongo/plot/{book}", book).then().statusCode(200);
        given().port(port).basePath("/api/mongo/plot/").get(book).then().statusCode(200);

    }

    @Test
    public void checkMongoBooksPlotStatus()
    {
        String author = "Hayley, William";
//        given().when().get("/api/mongo/booksplot/{author}", author).then().statusCode(200);
        given().port(port).basePath("/api/mongo/booksplot/").get(author).then().statusCode(200);

    }

    @Test
    public void checkMongoVicinityStatus()
    {
        I_Coordinate coord = new I_Coordinate()
        {
            @Override
            public double getLang()
            {
                return 13.41053;
            }

            @Override
            public double getLat()
            {
                return 52.52437;
            }
        };

//        given().when().get("/api/mongo/vicinity/{lat}/{lon}", coord.getLat(), coord.getLang()).then().statusCode(200);
        given().port(port).basePath("/api/mongo/vicinity/").get(coord.getLat() + "/" + coord.getLang()).then().statusCode(200);

    }

    //value tests
    @Test
    public void checkMongoBooksValues()
    {
        String city = "Oxford";
//        ValidatableResponse response = given().when().get("/api/mongo/books/{city}", city).then();
        ValidatableResponse response = given().port(port).basePath("/api/mongo/books/").get(city).then().statusCode(200);


        List list = response.extract().jsonPath().get();
        assertThat(list.size(), equalTo(4340));
        assertThat(list.get(0).toString(), equalTo("{releaseDate=1971-12-01, author={booksWritten=null, name=Jefferson, Thomas}, textContent=null, title=The Declaration of Independence of the United States of America}"));

    }

    @Test
    public void checkMongoPlotValues()
    {
        String book = "The Magna Carta";
//        ValidatableResponse response = given().when().get("/api/mongo/plot/{book}", book).then();
        ValidatableResponse response = given().port(port).basePath("/api/mongo/plot/").get(book).then().statusCode(200);

        List list = response.extract().jsonPath().get();
        assertThat(list.size(), equalTo(92));
        assertThat(list.get(0).toString(), equalTo("{cityName=Alexander, geoLocation={lang=-92.44127, lat=34.62954}}"));

    }

    @Test
    public void checkMongoBooksPlotValues()
    {
        String author = "Hayley, William";
//        ValidatableResponse response = given().when().get("/api/mongo/booksplot/{author}", author).then();
        ValidatableResponse response = given().port(port).basePath("/api/mongo/booksplot/").get(author).then().statusCode(200);

        List list = response.extract().jsonPath().getList("cities");
        List list2 = response.extract().jsonPath().getList("books");
        assertThat(list2.size(), equalTo(3));
        assertThat(list2.get(0).toString(), equalTo("{releaseDate=2003-11-01, author={booksWritten=null, name=Hayley, William}, textContent=null, title=The Eulogies of Howard: A Vision}"));
        assertThat(list.size(), equalTo(40));
        assertThat(list.get(0).toString(), equalTo("{cityName=Till, geoLocation={lang=35.21293, lat=32.20068}}"));
    }

    @Test
    public void checkMongoQuery4()
    {
        I_Coordinate coord = new I_Coordinate()
        {
            @Override
            public double getLang()
            {
                return 13.41053;
            }

            @Override
            public double getLat()
            {
                return 52.52437;
            }
        };
//        ValidatableResponse response = given().when().get("/api/mongo/vicinity/{lat}/{lon}", coord.getLat(), coord.getLang()).then();
        ValidatableResponse response = given().port(port).basePath("/api/mongo/vicinity/").get(coord.getLat() + "/" + coord.getLang()).then().statusCode(200);


        List list = response.extract().jsonPath().get();
        assertThat(list.size(), equalTo(2539));
        assertThat(list.get(0).toString(), equalTo("{releaseDate=2003-11-01, author={booksWritten=null, name=Waddington, Mary King}, textContent=null, title=My First Years as a Frenchwoman, 1876-1879}"));

    }
}
