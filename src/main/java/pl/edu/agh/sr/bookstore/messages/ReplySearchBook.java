package pl.edu.agh.sr.bookstore.messages;

import pl.edu.agh.sr.bookstore.model.Book;
import pl.edu.agh.sr.bookstore.model.Client;

public class ReplySearchBook extends BookstoreReply {

    private final boolean result;
    private final String database;

    public ReplySearchBook(Book book, Client client, boolean result, String database) {
        super(book, client);
        this.result = result;
        this.database = database;
    }

    @Override
    public String toString() {
        if (result) {
            return String.format("%s Ksiazka: %s Cena: %s Klient: %s w bazie danych: %s.",
                    "Znaleziono", super.getBook().getName(), super.getBook().getPrice(), super.getClient(), database);
        } else {
            return String.format("%s Ksiazka: %s Klient: %s w bazie danych: %s.", "" +
                    "Nie znaleziono", super.getBook().getName(), super.getClient(), database);
        }
    }
}
