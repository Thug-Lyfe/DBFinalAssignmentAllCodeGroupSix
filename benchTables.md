query 1: get books from city\
query 2: get cities from book\
query 3: get cities and books from author\
query 4: get books from coordinates\

Things used in queries:
Cities: 
1. Copenhagen
2. London
3. Oxford
4. Roskilde
5. Ushuaia

Books: 
1. Byron
2. Denmark
3. Yeast: a Problem
4. Phantom Fortune, a Novel
5. The Gold Diggings of Cape Horn: A Study of Life in Tierra del Fuego and Patagonia

Authors:
1. Bourne, Edward Gaylord
2. Various
3. Hodgson, William Hope
4. Goldsmith, Oliver
5. Spears, John Randolph

Coordinates: (latatitude,longitude)
1. 43.4052, 87.1952 (geographical center of asia)
2. 10,10
3. 53.3439, 23.0622 (geographical center of europe)
4. 55.682319, 12.563728 (Copenhagen)
5. 38.883139, -77.016278 (Alexandria in the US)


# ex 1. big table
**part/qeury/db**|**avg time taken pr. query**|**% diff**
:-----:|:-----:|:-----:
Controller,1,Mongo|6,09 s|100,00%
Controller,1,Neo4j|1,33 s|21,87%
Controller,1,Psql|1,59 s|26,08%
Controller,2,Mongo|1,69 s|100,00%
Controller,2,Neo4j|0,98 s|58,01%
Controller,2,Psql|1,06 s|62,56%
Controller,3,Mongo|5,34 s|100,00%
Controller,3,Neo4j|2,33 s|43,56%
Controller,3,Psql|1,84 s|34,34%
Controller,4,Mongo|4,89 s|100,00%
Controller,4,Neo4j|1,33 s|27,13%
Controller,4,Psql|1,43 s|29,20%
RestApi,1,Mongo|1,30 s|100,00%
RestApi,1,Neo4j|0,90 s|69,28%
RestApi,1,Psql|1,04 s|79,59%
RestApi,2,Mongo|0,35 s|39,33%
RestApi,2,Neo4j|0,37 s|41,09%
RestApi,2,Psql|0,90 s|100,00%
RestApi,3,Mongo|2,79 s|100,00%
RestApi,3,Neo4j|0,89 s|31,80%
RestApi,3,Psql|1,16 s|41,43%
RestApi,4,Mongo|1,38 s|100,00%
RestApi,4,Neo4j|0,99 s|72,08%
RestApi,4,Psql|0,94 s|68,34%
Frontend,1,Mongo|3,99 s|100,00%
Frontend,1,Neo4j|3,65 s|91,37%
Frontend,1,Psql|3,96 s|99,30%
Frontend,2,Mongo|1,09 s|59,58%
Frontend,2,Neo4j|1,48 s|81,01%
Frontend,2,Psql|1,83 s|100,00%
Frontend,3,Mongo|3,95 s|100,00%
Frontend,3,Neo4j|2,04 s|51,64%
Frontend,3,Psql|1,60 s|40,36%
Frontend,4,Mongo|2,24 s|86,99%
Frontend,4,Neo4j|2,58 s|100,00%
Frontend,4,Psql|2,24 s|86,73%

# ex 2. 3 smaller tables ordered by part
### Controller
**qeury/db**|**avg time taken pr. query**|**% diff**
:-----:|:-----:|:-----:
1,Mongo|6,09 s|100,00%
1,Neo4j|1,33 s|21,87%
1,Psql|1,59 s|26,08%
2,Mongo|1,69 s|100,00%
2,Neo4j|0,98 s|58,01%
2,Psql|1,06 s|62,56%
3,Mongo|5,34 s|100,00%
3,Neo4j|2,33 s|43,56%
3,Psql|1,84 s|34,34%
4,Mongo|4,89 s|100,00%
4,Neo4j|1,33 s|27,13%
4,Psql|1,43 s|29,20%
### RestApi
**part/qeury/db**|**avg time taken pr. query**|**% diff**
:-----:|:-----:|:-----:
1,Mongo|1,30 s|100,00%
1,Neo4j|0,90 s|69,28%
1,Psql|1,04 s|79,59%
2,Mongo|0,35 s|39,33%
2,Neo4j|0,37 s|41,09%
2,Psql|0,90 s|100,00%
3,Mongo|2,79 s|100,00%
3,Neo4j|0,89 s|31,80%
3,Psql|1,16 s|41,43%
4,Mongo|1,38 s|100,00%
4,Neo4j|0,99 s|72,08%
4,Psql|0,94 s|68,34%
### Frontend
**part/qeury/db**|**avg time taken pr. query**|**% diff**
:-----:|:-----:|:-----:
1,Mongo|3,99 s|100,00%
1,Neo4j|3,65 s|91,37%
1,Psql|3,96 s|99,30%
2,Mongo|1,09 s|59,58%
2,Neo4j|1,48 s|81,01%
2,Psql|1,83 s|100,00%
3,Mongo|3,95 s|100,00%
3,Neo4j|2,04 s|51,64%
3,Psql|1,60 s|40,36%
4,Mongo|2,24 s|86,99%
4,Neo4j|2,58 s|100,00%
4,Psql|2,24 s|86,73%

