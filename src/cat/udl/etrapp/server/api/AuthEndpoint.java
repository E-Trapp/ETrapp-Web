package cat.udl.etrapp.server.api;

import cat.udl.etrapp.server.controllers.UsersDAO;
import cat.udl.etrapp.server.models.Credentials;
import cat.udl.etrapp.server.models.User;
import cat.udl.etrapp.server.utils.Utils;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthEndpoint {

    @POST
    @Path("/sign_in")
    public Response signIn(Credentials credentials) {
        try {
            User user = UsersDAO.getInstance().authenticate(credentials);
            return Response.ok(user.getToken()).build();
        } catch (Exception e) {
            return Response.status(422).build();
        }

    }

}
