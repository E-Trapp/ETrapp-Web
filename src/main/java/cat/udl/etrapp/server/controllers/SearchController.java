package cat.udl.etrapp.server.controllers;

import cat.udl.etrapp.server.daos.EventsDAO;
import cat.udl.etrapp.server.db.SearchManager;
import cat.udl.etrapp.server.models.AlgoliaEvent;
import cat.udl.etrapp.server.models.Event;
import cat.udl.etrapp.server.models.User;
import cat.udl.etrapp.server.models.UserInfo;
import com.algolia.search.AsyncIndex;

import java.util.Map;

public class SearchController {

    private static final String INDEX_EVENTS = "events";
    private static final String INDEX_USERS = "users";

    private static SearchController instance;
    private static AsyncIndex<AlgoliaEvent> eventIndex;
    private static AsyncIndex<UserInfo> usersIndex;

    private SearchController() {
        eventIndex = SearchManager.getClient().initIndex(INDEX_EVENTS, AlgoliaEvent.class);
        usersIndex = SearchManager.getClient().initIndex(INDEX_USERS, UserInfo.class);
    }

    public static synchronized SearchController getInstance() {
        if (instance == null) instance = new SearchController();
        return instance;
    }

    public void addUser(User user) {
        usersIndex.addObject(UserInfo.fromUser(user));
    }

    public void addEvent(Event event) {
        eventIndex.addObject(AlgoliaEvent.fromEvent(event));
    }

    public void editEvent(long id, Map<String, Object> updates) {
        Event e = EventsDAO.getInstance().getEventById(id);

        for (String key : updates.keySet()) {
            switch (key) {
                case "title":
                    e.setTitle((String) updates.get(key));
                    break;
                case "description":
                    e.setDescription((String) updates.get(key));
                    break;
                case "location":
                    e.setLocation((String) updates.get(key));
                    break;
                case "isEnabled":
                    e.setEnabled((boolean) updates.get(key));
                    break;
                case "isFeatured":
                    e.setFeatured((boolean) updates.get(key));
                    break;
                case "startsAt":
                    e.setStartsAt((long) updates.get(key));
                    break;
            }
        }

        eventIndex.partialUpdateObject(String.valueOf(id), e);
    }

}
