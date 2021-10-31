package com.biblioteka;


import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Person zalogowana = null;


    static Boolean logowanie(String login, String haslo, Library library)
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



    static  Boolean wylogowanie()
    {
            zalogowana = null;
            return true;
    }


    static  Person tworzenieKonta()
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

    static public void menu(Library biblioteka, int menu) throws IOException {
        do {
//Komunikat początkowy
            if(zalogowana==null)    //niezalogowany
            {
                System.out.println("----BIBLIOTEKA----\nWybierz opcję:\n1. Zaloguj się\n2. Załóż konto.\n3. Wypisz dostępne książki.\n0. Wyjdź z programu.");
            }
            else    //zalogowany
            {
                System.out.println("----BIBLIOTEKA---- Zalogowany: |"+zalogowana.login+ "| "+ zalogowana.imię+" "+zalogowana.nazwisko+"\nWybierz opcję: \n1. Wyloguj się \n2. Wypożycz książkę. \n3. Oddaj książkę. \n4. Wypisz dostępne książki. \n5. Wypisz wypożyczone przez ciebie książki. \n0. Wyjdź z programu.");
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

                            if(logowanie(login,haslo,biblioteka)) System.out.println("Udane logowanie");
                            else System.out.println("Nieudana próba logowania\n");

                        break;
                    case 2: //Tworzenie konta i logowanie
                        Person konto = tworzenieKonta();
                        biblioteka.dodajOsobe(konto);
                        if(logowanie(konto.login,konto.hasło,biblioteka)) System.out.println("Udane logowanie");
                        else System.out.println("Nieudana próba logowania\n");
                        break;
                    case 3: //wypisanie książek
                        System.out.println("--DOSTĘPNE KSIĄŻKI--");
                        biblioteka.wypiszDostepne();
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
                        System.out.println("--Wylogowanie-- \nNastępuje wylogowanie aktualnego użytkownika");
                        wylogowanie();
                        break;

                    case 2: //wypożyczenie książki
                        System.out.println("--DOSTĘPNE KSIĄŻKI--\n");
                        biblioteka.wypiszDostepne();
                        System.out.println("Podaj numer książki, aby ją wypożyczyć; podaj dowolny inny znak, aby pominąć");
                        scanner = new Scanner(System.in);
                        try {
                            index = Integer.parseInt(scanner.nextLine());
                        } catch(NumberFormatException ex) {
                            index = biblioteka.ksiazki.size()-1;
                        }
                        ksiazka = biblioteka.ksiazki.get(index);
                        if(zalogowana.wypozyczKsiazke(index,ksiazka))
                        {
                            System.out.println("Wypożyczono książkę: "+ ksiazka);
                        }
                        else System.out.println("Nie udało się wypożyczyć książki");
                        break;

                    case 3: //oddanie książki
                        System.out.println("--WYPOŻYCZONE KSIĄŻKI--\n");
                        zalogowana.wypozyczone();
                        System.out.println("Podaj numer książki, aby ją oddać; podaj dowolny inny znak, aby pominąć");
                        scanner = new Scanner(System.in);
                        try {
                            index = Integer.parseInt(scanner.nextLine());
                        } catch(NumberFormatException ex) {
                            index = -1;
                        }
                        ksiazka = zalogowana.wypozyczoneKsiazki.get(index);
                        if(zalogowana.oddajKsiazke(index,ksiazka))
                        {
                            System.out.println("Oddano książkę: "+ ksiazka.toString());
                        }
                        else System.out.println("Nie udało się oddać książki");

                        break;
                    case 4: //wypisanie książek
                        System.out.println("--DOSTĘPNE KSIĄŻKI--");
                        biblioteka.wypiszDostepne();
                        System.out.println("Kontynuuj...");
                        System.in.read();
                        break;
                    case 5: //wypisanie wypożyczonych książek
                        System.out.println("--WYPOŻYCZONE KSIĄŻKI--\n");
                        zalogowana.wypozyczone();
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

        biblioteka.load();
        menu(biblioteka, menu);
        biblioteka.save();
    }
}
