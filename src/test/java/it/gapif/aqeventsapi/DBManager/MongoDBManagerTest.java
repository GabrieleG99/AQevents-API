package it.gapif.aqeventsapi.DBManager;

import it.gapif.aqeventsapi.classes.models.NewsModel;
import org.bson.Document;

class MongoDBManagerTest {

    public static void main(String[] args) {

        MongoDBManager.getInstance().connectWithUsernameAndPassword("admin", "admin");

        //System.out.println(MongoDBManager.getInstance().isConnectionActive());

        //MongoDBManager.getInstance().createCollection("News");

        NewsModel newsModel = new NewsModel("prova", "prova", "prova");

        System.out.println(newsModel.toDocument());

        //MongoDBManager.getInstance().addOneDocumentToCollection(newsModel.toDocument(), "News");

        Document result = MongoDBManager.getInstance().getOneDocumentFromCollectionWithID("1", "News");

        System.out.println(result);

    }

}