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



## Benchmark Discussion

For this application we used three different databases to handle our data (PostgresSQL, MongoDB and Neo4J). The purpose of our benchmark tests is to determine which of the three is the "best" database to use for this specific project.

To determine the answer to this question, we designed our benchmark tests to see how fast each database would handle five different calls to each implemented query, with the same five search terms (the queries being: query 1: "Find books that mention city", query 2: "Plot cities mentioned by a book", query 3: "Plot cities mentioned by books written by a given author" and query 4: "List all books that mention cities in the vicinity of a geolocation").


For good measure we decided to record benchmarks at two different points in the system, namely at the API level (which is to say that we just call the API and record the time it takes for the response to arrive - similar to if we just used curl) and lastly the frontend level (where we record the time it takes for the frontend from the moment we send the request to the moment rendering of the result is complete).

We conducted the benchmark tests by running each query at each different point five times for the three databases and then calculated the average time it took to complete.

### Search Terms
#### Cities
Copenhagen, London, Oxford, Roskilde & Ushuaia
#### Book titles
Byron, Denmark, Yeast: a Problem, Phantom Fortune, a Novel & The Gold Diggings of Cape Horn: A Study of Life in Tierra del Fuego and Patagonia 
#### Authors
Bourne, Edward Gaylord, Various, Hodgson, William Hope Goldsmith, Oliver Spears & John Randolph
#### Coordinates(geolocations)
43.4052, 87.1952 (geographical center of asia) - 10, 10 (just a random coordinate) - 53.3439, 23.0622 (geographical center of europe) - 55.682319, 12.563728 (Copenhagen) - 38.883139, -77.016278 (Alexandria in the US) 

### Test Enviroment
#### Client
Test system specs: 
i7:770k

16 Gb memor

SSD disc

Browser for frontend tests: Firefox

#### Host
3 GB memory

1 virtual CPU (Intel Xeon) ranging from 1.8-3.0 GHz (depending on the mood of digitalocean on that particular time)

SSD disc.


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

Now comes the point where we have to make a recommendation as to which database engine is the most optimal for this application.

Juding solely from the benchmark tests Neo4J must be said to be the overall fastest database, with PSQL a close second.
There is however another area where Neo4J pulls ahead of the competition, and that is the ease of the setup. 

The reason for this is that the design of a Neo4J database is, in our humble oppinion, much easier than creating a lot of tables and relations,
and since the data needs to be "massaged" in quite a similar way to PSQL, Neo4J ends up being easier in the grand sceme of things.

Besides the above, our entire group is in agreement, Neo4J's Cypher query language is much easier than regular SQL to write, again making
Neo4J a brezze to work with compared to PSQL.
