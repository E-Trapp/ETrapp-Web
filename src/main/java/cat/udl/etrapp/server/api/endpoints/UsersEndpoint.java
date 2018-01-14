package cat.udl.etrapp.server.api.endpoints;

import cat.udl.etrapp.server.api.annotations.Authorized;
import cat.udl.etrapp.server.api.annotations.PATCH;
import cat.udl.etrapp.server.api.annotations.Secured;
import cat.udl.etrapp.server.daos.EventsDAO;
import cat.udl.etrapp.server.daos.UsersDAO;
import cat.udl.etrapp.server.models.Event;
import cat.udl.etrapp.server.models.User;
import cat.udl.etrapp.server.models.UserAuth;
import cat.udl.etrapp.server.models.UserInfo;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
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

    @PATCH
    @Authorized
    @Path("/{id}")
    public Response update_partially(@PathParam("id") long id, Map<String, Object> updates) {
        if (UsersDAO.getInstance().editUser(id, updates)) return Response.ok().build();
        else return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Authorized
    @Path("/{id}/token")
    public Response updateUserToken(@PathParam("id") long id, Map<String, String> tokenInfo) {
        UsersDAO.getInstance().updateNotificationToken(id, tokenInfo.get("token"));
        return Response.ok().build();
    }

    @GET
    @Secured
    @Path("/{id}/events")
    public Response getUserEvents(@PathParam("id") long id,
                                  @QueryParam("start") final Integer startPosition,
                                  @QueryParam("max") final Integer maxResult) {
        List<Event> userEvents = EventsDAO.getInstance().getEvents(id, startPosition, maxResult);
        if (userEvents.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(userEvents).build();
        }
    }

    @GET
    @Secured
    @Path("/{id}/subscribe")
    public Response getUserSubscribe(@PathParam("id") long id,
                                  @QueryParam("start") final Integer startPosition,
                                  @QueryParam("max") final Integer maxResult) {
        List<Event> userEvents = EventsDAO.getInstance().getEventsSubscribe(id, startPosition, maxResult);
        if (userEvents.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(userEvents).build();
        }
    }

}