# ex 3. ordered by db
### Mongo
**part**|**query**|**avg time taken**
:-----:|:-----:|:-----:
Controller|1|6,09 s
Controller|2|1,69 s
Controller|3|5,34 s
Controller|4|4,89 s
RestApi|1|1,30 s
RestApi|2|0,35 s
RestApi|3|2,79 s
RestApi|4|1,38 s
Frontend|1|3,99 s
Frontend|2|1,09 s
Frontend|3|3,95 s
Frontend|4|2,24 s
### Psql
**part**|**query**|**avg time taken**
:-----:|:-----:|:-----:
Controller|1|1,59 s
Controller|2|1,06 s
Controller|3|1,84 s
Controller|4|1,43 s
RestApi|1|1,04 s
RestApi|2|0,90 s
RestApi|3|1,16 s
RestApi|4|0,94 s
Frontend|1|3,96 s
Frontend|2|1,83 s
Frontend|3|1,60 s
Frontend|4|2,24 s
### Neo4J
**part**|**query**|**avg time taken**
:-----:|:-----:|:-----:
Controller|1|1,33 s
Controller|2|0,98 s
Controller|3|2,33 s
Controller|4|1,33 s
RestApi|1|0,90 s
RestApi|2|0,37 s
RestApi|3|0,89 s
RestApi|4|0,99 s
Frontend|1|3,65 s
Frontend|2|1,48 s
Frontend|3|2,04 s
Frontend|4|2,58 s

# ex 4. sorted by query
### query 1
**part**|**db**|**avg time taken**
:-----:|:-----:|:-----:
Controller|Mongodb|6,09 s
RestApi|Mongodb|1,30 s
Frontend|Mongodb|3,99 s
Controller|Psql|1,59 s
RestApi|Psql|1,04 s
Frontend|Psql|3,96 s
Controller|Neo4J|1,33 s
RestApi|Neo4J|0,90 s
Frontend|Neo4J|3,65 s
### query 2
**part**|**db**|**avg time taken**
:-----:|:-----:|:-----:
Controller|Mongodb|1,69 s
RestApi|Mongodb|0,35 s
Frontend|Mongodb|1,09 s
Controller|Psql|1,06 s
RestApi|Psql|0,90 s
Frontend|Psql|1,83 s
Controller|Neo4J|0,98 s
RestApi|Neo4J|0,37 s
Frontend|Neo4J|1,48 s
### query 3
**part**|**db**|**avg time taken**
:-----:|:-----:|:-----:
Controller|Mongodb|5,34 s
RestApi|Mongodb|2,79 s
Frontend|Mongodb|3,95 s
Controller|Psql|1,84 s
RestApi|Psql|1,16 s
Frontend|Psql|1,60 s
Controller|Neo4J|2,33 s
RestApi|Neo4J|0,89 s
Frontend|Neo4J|2,04 s
### query 4
**part**|**db**|**avg time taken**
:-----:|:-----:|:-----:
Controller|Mongodb|4,89 s
RestApi|Mongodb|1,38 s
Frontend|Mongodb|2,24 s
Controller|Psql|1,43 s
RestApi|Psql|0,94 s
Frontend|Psql|2,24 s
Controller|Neo4J|1,33 s
RestApi|Neo4J|0,99 s
Frontend|Neo4J|2,58 s



# ex. 5 alex tables
### Mongo
**query**|**avg api time**|**avg frontend time**|**difference**
:-----:|:-----:|:-----:|:-----:
1|1,30 s|3,99 s|306,75%
2|0,35 s|1,09 s|307,33%
3|2,79 s|3,95 s|141,76%
4|1,38 s|2,24 s|162,57%
### Psql
**query**|**avg api time**|**avg frontend time**|**difference**
:-----:|:-----:|:-----:|:-----:
1|1,04 s|3,96 s|382,72%
2|0,90 s|1,83 s|202,90%
3|1,16 s|1,60 s|138,11%
4|0,94 s|2,24 s|237,18%
### Neo4J
**query**|**avg api time**|**avg frontend time**|**difference**
:-----:|:-----:|:-----:|:-----:
1|0,90 s|3,65 s|404,57%
2|0,37 s|1,48 s|400,05%
3|0,89 s|2,04 s|230,20%
4|0,99 s|2,58 s|259,28%
