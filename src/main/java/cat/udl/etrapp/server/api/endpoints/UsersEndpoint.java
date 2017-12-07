package cat.udl.etrapp.server.api.endpoints;

import cat.udl.etrapp.server.api.annotations.Authorized;
import cat.udl.etrapp.server.api.annotations.PATCH;
import cat.udl.etrapp.server.daos.UsersDAO;
import cat.udl.etrapp.server.models.User;
import cat.udl.etrapp.server.models.UserAuth;
import cat.udl.etrapp.server.models.UserInfo;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Map;

@RequestScoped
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
    @Path("/{id}")
    public Response getUserById(@PathParam("id") long id) {
        final UserInfo user;
        if ((user = UsersDAO.getInstance().getUserInfoById(id)) != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Authorized
    @Path("/{id}")
    public Response updateUser(@PathParam("id") long id, UserInfo userInfo) {
        userInfo.setId(id);
        return Response.ok().build();
    }

    @PATCH
    @Authorized
    @Path("/{id}")
    public Response update_partially(@PathParam("id") long id, Map<String, Object> updates) {
        if (UsersDAO.getInstance().editUser(id, updates))
            return Response.ok().build();
        else return Response.status(Response.Status.BAD_REQUEST).build();
    }


}
