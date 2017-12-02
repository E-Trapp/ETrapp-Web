package cat.udl.etrapp.server.api;

import cat.udl.etrapp.server.api.annotations.Secured;
import cat.udl.etrapp.server.controllers.EventMessagesDAO;
import cat.udl.etrapp.server.controllers.EventsDAO;
import cat.udl.etrapp.server.models.EventMessage;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.util.HashMap;
import java.util.Map;

import static cat.udl.etrapp.server.utils.Utils.getAuthToken;

@RequestScoped
@Path("/events/{id}/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventMessagesEndpoint {

    @PathParam("id")
    long eventId;

    @POST
    @Secured
    public Response writeMessage(@Context HttpHeaders headers, EventMessage message) {
        EventMessage eventMessage = EventMessagesDAO.getInstance().writeMessage(message, getAuthToken(headers), eventId);
        Map<String, String> data = new HashMap<>();
        data.put("id", String.valueOf(eventId));
        data.put("index", String.valueOf(eventMessage.getId()));
        return Response.created(UriBuilder.fromResource(EventMessagesEndpoint.class).buildFromMap(data)).build();
    }

    @GET
    @Path("/{index}")
    public Response getMessage() {
        return Response.ok().build();
    }

}
