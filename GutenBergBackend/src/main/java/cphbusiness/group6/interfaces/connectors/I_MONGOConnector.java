package cphbusiness.group6.interfaces.connectors;

import com.mongodb.*;
import cphbusiness.group6.exceptions.IncorrectUsrNameOrPasswordException;

public interface I_MONGOConnector
{

    public MongoClient getMongoDBConnection(String connectionUrlAndPort, String userName, String password) throws IncorrectUsrNameOrPasswordException;
}
