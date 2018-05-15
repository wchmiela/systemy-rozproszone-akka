package pl.edu.agh.sr.bookstore.actors;

import akka.actor.AbstractActor;

public class StreamActor extends AbstractActor {

    @Override
    public void preStart() throws Exception {
        super.preStart();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().build();
    }
}
