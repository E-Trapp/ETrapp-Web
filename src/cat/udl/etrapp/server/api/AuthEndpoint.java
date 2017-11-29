package cat.udl.etrapp.server.api;

import cat.udl.etrapp.server.controllers.UsersDAO;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthEndpoint {

    @POST
    @Path("/sign_in")
    public Response signIn() {

        UsersDAO.getInstance().updateToken("testToken", 1);

        return Response.ok("token").build();
    }

}
