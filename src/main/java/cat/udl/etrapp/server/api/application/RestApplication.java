package cat.udl.etrapp.server.api.application;

import cat.udl.etrapp.server.api.endpoints.*;
import cat.udl.etrapp.server.api.filters.AuthenticationFilter;
import cat.udl.etrapp.server.api.filters.AuthorizationFilter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/v1")
public class RestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<>(Arrays.asList(
                AuthEndpoint.class,
                CategoriesEndpoint.class,
                EventsEndpoint.class,
                EventCommentsEndpoint.class,
                EventMessagesEndpoint.class,
                UsersEndpoint.class,
                AuthenticationFilter.class,
                AuthorizationFilter.class));
    }

}
