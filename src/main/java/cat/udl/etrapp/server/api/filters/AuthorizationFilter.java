package cat.udl.etrapp.server.api.filters;

import cat.udl.etrapp.server.api.annotations.Authorized;
import cat.udl.etrapp.server.api.exceptions.InvalidTokenException;
import cat.udl.etrapp.server.api.exceptions.UnauthorizedResourceAccessException;
import cat.udl.etrapp.server.daos.EventsDAO;
import cat.udl.etrapp.server.daos.UsersDAO;
import cat.udl.etrapp.server.models.Event;
import cat.udl.etrapp.server.models.User;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@Provider
@Authorized
@Priority(Priorities.AUTHENTICATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    private static final String REALM = "example";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        final List<PathSegment> pathSegments = requestContext.getUriInfo().getPathSegments();
        final String resource = pathSegments.get(0).toString();
        final long resourceId = Long.parseLong(pathSegments.get(1).toString());

        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader
                .substring(AUTHENTICATION_SCHEME.length()).trim();

        try {
            // Validate the token
            validateToken(token);

            // Once the token is valid, we compare the user against the resource owner
            final User requestUser = UsersDAO.getInstance().getUserByToken(token);
            switch (resource) {
                case "users":
                    final User user;
                    if ((user = UsersDAO.getInstance().getUserById(resourceId)) != null) {
                        if (user.getId() != requestUser.getId())
                            throw new UnauthorizedResourceAccessException("Access forbidden");
                    } else {
                        abortWithNotFound(requestContext);
                    }
                    break;
                case "events":
                    final Event event;
                    if ((event = EventsDAO.getInstance().getEventById(resourceId)) != null) {
                        if (event.getOwner() != requestUser.getId())
                            throw new UnauthorizedResourceAccessException("Access forbidden");
                    } else {
                        abortWithNotFound(requestContext);
                    }

            }
        } catch (InvalidTokenException e) {
            abortWithUnauthorized(requestContext);
        } catch (UnauthorizedResourceAccessException e) {
            abortWithForbidden(requestContext);
        }
    }

    private void abortWithForbidden(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {

        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {

        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE,
                                AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"")
                        .build());
    }

    private void abortWithNotFound(ContainerRequestContext requestContext) {
        requestContext.abortWith(Response.status(Response.Status.NOT_FOUND).build());
    }

    private void validateToken(String token) throws InvalidTokenException {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
        if (!UsersDAO.getInstance().validateToken(token))
            throw new InvalidTokenException("Token " + token + " is not valid.");
    }
}