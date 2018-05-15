package pl.edu.agh.sr.bookstore.messages;

import pl.edu.agh.sr.bookstore.model.Client;

public class RequestStreamBook extends BookstoreRequest {

    private final String bookName;

    public RequestStreamBook(Client client, String bookName) {
        super(client);
        this.bookName = bookName;
    }

    @Override
    public String toString() {
        return String.format("Operacja - Ksiazka do strumieniowania: %s od Klienta: %s", bookName, super.getClient());
    }
}
