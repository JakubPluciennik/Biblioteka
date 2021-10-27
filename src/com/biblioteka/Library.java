package com.biblioteka;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.util.Vector;

public class Library
{
    Vector<Person> osoby;
    Vector<Book> ksiazki;
    private static final String BOOKS_PATH = "src/com/biblioteka/resources/Books";
    private static final String PEOPLE_PATH = "src/com/biblioteka/resources/People";
    public Library()
    {
      osoby = new Vector<Person>();
      ksiazki = new Vector<Book>();
    }

    public void WypiszDostepne()
    {
        for (Book ksiazka:ksiazki)
        {
            if(ksiazka.dostępne != 0)
            {
                System.out.println("["+ksiazki.indexOf(ksiazka)+ "]:  " + ksiazka);
            }
        }
    }
    public Boolean DodajOsobe(Person osoba)
    {
        if(!osoby.contains(osoba)) return osoby.add(osoba);
        return false;
    }
    public Boolean DodajKsiazke(Book ksiazka)
    {
        if(!ksiazki.contains(ksiazka)) return ksiazki.add(ksiazka);
        return false;
    }
    private Book LoadWierszKsiazka(String linia)
    {
        String[] dane = linia.split("\\|");
        //linia: tytul|autor|rok|dostepnych
        //dane: [tytul, autor, rok, dostepnych]

        Book ksiazka = new Book(dane[1], dane[2], Year.parse(dane[3]), Integer.parseInt(dane[4]));

        return ksiazka;
    }

    private Person LoadWierszOsoba(String linia)
    {
        String[] dane = linia.split("\\|");
        //linia: imie|nazwisko|login|haslo|wypozyczoneKsiazki
        //dane: [imie, nazwisko, login, haslo, [wypozyczoneKsiazki]]
        String[] wypozyczoneIndex = dane[dane.length-1].split(",");
        Person osoba = new Person(dane[0],dane[1],dane[2],dane[3]);

        //Wypożyczanie książek
        if(!wypozyczoneIndex[0].equals("-1"))
        {
            int[] wypozyczoneKsiazki = new int[wypozyczoneIndex.length];

            for (int i = 0; i < wypozyczoneIndex.length; i++)
            {
                wypozyczoneKsiazki[i] = Integer.parseInt(wypozyczoneIndex[i]);
            }
            for(int i:wypozyczoneKsiazki)
            {
                osoba.WypozyczKsiazkeLoadPerson(i,ksiazki.get(i));
            }
        }

        return osoba;
    }

    public void cleanUpFiles(String path)
    {
        File targetFile = new File(path);
        targetFile.delete();
    }
    private void SaveBooks() throws IOException
    {
        cleanUpFiles(BOOKS_PATH);
        Path newFilePath = Paths.get(BOOKS_PATH);
        Files.createFile(newFilePath);

        BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_PATH));
        for(Book ksiazka: ksiazki)
        {
            //[index]|tytul|autor|rokWydania|dostępne
            writer.write(ksiazki.indexOf(ksiazka)+"|"+ksiazka.saveWiersz());
        }
        writer.close();
    }

    private void LoadBooks () throws IOException
    {
        try(BufferedReader br = new BufferedReader(new FileReader(BOOKS_PATH)))
        {
            for (String linia; (linia = br.readLine()) != null; )
            {
                //dodawane po kolei, więc nie potrzeba precyzować indeksu
                ksiazki.add(LoadWierszKsiazka(linia));
            }
        }
    }


    private void SavePeople() throws IOException
    {
        cleanUpFiles(PEOPLE_PATH);
        Path newFilePath = Paths.get(PEOPLE_PATH);
        Files.createFile(newFilePath);

        BufferedWriter writer = new BufferedWriter(new FileWriter(PEOPLE_PATH));
        for(Person osoba: osoby)
        {

            writer.write(osoba.saveWiersz());
        }
        writer.close();
    }


    private void LoadPeople() throws IOException
    {
        try(BufferedReader br = new BufferedReader(new FileReader(PEOPLE_PATH)))
        {
            for (String linia; (linia = br.readLine()) != null; )
            {
                osoby.add(LoadWierszOsoba(linia));
            }
        }
    }

    void Save() throws IOException
    {
        SaveBooks();
        SavePeople();
        System.out.println("Zapisano dane");
    }

    void Load() throws IOException
    {
        LoadBooks();
        LoadPeople();
        System.out.println("Załadowano dane");
    }

}
