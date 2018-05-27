## Database Engines

We ran 3 different databases, a SQL database, a NoSQL database and a graph database. 

For SQL we ran PostgreSQL as that is what we have worked with during the semester.
For NoSQL we ran MongoDB, which we had also covered during this semester as well as previous semesters. 
For the graph database we ran Neo4J, which we had been introduced to this semester.

Each of our databases are hosted on the production server in each their own docker images. We started them with the following docker commands:   

```bash
docker run -d
	-p=27017:27017 
	-v $(pwd)/mongodb-data:/data/db 
	--env MONGO_INITDB_ROOT_USERNAME={username} 
	--env MONGO_INITDB_ROOT_PASSWORD={password} 
	--name mongodb 
	mongo:latest mongod --auth
```

```bash
docker run -d
	-p 5432:5432 
	-v $(pwd)/psql-data:/var/lib/postgresql/data 
	--name psql 
	postgres:alpine
```

```bash
docker run -d
	-p=7474:7474 
	-p=7687:7687 
	-v $(pwd)/neo4j-data:/data 
	--env NEO4J_AUTH={username}/{password} 
	--name neo4j 
	neo4j
```

We included authentication on all the databases so that our data was somewhat secure. Previous semester we experienced a “hack” where someone or a script inserted a collection into our mongo database. 

For the PostgreSQL database we setup the auth user in the user table. 

## Database Data Models

### MongoDB

The mongo database we set up with one db called books where we had collections of books, cities and authors. The collections where structured as follows:

```{json error=TRUE}
book: [{
    "_id": "book1",
    "filename": "10001.txt",
    "title": "Abocolocyntosis",
    "author": "Seneca, Lucius Annaeus",
    "release_date": "2003-11-01"
    "cities": [cityIDs...]
}{...}]

auths: [{
    "_id": "auth0",
    "name": "Jefferson, Thomas"
}{...}]

city: [{
    "_id": "city122685",
    "name": "London",
    "location": {
        "type": "Point",
        "coordinates": [-84.08326,37.12898]
    }
}{...}]
```

### PostgreSQL

For the SQL database we set up as shown in the following ER-Diagram.  

![alt text](https://raw.githubusercontent.com/Thug-Lyfe/DBFinalAssignmentAllCodeGroupSix/master/pics/photo_2018-05-27_18-45-55.jpg "SQL ER-Diagram")


### Neo4J

In Neo4J we made the database with 3 types of nodes (city, book and author), then we had 2 types of relations (written_by and mentions)

Visually the data would look as below:
![alt text](https://raw.githubusercontent.com/Thug-Lyfe/DBFinalAssignmentAllCodeGroupSix/master/pics/photo_2018-05-27_18-58-20.jpg "Graph Nodes and Relations")

This can also be seen when we queary through the Neo4J webinterface using the query: 
```cypher
MATCH (c:city {name : 'Roskilde'})<-[:Mentions]-(a :book)<-[:Written_by]-(b :author) return distinct a, b, c
```

Example Data from Neo4j Webinterface:

![alt text](https://raw.githubusercontent.com/Thug-Lyfe/DBFinalAssignmentAllCodeGroupSix/master/pics/photo_2018-05-27_19-09-08.jpg "Example Data from Neo4j Webinterface")

