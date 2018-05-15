package pl.edu.agh.sr.bookstore.messages;

import pl.edu.agh.sr.bookstore.model.Book;
import pl.edu.agh.sr.bookstore.model.Client;

public abstract class BookstoreReply {

    private final Book book;
    private final Client client;

    protected BookstoreReply(Book book, Client client) {
        this.book = book;
        this.client = client;
    }

    public Book getBook() {
        return book;
    }

    public Client getClient() {
        return client;
    }
}
