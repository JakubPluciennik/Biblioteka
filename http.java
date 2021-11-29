package com.biblioteka;


import java.io.*;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.http.HttpClient;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.lang.Number;
import java.lang.Number;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;



public class http{
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            try {


                String response = "";

                        response = "<html><html lang=\"pl-PL\"><head><meta charset=\"UTF-8\"></head><body><style>\n" +
                                "body{\n" +
                                " background-color: tan;\n" +
                                "}\n" +
                                "p{\n" +
                                "font-size: 1.5em;\n" +
                                "}\n" +
                                "\n" +
                                "a {\n" +
                                "\n" +
                                "display:block;\n" +
                                "width: 12em;\n" +
                                "height: 1.5em;\n" +
                                "font-size: 2em;\n" +
                                "text-align: center; \n" +
                                "background-color: wheat;\n" +
                                "color: black; \n" +
                                "border: 1px solid black;\n" +
                                "text-decoration: none;\n" +
                                "}\n" +
                                "h1 {\n" +
                                "color: DarkRed;\n" +
                                "font-size: 5em;\n" +
                                "}" +
                                "</style><h1>BIBLIOTEKA</h1> ";
                if(Main.getZalogowana() != null)
                {
                        response += "<p>Jestes niezalogowany </p><br><p> Wybierz opcje z listy:</p> <br>";
                        response +="<a href=\"http://localhost:8004/test\">Wyloguj sie</a><br>";
                        response +="<a href=\"http://localhost:8005/test\">Wypozycz ksiazke</a><br>";
                        response +="<a href=\"http://localhost:8006/test\">Oddaj ksiazke</a><br>";
                        response +="<a href=\"http://localhost:8003/test\">Wypisz dostepne ksiazki</a><br>";
                        response +="<a href=\"http://localhost:8007/test\">Wypisz Twoje ksiazki </a>";
                    }
                    else
                    {

                        response += "<p> Jestes zalogowany </p> <br><p> Wybierz opcje z listy: </p><br>";
                        response +="<a href=\"http://localhost:8001/test\">Zaloguj sie</a><br>";
                        response +="<a href=\"http://localhost:8002/test\">Zaloz konto</a><br>";
                        response +="<a href=\"http://localhost:8003/test\">Wypisz dostepne ksiazki</a><br>";
                    }


                String f = t.getRequestURI().toString();
                        t.sendResponseHeaders(200, response.length());
                        OutputStream os = t.getResponseBody();
                        os.write(response.getBytes());

                        System.out.println(f);
                        System.out.println(t.getRequestMethod());





                        os.close();
            }
            catch (Exception e)
            {
                     e.printStackTrace();
            }
        }
    }




    static class MyHandler2 implements HttpHandler {
       Library biblioteka;
        public MyHandler2(Library b)
        {
            this.biblioteka = b;
        }
        @Override
        public void handle(HttpExchange t) throws IOException {
            try {

                        String response = "";
                        String f = t.getRequestURI().toString();
                        response += "<html><html lang=\"pl-PL\"><head><meta charset=\"UTF-8\"></head><style>\n" +
                                "body{\n" +
                                " background-color: tan;\n" +
                                "}\n" +
                                "p{\n" +
                                "font-size: 1.5em;\n" +
                                "}\n" +
                                "\n" +
                                "a {\n" +
                                "\n" +
                                "display:block;\n" +
                                "width: 12em;\n" +
                                "height: 1.5em;\n" +
                                "font-size: 2em;\n" +
                                "text-align: center; \n" +
                                "background-color: wheat;\n" +
                                "color: black; \n" +
                                "border: 1px solid black;\n" +
                                "text-decoration: none;\n" +
                                "}\n" +
                                "input {\n" +
                                "\n" +
                                "display:block;\n" +
                                "width: 12em;\n" +
                                "height: 1.5em;\n" +
                                "font-size: 2em;\n" +
                                "text-align: center; \n" +

                                "color: black; \n" +
                                "border: 1px solid black;\n" +
                                "text-decoration: none;\n" +
                                "}\n" +
                                "h1 {\n" +
                                "color: DarkRed;\n" +
                                "font-size: 5em;\n" +
                                "}" +
                                "</style><h1>BIBLIOTEKA</h1> ";
                        response += "<FORM METHOD= \"POST\" > <p>Zaloguj sie: </p> <p>Login: </p><INPUT TYPE=text  METHOD= \"POST\"  NAME = \"login\"> <p> Haslo: </p><INPUT TYPE=text  METHOD= \"POST\"  NAME = \"name\"><input type=submit METHOD= \"POST\" value = \"wyslij\"></FORM>  </body></html>";
                        response += "<a href=\"http://localhost:8000/test\" style=\"margin: 20 200px;\"><- POWROT</a>";


                t.sendResponseHeaders(200, response.length());
                        OutputStream os = t.getResponseBody();
                        os.write(response.getBytes());

                        System.out.println(f);
                        System.out.println(t.getRequestMethod());


                        if (t.getRequestMethod().equals("POST")) {
                            InputStream inputStream = t.getRequestBody();

                            String text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).lines().collect(Collectors.joining("\n"));
                            System.out.println(text);
                            String login = "";
                            String haslo = "";
                            char[] dane = text.toCharArray();
                            Boolean czy = true;
                            for(int i = 6; i < dane.length; i++)
                            {
                                if(dane[i] == '&') {
                                    czy = false;
                                    i = i +6;
                                }
                                if(czy) {
                                    login += dane[i];
                                }
                                if(!czy){
                                    haslo += dane[i];
                                }
                            }
                            System.out.println(login + " " + haslo);
                            if(Main.logowanie(login, haslo, biblioteka))
                            {
                                System.out.println("Zalogowano pomyślnie!");
                                String resp = "<p style=\"width: 500px;\"> Zalogowano pomyslnie!</p>";
                                os.write(resp.getBytes());
                            }
                        }


                        os.close();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    static class MyHandler3 implements HttpHandler {
        Library biblioteka;
        public MyHandler3(Library b)
        {
            this.biblioteka = b;
        }
        @Override
        public void handle(HttpExchange t) throws IOException {
            try {
                String response = "";
                String f = t.getRequestURI().toString();

                    response += "<html><html lang=\"pl-PL\"><head><meta charset=\"UTF-8\"></head><body><style>\n" +
                            "body{\n" +
                            " background-color: tan;\n" +
                            "}\n" +
                            "p{\n" +
                            "font-size: 1.5em;\n" +
                            "}\n" +
                            "\n" +
                            "a {\n" +
                            "\n" +
                            "display:block;\n" +
                            "width: 12em;\n" +
                            "height: 1.5em;\n" +
                            "font-size: 2em;\n" +
                            "text-align: center; \n" +
                            "background-color: wheat;\n" +
                            "color: black; \n" +
                            "border: 1px solid black;\n" +
                            "text-decoration: none;\n" +
                            "}\n" +
                            "input {\n" +
                            "\n" +
                            "display:block;\n" +
                            "width: 12em;\n" +
                            "height: 1.5em;\n" +
                            "font-size: 2em;\n" +
                            "text-align: center; \n" +

                            "color: black; \n" +
                            "border: 1px solid black;\n" +
                            "text-decoration: none;\n" +
                            "}\n" +
                            "h1 {\n" +
                            "color: DarkRed;\n" +
                            "font-size: 5em;\n" +
                            "}" +
                            "</style><h1>BIBLIOTEKA</h1> ";
                if(Main.getZalogowana() != null)
                {
                    response+= "<p>Jestes juz zalogowany!</p>";
                }
                else {
                    response+= "<p>Jestes niezalogowany!</p>";
                     response += "<FORM METHOD= \"POST\" ><p> Zarejestruj sie: </p> Imie: <INPUT TYPE=text  METHOD= \"POST\"  NAME = \"imie\"> <br> Nazwisko: <INPUT TYPE=text  METHOD= \"POST\"  NAME = \"haslo\"><br> Login: <INPUT TYPE=text  METHOD= \"POST\"  NAME = \"login\"><br> Haslo: <INPUT TYPE=text  METHOD= \"POST\"  NAME = \"passs\"><input type=submit METHOD= \"POST\" value = \"wyslij\"></FORM>  </body></html>";
                    response += "<a href=\"http://localhost:8000/test\" style=\"margin: 20 200px;\"><- POWROT</a>";
                }

                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());

                System.out.println(f);
                System.out.println(t.getRequestMethod());


                if (t.getRequestMethod().equals("POST")) {
                    InputStream inputStream = t.getRequestBody();

                    String text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).lines().collect(Collectors.joining("\n"));
                    System.out.println(text);
                    String imie = "";
                    String nazwisko = "";
                    String login = "";
                    String haslo = "";
                    char[] dane = text.toCharArray();
                    int czy = 0;
                    for(int i = 5; i < dane.length; i++)
                    {
                        if(dane[i] == '&') {
                            czy++;
                            i = i +7;
                        }
                        if(czy == 0) {
                            imie += dane[i];
                        }
                        if(czy == 1){
                            nazwisko += dane[i];
                        }
                        if(czy == 2){
                            login += dane[i];
                        }
                        if(czy == 3){
                            haslo += dane[i];
                        }
                    }
                    System.out.println(imie + " " + nazwisko + " " + login + " " + haslo );
                    Person a = new Person(imie, nazwisko, login, haslo);
                    biblioteka.dodajOsobe(a);
                    if(a!= null) {
                        System.out.println("Zarejestrowano pomyslnie!");

                        if(Main.logowanie(login, haslo, biblioteka)){
                            System.out.println("Zalogowano...");
                        }
                    }
                }


                os.close();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    static class MyHandler4 implements HttpHandler {
        Library biblioteka;
        public MyHandler4(Library b)
        {
            this.biblioteka = b;
        }
        @Override
        public void handle(HttpExchange t) throws IOException {
            try {

                String response = "";
                String f = t.getRequestURI().toString();
                response += "<html><html lang=\"pl-PL\"><head><meta charset=\"UTF-8\"></head><body><style>\n" +
                        "body{\n" +
                        " background-color: tan;\n" +
                        "}\n" +
                        "p{\n" +
                        "font-size: 1.5em;\n" +
                        "}\n" +
                        "\n" +
                        "a {\n" +
                        "\n" +
                        "display:block;\n" +
                        "width: 12em;\n" +
                        "height: 1.5em;\n" +
                        "font-size: 2em;\n" +
                        "text-align: center; \n" +
                        "background-color: wheat;\n" +
                        "color: black; \n" +
                        "border: 1px solid black;\n" +
                        "text-decoration: none;\n" +
                        "}\n" +
                        "input {\n" +
                        "\n" +
                        "display:block;\n" +
                        "width: 12em;\n" +
                        "height: 1.5em;\n" +
                        "font-size: 2em;\n" +
                        "text-align: center; \n" +

                        "color: black; \n" +
                        "border: 1px solid black;\n" +
                        "text-decoration: none;\n" +
                        "}\n" +
                        "h1 {\n" +
                        "color: DarkRed;\n" +
                        "font-size: 5em;\n" +
                        "}" +
                        "</style><h1>BIBLIOTEKA</h1> ";
                if(Main.getZalogowana() != null)
                {
                    response += "<p>Jestes zalogowany!</p>";

                }
                else {
                    response += "<p>Jestes niezalogowany!</p>";

                }

                response += "";

                for (Book ksiazka : biblioteka.ksiazki) {
                    if (ksiazka.dostępne != 0) {
                       response += "[ " + biblioteka.ksiazki.indexOf(ksiazka) +" ]: "  + new String(ksiazka.prettyToString().getBytes("ISO-8859-1"), "UTF-8" ) + "] <br>";
                    }
                }
                response += "<a href=\"http://localhost:8000/test\" style=\"margin: 20 200px;\"><- POWROT</a>";


                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());

                System.out.println(f);
                System.out.println(t.getRequestMethod());


                System.out.println(response);
                if (t.getRequestMethod().equals("POST")) {
                    InputStream inputStream = t.getRequestBody();

                    String text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).lines().collect(Collectors.joining("\n"));
                    System.out.println(text);

                }


                os.close();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

   static class MyHandler5 implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            try {

                String response = "";
                String f = t.getRequestURI().toString();
                response += "<html><html lang=\"pl-PL\"><head><meta charset=\"UTF-8\"></head><body><style>\n" +
                        "body{\n" +
                        " background-color: tan;\n" +
                        "}\n" +
                        "p{\n" +
                        "font-size: 1.5em;\n" +
                        "}\n" +
                        "\n" +
                        "a {\n" +
                        "\n" +
                        "display:block;\n" +
                        "width: 12em;\n" +
                        "height: 1.5em;\n" +
                        "font-size: 2em;\n" +
                        "text-align: center; \n" +
                        "background-color: wheat;\n" +
                        "color: black; \n" +
                        "border: 1px solid black;\n" +
                        "text-decoration: none;\n" +
                        "}\n" +
                        "input {\n" +
                        "\n" +
                        "display:block;\n" +
                        "width: 12em;\n" +
                        "height: 1.5em;\n" +
                        "font-size: 2em;\n" +
                        "text-align: center; \n" +

                        "color: black; \n" +
                        "border: 1px solid black;\n" +
                        "text-decoration: none;\n" +
                        "}\n" +
                        "h1 {\n" +
                        "color: DarkRed;\n" +
                        "font-size: 5em;\n" +
                        "}" +
                        "</style><h1>BIBLIOTEKA</h1> ";
                if(Main.getZalogowana() == null)
                {
                    response += "<p>Nie jestes zalogowany!</p>";

                }
                else {
                      if(Main.wylogowanie())
                    {
                        response += "<p>Wylogowales sie!</p>";
                    }
                }

                response += "<a href=\"http://localhost:8000/test\" style=\"margin: 20 200px;\"><- POWROT</a>";




                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());

                System.out.println(f);
                System.out.println(t.getRequestMethod());


                if (t.getRequestMethod().equals("POST")) {
                    InputStream inputStream = t.getRequestBody();

                    String text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).lines().collect(Collectors.joining("\n"));
                    System.out.println(text);

                }

                os.close();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    static class MyHandler6 implements HttpHandler {
        Library biblioteka;
        public MyHandler6(Library b)
        {
            this.biblioteka = b;
        }
        @Override
        public void handle(HttpExchange t) throws IOException {
            try {

                String response = "";
                String f = t.getRequestURI().toString();
                response += "<html><html lang=\"pl-PL\"><head><meta charset=\"UTF-8\"></head><body><style>\n" +
                        "body{\n" +
                        " background-color: tan;\n" +
                        "}\n" +
                        "p{\n" +
                        "font-size: 1.5em;\n" +
                        "}\n" +
                        "\n" +
                        "a {\n" +
                        "\n" +
                        "display:block;\n" +
                        "width: 12em;\n" +
                        "height: 1.5em;\n" +
                        "font-size: 2em;\n" +
                        "text-align: center; \n" +
                        "background-color: wheat;\n" +
                        "color: black; \n" +
                        "border: 1px solid black;\n" +
                        "text-decoration: none;\n" +
                        "}\n" +
                        "input {\n" +
                        "\n" +
                        "display:block;\n" +
                        "width: 12em;\n" +
                        "height: 1.5em;\n" +
                        "font-size: 2em;\n" +
                        "text-align: center; \n" +

                        "color: black; \n" +
                        "border: 1px solid black;\n" +
                        "text-decoration: none;\n" +
                        "}\n" +
                        "h1 {\n" +
                        "color: DarkRed;\n" +
                        "font-size: 5em;\n" +
                        "}" +
                        "</style><h1>BIBLIOTEKA</h1> ";
                if(Main.getZalogowana() != null)
                {
                    response += "<p>Jestes zalogowany!</p>";

                }
                else {
                    response += "<p>Jestes niezalogowany!</p>";

                }

                response += "";

                for (Book ksiazka : biblioteka.ksiazki) {
                    if (ksiazka.dostępne != 0) {
                        response += "[ " + biblioteka.ksiazki.indexOf(ksiazka) +" ]: "  + new String(ksiazka.prettyToString().getBytes("ISO-8859-1"), "UTF-8" ) + "] <br>";
                    }
                }
                response += "<FORM METHOD= \"POST\" ><p>Podaj numer ksiazki, ktora chcesz wypozyczyc:</p><INPUT TYPE=text  METHOD= \"POST\"  NAME = \"numer\"> <input type=submit METHOD= \"POST\" value = \"zatwierdz\"></FORM>  </body></html>";
                response += "<a href=\"http://localhost:8000/test\" style=\"margin: 20 200px;\"><- POWROT</a>";


                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());

                System.out.println(f);
                System.out.println(t.getRequestMethod());

                if (t.getRequestMethod().equals("POST")) {
                    InputStream inputStream = t.getRequestBody();

                    String text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).lines().collect(Collectors.joining("\n"));
                    System.out.println(text);

                    char[] dane = text.toCharArray();

                    String numer="";

                    for(int i = 6; i < dane.length; i++)
                    {
                        numer+=dane[i];
                    }

                    System.out.println(numer);

                    int index=Integer.parseInt(numer);

                    Book ksiazka;
                    ksiazka = biblioteka.ksiazki.get(index);
                    if (Main.zalogowana.wypozyczKsiazke(index, ksiazka))
                        System.out.println("Wypożyczono książkę: " + ksiazka);
                }


                os.close();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    static class MyHandler7 implements HttpHandler {
        Library biblioteka;
        public MyHandler7(Library b)
        {
            this.biblioteka = b;
        }
        @Override
        public void handle(HttpExchange t) throws IOException {
            try {

                String response = "";
                String f = t.getRequestURI().toString();
                response += "<html><html lang=\"pl-PL\"><head><meta charset=\"UTF-8\"></head><body><style>\n" +
                        "body{\n" +
                        " background-color: tan;\n" +
                        "}\n" +
                        "p{\n" +
                        "font-size: 1.5em;\n" +
                        "}\n" +
                        "\n" +
                        "a {\n" +
                        "\n" +
                        "display:block;\n" +
                        "width: 12em;\n" +
                        "height: 1.5em;\n" +
                        "font-size: 2em;\n" +
                        "text-align: center; \n" +
                        "background-color: wheat;\n" +
                        "color: black; \n" +
                        "border: 1px solid black;\n" +
                        "text-decoration: none;\n" +
                        "}\n" +
                        "input {\n" +
                        "\n" +
                        "display:block;\n" +
                        "width: 12em;\n" +
                        "height: 1.5em;\n" +
                        "font-size: 2em;\n" +
                        "text-align: center; \n" +

                        "color: black; \n" +
                        "border: 1px solid black;\n" +
                        "text-decoration: none;\n" +
                        "}\n" +
                        "h1 {\n" +
                        "color: DarkRed;\n" +
                        "font-size: 5em;\n" +
                        "}" +
                        "</style><h1>BIBLIOTEKA</h1> ";
                if(Main.getZalogowana() != null)
                {
                    response += "<p>Jestes zalogowany!</p>";

                }
                else {
                    response += "<p>Jestes niezalogowany!</p>";

                }

                response += "";

                response += "<p>Twoje wypozyczone ksiazki:</p>";
                Main.zalogowana.wypozyczone();

                for (int i = 0; i < Main.zalogowana.wypozyczoneKsiazki.size(); i++) {
                    Integer[] key = Main.zalogowana.wypozyczoneKsiazki.keySet().toArray(new Integer[0]);
                    response += "[ " + biblioteka.ksiazki.indexOf(Main.zalogowana.wypozyczoneKsiazki.get(key[i])) + " ]: " + new String(Main.zalogowana.wypozyczoneKsiazki.get(key[i]).prettyToString().getBytes("ISO-8859-1"), "UTF-8") + "<br>";
                }

                response += "<FORM METHOD= \"POST\" ><p>Podaj numer ksiazki, ktora chcesz zwrocic:</p><INPUT TYPE=text  METHOD= \"POST\"  NAME = \"numer\"> <input type=submit METHOD= \"POST\" value = \"zatwierdz\"></FORM>  </body></html>";
                response += "<a href=\"http://localhost:8000/test\" style=\"margin: 20 200px;\"><- POWROT</a>";


                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());

                System.out.println(f);
                System.out.println(t.getRequestMethod());

                if (t.getRequestMethod().equals("POST")) {
                    InputStream inputStream = t.getRequestBody();

                    String text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).lines().collect(Collectors.joining("\n"));
                    System.out.println(text);

                    char[] dane = text.toCharArray();

                    String numer="";

                    for(int i = 6; i < dane.length; i++)
                    {
                        numer+=dane[i];
                    }

                    System.out.println(numer);

                    int index=Integer.parseInt(numer);

                    Book ksiazka;
                    ksiazka = biblioteka.ksiazki.get(index);
                    if (Main.zalogowana.oddajKsiazke(index, ksiazka))
                        System.out.println("Zwrócono książkę: " + ksiazka);
                }


                os.close();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    static class MyHandler8 implements HttpHandler {
        Library biblioteka;
        public MyHandler8(Library b)
        {
            this.biblioteka = b;
        }
        @Override
        public void handle(HttpExchange t) throws IOException {
            try {

                String response = "";
                String f = t.getRequestURI().toString();
                response += "<html><html lang=\"pl-PL\"><head><meta charset=\"UTF-8\"></head><body><style>\n" +
                        "body{\n" +
                        " background-color: tan;\n" +
                        "}\n" +
                        "p{\n" +
                        "font-size: 1.5em;\n" +
                        "}\n" +
                        "\n" +
                        "a {\n" +
                        "\n" +
                        "display:block;\n" +
                        "width: 12em;\n" +
                        "height: 1.5em;\n" +
                        "font-size: 2em;\n" +
                        "text-align: center; \n" +
                        "background-color: wheat;\n" +
                        "color: black; \n" +
                        "border: 1px solid black;\n" +
                        "text-decoration: none;\n" +
                        "}\n" +
                        "input {\n" +
                        "\n" +
                        "display:block;\n" +
                        "width: 12em;\n" +
                        "height: 1.5em;\n" +
                        "font-size: 2em;\n" +
                        "text-align: center; \n" +

                        "color: black; \n" +
                        "border: 1px solid black;\n" +
                        "text-decoration: none;\n" +
                        "}\n" +
                        "h1 {\n" +
                        "color: DarkRed;\n" +
                        "font-size: 5em;\n" +
                        "}" +
                        "</style><h1>BIBLIOTEKA</h1> ";
                if(Main.getZalogowana() != null)
                {
                    response += "<p>Jestes zalogowany!</p>";

                }
                else {
                    response += "<p>Jestes niezalogowany!</p>";

                }

                response += "";

                response += "<p>Twoje wypozyczone ksiazki:</p>";
                Main.zalogowana.wypozyczone();

                for (int i = 0; i < Main.zalogowana.wypozyczoneKsiazki.size(); i++) {
                    Integer[] key = Main.zalogowana.wypozyczoneKsiazki.keySet().toArray(new Integer[0]);
                    response += "[ " + biblioteka.ksiazki.indexOf(Main.zalogowana.wypozyczoneKsiazki.get(key[i])) + " ]: " + new String(Main.zalogowana.wypozyczoneKsiazki.get(key[i]).prettyToString().getBytes("ISO-8859-1"), "UTF-8") + "<br>";
                }

                response += "<a href=\"http://localhost:8000/test\" style=\"margin: 20 200px;\"><- POWROT</a>";


                t.sendResponseHeaders(200, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());

                System.out.println(f);
                System.out.println(t.getRequestMethod());

                if (t.getRequestMethod().equals("POST")) {
                    InputStream inputStream = t.getRequestBody();

                    String text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).lines().collect(Collectors.joining("\n"));
                    System.out.println(text);

                }


                os.close();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
