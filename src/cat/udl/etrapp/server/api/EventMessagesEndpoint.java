package cat.udl.etrapp.server.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path("/events/{id}/messages")
public class EventMessagesEndpoint {

    @PathParam("id") long eventId;

    @POST
    public Response writeMessage(Map<String, String> data) {
        System.out.println("Event id: " + eventId + " payload: " + data.keySet().toString());
        return Response.ok().build();
    }

}
