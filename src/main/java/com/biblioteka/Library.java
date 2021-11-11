package com.biblioteka;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.util.Vector;

public class Library {
    Vector<Person> osoby;
    Vector<Book> ksiazki;
    private static final String BOOKS_PATH = "src/main/resources/CSV/Books.csv";
    private static final String PEOPLE_PATH = "src/main/resources/CSV/People.csv";

    public Vector<Book> getKsiazki() { return ksiazki; }
    public int bookCount() { return ksiazki.size(); }
    public int userCount() { return osoby.size(); }
    public Book getKsiazka(int index) { return ksiazki.elementAt(index); }
    public  Person getUser(int index) { return osoby.elementAt(index); }

    public Library() {
        osoby = new Vector<Person>();
        ksiazki = new Vector<Book>();
    }

    public void wypiszDostepne() {
        for (Book ksiazka : ksiazki) {
            if (ksiazka.dostępne != 0) {
                System.out.println("[" + ksiazki.indexOf(ksiazka) + "]:  " + ksiazka.prettyToString());
            }
        }
    }

    public Boolean dodajOsobe(Person osoba) {
        for (Person p : osoby) {
            //jeśli jest już konto z takim samym loginem, nie dodaje jej do osób w bibliotece
            if (osoba.login.equalsIgnoreCase(p.login)) return false;
        }
        return osoby.add(osoba);
    }

    public Boolean dodajKsiazke(Book ksiazka) {
        for (Book b : ksiazki) {
            //jeśli istnieje już taka książka w bibliotece, dodanie ilości dostęnych książek
            if (ksiazka.autor.equalsIgnoreCase(b.autor) && ksiazka.tytuł.equalsIgnoreCase(b.tytuł) && ksiazka.rokWydania.equals(b.rokWydania)) {
                b.dostępne += ksiazka.getDostępne();
            }
        }
        return true;
    }

    private Book loadWierszKsiazka(String linia) {
        String[] dane = linia.split(",");
        //linia: tytul|autor|rok|dostepnych
        //dane: [tytul, autor, rok, dostepnych]

        Book ksiazka = new Book(dane[1], dane[2], Year.parse(dane[3]), Integer.parseInt(dane[4]));

        return ksiazka;
    }

    private Person loadWierszOsoba(String linia) {
        String[] dane = linia.split(",");
        //linia: imie|nazwisko|login|haslo|wypozyczoneKsiazki
        //dane: [imie, nazwisko, login, haslo, [wypozyczoneKsiazki]]
        String[] wypozyczoneIndex = dane[dane.length - 1].split(";");
        Person osoba = new Person(dane[0], dane[1], dane[2], dane[3]);

        //Wypożyczanie książek
        if (!wypozyczoneIndex[0].equals("-1")) {
            int[] wypozyczoneKsiazki = new int[wypozyczoneIndex.length];

            for (int i = 0; i < wypozyczoneIndex.length; i++) {
                wypozyczoneKsiazki[i] = Integer.parseInt(wypozyczoneIndex[i]);
            }
            for (int i : wypozyczoneKsiazki) {
                osoba.wypozyczKsiazkeLoadPerson(i, ksiazki.get(i));
            }
        }

        return osoba;
    }

    public void cleanUpFiles(String path) {
        File targetFile = new File(path);
        targetFile.delete();
    }

    private void saveBooks() throws IOException {
        cleanUpFiles(BOOKS_PATH);
        Path newFilePath = Paths.get(BOOKS_PATH);
        Files.createFile(newFilePath);

        BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_PATH));
        for (Book ksiazka : ksiazki) {
            //[index]|tytul|autor|rokWydania|dostępne
            writer.write(ksiazki.indexOf(ksiazka) + "," + ksiazka.saveWiersz());
        }
        writer.close();
    }

    private void loadBooks() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKS_PATH))) {
            for (String linia; (linia = br.readLine()) != null; ) {
                //dodawane po kolei, więc nie potrzeba precyzować indeksu
                ksiazki.add(loadWierszKsiazka(linia));
            }
        }
    }


    private void savePeople() throws IOException {
        cleanUpFiles(PEOPLE_PATH);
        Path newFilePath = Paths.get(PEOPLE_PATH);
        Files.createFile(newFilePath);

        BufferedWriter writer = new BufferedWriter(new FileWriter(PEOPLE_PATH));
        for (Person osoba : osoby) {

            writer.write(osoba.saveWiersz());
        }
        writer.close();
    }


    private void loadPeople() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(PEOPLE_PATH))) {
            for (String linia; (linia = br.readLine()) != null; ) {
                osoby.add(loadWierszOsoba(linia));
            }
        }
    }

    public void save() throws IOException {
        saveBooks();
        savePeople();
        //System.out.println("Zapisano dane");
    }

    void load() throws IOException {
        loadBooks();
        loadPeople();
        //System.out.println("Załadowano dane");
    }

}
