package it.gapif.aqeventsapi.classes.utils;

public class ModelUtils {

    public static String retriveCollectionNameFromModel(Object className){

        String classname = className.getClass().toString().split(" ")[1];

        if (classname.contains("Model")){
            classname = classname.replaceAll("Model", "");
        }

        return classname;
    }
}
