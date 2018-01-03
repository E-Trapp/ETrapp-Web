package cat.udl.etrapp.server.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.*;

public class Event {

    public static final Set<String> updatable = new HashSet<>(Arrays.asList(
            "title",
            "description",
            "location",
            "isEnabled",
            "isFeatured",
            "startsAt")
    );

    private static final Map<String, String> keyMap;
    static {
        keyMap = new HashMap<>();
        keyMap.put("startsAt", "starts_at");
        keyMap.put("isEnabled", "is_enabled");
        keyMap.put("isFeatured", "is_featured");
    }

    public static String map_keys(String key) {
        return keyMap.getOrDefault(key, key);
    }

    private long id;
    private long owner;
    private String title;
    private String location;
    private long category;
    private String description;
    private String imageUrl;
    private long startsAt;
    private boolean isEnabled;
    private boolean isFeatured;

    public Event() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(long startsAt) {
        this.startsAt = startsAt;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
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
