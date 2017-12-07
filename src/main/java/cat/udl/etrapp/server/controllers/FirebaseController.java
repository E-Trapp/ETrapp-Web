package cat.udl.etrapp.server.controllers;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FirebaseController {

    static {
        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream("e-trapp-5d5b92dd3b2c.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                    .setDatabaseUrl("https://e-trapp.firebaseio.com/")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FirebaseApp.initializeApp(options);
    }

    private static FirebaseController instance;

    private FirebaseController() {
    }

    public static synchronized FirebaseController getInstance() {
        if (instance == null) instance = new FirebaseController();
        return instance;
    }

}
