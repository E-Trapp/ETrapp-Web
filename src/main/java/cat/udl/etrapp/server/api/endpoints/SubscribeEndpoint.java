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
    // The Java method will process HTTP GET requests
    /*@GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("text/plain")
    @Path("../test")
    public String getClichedMessage() {
        // Return some cliched textual content
        return "Hello World";
    }*/

    @GET
    public String[] test() {
        String[] kk = new String[3];
        kk[0] = "0";
        kk[1] = "1";
        kk[2] = "2";

        return kk;
    }


    @POST
    public Response createSubscribe(final Subscribe subscribe) {
        System.out.println("DHIOSAHDSHDIAOS");
        if(SubscribeDAO.getInstance().createSubscribe(subscribe) == null){
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } else {
            return Response.created(UriBuilder.fromResource(SubscribeEndpoint.class).path(String.valueOf(subscribe.getId())).build()).build();
        }
    }

}
