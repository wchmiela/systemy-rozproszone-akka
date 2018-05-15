package pl.edu.agh.sr.bookstore.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Book implements Serializable {
    private final String name;
    private final BigDecimal price;

    public Book(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
