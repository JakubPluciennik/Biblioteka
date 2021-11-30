package com.biblioteka;

import javax.persistence.*;
import java.time.Year;

@Entity
@Table(name="BOOKS")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Id")
    private long id;

    @Column(name="Title")
    String tytuł;

    @Column(name="Author")
    String autor;

    @Transient
    Year rokWydania;

    @Column(name="PublicationYear")
    int rokWydaniaInt;

    @Column(name="Available")
    int dostępne;

    public Book(String tytuł, String autor, Year rokWydania, int dostępne) {
        this.tytuł = tytuł;
        this.autor = autor;
        this.rokWydania = rokWydania;
        this.dostępne = dostępne;
    }

    // na potrzeby zapisu do bazy danych
    public Book(String tytuł, String autor, int rokWydaniaInt, int dostępne) {
        this.tytuł = tytuł;
        this.autor = autor;
        this.rokWydaniaInt = rokWydaniaInt;
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
    public long getId() { return id; }

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
