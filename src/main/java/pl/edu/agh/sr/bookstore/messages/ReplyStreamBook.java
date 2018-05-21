package pl.edu.agh.sr.bookstore.messages;

import pl.edu.agh.sr.bookstore.model.Book;
import pl.edu.agh.sr.bookstore.model.Client;

public class ReplyStreamBook extends BookstoreReply {

    private final String line;

    public ReplyStreamBook(Book book, Client client, String line) {
        super(book, client);
        this.line = line;
    }

    @Override
    public String toString() {
        return String.format("%s\n", line);
    }
}
