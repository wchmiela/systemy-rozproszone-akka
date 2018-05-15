package pl.edu.agh.sr.bookstore.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import pl.edu.agh.sr.bookstore.messages.BookstoreRequest;

public class ServerActor extends AbstractActor {

    @Override
    public void preStart() throws Exception {
        context().actorOf(Props.create(OrderActor.class), "order");
        context().actorOf(Props.create(SearchActor.class), "search");
        context().actorOf(Props.create(StreamActor.class), "stream");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(BookstoreRequest.class, request -> {

            System.out.println(request);

        }).build();
    }
}
