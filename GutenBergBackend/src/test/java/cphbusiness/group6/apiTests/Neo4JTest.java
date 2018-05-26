package cphbusiness.group6.apiTests;

import com.jayway.restassured.response.ValidatableResponse;
import cphbusiness.group6.api.APIMAINTEST;
import cphbusiness.group6.api.MongoAPI;
import cphbusiness.group6.api.Neo4jAPI;
import cphbusiness.group6.interfaces.entities.I_Coordinate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {APIMAINTEST.class})
@Configuration
@ComponentScan("cphbusiness.group6.api")
public class Neo4JTest
{

    @LocalServerPort
    private int port;


    @Test
    public void checkNeo4jBooksStatus()
    {
        System.out.println("fucking yolo");
        String city = "Oxford";
//        given().when().get("/api/neo4j/books/{city}", city).then().statusCode(200);
        given().port(port).basePath("/api/neo4j/books/").get(city).then().statusCode(200);

    }

    @Test
    public void checkNeo4jPlotStatus()
    {
        String book = "The Magna Carta";
//        given().when().get("/api/neo4j/plot/{book}", book).then().statusCode(200);
        given().port(port).basePath("/api/neo4j/plot/").get(book).then().statusCode(200);

    }

    @Test
    public void checkNeo4jBooksPlotStatus()
    {
        String author = "Hayley, William";
//        given().when().get("/api/neo4j/booksplot/{author}", author).then().statusCode(200);
        given().port(port).basePath("/api/neo4j/booksplot/").get(author).then().statusCode(200);

    }

    @Test
    public void checkNeo4jVicinityStatus()
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

//        given().when().get("/api/neo4j/vicinity/{lat}/{lon}", coord.getLat(), coord.getLang()).then().statusCode(200);
        given().port(port).basePath("/api/neo4j/vicinity/").get(coord.getLat() + "/" + coord.getLang()).then().statusCode(200);

    }

    //value tests
    @Test
    public void checkNeo4jBooksValues()
    {
        String city = "Oxford";
//        ValidatableResponse response = given().when().get("/api/neo4j/books/{city}", city).then();
        ValidatableResponse response = given().port(port).basePath("/api/neo4j/books/").get(city).then().statusCode(200);

        List list = response.extract().jsonPath().get();
        assertThat(list.size(), equalTo(4340));
        assertThat(list.get(0).toString(), equalTo("{releaseDate=2002-11-01, author={booksWritten=null, name=Tolstoy, Leo, graf}, textContent=null, title=What to Do? Thoughts Evoked By the Census of Moscow}"));
    }

    @Test
    public void checkNeo4jPlotValues()
    {
        String book = "The Magna Carta";
//        ValidatableResponse response = given().when().get("/api/neo4j/plot/{book}", book).then();
        ValidatableResponse response = given().port(port).basePath("/api/neo4j/plot/").get(book).then().statusCode(200);

        List list = response.extract().jsonPath().get();
        assertThat(list.size(), equalTo(92));
        assertThat(list.get(0).toString(), equalTo("{cityName=Worcester, geoLocation={lang=19.44852, lat=-33.64651}}"));

    }

    @Test
    public void checkNeo4jBooksPlotValues()
    {
        String author = "Hayley, William";
//        ValidatableResponse response = given().when().get("/api/neo4j/booksplot/{author}", author).then();
        ValidatableResponse response = given().port(port).basePath("/api/neo4j/booksplot/").get(author).then().statusCode(200);

        List list = response.extract().jsonPath().getList("cities");
        List list2 = response.extract().jsonPath().getList("books");
        assertThat(list2.size(), equalTo(3));
        assertThat(list2.get(0).toString(), equalTo("{releaseDate=2005-10-01, author={booksWritten=null, name=Hayley, William}, textContent=null, title=Ballads, Founded on Anecdotes Relating to Animals}"));
        assertThat(list.size(), equalTo(40));
        assertThat(list.get(0).toString(), equalTo("{cityName=Commerce, geoLocation={lang=-118.15979, lat=34.00057}}"));
    }

    @Test
    public void checkNeo4jQuery4()
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
//        ValidatableResponse response = given().when().get("/api/neo4j/vicinity/{lat}/{lon}", coord.getLat(), coord.getLang()).then();
        ValidatableResponse response = given().port(port).basePath("/api/neo4j/vicinity/").get(coord.getLat() + "/" + coord.getLang()).then().statusCode(200);

        List list = response.extract().jsonPath().get();
        assertThat(list.size(), equalTo(2546));
        assertThat(list.get(0).toString(), equalTo("{releaseDate=2000-05-01, author={booksWritten=null, name=Bird, Isabella L. (Isabella Lucy)}, textContent=null, title=Unbeaten Tracks in Japan}"));

    }

}
