package it.gapif.aqeventsapi.DBManager;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoDBManager {

    static protected Logger log = Logger.getLogger(MongoDBManager.class);

    private boolean connectionActive = false;

    private static MongoDBManager instance;

    private MongoClient client;

    private MongoDBManager(){}

    public static MongoDBManager getInstance() {

        log.debug(" ### MongoDBManager.class|dbManager getInstance START.");
        if (instance == null){
            log.info(" ### MongoDBManager.class|dbManager object is empty:initialize.");
            synchronized (MongoDBManager.class){
                if (instance == null) instance = new MongoDBManager();
            }
        }
        log.debug(" ### MongoDBManager.class|dbManager getInstance FINISH.");
        return instance;
    }

    public void connectWithUsernameAndPassword(String username, String password){
        final String URI = "mongodb://" + username + ":" + password + "@localhost:27017/admin?authSource=admin";

        if (!isConnectionActive()) {
            try {
                client = new MongoClient(new MongoClientURI(URI));
                this.connectionActive = true;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public MongoClient getMongoClient(){
        return client;
    }

    public boolean isConnectionActive() {
        return connectionActive;
    }

    private MongoDatabase getDatabase(){
        return getMongoClient().getDatabase("AQevents");
    }

    private MongoCollection<Document> getCollection(String collectionName){
        return getDatabase().getCollection(collectionName);
    }

    public void createCollection(String collectionName){
        getDatabase().createCollection(collectionName);
    }

    public void addOneDocumentToCollection(Document document, String collectionName) {
        getCollection(collectionName).insertOne(document.append("lastUpdated", new SimpleDateFormat("yyyy.MM.dd&HH:mm:ss").format(new Date())));
    }

    public void addManyDocumentsToCollection(List<Document> documents, String collectionName) {
        getCollection(collectionName).insertMany(documents);
    }

    public void updateOneDocumentInACollection(Document query, Document newValue, String collectionName) {
        getCollection(collectionName).updateOne(query, newValue.append("lastUpdated", new SimpleDateFormat("yyyy.MM.dd&HH:mm:ss").format(new Date())));
    }

    public void updateOneDocumentInACollectionWithID(String id, Document newValue, String collection){
        getCollection(collection).updateOne(eq("_id", new ObjectId(id)), new Document("$set", newValue.append("lastUpdated", new SimpleDateFormat("yyyy.MM.dd&HH:mm:ss").format(new Date()))));
    }

    public void updateManyDocumentsInACollection(Document query, Document document, String collectionName) {
        getCollection(collectionName).updateMany(query, document);
    }

    public Document getOneDocumentFromCollection(Document query, String collectionName) {

        Document result = getCollection(collectionName).find(query).first();

        if (result == null){
            System.out.println("No result found.");
        }

        return result;
    }

    public Document getOneDocumentFromCollectionWithID(String id, String collection){

        try {
            return getCollection(collection).find(eq("_id", new ObjectId(id))).first();
        } catch (IllegalArgumentException e){
            System.out.println("L'id fornito non è nel giusto formato " + e);
            return null;
        }
    }

    public void deleteOneDocumentFromCollection(String id, String collection){

        if (getOneDocumentFromCollectionWithID(id, collection) != null) {
            getCollection(collection).deleteOne(eq("_id", new ObjectId(id)));
        } else {
            log.info("Documento non presente all'interno del database");
        }
    }

    public boolean closeConnection(){

        try {
            client = null;
            connectionActive = false;
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
