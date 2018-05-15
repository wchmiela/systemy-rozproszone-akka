package pl.edu.agh.sr.bookstore.model;

import java.io.Serializable;

public class Order implements Serializable {
    private final Book book;
    private final Client client;

    public Order(Book book, Client client) {
        this.book = book;
        this.client = client;
    }
}
