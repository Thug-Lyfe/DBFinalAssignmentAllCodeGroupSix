
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
Here we first need to stop the service, delete the graph.db import a new graph.db and start it up again
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
