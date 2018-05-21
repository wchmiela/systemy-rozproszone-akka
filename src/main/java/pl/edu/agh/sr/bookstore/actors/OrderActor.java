package pl.edu.agh.sr.bookstore.actors;

import akka.actor.AbstractActor;
import pl.edu.agh.sr.bookstore.messages.BookstoreReply;
import pl.edu.agh.sr.bookstore.messages.ReplyOrderBook;
import pl.edu.agh.sr.bookstore.messages.RequestOrderBook;
import pl.edu.agh.sr.bookstore.model.Book;
import pl.edu.agh.sr.bookstore.model.Order;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

public class OrderActor extends AbstractActor {

    private final static String ordersPath = "/home/wojtek/Dropbox/Studia/Semestr_6/Systemy_rozproszone/Laboratoria/Lab_5_Akka/bookstore/src/main/resources/orders.txt";

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(RequestOrderBook.class, request -> {
            File file = new File(ordersPath);
            if (!file.exists()) {
                file.createNewFile();
            }

            Book book = new Book(request.getBookName(), BigDecimal.ZERO);
            Order order = new Order(book, request.getClient());

            writeToFile(file, order);

            BookstoreReply reply = new ReplyOrderBook(book, request.getClient(), true);
            context().sender().tell(reply, self());
        }).build();
    }

    private void writeToFile(File file, Order order) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));

        bufferedWriter.write(order.toString() + "\n");
        bufferedWriter.flush();
    }
}
