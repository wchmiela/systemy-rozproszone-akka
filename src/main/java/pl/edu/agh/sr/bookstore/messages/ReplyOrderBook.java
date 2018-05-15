package pl.edu.agh.sr.bookstore.messages;

import pl.edu.agh.sr.bookstore.model.Book;
import pl.edu.agh.sr.bookstore.model.Client;

import java.io.Serializable;

public class ReplyOrderBook extends BookstoreReply implements Serializable {

    private final boolean result;

    public ReplyOrderBook(Book book, Client client, boolean result) {
        super(book, client);
        this.result = result;
    }

    @Override
    public String toString() {
        return String.format("%s Ksiazka: %s Cena: %s Klient: %s",
                result ? "--Zamowiono--" : "--Nie zamowiono--",
                super.getBook(),
                super.getBook().getPrice(),
                super.getClient());
    }
}
