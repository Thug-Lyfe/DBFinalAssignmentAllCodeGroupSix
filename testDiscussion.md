## Benchmark Discussion

We used three different databases to handle our data. However one of the databases has to be the fastest for this kind of data but which one?
To figure this our we did some benchmark tests to see how fast each database would handle five different calls for the queries. We tested three different times, the time it took for the backend controller to execute the query, the time it took for the api to call a rest call on the production server, and how long it took for our application to run the different queries from a user input perspective. The results were rather surprising.

### Mongo

Lets start with the slowest of the three databases for this type of data extraction, MongoDb. 

![alt text](https://github.com/Thug-Lyfe/DBFinalAssignmentAllCodeGroupSix/blob/master/pics/MongoBench.png "Mongo Benchmark")

The image above is a list of the results from running bench marks on three aspects mentioned earlier. Con tests are the controller tests, Api the api, and react the frontend.
As we can see from the image the controller tests were actually the slowest tests. This created a lot of confusion in our group as we couldn't understand how the frontend was making our queries faster. We eventually did realize though that it was due to the production server calling already compiled queries where as the controller created a new instance of it recompiling the code for that specific test.
This means the controller tests don't help us with testing the difference in speed between the frontend and backend, however we did feel that it was a very interesting test to see to show that the queries were actually slower than the frontend getting them via restcalls.
Comparing the api tests to the frontend tests it seems that there is a clear difference in speed. On nearly all of the queries the frontend takes over 100% more time than the rest call itself. This massive difference in time could also be a cause of the tests for the frontend maybe not executing as fluidly as possible, but it wouldn't make up for everything. The most interesting time however is query 3. For some reason query 3 was the slowest for the api tests but it was only the second slowest for frontend tests. This is the only time where the frontend was less than 100% slower which we are unsure as to why it is so.
Although we haven't looked at the other two databases yet we are still going to conclude that mongo db is significantly slower for this type of data handling for some obvious reasons. Mongo is a document type nosql database which means for it to find the relations it has to search through every single document to find the data fitting what was searched for. For example each book document has a list of all cities mentioned in that book. This means there is a lot of extra data the database has to go through on each search. The more data we want to get the longer mongo will take to get it when it comes to relations data of this type.

### Postgres

Now lets take a look at how an SQL database handled this type of data.

![alt text](https://github.com/Thug-Lyfe/DBFinalAssignmentAllCodeGroupSix/blob/master/pics/PsqlBench.png "PSQL Benchmark")

The image above contains the same data as the mongo image except for our postgres database instead. Already its clearly evident that sql databases are way faster than mongo for this type of data handling.
Sql's usage of relations with primary and foreign makes search for relations between tables a lot easier as you only have to look at the data searched for rather than going through every single document to find it. Although psql is significantly faster than mongo we see a similar result for these tests, which roughly correlates to the frontend taking about 100% longer than just the api calls would take.
Here we again have a strange outlier though. The first frontend test seems to have taken quite a bit longer than the rest, we are not 100% certain as to why that is, however our best guess is due to the way our tests work. The first time we call the test it switched to using the postgres db on the frontend, this doesn't happen on the following tests as they are run in synchronization which means there may be some extra time unaccounted for when changing the db on the frontend.

### Neo4j

Now lets look at the last of the three databases Neo4j.

![alt text](https://github.com/Thug-Lyfe/DBFinalAssignmentAllCodeGroupSix/blob/master/pics/Neo4jBench.png "Neo4j Benchmark")


Neo4j had very similar times to psql for every single query. Both the frontend and api calls were within a second of each other between these 2 databases. However neo4j is slightly faster on average than psql. The larger the data sent the bigger the difference in the two databases speeds is apparent.
From our results though we can't make a clear distinction as to which database is the absolute best however the worst is very apparent. If we were to chose a best database though for this kindof data take both the benchmark tests as well as how easy the queries are to write, then neo4j wins by a landslide.
