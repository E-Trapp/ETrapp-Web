package cat.udl.etrapp.server.db;

import com.algolia.search.AsyncAPIClient;
import com.algolia.search.AsyncHttpAPIClientBuilder;

public class SearchManager {

    private static AsyncAPIClient client;

    static {
        client = new AsyncHttpAPIClientBuilder("DIECQMQ6FF", "7fd1e81146b9c68f1427e5babb2dd643").build();
    }

    public static AsyncAPIClient getClient() {
        return client;
    }

}
