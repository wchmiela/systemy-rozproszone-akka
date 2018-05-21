package pl.edu.agh.sr.bookstore.actors;

import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.stream.ActorMaterializer;
import akka.stream.OverflowStrategy;
import akka.stream.ThrottleMode;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import pl.edu.agh.sr.bookstore.messages.ReplyStreamBook;
import pl.edu.agh.sr.bookstore.messages.RequestStreamBook;
import pl.edu.agh.sr.bookstore.model.Book;
import scala.concurrent.duration.FiniteDuration;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class StreamActor extends AbstractActor {

    private final static String booksRootPath = "/home/wojtek/Dropbox/Studia/Semestr_6/Systemy_rozproszone/Laboratoria/Lab_5_Akka/bookstore/src/main/resources/books/";

    @Override
    public void preStart() throws Exception {
        super.preStart();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(RequestStreamBook.class, request -> {
            String bookPath = String.format("%s%s.txt", booksRootPath, request.getBookName());

            ActorMaterializer materializer = ActorMaterializer.create(getContext());
            ActorRef run = Source.actorRef(1000, OverflowStrategy.dropNew())
                    .throttle(1, FiniteDuration.create(1, TimeUnit.SECONDS), 1, ThrottleMode.shaping())
                    .to(Sink.actorRef(getSender(), NotUsed.getInstance()))
                    .run(materializer);

            Book book = new Book(request.getBookName(), BigDecimal.ZERO);

            Stream<String> lines = Files.lines(Paths.get(bookPath));
            lines.filter(line -> line.length() != 0).forEachOrdered(
                    line -> run.tell(new ReplyStreamBook(book, request.getClient(), line), self()));
        }).build();
    }
}
