package cat.udl.etrapp.server.models;

public class AlgoliaEvent extends Event {

    private String objectID;

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public static AlgoliaEvent fromEvent(Event event) {
        AlgoliaEvent algoliaEvent = new AlgoliaEvent();
        algoliaEvent.setId(event.getId());
        algoliaEvent.setOwner(event.getOwner());
        algoliaEvent.setCategory(event.getCategory());
        algoliaEvent.setTitle(event.getTitle());
        algoliaEvent.setDescription(event.getDescription());
        algoliaEvent.setStartsAt(event.getStartsAt());
        algoliaEvent.setImageUrl(event.getImageUrl());
        algoliaEvent.setLocation(event.getLocation());
        algoliaEvent.setFeatured(event.isFeatured());
        algoliaEvent.setEnabled(event.isEnabled());
        algoliaEvent.setObjectID(String.valueOf(event.getId()));
        return algoliaEvent;
    }
}
