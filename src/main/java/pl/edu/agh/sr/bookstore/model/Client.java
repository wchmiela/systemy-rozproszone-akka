package pl.edu.agh.sr.bookstore.model;

import java.io.Serializable;

public class Client implements Serializable {
    private final String firstName;
    private final String secondName;

    public Client(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    @Override
    public String toString() {
        return String.format("%s %s", firstName, secondName);
    }
}
