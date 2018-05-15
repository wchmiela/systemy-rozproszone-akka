package pl.edu.agh.sr.bookstore.messages;

import pl.edu.agh.sr.bookstore.model.Book;
import pl.edu.agh.sr.bookstore.model.Client;

public class ReplySearchBook extends BookstoreReply {

    private final boolean result;

    public ReplySearchBook(Book book, Client client, boolean result) {
        super(book, client);
        this.result = result;
    }

    @Override
    public String toString() {
        return String.format("%s Ksiazka: %s Cena: %s Klient: %s",
                result ? "--Znaleziono--" : "--Nie znaleziono--",
                super.getBook(),
                super.getBook().getPrice(),
                super.getClient());
    }
}
