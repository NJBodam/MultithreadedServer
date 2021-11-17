package service.clienthandler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

class ClientHandlerTest {
    private static ServerSocket serverSocket;
    private static int port = 5052;



    @Test
    void shouldOutputHtml() {
        StringBuilder request = new StringBuilder("""
                GET / HTTP/1.1
                Host: localhost:5050
                """);
        String firstLine = request.toString().split("\n")[0];
        final String expectedOutput = "/";
        final String actualOutput = firstLine.split(" ")[1];;
        Assertions.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    void shouldOutputJson() {
        StringBuilder request = new StringBuilder("""
                GET /json HTTP/1.1
                Host: localhost:5050
                """);
        String firstLine = request.toString().split("\n")[0];
        final String expectedOutput = "/json";
        final String actualOutput = firstLine.split(" ")[1];;
        Assertions.assertEquals(actualOutput, expectedOutput);
    }

    @Test
    void shouldOutputError() {
        StringBuilder request = new StringBuilder("""
                GET /hvryhh HTTP/1.1
                Host: localhost:5050
                """);
        String firstLine = request.toString().split("\n")[0];
        final Boolean expectedOutput = true;
        final Boolean actualOutput = ((!firstLine.split(" ")[1].equals("/")) || (!firstLine.split(" ")[1].equals("/json")));;
        Assertions.assertEquals(actualOutput, expectedOutput);
    }



}