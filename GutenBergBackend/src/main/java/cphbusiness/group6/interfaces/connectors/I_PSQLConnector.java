package cphbusiness.group6.interfaces.connectors;

import cphbusiness.group6.exceptions.IncorrectUsrNameOrPasswordException;

import java.sql.Connection;
import java.sql.SQLException;

public interface I_PSQLConnector
{
    public Connection getPSQLConnection(String connectionUrlAndPort, String userName, String password) throws SQLException, IncorrectUsrNameOrPasswordException;
}
