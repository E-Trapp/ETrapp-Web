package cat.udl.etrapp.server.api.endpoints;

import cat.udl.etrapp.server.daos.CategoriesDAO;
import cat.udl.etrapp.server.models.Category;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriesEndpoint {

    @GET
    public Response getAllCategories() {
        final List<Category> categories = CategoriesDAO.getInstance().getCategories();
        if (categories.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(categories).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void newCategory(@FormParam("title") String categoryTitle,
                            @Context HttpServletResponse response) throws Exception {
        CategoriesDAO.getInstance().createCategory(categoryTitle);
        response.sendRedirect("/etrapp-server/categories.jsp");
    }

    @GET
    @Path("{id}/delete")
    public void deleteCategory(@PathParam("id") long categoryId, @Context HttpServletResponse response) throws Exception {
        CategoriesDAO.getInstance().deleteCategory(categoryId);
        response.sendRedirect("/etrapp-server/categories.jsp");
    }

}
