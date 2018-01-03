package cat.udl.etrapp.server.api.endpoints;

import cat.udl.etrapp.server.api.annotations.Secured;
import cat.udl.etrapp.server.daos.EventMessagesDAO;
import cat.udl.etrapp.server.models.EventComment;
import cat.udl.etrapp.server.models.EventMessage;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cat.udl.etrapp.server.utils.Utils.getAuthToken;

@RequestScoped
@Path("/events/{id}/comments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventCommentsEndpoint {

    @PathParam("id")
    long eventId;

//    @GET
//    public Response getMessages(@QueryParam("start") final Integer startPosition,
//                                @QueryParam("max") final Integer maxResult) {
//        List<EventMessage> messages = EventMessagesDAO.getInstance().getMessages(startPosition, maxResult, eventId);
//        if (messages.isEmpty()) {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        } else return Response.ok(messages).build();
//
//    }

    @POST
    @Secured
    public Response writeComment(@Context HttpHeaders headers, EventComment message) {
        EventComment eventComment = EventMessagesDAO.getInstance().writeComment(message, getAuthToken(headers), eventId);
        Map<String, String> data = new HashMap<>();
        data.put("id", String.valueOf(eventId));
        data.put("index", String.valueOf(eventComment.getId()));
        return Response.created(UriBuilder.fromResource(EventCommentsEndpoint.class).buildFromMap(data)).build();
    }

    @GET
    @Path("/{index}")
    public Response getMessage() {
        // Messages can only be retrieved through Firebase.
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
