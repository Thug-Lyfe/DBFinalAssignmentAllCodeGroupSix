# TestFinalAssignmentAllCodeGroupSix

| file name         | Description |
|-------------------|-------------|
| GertenBergTheGame | A how to setup of all three databases, with data files to initate |
| GutenBergBackend  | A repository containing our backend |
| GutenBorgFrontend | A repository containing our frontend |
| GutenBurgReader   | A repository with the js script that extract data from books |




## Application Data Modeling 
When we started the Gutenberg project, one of the first things we did when trying to determine the design of the application, was to look at the problem domain and do the “noun game”. The purpose of this is, of course, was to identify potential entities in the domain so we could map them to their software equivalent. 

During this process we managed to isolate the following things as useful entities in our application.

* Book
* Author
* City
* Coordinate

This ended up in a class design that looks like this:

![alt text](https://raw.githubusercontent.com/Thug-Lyfe/DBFinalAssignmentAllCodeGroupSix/master/pics/classDiagramEntities.png "Class diagram")

The specific reason why we chose to operate with just these four entities has to do with the problem/assignment we were asked to solve, namely that the end user functionality we needed to provide concerns itself only with one or more of the above, nothing else.

## Business Logic

During the our application’s design phase we had to make a choice in regards to how we would handle the fact that multiple cities in the world can share a name (Large cities often share a name with other cities, especially if the “original” city belongs to a nation with a history of colonization, such as England).

After some pondering we, however, discovered that we have little opportunity to distinguish one Oxford from another, therefore we decided to handle this issue by just returning all the cities that share a name, such that a search for London will return all cities by the that name.
Below is a screenshot of this decision “in action” - we searched for the book: <i>“Charles I Makers of History”</i> which mention a lot of english city names, but as you can see many former (and present) british dependencies and colonies also has cities named London, Oxford etc. 

![alt text](https://raw.githubusercontent.com/Thug-Lyfe/DBFinalAssignmentAllCodeGroupSix/master/pics/frontendScreenShot.PNG "frontend screenshot")








## Data extraction
As we now knew how we wanted the data to be modeled, it was time to extract the data.  
We used two js files, one for the main extraction and one for small changes we found out later needed to made.  
Such as unique ids cross nodes in neo4j. We used js to make use of easy multithreading and async functions.

The code for the reader can be found here: https://github.com/Thug-Lyfe/DBFinalAssignmentAllCodeGroupSix/blob/master/GutenBurgReader/reader_v2.js  
The small fixer can be found here: https://github.com/Thug-Lyfe/DBFinalAssignmentAllCodeGroupSix/blob/master/GertenBergTheGame/fixer.js  
The city scan thread code can be found here: https://github.com/Thug-Lyfe/DBFinalAssignmentAllCodeGroupSix/blob/master/GutenBurgReader/cityscan.js  
The finished csv files can be found here: https://github.com/Thug-Lyfe/DBFinalAssignmentAllCodeGroupSix/tree/master/GertenBergTheGame/csvs_backup  

1. Is to read the files.
2. Deal with the small amount of files that does not have an id or lacking meta data in cache.  
ex. of one of the exeptions
```Javascript
if (filename.indexOf("G-") == 0) {
  title_ind = content.indexOf("\n") + 1
  title = content.substring(title_ind, content.indexOf("1", title_ind)).replace(/(\r\n\t|\n|\r\t|\*|\r)/gm, "");
  auth = "unknown";
  release = "unknown";
  id = filename
  successFunc_meta(id, dirname.substring(6, dirname.length), auth, title, release, (book_id, mongo_temp) => {
    cpQ(content, book_id, mongo_temp, (res) => {
      callback(res)
    })
  });
}
```
3. The rest gets the same treatment:  
The function below, takes the id of a file, finds the meta data file from the cache folder and extracts the meta data.  
If succesful it will follow the chain succesFunc_meta -> cpQ
```Javascript
fs.readFile("cache/epub/" + id + "/pg" + id + ".rdf", 'utf-8', function (err, meta_data) {
  if (meta_data != undefined && meta_data != null) {
    if (meta_data.indexOf("<dcterms:title>") != -1) {
      title = meta_data.substring(meta_data.indexOf("<dcterms:title>") + 15, meta_data.indexOf("</dcterms:title>")).replace(/(\r\n\t|\n|\r\t|\*|\r)/gm, " ");
    } else {
      title = "unknown";
    }
    if (meta_data.indexOf("<pgterms:name>") != -1) {
      auth = meta_data.substring(meta_data.indexOf("<pgterms:name>") + 14, meta_data.indexOf("</pgterms:name>")).replace(/(\r\n\t|\n|\r\t|\*|\r)/gm, " ");
    } else {
      auth = "unknown";
    }
    if (meta_data.indexOf("</dcterms:issued>") != -1) {
      release = meta_data.substring(meta_data.indexOf("</dcterms:issued>") - 10, meta_data.indexOf("</dcterms:issued>")).replace(/(\r\n\t|\n|\r\t|\*|\r)/gm, " ");
    } else {
      release = "unknown";
    }
  } else {
    failFunc(filename, "meta_data err: ", id, dirname);
  }
  if (title == "unknown") {
    failFunc(filename, "unknown title: ", id, dirname);
  } else {
    successFunc_meta(id, dirname.substring(6, dirname.length), auth, title, release, (book_id, mongo_temp) => {
      cpQ(content, book_id, mongo_temp, (res) => {
        callback(res)
      })
    });
  }
})
```
4. Send book content to childpool for cityscanning
5. Append city info to csv/json files  
The succesful_meta function appends all the relavant csv/json files with books and authors.  
The cpQ function is a two part function with the cp variable being our childpool, a msgqueue for threads.  
```Javascript (childpool)
const pool = require('fork-pool');
let cp = new pool('./cityscan.js', null, null, {});
```
the cp gets the whole book (minus the intro and ourtro of the gutenberg project)  
the rest of the function is to append to the csv/json files with city information
```Javascript (city appender)
let cpQ = async function (content, book_id, mongo_temp, callback) {
    cp.enqueue(content, (err, list) => {
        list.stdout.city_list.forEach(function (city_id) {
            if (!city_ids.includes(city_id)) {
                city_ids.push(city_id)
                appendToCsv([city_id, "\"" + cities[city_id].name + "\"", cities[city_id].lat, cities[city_id].lon], "psql_city.csv")
                appendToJson({
                    _id: "city" + city_id,
                    name: cities[city_id].name,
                    location: { type: 'Point', coordinates: [cities[city_id].lon, cities[city_id].lat] }
                }, "mongo_city.json")
            }
            mongo_temp.cities.push("city" + city_id)
            appendToCsv([book_id, city_id], "psql_mention.csv")
        })
        appendToJson(mongo_temp, "mongo_book.json")

        callback("books fin: " + ++count_fin + "\tbooks remaining: " + (count - count_fin) + "\t% done: " + Math.floor(count_fin * 10000 / count) / 100 + "\ttotal time: " + (Date.now() - totalTime) / 1000 + "s", )
    })

}
```
This here is the actual cityscanner that searches the content of the book for cities found in the "all-the-cities" npm pack
```Javascript (cityscanner)
const cities = require("all-the-cities");
async function scanCities(content) {
    let index = content.indexOf("*** START OF THIS PROJECT GUTENBERG") + 35;
    let end = content.indexOf("*** END OF THIS PROJECT GUTENBERG");
    if (end == -1) {
        end = content.length
    }
    content = content.substring(index, end)
    let reg = new RegExp(/\b^[A-Z].*?\b/, 'gm')
    let found = content.match(reg)
    let list = [];
    cities.forEach(function (city, index) {
        if (city.name.match(/[^\w\*]/, 'gm') == null) {
            if (found.includes(city.name)) {
                list.push(index);
            }
        } else {
            if (content.indexOf(" " + city.name + " ") != -1) {
                list.push(index);
            }
        }
    })
    return list
}
process.on('message', async (content) => {
    const list = await scanCities(content);
    process.send({ city_list: list })
})
```
## Table creation and Data importing
With the multithreading this still took around 10-11 hours but after it was done we could start importing the data.  
As we allready knew how we wanted to model the data we could make the following table creations:  
#### MongoDB
We had three collections for mongo as we thought we had way too many city relations pr. book to not have the data in a seperate collection  
We also made the auths collection, even though we never got around to using it, as it was to be an additional feature if the time was found to implement it.
```Bash (Mongodb)
use books
db.createCollection('book')
db.createCollection('auths')
db.createCollection('city')

mongoimport -u user -p password --db books --collection auths --authenticationDatabase admin --file mongo_auth.json
mongoimport -u user -p password --db books --collection book --authenticationDatabase admin --file mongo_book.json
mongoimport -u user -p password --db books --collection city --authenticationDatabase admin --file mongo_city.json
```
#### PSQL
For psql we needed four tables, authors, books, cities and the many to many relation between cities and books.
```Bash(PSQL)
create table t_auth(id int primary key,name varchar(250));
create table t_book(id int primary key,filename varchar(50),auth_ID int references t_auth(id),name varchar(1000),release_date varchar(50));
create table t_city(id int primary key,name varchar(100), latitude numeric ,longitude numeric);
create table t_ment(id serial PRIMARY KEY, book_ID int references t_book(id),city_ID int references t_city(id));

copy t_auth(id,name) from '/psql_auth.csv' DELIMITER ',' CSV HEADER;
copy t_book(id,filename,auth_ID,name,release_date) from '/psql_book.csv' DELIMITER ',' CSV HEADER;
copy t_city(id,name,latitude,longitude) from '/psql_city.csv' DELIMITER ',' CSV HEADER;
copy t_ment(book_ID,city_ID) from '/psql_mention.csv' DELIMITER ',' CSV HEADER;
```
#### Neo4J
The two other dbs do not require any header, (the psql one directly ignores it in our case). But the Neo4J has some specific requirements to the header:  
relation csv has to be: :START_ID,:END_ID  
While node csv has to be node_id:ID,filename,authID,name,release_date.  
(ofc other proteries can thrown in instead of ours, but one of them needs the :ID tag for it to work)  
From here we first need to stop the service, delete the graph.db import a new graph.db and start it up again
```Bash
neo4j stop
rm -rf data/databases/graph.db
neo4j-admin import \
    --nodes:book neo4j_book.csv \
    --nodes:author neo4j_author.csv \
    --nodes:city neo4j_city.csv \
    --relationships:Written_by neo4j_auth_book.csv \
    --relationships:Mentions neo4j_mention.csv \
    --ignore-missing-nodes=true \
    --ignore-duplicate-nodes=true \
    --id-type=STRING
neo4j start
```


## Benchmark Discussion

For this application we used three different databases to handle our data (PostgresSQL, MongoDB and Neo4J). The purpose of our benchmark tests is to determine which of the three is the "best" database to use for this specific project.

To determine the answer to this question, we designed our benchmark tests to see how fast each database would handle five different calls to each implemented query, with the same five search terms (the queries being: query 1: "Find books that mention city", query 2: "Plot cities mentioned by a book", query 3: "Plot cities mentioned by books written by a given author" and query 4: "List all books that mention cities in the vicinity of a geolocation").

For good measure we decided to record benchmarks at two different points in the system, namely at the API level (which is to say that we just call the API and record the time it takes for the response to arrive - similar to if we just used curl) and lastly the frontend level (where we record the time it takes for the frontend from the moment we send the request to the moment rendering of the result is complete).

We conducted the benchmark tests by running each query at each different point five times for the three databases and then calculated the average time it took to complete.

### Search Terms
| Cities                    | Copenhagen, London, Oxford, Roskilde & Ushuaia |
| Book Titles               | Byron, Denmark, Yeast: a Problem, Phantom Fortune, a Novel & The Gold Diggings of Cape Horn: A Study of Life in Tierra del Fuego and Patagonia  |
| Authors                   | Bourne, Edward Gaylord, Various, Hodgson, William Hope Goldsmith, Oliver Spears & John Randolph   |
| Coordinates(geolocations) | 43.4052, 87.1952 (geographical center of asia) - 10, 10 (just a random coordinate) - 53.3439, 23.0622 (geographical center of europe) - 55.682319, 12.563728 (Copenhagen) - 38.883139, -77.016278 (Alexandria in the US) |

### Test Enviroment
| Hardware | Client   | Host                                   |
|----------|----------|----------------------------------------|
| CPU      | i7:7700k | 1 Virtual CPU (Intel Xeon) 1.8-3.0 GHz |
| Memory   | 16 GB    | 3 GB                                   |
| Storage  | SSD disc | SSD disc                               |

Browser for frontend tests: Firefox



### MongoDB (Mongo)

Lets start with the slowest (as it turns out) of the three databases for this type of operation; MongoDB. 
**type**|**query**|**avg time taken**
:-----:|:-----:|:-----:
RestApi|1|1,30 s
RestApi|2|0,35 s
RestApi|3|2,79 s
RestApi|4|1,38 s
Frontend|1|3,99 s
Frontend|2|1,09 s
Frontend|3|3,95 s
Frontend|4|2,24 s

From the test we can see that the frontend takes a significant amount of time to process and render the search results (genrally more than twice the time of the API call itself)
We consider this to be reasonable but it might change (for better or worse) if we were to benchmark the queries on a different computer, since the frontend javascript is executed by the
client's own computer.


### Postgres (PSQL)

Next up let's take a look at how Postgres did!
**type**|**query**|**avg time taken**
:-----:|:-----:|:-----:
RestApi|1|1,04 s
RestApi|2|0,90 s
RestApi|3|1,16 s
RestApi|4|0,94 s
Frontend|1|3,96 s
Frontend|2|1,83 s
Frontend|3|1,60 s
Frontend|4|2,24 s

From the table it is readily evident that PSQL handles this problem better than MongoDB. When you stop to think about it, 
the reason for this becomes obvious - since Mongo is document based each document might contain a number of things that's irrevant to the seach
but Mongo still has to trawl through the data to provide an answer. PSQL however, with it's relation system of primary- and forign keys handles
this sort of search with a lot of relations (such as the relation between authors and books) much better than Mongo.

Again we see the same trend as with Mongo, where the front end takes atleast twice as long to render the results as the HTTP request itself takes.


### Neo4J (Neo)

Lastly let's take a look at the results of our Neo benchmarks.
**type**|**query**|**avg time taken**
:-----:|:-----:|:-----:
RestApi|1|0,90 s
RestApi|2|0,37 s
RestApi|3|0,89 s
RestApi|4|0,99 s
Frontend|1|3,65 s
Frontend|2|1,48 s
Frontend|3|2,04 s
Frontend|4|2,58 s

Overall Neo is a little bit faster than PSQL in it's execution speed. Interestingly enough the larger the amount of data that the query needs
to retrieve, the more readily apparent the difference between Neo and PSQL becomes, with Neo pulling ahead when the amount of data increases.


### Best Choice of Database

Now comes the point where we have to make a recommendation as to which database engine is the most optimal for this application.\
Juding solely from the benchmark tests Neo4J must be said to be the overall fastest database, with PSQL a close second.
There is however another area where Neo4J pulls ahead of the competition, and that is the ease of the setup. \
The reason for this is that the design of a Neo4J database is, in our humble oppinion, much easier than creating a lot of tables and relations, and since the data needs to be "massaged" in quite a similar way to PSQL, Neo4J ends up being easier in the grand sceme of things.\
Besides the above, our entire group is in agreement, Neo4J's Cypher query language is much easier than regular SQL to write, again making\
Neo4J a brezze to work with compared to PSQL.
