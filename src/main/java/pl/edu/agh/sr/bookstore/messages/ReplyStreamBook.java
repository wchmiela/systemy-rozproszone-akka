package pl.edu.agh.sr.bookstore.messages;

import pl.edu.agh.sr.bookstore.model.Book;
import pl.edu.agh.sr.bookstore.model.Client;

public class ReplyStreamBook extends BookstoreReply {

    protected ReplyStreamBook(Book book, Client client) {
        super(book, client);
    }
}
