package pl.edu.agh.sr.bookstore.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import pl.edu.agh.sr.bookstore.messages.ReplySearchBook;
import pl.edu.agh.sr.bookstore.messages.RequestSearchBook;

import java.util.HashMap;
import java.util.Map;

public class SearchActor extends AbstractActor {

    private final String serverPath = "akka.tcp://server_system@127.0.0.1:3552/user/server";
    private final String path = String.format("%s/%s", serverPath, "search");
    private final Map<String, String> paths = new HashMap<>();

    @Override
    public void preStart() throws Exception {
        context().actorOf(Props.create(DatabaseActor.class), "dba1");
        context().actorOf(Props.create(DatabaseActor.class), "dba2");
        paths.put("dba1", String.format("%s/%s", path, "dba1"));
        paths.put("dba2", String.format("%s/%s", path, "dba2"));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(RequestSearchBook.class, request -> {
            getContext().actorSelection(paths.get("dba1")).tell(request, getSelf());
            //getContext().actorSelection(paths.get("dba2")).tell(request, getSelf());
        }).match(ReplySearchBook.class, reply -> {
            context().actorSelection(serverPath).tell(reply, self());
        }).build();
    }
}
