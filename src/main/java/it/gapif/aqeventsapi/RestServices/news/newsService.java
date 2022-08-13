package it.gapif.aqeventsapi.RestServices.news;

import it.gapif.aqeventsapi.classes.models.NewsModel;
import it.gapif.aqeventsapi.responses.NewsResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/newsService")
public class newsService {

    final String OK = "200";
    final String NOT_FOUND = "404";

    final String SUCCESS = "Success";
    final String NOTFOUND = "Not Found";
    @POST
    @Path("createNews")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNews(NewsModel news){

        newsEngine engine = new newsEngine();
        NewsResponse response = new NewsResponse();

        boolean result = engine.createNews(news);

        return getResponse(news, response, result);

    }

    @GET
    @Path("readNews/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readNews(@PathParam("id") String id){

        newsEngine engine = new newsEngine();
        NewsResponse response = new NewsResponse();

        NewsModel result = engine.readNews(id);

        if (result != null){
            response.setTitle(result.getTitle());
            response.setResult(SUCCESS);
            response.setCode(OK);
            return Response.status(Response.Status.OK).entity(response).build();
        }

        response.setTitle("No data detected");
        response.setResult(NOTFOUND);
        response.setCode(NOT_FOUND);

        return Response.status(Response.Status.NOT_FOUND).entity(response).build();
    }

    @PUT
    @Path("updateNews/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateNews(@PathParam("id") String id, NewsModel newValue){

        newsEngine engine = new newsEngine();
        NewsResponse response = new NewsResponse();

        boolean result = engine.updateNews(id, newValue);

        return getResponse(newValue, response, result);
    }

    private Response getResponse(NewsModel newValue, NewsResponse response, boolean result) {
        if (result){
            response.setTitle(newValue.getTitle());
            response.setResult(SUCCESS);
            response.setCode(OK);

            return Response.status(Response.Status.OK).entity(response).build();
        }

        response.setTitle(newValue.getTitle());
        response.setResult(NOTFOUND);
        response.setCode(NOT_FOUND);

        return Response.status(Response.Status.NOT_FOUND).entity(response).build();
    }

    @DELETE
    @Path("deleteNews/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteNews(@PathParam("id") String id){

        newsEngine engine = new newsEngine();
        NewsResponse response = new NewsResponse();

        NewsModel deletedItem = engine.readNews(id);

        boolean result = engine.deleteNews(id);

        return getResponse(deletedItem, response, result);
    }
}
