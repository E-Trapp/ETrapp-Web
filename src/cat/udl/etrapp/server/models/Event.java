package cat.udl.etrapp.server.models;

public class Event {

    private long id;
    private long owner;
    private String title;
    private long category;
    private String description;

    public Event() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public long getCategory() {
        return category;
    }

    public void setCategory(long category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", owner=" + owner +
                ", title='" + title + '\'' +
                ", category=" + category +
                ", description='" + description + '\'' +
                '}';
    }
}
