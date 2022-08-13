package it.gapif.aqeventsapi.RestServices.news;

import it.gapif.aqeventsapi.DBManager.MongoDBManager;
import it.gapif.aqeventsapi.classes.models.NewsModel;
import it.gapif.aqeventsapi.utils.CollectionNames;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class newsEngine {

    static protected Logger log = LogManager.getLogger(newsEngine.class);

    private String operation_ID;

    public newsEngine(){}

    public newsEngine(String operation_ID){
        this.operation_ID = operation_ID;
    }

    public boolean createNews(NewsModel news){

        log.info(" - " + operation_ID + " - **** newsEngine.class|createNews|Start");

        connectIfDisconnected();

        Document newsDocument = news.toDocument();

        if (MongoDBManager.getInstance().getOneDocumentFromCollection(newsDocument, CollectionNames.News.toString()) == null){
            MongoDBManager.getInstance().addOneDocumentToCollection(newsDocument, CollectionNames.News.toString());
            log.info(" - " + operation_ID + " - **** newsEngine.class|createNews|Stop");
            return true;
        }

        System.out.println("Il documento è già presente all'interno del database");

        log.info(" - " + operation_ID + " - **** newsEngine.class|createNews|Stop");

        return false;
    }

    public void createMultipleNews(List<NewsModel> newsModels){

        log.info(" - " + operation_ID + " - **** newsEngine.class|createMultipleNews|Start");

        connectIfDisconnected();

        List<Document> documents = new ArrayList<>();

        for (NewsModel newsModel : newsModels) {
            documents.add(newsModel.toDocument());
        }

        MongoDBManager.getInstance().addManyDocumentsToCollection(documents, CollectionNames.News.toString());
    }

    public NewsModel readNews(String id){
        log.info(" - " + operation_ID + " - **** newsEngine.class|readNews|Start");

        connectIfDisconnected();

        Document result = null;

        NewsModel __result = new NewsModel();

        if (MongoDBManager.getInstance().getOneDocumentFromCollectionWithID(id, CollectionNames.News.toString()) != null){

            result = MongoDBManager.getInstance().getOneDocumentFromCollectionWithID(id, CollectionNames.News.toString());

            __result.setTitle(result.getString("title"));
            __result.setImage(result.getString("image"));
            __result.setDescription(result.getString("description"));

            log.info(" - " + operation_ID + " - **** newsEngine.class|readNews|Stop");

            return __result;
        }

        log.info(" - " + operation_ID + " - **** newsEngine.class|readNews|Stop");

        return null;
    }

    public boolean updateNews(String id, NewsModel news){
        log.info(" - " + operation_ID + " - **** newsEngine.class|updateNews|Start");

        connectIfDisconnected();

        Document newsDocument = news.toDocument();

        if (MongoDBManager.getInstance().getOneDocumentFromCollectionWithID(id, CollectionNames.News.toString()) != null) {
            MongoDBManager.getInstance().updateOneDocumentInACollectionWithID(id, newsDocument, CollectionNames.News.toString());
            log.info(" - " + operation_ID + " - **** newsEngine.class|updateNews|Stop");
            return true;
        }

        System.out.println("Non è possibile eseguire l'update perché l'elemento non è presente all'interno del database");

        log.info(" - " + operation_ID + " - **** newsEngine.class|updateNews|Stop");

        return false;
    }

    public boolean deleteNews(String id){
        log.info(" - " + operation_ID + " - **** newsEngine.class|deleteNews|Start");

        connectIfDisconnected();

        if (MongoDBManager.getInstance().getOneDocumentFromCollectionWithID(id, CollectionNames.News.toString()) != null){
            MongoDBManager.getInstance().deleteOneDocumentFromCollection(id, CollectionNames.News.toString());
            log.info(" - " + operation_ID + " - **** newsEngine.class|deleteNews|Stop");
            return true;
        }

        System.out.println("Il documento no è presente all'interno del database");

        log.info(" - " + operation_ID + " - **** newsEngine.class|deleteNews|Stop");

        return false;
    }

    private void connectIfDisconnected(){
        if (!MongoDBManager.getInstance().isConnectionActive()){
            MongoDBManager.getInstance().connectWithUsernameAndPassword("admin", "admin");
        }
    }
}
