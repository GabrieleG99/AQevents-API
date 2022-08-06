package it.gapif.aqeventsapi.classes.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class NewsModel {

    @JsonProperty
    String title;

    @JsonProperty
    String description;

    @JsonProperty
    String image;

    public NewsModel(){}

    public NewsModel(String title, String description, String image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Map<String, Object> toHasMap(){

        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("title", this.title);
        dataMap.put("description", this.description);
        dataMap.put("image", this.image);

        return dataMap;
    }

    public Document toDocument(){
        return new Document(toHasMap());
    }
}