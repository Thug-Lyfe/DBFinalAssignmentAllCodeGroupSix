package cphbusiness.group6;

import cphbusiness.group6.Connectors.MongoConnector;
import cphbusiness.group6.Connectors.Neo4JConnector;
import cphbusiness.group6.Connectors.PSQLConnector;
import cphbusiness.group6.exceptions.IncorrectUsrNameOrPasswordException;
import cphbusiness.group6.interfaces.connectors.I_MONGOConnector;
import cphbusiness.group6.interfaces.connectors.I_NEO4JConnector;
import cphbusiness.group6.interfaces.connectors.I_PSQLConnector;

import java.sql.SQLException;

public class MainTilTestBrug
{

    public static void main(String[] args) throws SQLException, IncorrectUsrNameOrPasswordException
    {
//        I_MONGOConnector mongoConnector = new MongoConnector();
//        mongoConnector.getMongoDBConnection("167.99.237.199:27017", "mongo", "jesus");
//
//        I_NEO4JConnector neo4JConnector = new Neo4JConnector();
//        neo4JConnector.getNeo4JConnection("167.99.237.199:7687","neo4j","jesus");

//        I_PSQLConnector psqlConnector = new PSQLConnector();
//        psqlConnector.getPSQLConnection("167.99.237.199:5432", "postgres", "jesus");
    }

}
