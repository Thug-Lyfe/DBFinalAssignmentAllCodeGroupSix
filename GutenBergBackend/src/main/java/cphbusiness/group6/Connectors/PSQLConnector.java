package cphbusiness.group6.Connectors;

import cphbusiness.group6.exceptions.IncorrectUsrNameOrPasswordException;
import cphbusiness.group6.interfaces.connectors.I_PSQLConnector;
import org.postgresql.util.PSQLException;

import java.sql.*;

public class PSQLConnector implements I_PSQLConnector
{
    @Override
    public Connection getPSQLConnection(String connectionUrlAndPort, String userName, String password) throws SQLException, IncorrectUsrNameOrPasswordException
    {
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://" + connectionUrlAndPort + "/postgres", userName, password);
            return connection;
        }
        catch (PSQLException e)
        {
            throw new IncorrectUsrNameOrPasswordException();
        }

        /*
       /GET TABLE NAMES

            DatabaseMetaData dbmd = connection.getMetaData();
            try (ResultSet tables = dbmd.getTables(null, null, "%", new String[]{"TABLE"}))
            {
                while (tables.next())
                {
                    System.out.println(tables.getString("TABLE_NAME"));
                }
            }

        GET SOMETHING FROM TABLE (name of author)


        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.setFetchSize(50);
        ResultSet rs = statement.executeQuery("SELECT * FROM t_auth limit 5");
        while (rs.next())
        {
            System.out.print(rs.getString("name") + "\n");
        }
        rs.close();

     */
    }
}
