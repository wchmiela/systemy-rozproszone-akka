package pl.edu.agh.sr.bookstore.messages;

import pl.edu.agh.sr.bookstore.model.Client;

public final class RequestSearchBook extends BookstoreRequest {

    private final String bookName;

    public RequestSearchBook(Client client, String bookName) {
        super(client);
        this.bookName = bookName;
    }

    @Override
    public String toString() {
        return String.format("Wyszukiwanie--- Ksiazka: %s Klient: %s", bookName, super.getClient());
    }

    public String getBookName() {
        return bookName;
    }
}
