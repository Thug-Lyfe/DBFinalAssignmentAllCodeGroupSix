package cphbusiness.group6.Benchmark;


import cphbusiness.group6.Connectors.MongoConnector;
import cphbusiness.group6.Connectors.PSQLConnector;
import cphbusiness.group6.Controllers.MongoController;
import cphbusiness.group6.Controllers.PSQLController;
import cphbusiness.group6.api.APIMAINTEST;
import cphbusiness.group6.entities.Book;
import cphbusiness.group6.entities.City;
import cphbusiness.group6.entities.Coordinate;
import cphbusiness.group6.exceptions.IncorrectUsrNameOrPasswordException;
import cphbusiness.group6.interfaces.entities.I_Book;
import cphbusiness.group6.interfaces.entities.I_City;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PsqlBench {
/*
    static PSQLConnector monCon = null;
    static PSQLController yatzi = null;
    static WebDriver driver;

    @LocalServerPort
    private int port;


    @BeforeClass
    public static void setup(){
        monCon = new PSQLConnector();
        yatzi = new PSQLController();
        System.setProperty("webdriver.gecko.driver","geckodriver.exe");
        driver = new FirefoxDriver();
        driver.get("http://167.99.237.199/");


    }

    @AfterClass
    public static void eliminate(){

    }

    @Test
    public void getpsqlCon1(){
        long now = System.currentTimeMillis();
        List<I_Book> list1 = yatzi.getAllBooksThatMentionCity("Copenhagen");
        List<I_Book> list2 = yatzi.getAllBooksThatMentionCity("London");
        List<I_Book> list3 = yatzi.getAllBooksThatMentionCity("Oxford");
        List<I_Book> list4 = yatzi.getAllBooksThatMentionCity("Roskilde");
        List<I_Book> list5 = yatzi.getAllBooksThatMentionCity("Ushuaia");
        System.out.println(System.currentTimeMillis() - now);
        assertThat(list1,notNullValue());
        assertThat(list2,notNullValue());
        assertThat(list3,notNullValue());
        assertThat(list4,notNullValue());
        assertThat(list5,notNullValue());
    }

    @Test
    public void getpsqlCon2(){
        List list1 = yatzi.getAllCitiesMentionedInBook("Byron");
        List list2 = yatzi.getAllCitiesMentionedInBook("Denmark");
        List list3 = yatzi.getAllCitiesMentionedInBook("Yeast: a Problem");
        List list4 = yatzi.getAllCitiesMentionedInBook("Phantom Fortune, a Novel");
        List list5 = yatzi.getAllCitiesMentionedInBook("The Gold Diggings of Cape Horn: A Study of Life in Tierra del Fuego and Patagonia");
        assertThat(list1,notNullValue());
        assertThat(list2,notNullValue());
        assertThat(list3,notNullValue());
        assertThat(list4,notNullValue());
        assertThat(list5,notNullValue());

    }
    @Test
    public void getpsqlCon3(){
        List<I_Book> list1 = yatzi.getAllBooksWrittenByAuthor("Bourne, Edward Gaylord");
        List<I_Book> list2 = yatzi.getAllBooksWrittenByAuthor("Various");
        List<I_Book> list3 = yatzi.getAllBooksWrittenByAuthor("Hodgson, William Hope");
        List<I_Book> list4 = yatzi.getAllBooksWrittenByAuthor("Goldsmith, Oliver");
        List<I_Book> list5 = yatzi.getAllBooksWrittenByAuthor("Spears, John Randolph");
        assertThat(list1,notNullValue());
        assertThat(list2,notNullValue());
        assertThat(list3,notNullValue());
        assertThat(list4,notNullValue());
        assertThat(list5,notNullValue());
        List<I_City> list11 = yatzi.getCitiesFromManyBooks(list1);
        List<I_City> list22 = yatzi.getCitiesFromManyBooks(list2);
        List<I_City> list33 = yatzi.getCitiesFromManyBooks(list3);
        List<I_City> list44 = yatzi.getCitiesFromManyBooks(list4);
        List<I_City> list55 = yatzi.getCitiesFromManyBooks(list5);
        assertThat(list11,notNullValue());
        assertThat(list22,notNullValue());
        assertThat(list33,notNullValue());
        assertThat(list44,notNullValue());
        assertThat(list55,notNullValue());



    }
    @Test
    public void getpsqlCon4() {
        List<I_Book> list1 = yatzi.getCitiesCloseToGeoLocation(new Coordinate(43.4052, 87.1952));
        List<I_Book> list2 = yatzi.getCitiesCloseToGeoLocation(new Coordinate(10,10));
        List<I_Book> list3 = yatzi.getCitiesCloseToGeoLocation(new Coordinate(53.3439, 23.0622));
        List<I_Book> list4 = yatzi.getCitiesCloseToGeoLocation(new Coordinate(55.682319, 12.563728));
        List<I_Book> list5 = yatzi.getCitiesCloseToGeoLocation(new Coordinate(38.883139, -77.016278));
        assertThat(list1,notNullValue());
        assertThat(list2,notNullValue());
        assertThat(list3,notNullValue());
        assertThat(list4,notNullValue());
        assertThat(list5,notNullValue());
    }
    @Test
    public void getpsqlApi1() throws IOException {
        URL url = new URL("http://167.99.237.199:8080/api/psql/books/Copenhagen");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int code = connection.getResponseCode();
        System.out.println(code);
        connection.disconnect();

        URL url2 = new URL("http://167.99.237.199:8080/api/psql/books/London");
        HttpURLConnection connection2 = (HttpURLConnection)url2.openConnection();
        connection2.setRequestMethod("GET");
        connection2.connect();
        int code2 = connection2.getResponseCode();
        System.out.println(code2);
        connection2.disconnect();

        URL url3 = new URL("http://167.99.237.199:8080/api/psql/books/Oxford");
        HttpURLConnection connection3 = (HttpURLConnection)url3.openConnection();
        connection3.setRequestMethod("GET");
        connection3.connect();
        int code3 = connection3.getResponseCode();
        System.out.println(code3);
        connection3.disconnect();

        URL url4 = new URL("http://167.99.237.199:8080/api/psql/books/Roskilde");
        HttpURLConnection connection4 = (HttpURLConnection)url4.openConnection();
        connection4.setRequestMethod("GET");
        connection4.connect();
        int code4 = connection4.getResponseCode();
        System.out.println(code4);
        connection4.disconnect();

        URL url5 = new URL("http://167.99.237.199:8080/api/psql/books/Ushuaia");
        HttpURLConnection connection5 = (HttpURLConnection)url5.openConnection();
        connection5.setRequestMethod("GET");
        connection5.connect();
        int code5 = connection5.getResponseCode();
        System.out.println(code5);
        connection5.disconnect();

    }
    @Test
    public void getpsqlApi2() throws IOException {
        URL url = new URL("http://167.99.237.199:8080/api/psql/plot/Byron");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int code = connection.getResponseCode();
        System.out.println(code);
        connection.disconnect();

        URL url2 = new URL("http://167.99.237.199:8080/api/psql/plot/Denmark");
        HttpURLConnection connection2 = (HttpURLConnection)url2.openConnection();
        connection2.setRequestMethod("GET");
        connection2.connect();
        int code2 = connection2.getResponseCode();
        System.out.println(code2);
        connection2.disconnect();

        URL url3 = new URL("http://167.99.237.199:8080/api/psql/plot/Yeast:%20a%20Problem");
        HttpURLConnection connection3 = (HttpURLConnection)url3.openConnection();
        connection3.setRequestMethod("GET");
        connection3.connect();
        int code3 = connection3.getResponseCode();
        System.out.println(code3);
        connection3.disconnect();

        URL url4 = new URL("http://167.99.237.199:8080/api/psql/plot/Phantom%20Fortune,%20a%20Novel");
        HttpURLConnection connection4 = (HttpURLConnection)url4.openConnection();
        connection4.setRequestMethod("GET");
        connection4.connect();
        int code4 = connection4.getResponseCode();
        System.out.println(code4);
        connection4.disconnect();

        URL url5 = new URL("http://167.99.237.199:8080/api/psql/plot/The%20Gold%20Diggings%20of%20Cape%20Horn:%20A%20Study%20of%20Life%20in%20Tierra%20del%20Fuego%20and%20Patagonia");
        HttpURLConnection connection5 = (HttpURLConnection)url5.openConnection();
        connection5.setRequestMethod("GET");
        connection5.connect();
        int code5 = connection5.getResponseCode();
        System.out.println(code5);
        connection5.disconnect();
    }
    @Test
    public void getpsqlApi3() throws IOException {
        URL url = new URL("http://167.99.237.199:8080/api/psql/booksplot/Bourne,%20Edward%20Gaylord");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int code = connection.getResponseCode();
        System.out.println(code);
        connection.disconnect();

        URL url2 = new URL("http://167.99.237.199:8080/api/psql/booksplot/Various");
        HttpURLConnection connection2 = (HttpURLConnection)url2.openConnection();
        connection2.setRequestMethod("GET");
        connection2.connect();
        int code2 = connection2.getResponseCode();
        System.out.println(code2);
        connection2.disconnect();

        URL url3 = new URL("http://167.99.237.199:8080/api/psql/booksplot/Hodgson,%20William%20Hope");
        HttpURLConnection connection3 = (HttpURLConnection)url3.openConnection();
        connection3.setRequestMethod("GET");
        connection3.connect();
        int code3 = connection3.getResponseCode();
        System.out.println(code3);
        connection3.disconnect();

        URL url4 = new URL("http://167.99.237.199:8080/api/psql/booksplot/Goldsmith,%20Oliver");
        HttpURLConnection connection4 = (HttpURLConnection)url4.openConnection();
        connection4.setRequestMethod("GET");
        connection4.connect();
        int code4 = connection4.getResponseCode();
        System.out.println(code4);
        connection4.disconnect();

        URL url5 = new URL("http://167.99.237.199:8080/api/psql/booksplot/Spears,%20John%20Randolph");
        HttpURLConnection connection5 = (HttpURLConnection)url5.openConnection();
        connection5.setRequestMethod("GET");
        connection5.connect();
        int code5 = connection5.getResponseCode();
        System.out.println(code5);
        connection5.disconnect();
    }
    @Test
    public void getpsqlApi4() throws IOException {
        URL url = new URL("http://167.99.237.199:8080/api/psql/vicinity/43.4052/87.1952");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int code = connection.getResponseCode();
        System.out.println(code);
        connection.disconnect();

        URL url2 = new URL("http://167.99.237.199:8080/api/psql/vicinity/10/10");
        HttpURLConnection connection2 = (HttpURLConnection)url2.openConnection();
        connection2.setRequestMethod("GET");
        connection2.connect();
        int code2 = connection2.getResponseCode();
        System.out.println(code2);
        connection2.disconnect();

        URL url3 = new URL("http://167.99.237.199:8080/api/psql/vicinity/53.3439/23.0622");
        HttpURLConnection connection3 = (HttpURLConnection)url3.openConnection();
        connection3.setRequestMethod("GET");
        connection3.connect();
        int code3 = connection3.getResponseCode();
        System.out.println(code3);
        connection3.disconnect();

        URL url4 = new URL("http://167.99.237.199:8080/api/psql/vicinity/55.682319/12.563728");
        HttpURLConnection connection4 = (HttpURLConnection)url4.openConnection();
        connection4.setRequestMethod("GET");
        connection4.connect();
        int code4 = connection4.getResponseCode();
        System.out.println(code4);
        connection4.disconnect();

        URL url5 = new URL("http://167.99.237.199:8080/api/psql/vicinity/38.883139/-77.016278");
        HttpURLConnection connection5 = (HttpURLConnection)url5.openConnection();
        connection5.setRequestMethod("GET");
        connection5.connect();
        int code5 = connection5.getResponseCode();
        System.out.println(code5);
        connection5.disconnect();
    }
    @Test
    public void getpsqlReact1(){
        long now = System.currentTimeMillis();
        (new WebDriverWait(driver, 100)).until((ExpectedCondition<Boolean>) (WebDriver d) -> {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            driver.findElement(By.id("psql")).click();
            driver.findElement(By.id("1")).findElement(By.tagName("input")).click();
            WebElement input =  driver.findElement(By.id("city"));
            input.sendKeys("Copenhagen");
            driver.findElement(By.id("run")).click();

            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {
                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false1");
                    return false;
                }
                System.out.println("true1");
                return true;
            });
            input.clear();
            input.sendKeys("London");
            driver.findElement(By.id("run")).click();
            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {
                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false5");
                    return false;
                }
                System.out.println("true5");
                return true;
            });
            input.clear();
            input.sendKeys("Oxford");
            driver.findElement(By.id("run")).click();

            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {
                Boolean check = false;
                if(false){
                    check = driver.findElements(By.id("loader")).isEmpty();
                }
                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false2");
                    return false;
                }
                System.out.println("true2");
                return true;
            });
            input.clear();
            input.sendKeys("Roskilde");
            driver.findElement(By.id("run")).click();
            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {

                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false3");
                    return false;
                }
                System.out.println("true3");
                return true;
            });
            input.clear();
            input.sendKeys("Ushuaia");
            driver.findElement(By.id("run")).click();
            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {
                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false4");
                    return false;
                }
                System.out.println("true4");
                return true;
            });
            return true;
        });
        System.out.println(System.currentTimeMillis() - now);
    }
    @Test
    public void getpsqlReact2(){
        long now = System.currentTimeMillis();
        (new WebDriverWait(driver, 100)).until((ExpectedCondition<Boolean>) (WebDriver d) -> {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            driver.findElement(By.id("psql")).click();
            driver.findElement(By.id("2")).findElement(By.tagName("input")).click();
            WebElement input =  driver.findElement(By.id("book"));
            input.sendKeys("Byron");
            driver.findElement(By.id("run")).click();

            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {
                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false1");
                    return false;
                }
                System.out.println("true1");
                return true;
            });
            input.clear();
            input.sendKeys("Denmark");
            driver.findElement(By.id("run")).click();
            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {
                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false5");
                    return false;
                }
                System.out.println("true5");
                return true;
            });
            input.clear();
            input.sendKeys("Yeast: a Problem");
            driver.findElement(By.id("run")).click();

            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {
                Boolean check = false;
                if(false){
                    check = driver.findElements(By.id("loader")).isEmpty();
                }
                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false2");
                    return false;
                }
                System.out.println("true2");
                return true;
            });
            input.clear();
            input.sendKeys("Phantom Fortune, a Novel");
            driver.findElement(By.id("run")).click();
            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {

                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false3");
                    return false;
                }
                System.out.println("true3");
                return true;
            });
            input.clear();
            input.sendKeys("The Gold Diggings of Cape Horn: A Study of Life in Tierra del Fuego and Patagonia");
            driver.findElement(By.id("run")).click();
            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {
                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false4");
                    return false;
                }
                System.out.println("true4");
                return true;
            });
            return true;
        });
        System.out.println(System.currentTimeMillis() - now);
    }
    @Test
    public void getpsqlReact4(){
        long now = System.currentTimeMillis();
        (new WebDriverWait(driver, 100)).until((ExpectedCondition<Boolean>) (WebDriver d) -> {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            driver.findElement(By.id("psql")).click();
            driver.findElement(By.id("3")).findElement(By.tagName("input")).click();
            WebElement input =  driver.findElement(By.id("author"));
            input.sendKeys("Bourne, Edward Gaylord");
            driver.findElement(By.id("run")).click();

            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {
                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false1");
                    return false;
                }
                System.out.println("true1");
                return true;
            });
            input.clear();
            input.sendKeys("Spears, John Randolph");
            driver.findElement(By.id("run")).click();
            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {
                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false5");
                    return false;
                }
                System.out.println("true5");
                return true;
            });
            input.clear();
            input.sendKeys("Hodgson, William Hope");
            driver.findElement(By.id("run")).click();

            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {
                Boolean check = false;
                if(false){
                    check = driver.findElements(By.id("loader")).isEmpty();
                }
                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false2");
                    return false;
                }
                System.out.println("true2");
                return true;
            });
            input.clear();
            input.sendKeys("Goldsmith, Oliver");
            driver.findElement(By.id("run")).click();
            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {

                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false3");
                    return false;
                }
                System.out.println("true3");
                return true;
            });
            input.clear();
            input.sendKeys("Various");
            driver.findElement(By.id("run")).click();
            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {
                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false4");
                    return false;
                }
                System.out.println("true4");
                return true;
            });
            return true;
        });
        System.out.println(System.currentTimeMillis() - now);
    }
    @Test
    public void getpsqlReact3(){
        long now = System.currentTimeMillis();
        (new WebDriverWait(driver, 100)).until((ExpectedCondition<Boolean>) (WebDriver d) -> {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            driver.findElement(By.id("psql")).click();
            driver.findElement(By.id("4")).findElement(By.tagName("input")).click();
            WebElement input1 =  driver.findElement(By.id("lat"));
            WebElement input2 =  driver.findElement(By.id("lng"));
            input1.sendKeys("43.4052");
            input2.sendKeys("87.1952");
            driver.findElement(By.id("run")).click();

            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {
                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false1");
                    return false;
                }
                System.out.println("true1");
                return true;
            });
            input1.clear();
            input2.clear();
            input1.sendKeys("10");
            input2.sendKeys("10");
            driver.findElement(By.id("run")).click();
            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {
                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false5");
                    return false;
                }
                System.out.println("true5");
                return true;
            });
            input1.clear();
            input2.clear();
            input1.sendKeys("53.3439");
            input2.sendKeys("23.0622");
            driver.findElement(By.id("run")).click();
            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {
                Boolean check = false;
                if(false){
                    check = driver.findElements(By.id("loader")).isEmpty();
                }
                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false2");
                    return false;
                }
                System.out.println("true2");
                return true;
            });
            input1.clear();
            input2.clear();
            input1.sendKeys("55.682319");
            input2.sendKeys("12.563728");
            driver.findElement(By.id("run")).click();
            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {

                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false3");
                    return false;
                }
                System.out.println("true3");
                return true;
            });
            input1.clear();
            input2.clear();
            input1.sendKeys("38.883139,");
            input2.sendKeys("-77.016278");
            driver.findElement(By.id("run")).click();
            (new WebDriverWait(driver, 20)).until((ExpectedCondition<Boolean>) (WebDriver d1) -> {
                if(!driver.findElements(By.id("loader")).isEmpty()){
                    System.out.println("false4");
                    return false;
                }
                System.out.println("true4");
                return true;
            });
            return true;
        });
        System.out.println(System.currentTimeMillis() - now);
    }*/
}