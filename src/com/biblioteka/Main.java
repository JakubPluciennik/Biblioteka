package com.biblioteka;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.util.Scanner;

public class Main {
    static Person zalogowana = null;

    static public Boolean Logowanie(String login, String haslo, Library library)
    {
        for(Person osoba:library.osoby)
        {
            if(login.equalsIgnoreCase(osoba.login)&&(haslo.equals(osoba.hasło) || osoba.hasło.equals(" ")))
            {
                zalogowana = osoba;
                return true;
            }
        }
        return false;
    }

    static public Boolean Wylogowanie()
    {
            zalogowana = null;
            return true;
    }

    static public Person TworzenieKonta()
    {
        System.out.println("--TWORZENIE KONTA--");
        System.out.print("Imię: ");
        Scanner scanner = new Scanner(System.in);
        String imie = scanner.nextLine();

        System.out.print("Nazwisko: ");
        scanner = new Scanner(System.in);
        String nazwisko = scanner.nextLine();

        System.out.print("Login: ");
        scanner = new Scanner(System.in);
        String login = scanner.nextLine();

        System.out.print("Hasło: ");
        scanner = new Scanner(System.in);
        String haslo = scanner.nextLine();

        return new Person(imie, nazwisko, login, haslo);
    }

    static public void Menu(Library biblioteka, int menu) throws IOException {
        do {
//Komunikat początkowy
            if(zalogowana==null)    //niezalogowany
            {
                System.out.println("""
                    ----BIBLIOTEKA----
                    Wybierz opcję:
                    1. Zaloguj się
                    2. Załóż konto.
                    3. Wypisz dostępne książki.
                    0. Wyjdź z programu.""");
            }
            else    //zalogowany
            {
                System.out.println("""
                    \n----BIBLIOTEKA---- Zalogowany| """+zalogowana.login+ "| "+ zalogowana.imię+" "+zalogowana.nazwisko
                        +"""
                    \nWybierz opcję:
                    1. Wyloguj się
                    2. Wypożycz książkę.
                    3. Oddaj książkę.
                    4. Wypisz dostępne książki.
                    5. Wypisz wypożyczone przez ciebie książki
                    0. Wyjdź z programu.""");
            }
//koniec komunikatu początkowego

            Scanner scanner = new Scanner(System.in);
            try {
                menu = Integer.parseInt(scanner.nextLine());
            } catch(NumberFormatException ex) {
                System.out.println("Podano niepoprawną wartość, spróbuj ponownie.");
                menu = -1;
            }

//Switch dla pozycji menu
            if(zalogowana == null)  //niezalogowany
            {
                switch(menu)
                {
                    case 1: // Logowanie
                            System.out.println("--Logowanie--");
                            System.out.print("Podaj login do konta: ");
                            scanner = new Scanner(System.in);
                            String login = scanner.nextLine();
                            System.out.print("Podaj hasło dla użytkownika "+login+": ");
                            scanner = new Scanner(System.in);
                            String haslo = scanner.nextLine();

                            if(Logowanie(login,haslo,biblioteka)) System.out.println("Udane logowanie");
                            else System.out.println("Nieudana próba logowania\n");

                        break;
                    case 2: //Tworzenie konta i logowanie
                        Person konto = TworzenieKonta();
                        biblioteka.DodajOsobe(konto);
                        if(Logowanie(konto.login,konto.hasło,biblioteka)) System.out.println("Udane logowanie");
                        else System.out.println("Nieudana próba logowania\n");
                        break;
                    case 3: //wypisanie książek
                        System.out.println("--DOSTĘPNE KSIĄŻKI--");
                        biblioteka.WypiszDostepne();
                        break;
                }
            }
            else    //zalogowany
            {
                int index;
                Book ksiazka;
                switch(menu)
                {
                    case 1: //Wylogowanie
                        System.out.println("""
                            --Wylogowanie--
                            Następuje wylogowanie aktualnego użytkownika
                            """);
                        Wylogowanie();
                        break;

                    case 2: //wypożyczenie książki
                        System.out.println("--DOSTĘPNE KSIĄŻKI--\n");
                        biblioteka.WypiszDostepne();
                        System.out.println("Podaj numer książki, aby ją wypożyczyć; podaj dowolny inny znak, aby pominąć");
                        scanner = new Scanner(System.in);
                        try {
                            index = Integer.parseInt(scanner.nextLine());
                        } catch(NumberFormatException ex) {
                            index = biblioteka.ksiazki.size()-1;
                        }
                        ksiazka = biblioteka.ksiazki.get(index);
                        if(zalogowana.WypozyczKsiazke(index,ksiazka))
                        {
                            System.out.println("Wypożyczono książkę: "+ ksiazka);
                        }
                        else System.out.println("Nie udało się wypożyczyć książki");
                        break;

                    case 3: //oddanie książki
                        System.out.println("--WYPOŻYCZONE KSIĄŻKI--\n");
                        zalogowana.Wypozyczone();
                        System.out.println("Podaj numer książki, aby ją oddać; podaj dowolny inny znak, aby pominąć");
                        scanner = new Scanner(System.in);
                        try {
                            index = Integer.parseInt(scanner.nextLine());
                        } catch(NumberFormatException ex) {
                            index = -1;
                        }
                        ksiazka = zalogowana.wypozyczoneKsiazki.get(index);
                        if(zalogowana.OddajKsiazke(index,ksiazka))
                        {
                            System.out.println("Oddano książkę: "+ ksiazka.toString());
                        }
                        else System.out.println("Nie udało się oddać książki");

                        break;
                    case 4: //wypisanie książek
                        System.out.println("--DOSTĘPNE KSIĄŻKI--");
                        biblioteka.WypiszDostepne();
                        System.out.println("Kontynuuj...");
                        System.in.read();
                        break;
                    case 5: //wypisanie wypożyczonych książek
                        System.out.println("--WYPOŻYCZONE KSIĄŻKI--\n");
                        zalogowana.Wypozyczone();
                        System.out.println("Kontynuuj...");
                        System.in.read();
                        break;
                }
            }
// koniec Switcha
        } while(menu != 0); //początkowa wartość menu 0, jeśli nie jest zmieniona to wyjście z do...while
    }

    public static void main(String[] args) throws IOException
    {
        int menu = 0;
        Library biblioteka = new Library();

        biblioteka.Load();
        Menu(biblioteka, menu);
        biblioteka.Save();
    }
}
