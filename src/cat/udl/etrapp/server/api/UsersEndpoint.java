package cat.udl.etrapp.server.api;

import cat.udl.etrapp.server.api.annotations.Secured;
import cat.udl.etrapp.server.controllers.UsersDAO;
import cat.udl.etrapp.server.models.User;
import cat.udl.etrapp.server.models.UserAuth;
import cat.udl.etrapp.server.models.UserInfo;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@RequestScoped
@Path("/users")
@Produces("application/json")
@Consumes("application/json")
public class UsersEndpoint {

    @POST
    public Response createNewUser(UserAuth userAuth) {
        final User user;
        if ((user = UsersDAO.getInstance().createUser(userAuth)) == null) {
            return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } else {
            return Response.created(UriBuilder.fromResource(UsersEndpoint.class).path(String.valueOf(user.getId())).build()).entity(user).build();
        }
    }

    @GET
    @Secured
    @Path("/{id}")
    public Response getUserById(@PathParam("id") long id) {
        final UserInfo user;
        if ((user = UsersDAO.getInstance().getUserInfoById(id)) != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
