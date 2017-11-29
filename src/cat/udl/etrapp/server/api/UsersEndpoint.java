package cat.udl.etrapp.server.api;

import cat.udl.etrapp.server.models.UserAuth;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/users")
public class UsersEndpoint {

    @POST
    public Response createNewUser(UserAuth userAuth) {
        return Response.ok().build();
    }

}
