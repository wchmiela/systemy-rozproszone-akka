package pl.edu.agh.sr.bookstore.actors;

import akka.actor.AbstractActor;
import pl.edu.agh.sr.bookstore.messages.BookstoreRequest;
import scala.Option;

public class ClientActor extends AbstractActor {

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        super.preRestart(reason, message);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(BookstoreRequest.class, request -> {
            String path = "akka.tcp://server_system@127.0.0.1:3552/user/server";

            getContext().actorSelection(path).tell(request, null);
        }).build();
    }
}
