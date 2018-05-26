package cphbusiness.group6.Connectors;

import cphbusiness.group6.interfaces.connectors.I_NEO4JConnector;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;


public class Neo4JConnector implements I_NEO4JConnector
{

    Driver driver = null;

    @Override
    public Session getNeo4JConnection(String connectionUrlAndPort, String userName, String password)
    {
        driver = GraphDatabase.driver(connectionUrlAndPort, AuthTokens.basic(userName, password));

        return driver.session();


    }

    @Override
    public void closeDriver() {
        driver.close();
    }

}
