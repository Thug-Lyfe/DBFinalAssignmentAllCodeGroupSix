package cphbusiness.group6.Controllers;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import cphbusiness.group6.Connectors.MongoConnector;
import cphbusiness.group6.entities.Author;
import cphbusiness.group6.entities.Book;
import cphbusiness.group6.entities.City;
import cphbusiness.group6.entities.Coordinate;
import cphbusiness.group6.exceptions.IncorrectUsrNameOrPasswordException;
import cphbusiness.group6.interfaces.controllers.I_DBController;
import cphbusiness.group6.interfaces.entities.I_Book;
import cphbusiness.group6.interfaces.entities.I_City;
import cphbusiness.group6.interfaces.entities.I_Coordinate;
import org.bson.Document;

import javax.print.Doc;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

public class MongoController implements I_DBController
{
    @Override
    public List<I_Book> getAllBooksThatMentionCity(String cityName)
    {

        MongoClient hammerTime = null;

        try
        {
            MongoConnector mcHammer = new MongoConnector();


            try
            {
                hammerTime = mcHammer.getMongoDBConnection("167.99.237.199:27017","username","password");
            }
            catch (IncorrectUsrNameOrPasswordException e)
            {
                e.printStackTrace();
            }

            MongoDatabase db = hammerTime.getDatabase("books");

            MongoCollection<Document> book = db.getCollection("book");
            MongoCollection<Document> city = db.getCollection("city");
            String cityId = city.find(eq("name", cityName)).first().get("_id").toString();
            List<I_Book> list = new ArrayList();
            if (cityId != null)
            {
                MongoCursor<Document> books = book.find(eq("cities", cityId)).iterator();

                while (books.hasNext())
                {
                    Document doc = books.next();
                    list.add(new Book(doc.get("title").toString(), doc.get("release_date").toString(), new Author(doc.get("author").toString())));
                }
            }
            return list;

        }
        catch (MongoException game)
        {
            throw game;
        }
        finally
        {
            hammerTime.close();

        }


    }

    @Override
    public List<I_City> getAllCitiesMentionedInBook(String bookTitle)
    {

        MongoClient hammerTime = null;

        try
        {
            MongoConnector mcHammer = new MongoConnector();
            List<I_City> list = new ArrayList();

            try
            {
                hammerTime = mcHammer.getMongoDBConnection("167.99.237.199:27017", "username","password");
            }
            catch (IncorrectUsrNameOrPasswordException e)
            {
                e.printStackTrace();
            }
            MongoDatabase db = hammerTime.getDatabase("books");
            MongoCollection<Document> book = db.getCollection("book");
            MongoCollection<Document> city = db.getCollection("city");
            Document bookId = book.find(eq("title", bookTitle)).first();
            if (bookId != null)
            {
                List<String> cityID = (ArrayList) bookId.get("cities");
                String[] arr = cityID.toArray(new String[0]);
                MongoCursor<Document> cities = city.find(in("_id", arr)).iterator();
                while (cities.hasNext())
                {
                    Document doc = cities.next();
                    Document loco = (Document) doc.get("location");
                    List coor = (ArrayList) loco.get("coordinates");
                    list.add(new City(doc.getString("name"), new Coordinate( Double.valueOf(coor.get(1).toString()) ,Double.valueOf(coor.get(0).toString()))));
                }
            }
            return list;
        }
        catch (MongoException game)
        {
            throw game;
        }
        finally
        {
            hammerTime.close();
        }
    }

    @Override
    public List<I_Book> getAllBooksWrittenByAuthor(String authorName)
    {
        MongoClient hammerTime = null;

        try
        {
            MongoConnector mcHammer = new MongoConnector();
            try
            {
                hammerTime = mcHammer.getMongoDBConnection("167.99.237.199:27017","username","password");
            }
            catch (IncorrectUsrNameOrPasswordException e)
            {
                e.printStackTrace();
            }
            MongoDatabase db = hammerTime.getDatabase("books");

            MongoCollection<Document> book = db.getCollection("book");
            MongoCollection<Document> auth = db.getCollection("auths");
            MongoCursor<Document> books = book.find(eq("author", authorName)).iterator();
            List<I_Book> list = new ArrayList();
            while (books.hasNext())
            {
                Document doc = books.next();
                list.add(new Book(doc.get("title").toString(), doc.get("release_date").toString(), new Author(authorName)));
            }
            return list;
        }
        catch (MongoException game)
        {
            throw game;
        }
        finally
        {
            hammerTime.close();
        }
    }

