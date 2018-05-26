package cphbusiness.group6.api;

import com.google.gson.Gson;
import cphbusiness.group6.Controllers.MongoController;
import cphbusiness.group6.Controllers.Neo4JController;
import cphbusiness.group6.interfaces.entities.I_Book;
import cphbusiness.group6.interfaces.entities.I_City;
import cphbusiness.group6.interfaces.entities.I_Coordinate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Neo4jAPI
{


    Neo4JController mcHammer = new Neo4JController();

    @CrossOrigin
    @RequestMapping(value = "/api/neo4j/books/{city}", method = RequestMethod.GET, produces = "application/json")
    public List<I_Book> getBooks(@PathVariable("city") String city)
    {

        return mcHammer.getAllBooksThatMentionCity(city);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/neo4j/plot/{book}", method = RequestMethod.GET, produces = "application/json")
    public List<I_City> plotCities(@PathVariable("book") String book)
    {


        return mcHammer.getAllCitiesMentionedInBook(book);

    }

    @CrossOrigin
    @RequestMapping(value = "/api/neo4j/booksplot/{author}", method = RequestMethod.GET, produces = "application/json")
    public Map getBooksAndPlot(@PathVariable("author") String author)
    {

        List<I_Book> list = mcHammer.getAllBooksWrittenByAuthor(author);
        List<I_City> list2 = mcHammer.getCitiesFromManyBooks(list);

        Map<String, List> map = new HashMap();
        map.put("books", list);
        map.put("cities", list2);

        return map;
    }

    @CrossOrigin
    @RequestMapping(value = "/api/neo4j/vicinity/{lat}/{lon}", method = RequestMethod.GET, produces = "application/json")
    public List<I_Book> cityVicinity(@PathVariable("lat") double lat, @PathVariable("lon") double lon)
    {


        return mcHammer.getCitiesCloseToGeoLocation(new I_Coordinate()
        {
            @Override
            public double getLang()
            {
                return lon;
            }

            @Override
            public double getLat()
            {
                return lat;
            }
        });
    }
}
