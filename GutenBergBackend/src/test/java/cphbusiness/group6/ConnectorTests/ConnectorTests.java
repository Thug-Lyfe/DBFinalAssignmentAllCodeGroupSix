package cphbusiness.group6.ConnectorTests;

import com.mongodb.MongoClient;
import cphbusiness.group6.Connectors.MongoConnector;
import cphbusiness.group6.Connectors.Neo4JConnector;
import cphbusiness.group6.Connectors.PSQLConnector;
import cphbusiness.group6.exceptions.IncorrectUsrNameOrPasswordException;
import cphbusiness.group6.interfaces.connectors.I_MONGOConnector;
import cphbusiness.group6.interfaces.connectors.I_NEO4JConnector;
import cphbusiness.group6.interfaces.connectors.I_PSQLConnector;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.driver.v1.Session;

import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ConnectorTests
{

    private static I_MONGOConnector mongoConnectorTestObject;
    private static I_PSQLConnector psqlConnectorTestObject;
    private static I_NEO4JConnector neoConnectorTestObject;

    private static String MongoUserName;
    private static String MongoPassword;
    private static String MongoConnectionURL;

    private static String NeoUserName;
    private static String NeoPassword;
    private static String NeoConnectionURL;

    private static String PSQLConnectionURL;
    private static String PSQLUserName;
    private static String PSQLPassword;


    @BeforeClass
    public static void setUp() throws SQLException, IncorrectUsrNameOrPasswordException
    {
//        mongoConnectorTestObject = mock(I_MONGOConnector.class);
//        psqlConnectorTestObject = mock(I_PSQLConnector.class);
//        neoConnectorTestObject = mock(I_NEO4JConnector.class);
//        testNEOsession = mock(Session.class);
//        testPSQLConnection = mock(Connection.class);
//        when(mongoConnectorTestObject.getMongoDBConnection(connectionURL, userName, password)).thenReturn(new MongoClient());
//        when(psqlConnectorTestObject.getPSQLConnection(connectionURL, userName, password)).thenReturn(); //change this to correct connection object
//        when(neoConnectorTestObject.getNeo4JConnection(connectionURL, userName, password)).thenReturn(testNEOsession); //change this to correct connection object
//        when(mongoConnectorTestObject.getMongoDBConnection("nonsense", "nonsense", "nonsense")).thenThrow(IncorrectUsrNameOrPasswordException.class);
//        when(psqlConnectorTestObject.getPSQLConnection("nonsense", "nonsense", "nonsense")).thenThrow(IncorrectUsrNameOrPasswordException.class);
//        when(neoConnectorTestObject.getNeo4JConnection("nonsense", "nonsense", "nonsense")).thenThrow(IncorrectUsrNameOrPasswordException.class);


        mongoConnectorTestObject = new MongoConnector();
        psqlConnectorTestObject = new PSQLConnector();
        neoConnectorTestObject = new Neo4JConnector();


        MongoUserName = "username";
        MongoPassword = "password";
        MongoConnectionURL = "167.99.237.199:27017";

        NeoUserName = "username";
        NeoPassword = "password";
        NeoConnectionURL = "bolt://167.99.237.199:7687";

        PSQLUserName = "username";
        PSQLPassword = "password";
        PSQLConnectionURL = "167.99.237.199:5432";
    }

    @Test(expected = IncorrectUsrNameOrPasswordException.class)
    public void testCase1_MongoConnection() throws IncorrectUsrNameOrPasswordException
    {
        mongoConnectorTestObject.getMongoDBConnection("nonsense", "nonsense", "nonsense");
    }

    @Test
    public void testCase2_MongoConnection() throws IncorrectUsrNameOrPasswordException
    {
        MongoClient client = mongoConnectorTestObject.getMongoDBConnection(MongoConnectionURL, MongoUserName, MongoPassword);
        assertThat(client != null, is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCase3_NEO4JConnection() throws IncorrectUsrNameOrPasswordException
    {
        neoConnectorTestObject.getNeo4JConnection("nonsense", "nonsense", "nonsense");
    }


    @Test
    public void testCase4_NEO4JConnection() throws IncorrectUsrNameOrPasswordException
    {
        Session neoSession = neoConnectorTestObject.getNeo4JConnection(NeoConnectionURL, NeoUserName, NeoPassword);
        assertThat(neoSession != null, is(true));
    }

    @Test(expected = IncorrectUsrNameOrPasswordException.class)
    public void testCase5_PSQLConnection() throws IncorrectUsrNameOrPasswordException, SQLException
    {
        psqlConnectorTestObject.getPSQLConnection("nonsense", "nonsense", "nonsense");
    }


    @Test
    public void testCase6_PSQLJConnection() throws IncorrectUsrNameOrPasswordException, SQLException
    {
        Connection psqlConnection = psqlConnectorTestObject.getPSQLConnection(PSQLConnectionURL, PSQLUserName, PSQLPassword);
        assertThat(psqlConnection != null, is(true));
    }
}
