package cat.udl.etrapp.server.api.application;

import cat.udl.etrapp.server.api.AuthEndpoint;
import cat.udl.etrapp.server.api.CategoriesEndpoint;
import cat.udl.etrapp.server.api.EventsEndpoint;
import cat.udl.etrapp.server.api.UsersEndpoint;
import cat.udl.etrapp.server.api.filters.AuthenticationFilter;

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
                UsersEndpoint.class,
                AuthenticationFilter.class));
    }

}
