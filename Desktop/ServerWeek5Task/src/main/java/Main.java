import service.clienthandler.ClientHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;


public class Main {
    private static ServerSocket serverSocket;
    private static int port = 5050;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started.\n Listening for messages...");
            // The main Thread, accepting new connections
            while (true) {
                // 2. Handle a new incoming message
                // Here we've set the server to receive messages from the client
                Socket client = serverSocket.accept();
                System.out.println("SEE THE GUY" + client.getLocalSocketAddress());
                // client messages queued up
                System.out.println("New Client Connected" + client.getInetAddress().getHostAddress());
                ClientHandler clientSock = new ClientHandler(client);
                // Background thread handling each client separately
                new Thread(clientSock).start();
            }
        }    catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
