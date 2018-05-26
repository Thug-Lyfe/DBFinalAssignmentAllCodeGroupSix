mongoimport --db booktest --collection auths --file mongo_auth.json
mongoimport --db booktest --collection books --file mongo_book.json --jsonArray
node --max_old_space_size=2000000 .\reader_v2.js

mongoimport -u user -p password --db books --collection auth --authenticationDatabase admin --file mongo_auth.json



## from outside mongodb

1. docker cp $(pwd)\csvs_backup\mongo_auth.json mongodb:/mongo_auth.json
2. docker cp $(pwd)\csvs_backup\mongo_book.json mongodb:/mongo_book.json
3. docker cp $(pwd)\csvs_backup\mongo_city.json mongodb:/mongo_city.json

## mongodb -> mongo admin -u user -p password

1. use books
2. db.createCollection('book')
3. db.createCollection('auths')
4. db.createCollection('city')

## in bash inside mongodb container:

1. mongoimport -u user -p password --db books --collection auths --authenticationDatabase admin --file mongo_auth.json
2. mongoimport -u user -p password --db books --collection book --authenticationDatabase admin --file mongo_book.json
3. mongoimport -u user -p password --db books --collection city --authenticationDatabase admin --file mongo_city.json


# psql

### from ouside container:

1. docker cp $(pwd)\csvs_backup\psql_book.csv psql:psql_book.csv
2. docker cp $(pwd)\csvs_backup\psql_author.csv psql:psql_author.csv
3. docker cp $(pwd)\csvs_backup\psql_city.csv psql:psql_city.csv
4. docker cp $(pwd)\csvs_backup\psql_mention.csv psql:psql_mention.csv


## psql -> docker exec -it psql bash -c "psql -U postgres -W" (-W prompts for password)

1. create table t_auth(id int primary key,name varchar(250));
2. create table t_book(id int primary key,filename varchar(50),auth_ID int references t_auth(id),name varchar(1000),release_date varchar(50));
500 characters for the name might seam long... but there are books with longer titles.... some authors need better naming skills.
3. create table t_city(id int primary key,name varchar(100), latitude numeric ,longitude numeric);
4. create table t_ment(id serial PRIMARY KEY, book_ID int references t_book(id),city_ID int references t_city(id));

5. copy t_auth(id,name) from '/psql_auth.csv' DELIMITER ',' CSV HEADER;
6. copy t_book(id,filename,auth_ID,name,release_date) from '/psql_book.csv' DELIMITER ',' CSV HEADER;
7. copy t_city(id,name,latitude,longitude) from '/psql_city.csv' DELIMITER ',' CSV HEADER;
8. copy t_ment(book_ID,city_ID) from '/psql_mention.csv' DELIMITER ',' CSV HEADER;


# neo4j -> docker 

1. docker cp $(pwd)\csvs_backup\neo4j_auth_book.csv neo4j:/neo4j_auth_book.csv
2. docker cp $(pwd)\csvs_backup\neo4j_author.csv neo4j:/neo4j_author.csv
3. docker cp $(pwd)\csvs_backup\neo4j_book.csv neo4j:/neo4j_book.csv
4. docker cp $(pwd)\csvs_backup\neo4j_city.csv neo4j:/neo4j_city.csv
5. docker cp $(pwd)\csvs_backup\neo4j_mention.csv neo4j:/neo4j_mention.csv

6. docker exec -it neo4j bash
7. neo4j stop
8. rm -rf data/databases/graph.db
```bash
neo4j-admin import \
    --nodes:book neo4j_book.csv \
    --nodes:author neo4j_author.csv \
    --nodes:city neo4j_city.csv \
    --relationships:Written_by neo4j_auth_book.csv \
    --relationships:Mentions neo4j_mention.csv \
    --ignore-missing-nodes=true \
    --ignore-duplicate-nodes=true \
    --id-type=STRING
```
9. neo4j-admin import --nodes:book neo4j_book.csv --nodes:author neo4j_author.csv --nodes:city neo4j_city.csv --relationships:Written_by neo4j_auth_book.csv --relationships:Mentions neo4j_mention.csv --ignore-missing-nodes=true --ignore-duplicate-nodes=true --id-type=STRING

10. neo4j start
