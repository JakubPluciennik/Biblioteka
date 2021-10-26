package com.biblioteka;

import java.time.Year;

public class Book
{
    String tytuł;
    String autor;
    Year rokWydania;
    int dostępne;

    public Book(String tytuł, String autor, Year rokWydania, int dostępne)
    {
        this.tytuł = tytuł;
        this.autor = autor;
        this.rokWydania = rokWydania;
        this.dostępne = dostępne;
    }

    public int getDostępne() {return dostępne;}

    public void setDostępne(int dostępne) {this.dostępne = dostępne;}

    @Override
    public String toString()
    {
        return "Tytuł: '" + tytuł + '\'' +
                "| Autor: '" + autor + '\'' +
                "| Rok Wydania: " + rokWydania +
                "| Dostępne: " + dostępne;
    }
    public String saveWiersz()
    {
        return String.format("%s|%s|%s|%s\n",tytuł, autor,rokWydania, dostępne);
    }
}
