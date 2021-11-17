package service.clienthandler;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;


    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        OutputStream clientOutput = null;
        BufferedReader clientInput = null;

        try {
            clientOutput = clientSocket.getOutputStream();
            clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            StringBuilder request = new StringBuilder();
            String line; // temp variable that holds one line at time
            line = clientInput.readLine();
            while (!line.isBlank()) {
                try {
                    request.append(line + "\r\n");
                    line = clientInput.readLine();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
            }
            System.out.println("--REQUEST--");
            System.out.println(request);


            FileInputStream html = new FileInputStream("src/main/resources/index.html");
            FileInputStream json = new FileInputStream("src/main/resources/dazfile.json");
            FileInputStream err404 = new FileInputStream("src/main/resources/error/error404.jpeg");


            String firstLine = request.toString().split("\n")[0];
            String resource = firstLine.split(" ")[1];
            System.out.println(resource);


            if (resource.equals("/")) {
                clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
                clientOutput.write(("\r\n").getBytes());
                clientOutput.write(html.readAllBytes());
                clientOutput.flush();
            } else if(resource.equals("/json")) {
                clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
                clientOutput.write(("\r\n").getBytes());
                clientOutput.write(json.readAllBytes());
                clientOutput.flush();
            } else {
                clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
                clientOutput.write(("\r\n").getBytes());
                clientOutput.write(err404.readAllBytes());
                clientOutput.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (clientOutput != null) {
                    clientOutput.close();
                }
                if(clientInput != null)
                    clientInput.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
