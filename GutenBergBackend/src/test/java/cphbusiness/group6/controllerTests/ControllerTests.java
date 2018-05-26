//package cphbusiness.group6.controllerTests;
//
//import cphbusiness.group6.Controllers.MongoController;
//import cphbusiness.group6.Controllers.Neo4JController;
//import cphbusiness.group6.Controllers.PSQLController;
//import cphbusiness.group6.entities.Author;
//import cphbusiness.group6.entities.Book;
//import cphbusiness.group6.entities.City;
//import cphbusiness.group6.entities.Coordinate;
//import cphbusiness.group6.interfaces.controllers.I_DBController;
//import cphbusiness.group6.interfaces.entities.I_Author;
//import cphbusiness.group6.interfaces.entities.I_Book;
//import cphbusiness.group6.interfaces.entities.I_City;
//import cphbusiness.group6.interfaces.entities.I_Coordinate;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import static org.hamcrest.CoreMatchers.*;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//
//@RunWith(MockitoJUnitRunner.class)
//public class ControllerTests
//{
//
//    private static I_DBController mongoController;
//    private static I_DBController psqlController;
//    private static I_DBController neo4JController;
//
//    private static List<I_Book> booklist;
//    private static I_Book book;
//    private static List<I_City> cityList;
//    private static I_Author author;
//    private static I_City city;
//    private static I_Coordinate coordinate;
//
//    @BeforeClass
//    public static void setUpClass()
//    {
//        mongoController = mock(MongoController.class);
//        psqlController = mock(PSQLController.class);
//        neo4JController = mock(Neo4JController.class);
//
//        booklist = new ArrayList<>();
//        cityList = new ArrayList<>();
//
//        author = new Author("Test Author");
//        coordinate = new Coordinate(5.5, 5.5);
//        city = new City("Test city name", coordinate);
//        cityList.add(city);
//        book = new Book("Test Book", author);
//        booklist.add(book);
//
//
//
//        /*I'm mocking my daaaay away. And when the night comes to the city I say: I'M MOCKING MY DAY... (i'm mocking myyyyy daaaaay) .... AWAY */
//        when(mongoController.getAllBooksThatMentionCity("TestCityName")).thenReturn(booklist);
//        when(mongoController.getAllBooksWrittenByAuthor(author.getName())).thenReturn(booklist);
//        when(mongoController.getAllCitiesMentionedInBook(booklist.get(0).getTitle())).thenReturn(cityList);
//        when(mongoController.getCitiesCloseToGeoLocation(coordinate)).thenReturn(booklist);
//        when(mongoController.getCitiesFromManyBooks(booklist)).thenReturn(cityList);
////
//        when(psqlController.getAllBooksThatMentionCity("TestCityName")).thenReturn(booklist);
//        when(psqlController.getAllBooksWrittenByAuthor(author.getName())).thenReturn(booklist);
//        when(psqlController.getAllCitiesMentionedInBook(booklist.get(0).getTitle())).thenReturn(cityList);
//        when(psqlController.getCitiesCloseToGeoLocation(coordinate)).thenReturn(booklist);
//        when(psqlController.getCitiesFromManyBooks(booklist)).thenReturn(cityList);
//
//
//        when(neo4JController.getAllBooksThatMentionCity("TestCityName")).thenReturn(booklist);
//        when(neo4JController.getAllBooksWrittenByAuthor(author.getName())).thenReturn(booklist);
//        when(neo4JController.getAllCitiesMentionedInBook(booklist.get(0).getTitle())).thenReturn(cityList);
//        when(neo4JController.getCitiesCloseToGeoLocation(coordinate)).thenReturn(booklist);
//        when(neo4JController.getCitiesFromManyBooks(booklist)).thenReturn(cityList);
//    }
//
//
//    @Test
//    public void testCase1_MongoController()
//    {
//        assertThat(mongoController.getAllBooksThatMentionCity("TestCityName").get(0).getTitle().equalsIgnoreCase("Test Book"), is(true));
//
//    }
//
//    @Test
//    public void testCase2_MongoController()
//    {
//        assertThat(mongoController.getAllBooksWrittenByAuthor(author.getName()), is(booklist));
//        assertThat(mongoController.getAllBooksWrittenByAuthor(author.getName()).size(), is(1));
//    }
//
//    @Test
//    public void testCase3_MongoController()
//    {
//        assertThat(mongoController.getAllCitiesMentionedInBook(booklist.get(0).getTitle()), is(cityList));
//        assertThat(mongoController.getAllCitiesMentionedInBook(booklist.get(0).getTitle()).size(), is(1));
//    }
//
//    @Test
//    public void testCase4_MongoController()
//    {
//        assertThat(mongoController.getCitiesCloseToGeoLocation(coordinate), is(cityList));
//        assertThat(mongoController.getCitiesCloseToGeoLocation(coordinate).size(), is(1));
//    }
//
//    @Test
//    public void testCase5_MongoController()
//    {
//        assertThat(mongoController.getCitiesFromManyBooks(booklist), is(cityList));
//        assertThat(mongoController.getCitiesFromManyBooks(booklist).size(), is(1));
//    }
//
//
//    @Test
//    public void testCase1_PsqlController()
//    {
//        assertThat(psqlController.getAllBooksThatMentionCity("TestCityName").get(0).getTitle().equalsIgnoreCase("Test Book"), is(true));
//
//    }
//
//    @Test
//    public void testCase2_PsqlController()
//    {
//        assertThat(psqlController.getAllBooksWrittenByAuthor(author.getName()), is(booklist));
//        assertThat(psqlController.getAllBooksWrittenByAuthor(author.getName()).size(), is(1));
//    }
//
//    @Test
//    public void testCase3_PsqlController()
//    {
//        assertThat(psqlController.getAllCitiesMentionedInBook(booklist.get(0).getTitle()), is(cityList));
//        assertThat(psqlController.getAllCitiesMentionedInBook(booklist.get(0).getTitle()).size(), is(1));
//    }
//
//    @Test
//    public void testCase4_PsqlController()
//    {
//        assertThat(psqlController.getCitiesCloseToGeoLocation(coordinate), is(cityList));
//        assertThat(psqlController.getCitiesCloseToGeoLocation(coordinate).size(), is(1));
//    }
//
//    @Test
//    public void testCase5_PsqlController()
//    {
//        assertThat(psqlController.getCitiesFromManyBooks(booklist), is(cityList));
//        assertThat(psqlController.getCitiesFromManyBooks(booklist).size(), is(1));
//    }
//
//
//    @Test
//    public void testCase1_NeoController()
//    {
//        assertThat(psqlController.getAllBooksThatMentionCity("TestCityName").get(0).getTitle().equalsIgnoreCase("Test Book"), is(true));
//
//    }
//
//    @Test
//    public void testCase2_NeoController()
//    {
//        assertThat(psqlController.getAllBooksWrittenByAuthor(author.getName()), is(booklist));
//        assertThat(psqlController.getAllBooksWrittenByAuthor(author.getName()).size(), is(1));
//    }
//
//    @Test
//    public void testCase3_NeoController()
//    {
//        assertThat(psqlController.getAllCitiesMentionedInBook(booklist.get(0).getTitle()), is(cityList));
//        assertThat(psqlController.getAllCitiesMentionedInBook(booklist.get(0).getTitle()).size(), is(1));
//    }
//
//    @Test
//    public void testCase4_NeoController()
//    {
//        assertThat(psqlController.getCitiesCloseToGeoLocation(coordinate), is(cityList));
//        assertThat(psqlController.getCitiesCloseToGeoLocation(coordinate).size(), is(1));
//    }
//
//    @Test
//    public void testCase5_NeoController()
//    {
//        assertThat(psqlController.getCitiesFromManyBooks(booklist), is(cityList));
//        assertThat(psqlController.getCitiesFromManyBooks(booklist).size(), is(1));
//    }
//
//}
