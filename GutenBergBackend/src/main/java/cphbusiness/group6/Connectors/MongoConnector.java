package cphbusiness.group6.Connectors;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import cphbusiness.group6.exceptions.IncorrectUsrNameOrPasswordException;
import cphbusiness.group6.interfaces.connectors.I_MONGOConnector;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class MongoConnector implements I_MONGOConnector
{
    @Override
    public MongoClient getMongoDBConnection(String connectionUrlAndPort, String userName, String password) throws IncorrectUsrNameOrPasswordException
    {

        if (userName.equalsIgnoreCase("mongo") && password.equalsIgnoreCase("jesus"))
        {
            MongoClientURI mongoClientURI = new MongoClientURI("mongodb://" + userName + ":" + password + "@" + connectionUrlAndPort); //+"/books"
            MongoClient mongoClient = new MongoClient(mongoClientURI);
            return mongoClient; //Remeber to close me :)

        }
        else
        {
            throw new IncorrectUsrNameOrPasswordException();
        }

        /*
        Her er et eksempel på at kalde en specifik database og collection fra en mongoclient

        MongoDatabase db = mongoClient.getDatabase("books"); Giver hele DBen
        MongoCollection collection = db.getCollection("auths"); Giver hele collectionen
        collection.count(); Giver en count på antallet af things i collectionen
        collection.find().first(); finder den første author (for vores data er det Thomas Jefferson)
        System.out.println(collection.find(eq("name", "Jefferson, Thomas")).first()); //Her er en query der finder Jefferson (eq betyder at det skal være equal til det jeg skriver, der findes også andre filters)

         */
    }


}
//167.99.237.199:27017