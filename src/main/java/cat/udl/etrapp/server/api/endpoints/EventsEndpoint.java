package cat.udl.etrapp.server.api.endpoints;

import cat.udl.etrapp.server.api.annotations.AdminOnly;
import cat.udl.etrapp.server.api.annotations.Authorized;
import cat.udl.etrapp.server.api.annotations.PATCH;
import cat.udl.etrapp.server.api.annotations.Secured;
import cat.udl.etrapp.server.daos.EventsDAO;
import cat.udl.etrapp.server.daos.UsersDAO;
import cat.udl.etrapp.server.models.Event;

import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.sql.Date;
import java.text.SimpleDateFormat;
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

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void newEvent(@FormParam("title") String title,
                         @FormParam("desc") String description,
                         @FormParam("loc") String location,
                         @FormParam("starts") String startsAt,
                            @Context HttpServletResponse response) throws Exception {
        Event e = new Event();
        e.setTitle(title);
        e.setDescription(description);
        e.setLocation(location);
        e.setStartsAt(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startsAt.replace('T', ' ')).getTime());
        e.setOwner(22);
        EventsDAO.getInstance().createEvent(e);
        response.sendRedirect("/etrapp-server/events.jsp");
    }

    @GET
    @Path("{id}/delete")
    public void deleteEvent(@PathParam("id") long eventId, @Context HttpServletResponse response) throws Exception {
        EventsDAO.getInstance().deleteEvent(eventId);
        response.sendRedirect("/etrapp-server/events.jsp");
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
                               @QueryParam("max") final Integer maxResult,
                               @QueryParam("category") final Long category) {
        final List<Event> events = EventsDAO.getInstance().getEvents(startPosition, maxResult, category);
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
    public Response update_partially(@PathParam("id") Long id, final Map<String, Object> updates) {
        if (EventsDAO.getInstance().editEvent(id, updates))
            return Response.ok().build();
        else return Response.status(Status.BAD_REQUEST).build();
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

    @POST
    @AdminOnly
    @Path("/batch")
    public Response addBatchEvents(List<Event> events) {
        for(Event e : events) {
            if (EventsDAO.getInstance().createEvent(e) == null) {
                return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.ok().build();
    }

}