    @Override
    public List<I_City> getCitiesFromManyBooks(List<I_Book> books)
    {
        MongoClient hammerTime = null;

        try
        {
            MongoConnector mcHammer = new MongoConnector();
            List<I_City> list = new ArrayList();

            try
            {
                hammerTime = mcHammer.getMongoDBConnection("167.99.237.199:27017","username","password");
            }
            catch (IncorrectUsrNameOrPasswordException e)
            {
                e.printStackTrace();
            }
            MongoDatabase db = hammerTime.getDatabase("books");
            MongoCollection<Document> book = db.getCollection("book");
            MongoCollection<Document> city = db.getCollection("city");
            if (books != null && !books.isEmpty())
            {
                String[] bookList = new String[books.size()];
                for (int i = 0; i < books.size(); i++)
                {
                    bookList[i] = books.get(i).getTitle();
                }

                MongoCursor<Document> book_objs = book.find(in("title", bookList)).iterator();
                List<String> cityList = new ArrayList<>();
                while (book_objs.hasNext())
                {
                    Document doc = book_objs.next();
                    List<String> cities = (ArrayList) doc.get("cities");
                    for (String city_obj : cities)
                    {
                        if (!cityList.contains(city_obj))
                        {
                            cityList.add(city_obj);
                        }
                    }
                }
                if (!cityList.isEmpty())
                {
                    MongoCursor<Document> cities = city.find(in("_id", cityList.toArray(new String[0]))).iterator();
                    while (cities.hasNext())
                    {
                        Document doc = cities.next();
                        Document loco = (Document) doc.get("location");
                        List coor = (ArrayList) loco.get("coordinates");
                        list.add(new City(doc.getString("name"), new Coordinate( Double.valueOf(coor.get(1).toString()) ,Double.valueOf(coor.get(0).toString()))));
                    }
                }
            }
            return list;
        }
        catch (MongoException game)
        {
            throw game;
        }
        finally
        {
            hammerTime.close();
        }
    }

    @Override
    public List<I_Book> getCitiesCloseToGeoLocation(I_Coordinate geoLocationCoordinate)
    {


        MongoClient hammerTime = null;

        try
        {
            MongoConnector mcHammer = new MongoConnector();


            try
            {
                hammerTime = mcHammer.getMongoDBConnection("167.99.237.199:27017", "username","password");
            }
            catch (IncorrectUsrNameOrPasswordException e)
            {
                e.printStackTrace();
            }
            MongoDatabase db = hammerTime.getDatabase("books");
            MongoCollection<Document> book = db.getCollection("book");
            double[] currentLoc = new double[]{
                    geoLocationCoordinate.getLang(),
                    geoLocationCoordinate.getLat()
            };
            BasicDBObject myCmd = new BasicDBObject();
            myCmd.append("geoNear", "city");
            myCmd.append("near", currentLoc);
            myCmd.append("spherical", true);
            myCmd.append("maxDistance", (double) 200 / 12756.2);
            Document myResults = db.runCommand(myCmd);
            List<Document> res = (ArrayList) myResults.get("results");
            List<String> cities = new ArrayList();
            for (Document doc : res)
            {
                Document obj = (Document) doc.get("obj");
                cities.add(obj.getString("_id"));
            }

            MongoCursor<Document> book_objs = book.find(in("cities", cities.toArray(new String[0]))).iterator();
            List<I_Book> list = new ArrayList();
            while (book_objs.hasNext())
            {
                Document doc = book_objs.next();
                list.add(new Book(doc.getString("title"), doc.getString("release_date"), new Author(doc.getString("author"))));
            }
            return list;
        }
        catch (MongoException game)
        {
            throw game;
        }
        finally
        {
            hammerTime.close();
        }

    }
}
