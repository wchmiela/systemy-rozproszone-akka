package pl.edu.agh.sr.bookstore.actors;

import akka.actor.AbstractActor;
import pl.edu.agh.sr.bookstore.messages.BookstoreReply;
import pl.edu.agh.sr.bookstore.messages.BookstoreRequest;

public class ClientActor extends AbstractActor {

    private final String serverPath = "akka.tcp://server_system@127.0.0.1:3552/user/server";

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(BookstoreRequest.class, request -> {
            context().actorSelection(serverPath).tell(request, getSelf());
        }).match(BookstoreReply.class, reply -> {
            String message = String.format(">Otrzymano: %s", reply);
            System.out.println(message);
        }).build();
    }
}
