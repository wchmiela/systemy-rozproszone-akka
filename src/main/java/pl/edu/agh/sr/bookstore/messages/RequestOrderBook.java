package pl.edu.agh.sr.bookstore.messages;

import pl.edu.agh.sr.bookstore.model.Client;

public class RequestOrderBook extends BookstoreRequest {

    private final String bookName;

    public RequestOrderBook(Client client, String bookName) {
        super(client);
        this.bookName = bookName;
    }

    @Override
    public String toString() {
        return String.format("--Zamowienie-- Ksiazka: %s Klient: %s", bookName, super.getClient());
    }
}
