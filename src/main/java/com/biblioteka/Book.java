package com.biblioteka;

import java.time.Year;

public class Book {
    String tytuł;
    String autor;
    Year rokWydania;
    int dostępne;

    public Book(String tytuł, String autor, Year rokWydania, int dostępne) {
        this.tytuł = tytuł;
        this.autor = autor;
        this.rokWydania = rokWydania;
        this.dostępne = dostępne;
    }

    public int getDostępne() {
        return dostępne;
    }
    public void setDostępne(int dostępne) {
        this.dostępne = dostępne;
    }

    public String getTytuł() { return tytuł; }
    public String getAutor() { return autor; }
    public Year getRokWydania() { return rokWydania; }

    @Override
    public String toString() {
        return "Tytuł: '" + tytuł + '\'' +
                "| Autor: '" + autor + '\'' +
                "| Rok Wydania: " + rokWydania +
                "| Dostępne: " + dostępne;
    }

    public String prettyToString() {
        String inden1 = indentation(24 - tytuł.length());
        String inden2 = indentation(20 - autor.length());
        String inden3 = indentation(20 - rokWydania.length());

        String result = "Tytuł: '" + tytuł + '\'' + inden1 +
                "| Autor: '" + autor + '\'' + inden2 +
                "| Rok Wydania: " + rokWydania + inden3 +
                "| Dostępne: " + dostępne;


        return result;
    }

    private static String indentation(int numberOfSpaces) {
        String result = "";
        for (int i = 0; i < numberOfSpaces; i++)
            result += " ";
        return result;
    }

    public String saveWiersz() {
        return String.format("%s,%s,%s,%s\n", tytuł, autor, rokWydania, dostępne);
    }
}
