package it.gapif.aqeventsapi.DBManager;

import it.gapif.aqeventsapi.classes.models.NewsModel;
import it.gapif.aqeventsapi.utils.CollectionNames;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        String s = CollectionNames.News.toString();

        String modelClass = NewsModel.class.getName();

        List<String> modelList = new ArrayList<>();

        modelList = Arrays.stream(modelClass.split("\\.")).toList();

        System.out.println(modelList.get(modelList.size() - 1));

        System.out.println(s);

    }

}