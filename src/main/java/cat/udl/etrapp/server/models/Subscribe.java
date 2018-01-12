package cat.udl.etrapp.server.models;

public class Subscribe {
    private long id;
    private long event_id;
    private long user_id;

    public Subscribe(){}

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public long getEvent_id() { return event_id; }

    public void setEvent_id(long event_id) { this.event_id = event_id; }

    public long getUser_id() { return user_id; }

    public void setUser_id(long user_id) { this.user_id = user_id; }

}
