query 1: get books from city\
query 2: get cities from book\
query 3: get cities and books from author\
query 4: get books from coordinates\

big table:

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

3 smaller tables ordered by part
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
