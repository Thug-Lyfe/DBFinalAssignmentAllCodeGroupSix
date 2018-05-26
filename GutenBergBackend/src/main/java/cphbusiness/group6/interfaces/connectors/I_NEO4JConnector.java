package cphbusiness.group6.interfaces.connectors;

import cphbusiness.group6.exceptions.IncorrectUsrNameOrPasswordException;
import org.neo4j.driver.v1.*;

public interface I_NEO4JConnector
{
    public Session getNeo4JConnection(String connectionUrlAndPort, String userName, String password) throws IncorrectUsrNameOrPasswordException;

    public void closeDriver();
}
