package pl.edu.agh.sr.bookstore;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import pl.edu.agh.sr.bookstore.actors.ClientActor;
import pl.edu.agh.sr.bookstore.messages.BookstoreRequest;
import pl.edu.agh.sr.bookstore.messages.RequestOrderBook;
import pl.edu.agh.sr.bookstore.messages.RequestSearchBook;
import pl.edu.agh.sr.bookstore.messages.RequestStreamBook;
import pl.edu.agh.sr.bookstore.model.Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientApp {

    private static final String configurationPath = "/home/wojtek/Dropbox/Studia/Semestr_6/Systemy_rozproszone/Laboratoria/Lab_5_Akka/bookstore/src/main/resources/configuration/clientApp.conf";
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        System.out.println("===Konfiguracja Klienta START===");
        String firstName = "Jan";
        String secondName = "Kowalski";
//        System.out.print("Podaj swoje imie i nazwisko np. Jan Kowalski: ");
//        try {
//            String[] splittedLine = bufferedReader.readLine().split(" ");
//            firstName = splittedLine[0];
//            secondName = splittedLine[1];
//        } catch (IOException e) {
//            System.out.println("Wystapil blad z wczytaniem danych klienta.");
//        }

        Client client = new Client(firstName, secondName);

        File configFile = new File(configurationPath);
        Config config = ConfigFactory.parseFile(configFile);

        final ActorSystem system = ActorSystem.create("client_system", config);
        final ActorRef clientActor = system.actorOf(Props.create(ClientActor.class), "client");

        System.out.println("===Konfiguracja Klienta STOP===");

        System.out.print("----Wczytaj opcje\n" +
                "\t[1] Wyszukanie pozycji\n" +
                "\t[2] Zamowienie pozycji\n" +
                "\t[3] Strumieniowanie tekstu\n" +
                "\t[_] Zamknij program\n");

        System.out.print("Opcja: ");
        int option = -1;
        do {
            try {
                option = Integer.parseInt(bufferedReader.readLine());
            } catch (IOException e) {
                System.out.println("Wystapil blad z wczytaniem menu.");
            }
        } while (performTask(client, clientActor, option));

        system.terminate();
    }

    private static boolean performTask(Client client, ActorRef actor, int menuOption) {
        switch (menuOption) {
            case 1:
                try {
                    System.out.print("Wczytaj ksiazke do wyszukania: ");
                    String bookName = bufferedReader.readLine();
                    BookstoreRequest request = new RequestSearchBook(client, bookName);
                    actor.tell(request, null);
                } catch (IOException e) {
                    System.out.println("....");
                }

                break;
            case 2:
                try {
                    System.out.print("Wczytaj ksiazke do zamowienia: ");
                    String bookName = bufferedReader.readLine();
                    BookstoreRequest request = new RequestOrderBook(client, bookName);
                    actor.tell(request, null);
                } catch (IOException e) {
                    System.out.println("....");
                }

                break;
            case 3:
                try {
                    System.out.print("Wczytaj ksiazke do strumieniowania: ");
                    String bookName = bufferedReader.readLine();
                    BookstoreRequest request = new RequestStreamBook(client, bookName);
                    actor.tell(request, null);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            default:

                return false;
        }

        return true;
    }
}
