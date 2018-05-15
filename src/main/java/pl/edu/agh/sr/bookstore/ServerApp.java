package pl.edu.agh.sr.bookstore;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import pl.edu.agh.sr.bookstore.actors.OrderActor;
import pl.edu.agh.sr.bookstore.actors.SearchActor;
import pl.edu.agh.sr.bookstore.actors.ServerActor;
import pl.edu.agh.sr.bookstore.actors.StreamActor;
import pl.edu.agh.sr.bookstore.model.Bookstore;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerApp {

    private static final String configurationPath = "/home/wojtek/Dropbox/Studia/Semestr_6/Systemy_rozproszone/Laboratoria/Lab_5_Akka/bookstore/src/main/resources/configuration/serverApp.conf";
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        System.out.println("===Konfiguracja Serwera START===");
        String bookstoreName = "Bookstore";

//        try {
//            bookstoreName = bufferedReader.readLine();
//        } catch (IOException e) {
//            System.out.println("Wystapil blad z wczytaniem danych ksiegarni.");
//        }

        File configFile = new File(configurationPath);
        Config config = ConfigFactory.parseFile(configFile);

        Bookstore bookstore = new Bookstore(bookstoreName);

        final ActorSystem system = ActorSystem.create("server_system", config);
        final ActorRef serverActor = system.actorOf(Props.create(ServerActor.class), "server");

        System.out.println("===Konfiguracja Serwera STOP===");

        while (true) {
            try {
                String line = bufferedReader.readLine();
                if (line.equals("exit")) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        system.terminate();
    }
}
