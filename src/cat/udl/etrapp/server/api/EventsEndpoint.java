package cat.udl.etrapp.server.api;

import cat.udl.etrapp.server.api.annotations.Authorized;
import cat.udl.etrapp.server.api.annotations.PATCH;
import cat.udl.etrapp.server.api.annotations.Secured;
import cat.udl.etrapp.server.controllers.EventsDAO;
import cat.udl.etrapp.server.controllers.UsersDAO;
import cat.udl.etrapp.server.models.Event;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.Map;

import static cat.udl.etrapp.server.utils.Utils.getAuthToken;

@RequestScoped
@Path("/events")
@Produces("application/json")
@Consumes("application/json")
public class EventsEndpoint {

    @Context
    private HttpHeaders headers;

    @POST
    @Secured
    public Response create(final Event event) {
        event.setOwner(UsersDAO.getInstance().getUserByToken(getAuthToken(headers)).getId());
        if (EventsDAO.getInstance().createEvent(event) == null) {
            return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();
        } else {
            return Response.created(UriBuilder.fromResource(EventsEndpoint.class).path(String.valueOf(event.getId())).build()).build();
        }
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    public Response findById(@PathParam("id") final Long id) {
        //TODO: retrieve the event
        Event event = EventsDAO.getInstance().getEventById(id);
        if (event == null) return Response.status(Status.NOT_FOUND).build();
        return Response.ok(event).build();
    }

    @GET
    public List<Event> listAll(@QueryParam("start") final Integer startPosition,
                               @QueryParam("max") final Integer maxResult) {
        //TODO: retrieve the events
        final List<Event> events = EventsDAO.getInstance().getAllEvents();
        return events;
    }

    @PUT
    @Authorized
    @Path("/{id:[0-9][0-9]*}")
    public Response update(@PathParam("id") Long id, final Event event) {
        //TODO: process the given event
        return Response.noContent().build();
    }

    @PATCH
    @Authorized
    @Path("/{id:[0-9][0-9]*}")
    public Response update_partially(@PathParam("id") Long id, final Map<String, Object> data) {
        //TODO: process the given event
        for (String key : data.keySet()) {
            try {
                System.out.println(Event.class.getDeclaredField(key).toString());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return Response.noContent().build();
    }

    @DELETE
    @Authorized
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") final Long id) {
        //TODO: process the event matching by the given id
        return Response.noContent().build();
    }

}
