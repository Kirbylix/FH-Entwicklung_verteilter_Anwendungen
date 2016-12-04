package server;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;

public class StreckeServer {
    private static HttpServer server = null;
    private final static String BASE_URL = "http://localhost:8080/rest";

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        // Eine Instanz der Strecke muss bereitgestellt werden.
        Strecke strecke = Strecke.getInstance();

        // ToDo: Der Jersey-Server muss gestartet werden
        try {
            server = HttpServerFactory.create(BASE_URL);
            server.start();
            System.out.println("Server START");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        // Die Stecke-Instanz muss gespeichert werden
        Strecke.getInstance().streckeSpeichern();

        // ToDo: Der Jersey-Server muss gestoppt werden.
        server.stop(0);
        System.out.println("Server STOP");
    }
}