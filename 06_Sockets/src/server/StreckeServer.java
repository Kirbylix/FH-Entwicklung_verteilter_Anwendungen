package server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP Server Empfänger
 */
public class StreckeServer
{
	private static int port = 4712;
	private static ServerSocket serverSocket;
	private static boolean run;

		public static void main(String[] args)
		{
			StreckeServer streckeServer = new StreckeServer();
			streckeServer.start();
		}

		public StreckeServer(){
			try {
				run = true;
				serverSocket = new ServerSocket(port);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

	/**
	 * TCP Empfänger starten
	 */
	public void start(){
			System.out.println("Warte für Client Nachricht...");

			while (run) {
				try {
					Socket clientSocket = serverSocket.accept();
					System.out.println("Client " + clientSocket.getInetAddress() + " verbindet sich");
                    // für jede empfangene Nachricht wird ein neuer ClientHandler erzeugt der die Nachricht verarbeitet und antwortet
					new ClientHandler(clientSocket);
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
		}

		public void stop(){
			try {
				run = false;
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}