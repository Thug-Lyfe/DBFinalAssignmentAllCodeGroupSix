package cphbusiness.group6.apiTests;

import com.jayway.restassured.response.ValidatableResponse;
import cphbusiness.group6.api.APIMAINTEST;
import cphbusiness.group6.api.MongoAPI;
import cphbusiness.group6.api.Neo4jAPI;
import cphbusiness.group6.api.SqlAPI;
import cphbusiness.group6.interfaces.entities.I_Coordinate;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {APIMAINTEST.class})
@Configuration
@ComponentScan("cphbusiness.group6.api")
public class SqlTest
{

    @LocalServerPort
    private int port;

    @Test
    public void checkSqlBooksStatus()
    {
        String city = "Oxford";
//        given().when().get("/api/psql/books/{city}", city).then().statusCode(200);
        given().port(port).basePath("/api/psql/books/").get(city).then().statusCode(200);

    }

    @Test
    public void checkSqlPlotStatus()
    {
        String book = "The Magna Carta";
//        given().when().get("/api/psql/plot/{book}", book).then().statusCode(200);
        given().port(port).basePath("/api/psql/plot/").get(book).then().statusCode(200);

    }

    @Test
    public void checkSqlBooksPlotStatus()
    {
        String author = "Hayley, William";
//        given().when().get("/api/psql/booksplot/{author}", author).then().statusCode(200);
        given().port(port).basePath("/api/psql/booksplot/").get(author).then().statusCode(200);

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

//        given().when().get("/api/psql/vicinity/{lat}/{lon}", coord.getLat(), coord.getLang()).then().statusCode(200);
        given().port(port).basePath("/api/psql/vicinity/").get(coord.getLat() + "/" + coord.getLang()).then().statusCode(200);

    }

    //value tests
    @Test
    public void checkSqlBooksValues()
    {
        String city = "Oxford";
//        ValidatableResponse response = given().when().get("/api/psql/books/{city}", city).then();
        ValidatableResponse response = given().port(port).basePath("/api/psql/books/").get(city).then().statusCode(200);

        List list = response.extract().jsonPath().get();
        assertThat(list.size(), equalTo(4340));
        assertThat(list.get(0).toString(), equalTo("{releaseDate=2005-10-02, author={booksWritten=null, name=Abbey, Charles J. (Charles John)}, textContent=null, title=The English Church in the Eighteenth Century}"));
    }

    @Test
    public void checkSqlPlotValues()
    {
        String book = "The Magna Carta";
//        ValidatableResponse response = given().when().get("/api/psql/plot/{book}", book).then();
        ValidatableResponse response = given().port(port).basePath("/api/psql/plot/").get(book).then().statusCode(200);

        List list = response.extract().jsonPath().get();
        assertThat(list.size(), equalTo(92));
        assertThat(list.get(0).toString(), equalTo("{cityName=Alexander, geoLocation={lang=-92.44127, lat=34.62954}}"));

    }

    @Test
    public void checkSqlBooksPlotValues()
    {
        String author = "Hayley, William";
//        ValidatableResponse response = given().when().get("/api/psql/booksplot/{author}", author).then();
        ValidatableResponse response = given().port(port).basePath("/api/psql/booksplot/").get(author).then().statusCode(200);

        List list = response.extract().jsonPath().getList("cities");
        List list2 = response.extract().jsonPath().getList("books");
        assertThat(list2.size(), equalTo(3));
        assertThat(list2.get(0).toString(), equalTo("{releaseDate=2005-10-01, author={booksWritten=null, name=Hayley, William}, textContent=null, title=Ballads, Founded on Anecdotes Relating to Animals}"));
        assertThat(list.size(), equalTo(40));
        assertThat(list.get(0).toString(), equalTo("{cityName=As, geoLocation={lang=5.58453, lat=51.00755}}"));
    }

    @Test
    public void checkSqlQuery4()
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
//        ValidatableResponse response = given().when().get("/api/psql/vicinity/{lat}/{lon}", coord.getLat(), coord.getLang()).then();
        ValidatableResponse response = given().port(port).basePath("/api/psql/vicinity/").get(coord.getLat() + "/" + coord.getLang()).then().statusCode(200);

        List list = response.extract().jsonPath().get();
        assertThat(list.size(), equalTo(2546));
        assertThat(list.get(0).toString(), equalTo("{releaseDate=2011-02-07, author={booksWritten=null, name=Smith, Mary Stuart Harrison}, textContent=null, title='Clear the Track!' A Story of To-day}"));

    }
}
