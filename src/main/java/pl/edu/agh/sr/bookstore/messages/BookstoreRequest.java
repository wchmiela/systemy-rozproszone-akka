package pl.edu.agh.sr.bookstore.messages;

import pl.edu.agh.sr.bookstore.model.Client;

import java.io.Serializable;

public abstract class BookstoreRequest implements Serializable {

    private final Client client;

    protected BookstoreRequest(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
}
