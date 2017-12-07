package cat.udl.etrapp.server.api.endpoints;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/categories")
public class CategoriesEndpoint {
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("text/plain")
    @Path("../test")
    public String getClichedMessage() {
        // Return some cliched textual content
        return "Hello World";
    }

}
