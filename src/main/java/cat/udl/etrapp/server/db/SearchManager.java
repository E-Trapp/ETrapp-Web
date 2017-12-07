package cat.udl.etrapp.server.db;

import com.algolia.search.AsyncAPIClient;
import com.algolia.search.AsyncHttpAPIClientBuilder;

public class SearchManager {

    private static AsyncAPIClient client;

    static {
        client = new AsyncHttpAPIClientBuilder("", "").build();
    }

    public static AsyncAPIClient getClient() {
        return client;
    }

}
