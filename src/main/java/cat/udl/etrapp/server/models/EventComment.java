package cat.udl.etrapp.server.models;

import java.util.HashMap;
import java.util.Map;

public class EventComment {

    private long id;
    private String comment;
    private long userId;
    private long eventId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        // map.put("id", id);
        map.put("comment", comment);
        map.put("userId", userId);
        map.put("eventId", eventId);
        return map;
    }
}
