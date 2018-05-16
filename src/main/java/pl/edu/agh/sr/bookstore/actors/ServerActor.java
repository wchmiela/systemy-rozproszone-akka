package pl.edu.agh.sr.bookstore.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import pl.edu.agh.sr.bookstore.messages.*;

import java.util.HashMap;
import java.util.Map;

public class ServerActor extends AbstractActor {

    private final String clientPath = "akka.tcp://client_system@127.0.0.1:2552/user/client";
    private final String serverPath = "akka.tcp://server_system@127.0.0.1:3552/user/server";
    private final Map<String, String> paths = new HashMap<>();

    @Override
    public void preStart() throws Exception {
        context().actorOf(Props.create(SearchActor.class), "search");
        context().actorOf(Props.create(OrderActor.class), "order");
        context().actorOf(Props.create(StreamActor.class), "stream");
        paths.put("search", String.format("%s/%s", serverPath, "search"));
        paths.put("order", String.format("%s/%s", serverPath, "order"));
        paths.put("stream", String.format("%s/%s", serverPath, "stream"));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(BookstoreRequest.class, request -> {
            String message = String.format(">Otrzymano: %s.", request);
            System.out.println(message);

            if (request instanceof RequestSearchBook) {
                getContext().actorSelection(paths.get("search")).tell(request, getSelf());
            } else if (request instanceof RequestOrderBook) {
                getContext().actorSelection(paths.get("order")).tell(request, getSelf());
            } else if (request instanceof RequestStreamBook) {
                getContext().actorSelection(paths.get("stream")).tell(request, getSelf());
            }
        }).match(BookstoreReply.class, reply -> {
            context().actorSelection(clientPath).tell(reply, self());
        }).build();
    }
}
