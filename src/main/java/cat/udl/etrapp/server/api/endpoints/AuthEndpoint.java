package cat.udl.etrapp.server.api.endpoints;

import cat.udl.etrapp.server.api.annotations.Secured;
import cat.udl.etrapp.server.controllers.FirebaseController;
import cat.udl.etrapp.server.daos.UsersDAO;
import cat.udl.etrapp.server.models.Credentials;
import cat.udl.etrapp.server.models.User;
import cat.udl.etrapp.server.models.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static cat.udl.etrapp.server.utils.Utils.getAuthToken;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthEndpoint {

    @GET
    @Secured
    public Response getAuthUser(@Context HttpHeaders headers) {
        return Response.ok(UserInfo.fromUser(UsersDAO.getInstance().getUserByToken(getAuthToken(headers)))).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signIn(Credentials credentials) {
        final User user;
        if ((user = UsersDAO.getInstance().authenticate(credentials)) != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(422).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void authenticate(@FormParam("username") String username,
                             @FormParam("password") String password,
                             @Context HttpServletResponse response,
                             @Context HttpServletRequest request) throws Exception {

        Credentials c = new Credentials();
        c.setUsername(username);
        c.setPassword(password);

        if (UsersDAO.getInstance().authenticate(c) != null) {
            response.sendRedirect("/etrapp-server/charts.jsp");
        } else {
            request.setAttribute("authenticationError", "Invalid email/password.");
        }
    }

    @DELETE
    @Secured
    public Response signOut(@Context HttpHeaders headers) {
        // TODO: Handle failures
        if(UsersDAO.getInstance().deauthenticate(getAuthToken(headers))) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
    }

}
