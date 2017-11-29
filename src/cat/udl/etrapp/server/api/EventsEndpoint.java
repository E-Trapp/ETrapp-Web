package cat.udl.etrapp.server.api;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import cat.udl.etrapp.server.api.extras.PATCH;
import cat.udl.etrapp.server.controllers.EventsDAO;
import cat.udl.etrapp.server.models.Event;

@RequestScoped
@Path("/events")
@Produces("application/json")
@Consumes("application/json")
public class EventsEndpoint {

	@POST
	public Response create(final Event event) {
		//TODO: process the given event 
		//you may want to use the following return statement, assuming that Event#getId() or a similar method 
		//would provide the identifier to retrieve the created Event resource:
		//return Response.created(UriBuilder.fromResource(EventsEndpoint.class).path(String.valueOf(event.getId())).build()).build();
		return Response.created(null).build();
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
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") Long id, final Event event) {
		//TODO: process the given event 
		return Response.noContent().build();
	}

	@PATCH
    @Path("/{id:[0-9][0-9]*}")
    public Response update_partially(@PathParam("id") Long id, final Map<String, Object> data) {
        //TODO: process the given event
        for (String key:data.keySet()) {
            try {
                System.out.println(Event.class.getDeclaredField(key).toString());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
        return Response.noContent().build();
    }

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		//TODO: process the event matching by the given id 
		return Response.noContent().build();
	}

}
