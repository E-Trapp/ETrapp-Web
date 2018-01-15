package cat.udl.etrapp.server.api.endpoints;

import cat.udl.etrapp.server.daos.CategoriesDAO;
import cat.udl.etrapp.server.daos.ScoresDAO;
import cat.udl.etrapp.server.models.Category;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/scores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScoreEndpoint {

    @GET
    public Response userSubscribed(@QueryParam("event") long eventId,@QueryParam("user")  long userId) throws Exception {
        if(ScoresDAO.getInstance().getSubscribed(eventId, userId))
            return Response.ok().build();
        else
            return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
