package pl.edu.agh.sr.bookstore.actors;

import akka.actor.AbstractActor;
import pl.edu.agh.sr.bookstore.messages.BookstoreReply;
import pl.edu.agh.sr.bookstore.messages.ReplyOrderBook;
import pl.edu.agh.sr.bookstore.messages.ReplySearchBook;
import pl.edu.agh.sr.bookstore.messages.RequestSearchBook;
import pl.edu.agh.sr.bookstore.model.Book;
import pl.edu.agh.sr.bookstore.model.UnknownBook;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class DatabaseActor extends AbstractActor {


    private final String serverPath = "akka.tcp://server_system@127.0.0.1:3552/user/server";
    private final String path = String.format("%s/%s", serverPath, "search");

    private String db1Path = "/home/wojtek/Dropbox/Studia/Semestr_6/Systemy_rozproszone/Laboratoria/Lab_5_Akka/bookstore/src/main/resources/database/database1.txt";

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(RequestSearchBook.class, request -> {
            try (Stream<String> lines = Files.lines(Paths.get(db1Path)).parallel()) {
                Optional<String> bookTuple = lines
                        .filter(line -> line.split(";")[0].equals(request.getBookName()))
                        .findFirst();

                BookstoreReply reply;
                if (bookTuple.isPresent()) {
                    String[] splitted = bookTuple.get().split(";");
                    String bookName = splitted[0];
                    BigDecimal price = new BigDecimal(splitted[1]).setScale(2, RoundingMode.HALF_UP);
                    reply = new ReplySearchBook(new Book(bookName, price), request.getClient(), true);
                } else {
                    reply = new ReplySearchBook(new UnknownBook("Unknown", BigDecimal.ZERO), request.getClient(), false);
                }

                context().sender().tell(reply, self());
            }
        }).build();
    }
}
