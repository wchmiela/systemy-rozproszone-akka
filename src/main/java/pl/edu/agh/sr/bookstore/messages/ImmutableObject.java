package pl.edu.agh.sr.bookstore.messages;

import java.io.Serializable;

public final class ImmutableObject implements Serializable {

    private final String test;
    private final Integer i;

    public ImmutableObject(String test, Integer i) {
        this.test = test;
        this.i = i;
    }

    @Override
    public String toString() {
        return "ImmutableObject{" +
                "test='" + test + '\'' +
                ", i=" + i +
                '}';
    }
}
