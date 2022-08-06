package it.gapif.aqeventsapi.RestServices.news;

import it.gapif.aqeventsapi.classes.models.NewsModel;
import it.gapif.aqeventsapi.responses.NewsResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/newsService")
public class newsService {

    @POST
    @Path("createNews")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNews(NewsModel news){

        newsEngine engine = new newsEngine();
        NewsResponse response = new NewsResponse();

        engine.createNews(news);

        response.setTitle(news.getTitle());
        response.setResult("Success");
        response.setCode("200");

        return Response.status(200).entity(response).build();
    }

    @GET
    @Path("readNews/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readNews(@PathParam("id") String id){

        newsEngine engine = new newsEngine();
        NewsResponse response = new NewsResponse();

        NewsModel result = engine.readNews(id);

        response.setTitle(result.getTitle());
        response.setResult("Success");
        response.setCode("200");

        return Response.status(200).entity(response).build();
    }

    @PUT
    @Path("updateNews/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateNews(@PathParam("id") String id, NewsModel newValue){

        newsEngine engine = new newsEngine();
        NewsResponse response = new NewsResponse();

        engine.updateNews(id, newValue);

        response.setTitle(newValue.getTitle());
        response.setResult("Success");
        response.setCode("200");

        return Response.status(200).entity(response).build();
    }

    @DELETE
    @Path("deleteNews/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteNews(@PathParam("id") String id){

        newsEngine engine = new newsEngine();
        NewsResponse response = new NewsResponse();

        NewsModel deletedItem = engine.readNews(id);

        engine.deleteNews(id);

        response.setTitle(deletedItem.getTitle());
        response.setResult("Success");
        response.setCode("200");

        return Response.status(200).entity(response).build();
    }
}
