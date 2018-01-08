package cat.udl.etrapp.server.api.endpoints;

import cat.udl.etrapp.server.daos.CategoriesDAO;
import cat.udl.etrapp.server.models.Category;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriesEndpoint {
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
    public Response getAllCategories() {
        /*String[] a = new String[3];
        a[0]="1";
        a[1]="1";
        a[2]="1";

        return Response.ok(a).build();*/
        final List<Category> categories = CategoriesDAO.getInstance().getCategories();
        if (categories.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(categories).build();
        }
    }

}
