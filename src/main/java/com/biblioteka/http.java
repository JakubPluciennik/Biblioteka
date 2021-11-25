package com.biblioteka;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;



public class http {
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            try {
                String response = "";
                String f = t.getRequestURI().toString();
                response="<html><head></head><body><form method='post'><input type='text'>";
                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
                System.out.println(f);
                System.out.println(t.getRequestMethod());
                if(t.getRequestMethod().equals("POST")) {
                    InputStream inputStream = t.getRequestBody();

                    String text = new BufferedReader(
                            new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));

                    System.out.println(text);
                }
                os.close();
            }
            catch (Exception e)
            {

            }
        }
    }
}
