package cat.udl.etrapp.server.controllers;

import cat.udl.etrapp.server.models.EventMessage;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

public class FirebaseController {

    //    private static final String token = "ya29.c.ElsdBc_kntxDjA08b66guy0EsaSg7e8bhUfyvHY7ap4GHBRJPWqM4Q97RSSo9_cJ-H-CBfL983TsjOCV9kz1llxK4vZcdNdDkjSW5nOxIB-jHSUhhbvQiPAJiOMX";
    private static final String token = "nGJ9TaspgBT0fjffy7skgYrp69XV8SmSJ9xvtfo5";
    private static final String dbUrl = "https://e-trapp.firebaseio.com/";
    private static FirebaseController instance;

    private FirebaseController() {
    }

    public static synchronized FirebaseController getInstance() {
        if (instance == null) instance = new FirebaseController();
        return instance;
    }

    public void writeMessage(long eventId, EventMessage eventMessage) {
        Client client = ClientBuilder.newClient();
        Map<String, Object> data = eventMessage.asMap();
        Map<String, String> timestamp = new HashMap<>();
        timestamp.put(".sv", "timestamp");
        data.put("timestamp", timestamp);
        client.target(dbUrl + "eventMessages/event" + eventId + ".json?auth=" + token)
                .request(MediaType.APPLICATION_JSON)
                .async()
                .post(Entity.entity(data, MediaType.APPLICATION_JSON));
    }
}
