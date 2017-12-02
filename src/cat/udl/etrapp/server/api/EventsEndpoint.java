package cat.udl.etrapp.server.api;

import cat.udl.etrapp.server.api.annotations.Authorized;
import cat.udl.etrapp.server.api.annotations.PATCH;
import cat.udl.etrapp.server.api.annotations.Secured;
import cat.udl.etrapp.server.controllers.EventsDAO;
import cat.udl.etrapp.server.controllers.UsersDAO;
import cat.udl.etrapp.server.models.Event;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.util.List;
import java.util.Map;

import static cat.udl.etrapp.server.utils.Utils.getAuthToken;

@RequestScoped
@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
        Event event = EventsDAO.getInstance().getEventById(id);
        if (event == null) return Response.status(Status.NOT_FOUND).build();
        return Response.ok(event).build();
    }

    @GET
    public Response listAll(@QueryParam("start") final Integer startPosition,
                               @QueryParam("max") final Integer maxResult) {

        System.out.println("Start: " + startPosition + " maxResult: " + maxResult);
        final List<Event> events;
        if (startPosition != null && maxResult != null) {
            events = EventsDAO.getInstance().getEventsPaginated(startPosition, maxResult);
        } else events = EventsDAO.getInstance().getAllEvents();
        if (events.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        } else {
            return Response.ok(events).build();
        }
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
        if (EventsDAO.getInstance().deleteEvent(id)) {
            return Response.noContent().build();
        } else {
            return Response.serverError().build();
        }
    }

}
