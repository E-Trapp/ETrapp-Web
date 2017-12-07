package cat.udl.etrapp.server.controllers;

import cat.udl.etrapp.server.db.SearchManager;
import cat.udl.etrapp.server.models.Event;
import cat.udl.etrapp.server.models.User;
import cat.udl.etrapp.server.models.UserInfo;
import com.algolia.search.AsyncIndex;

public class SearchController {

    private static final String INDEX_EVENTS = "events";
    private static final String INDEX_USERS = "users";

    private static SearchController instance;
    private static AsyncIndex<Event> eventIndex;
    private static AsyncIndex<UserInfo> usersIndex;

    private SearchController() {
        eventIndex = SearchManager.getClient().initIndex(INDEX_EVENTS, Event.class);
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
        eventIndex.addObject(event);
    }

}
