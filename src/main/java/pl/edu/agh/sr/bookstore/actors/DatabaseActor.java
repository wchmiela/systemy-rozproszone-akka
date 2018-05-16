package pl.edu.agh.sr.bookstore.actors;

import akka.actor.AbstractActor;
import pl.edu.agh.sr.bookstore.messages.BookstoreReply;
import pl.edu.agh.sr.bookstore.messages.ReplySearchBook;
import pl.edu.agh.sr.bookstore.messages.RequestSearchBook;
import pl.edu.agh.sr.bookstore.model.Book;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class DatabaseActor extends AbstractActor {

    private final String serverPath = "akka.tcp://server_system@127.0.0.1:3552/user/server";
    private final String path = String.format("%s/%s", serverPath, "search");

    private final static String db1Path = "/home/wojtek/Dropbox/Studia/Semestr_6/Systemy_rozproszone/Laboratoria/Lab_5_Akka/bookstore/src/main/resources/database/database1.txt";
    private final static String db2Path = "/home/wojtek/Dropbox/Studia/Semestr_6/Systemy_rozproszone/Laboratoria/Lab_5_Akka/bookstore/src/main/resources/database/database2.txt";

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(RequestSearchBook.class, request -> {
            String dbPathToUse = self().path().name().equals("dba1") ? db1Path : db2Path;
            String database = self().path().name().equals("dba1") ? "database1" : "database2";

            try (Stream<String> lines = Files.lines(Paths.get(dbPathToUse)).parallel()) {
                Optional<String> bookTuple = lines
                        .filter(line -> line.split(";")[0].equals(request.getBookName()))
                        .findFirst();

                BookstoreReply reply;
                if (bookTuple.isPresent()) {
                    String[] splitted = bookTuple.get().split(";");
                    String bookName = splitted[0];
                    BigDecimal price = new BigDecimal(splitted[1]).setScale(2, RoundingMode.HALF_UP);
                    reply = new ReplySearchBook(new Book(bookName, price), request.getClient(), true, database);
                } else {
                    reply = new ReplySearchBook(new Book(request.getBookName(), BigDecimal.ZERO), request.getClient(), false, database);
                }

                context().sender().tell(reply, self());
            }
        }).build();
    }
}
