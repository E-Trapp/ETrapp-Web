package cat.udl.etrapp.server.models;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Event {

    public static final Set<String> updatable = new HashSet<>(Arrays.asList(
            "title",
            "description",
            "location",
            "isEnabled",
            "isFeatured",
            "startsAt")
    );

    private long id;
    private long owner;
    private String title;
    private String location;
    private long category;
    private String description;
    private long startsAt;
    private boolean isEnabled;
    private boolean isFeatured;

    public Event() {
    }

    public String getLocation() {
        return location;
    }

    public long getStartsAt() {
        return startsAt;
    }


    public void setStartsAt(long startsAt) {
        this.startsAt = startsAt;
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
