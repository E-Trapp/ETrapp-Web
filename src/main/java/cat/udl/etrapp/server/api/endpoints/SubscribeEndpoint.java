package cat.udl.etrapp.server.api.endpoints;

import cat.udl.etrapp.server.api.annotations.Secured;
import cat.udl.etrapp.server.daos.CategoriesDAO;
import cat.udl.etrapp.server.daos.SubscribeDAO;
import cat.udl.etrapp.server.models.Category;
import cat.udl.etrapp.server.models.Subscribe;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

// The Java class will be hosted at the URI path "/helloworld"
@RequestScoped
@Path("/subscribe")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubscribeEndpoint {



    @POST
    public Response createSubscribe(final Subscribe subscribe) {
        if(SubscribeDAO.getInstance().createSubscribe(subscribe) == null){
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } else {
            return Response.created(UriBuilder.fromResource(SubscribeEndpoint.class).path(String.valueOf(subscribe.getId())).build()).build();
        }
    }

    @POST
    public Response deleteSubscribe(final Subscribe subscribe) {
        if(SubscribeDAO.getInstance().deleteSubscribe(subscribe) == null){
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } else {
            return Response.created(UriBuilder.fromResource(SubscribeEndpoint.class).path(String.valueOf(subscribe.getId())).build()).build();
        }
    }

}
