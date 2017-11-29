package cat.udl.etrapp.server.api;

import cat.udl.etrapp.server.api.annotations.Secured;
import cat.udl.etrapp.server.controllers.UsersDAO;
import cat.udl.etrapp.server.models.Credentials;
import cat.udl.etrapp.server.models.User;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthEndpoint {

    @POST
    public Response signIn(Credentials credentials) {
        try {
            User user = UsersDAO.getInstance().authenticate(credentials);
            return Response.ok(user.getToken()).build();
        } catch (Exception e) {
            return Response.status(422).build();
        }

    }

    @DELETE
    @Secured
    public Response signOut(@Context HttpHeaders headers) {
        // TODO: Remove token from DB
        UsersDAO.getInstance().deauthenticate(headers.getHeaderString(HttpHeaders.AUTHORIZATION).substring("Bearer".length()).trim());
        return Response.ok().build();
    }

}
