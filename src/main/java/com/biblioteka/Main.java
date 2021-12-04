package com.biblioteka;

import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 * klasa Main.
 */
public class Main {
  static Person zalogowana = null;

  public static Person getZalogowana() {
    return zalogowana;
  }

  /**
   * Metoda do logowania użytkownika do biblioteki
   *
   * @param login   - login użytkownika
   * @param haslo   - hasło użytkownika
   * @param library - używana biblioteka
   * @return - true jeśli zalogowano użytkownika, false jeśli nie zalogowano
   */
  public static Boolean logowanie(String login, String haslo, Library library) {
    for (Person osoba : library.osoby) {
      if (login.equalsIgnoreCase(osoba.login) &&
          (haslo.equals(osoba.hasło) || osoba.hasło.equals(" "))) {
        zalogowana = osoba;
        return true;
      }
    }
    return false;
  }

  static void wylogowanie() {
    zalogowana = null;
  }

  static Person tworzenieKonta() {
    System.out.println("--TWORZENIE KONTA--");
    Scanner scanner = new Scanner(System.in);

    System.out.print("Imię: ");
    String imie = scanner.nextLine();

    System.out.print("Nazwisko: ");
    String nazwisko = scanner.nextLine();

    System.out.print("Login: ");
    String login = scanner.nextLine();

    System.out.print("Hasło: ");
    String haslo = scanner.nextLine();

    return new Person(imie, nazwisko, login, haslo);
  }

  /**
   * Wypisanie komunikatu początkowego i określenie wartości menu
   *
   * @param menu - wartość do przechodzenia switch
   * @return zmieniona wartość menu
   */
  private static int komunikatPoczatkowy(int menu) {
    if (zalogowana == null) {  //niezalogowany
      System.out.println(
          "----BIBLIOTEKA----\nWybierz opcję:\n1. Zaloguj się\n2. Załóż konto.\n3. Wypisz dostępne książki.\n0. Wyjdź z programu.");
    } else { //zalogowany
      System.out.println(
          "----BIBLIOTEKA---- Zalogowany: |" + zalogowana.login + "| " + zalogowana.imię + " "
              + zalogowana.nazwisko
              +
              "\nWybierz opcję: \n1. Wyloguj się \n2. Wypożycz książkę. \n3. Oddaj książkę. \n4. Wypisz dostępne książki. \n5. Wypisz wypożyczone przez ciebie książki. \n0. Wyjdź z programu.");
    } //koniec komunikatu początkowego
    Scanner scanner = new Scanner(System.in);
    try {
      menu = Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException ex) {
      System.out.println("Podano niepoprawną wartość, spróbuj ponownie.");
      menu = -1;
    }
    return menu;
  }

  private static void próbaLogowania(Library biblioteka) {
    System.out.println("--Logowanie--");
    Scanner scanner;
    System.out.print("Podaj login do konta: ");
    scanner = new Scanner(System.in);
    String login = scanner.nextLine();
    System.out.print("Podaj hasło dla użytkownika " + login + ": ");
    scanner = new Scanner(System.in);
    String haslo = scanner.nextLine();
    if (logowanie(login, haslo, biblioteka)) {
      System.out.println("Udane logowanie");
    } else {
      System.out.println("Nieudana próba logowania\n");
    }
  }

  private static void próbaWypożyczeniaKsiążki(Library biblioteka) {
    System.out.println("--DOSTĘPNE KSIĄŻKI--\n");
    biblioteka.wypiszDostepne();
    Scanner scanner;
    System.out.println(
        "Podaj numer książki, aby ją wypożyczyć; podaj dowolny inny znak, aby pominąć");
    scanner = new Scanner(System.in);
    int index;
    try {
      index = Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException ex) {
      index = biblioteka.ksiazki.size() - 1;
    }
    Book ksiazka = biblioteka.ksiazki.get(index);
    if (zalogowana.wypozyczKsiazke(index, ksiazka)) {
      System.out.println("Wypożyczono książkę: " + ksiazka);
    } else {
      System.out.println("Nie udało się wypożyczyć książki");
    }
  }

  private static void próbaOddaniaKsiążki(Library biblioteka) {
    System.out.println("--WYPOŻYCZONE KSIĄŻKI--\n");
    zalogowana.wypozyczone();
    System.out.println(
        "Podaj numer książki, aby ją oddać; podaj dowolny inny znak, aby pominąć");
    Scanner scanner = new Scanner(System.in);
    int index;
    try {
      index = Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException ex) {
      index = -1;
    }
    Book ksiazka = zalogowana.wypozyczoneKsiazki.get(index);
    if (zalogowana.oddajKsiazke(index, ksiazka)) {
      System.out.println("Oddano książkę: " + ksiazka.toString());
    } else {
      System.out.println("Nie udało się oddać książki");
    }
  }

  /**
   * metoda menu w której obsługiwana jest biblioteka w trybie konsolowym
   *
   * @param biblioteka - Obiekt biblioteka który przechowuje książki i osoby
   * @param menu       - zmienna do przechodzenia menu
   * @throws IOException - potrzebne do System.in.read()
   */
  @SuppressWarnings("checkstyle:OperatorWrap")
  public static void menu(Library biblioteka, int menu) throws IOException {
    do { //Komunikat początkowy
      menu = komunikatPoczatkowy(menu);

      //Switch dla pozycji menu
      if (zalogowana == null) {  //niezalogowany
        switch (menu) {
          case 1 -> próbaLogowania(biblioteka); // Logowanie

          case 2 -> { //Tworzenie konta i logowanie
            Person konto = tworzenieKonta();

            if (biblioteka.dodajOsobe(konto) && logowanie(konto.login, konto.hasło, biblioteka)) {
              System.out.println("Udane utworzenie konta i zalogowanie do biblioteki");

            } else if (!logowanie(konto.login, konto.hasło, biblioteka)) {
              System.out.println("Nieudana próba logowania\n");

            } else {
              System.out.println("Nie udało się utworzyć konta");
            }
          }
          case 3 -> { //wypisanie książek
            System.out.println("--DOSTĘPNE KSIĄŻKI--");
            biblioteka.wypiszDostepne();
          }
          default -> { }
        }
      } else { //zalogowany
        switch (menu) {
          case 1 -> { //Wylogowanie
            System.out.println("--Wylogowanie-- \nNastępuje wylogowanie aktualnego użytkownika");
            wylogowanie();
          }
          case 2 -> próbaWypożyczeniaKsiążki(biblioteka); //wypożyczenie książki

          case 3 -> próbaOddaniaKsiążki(biblioteka); //oddanie książki

          case 4 -> { //wypisanie książek
            System.out.println("--DOSTĘPNE KSIĄŻKI--");
            biblioteka.wypiszDostepne();
            System.out.println("Kontynuuj...");
            System.in.read();
          }
          case 5 -> { //wypisanie wypożyczonych książek
            System.out.println("--WYPOŻYCZONE KSIĄŻKI--\n");
            zalogowana.wypozyczone();
            System.out.println("Kontynuuj...");
            System.in.read();
          }
          default -> {
          }
        }
      } // koniec Switcha

    } while (menu != 0); //początkowa wartość menu 0, jeśli nie jest zmieniona to wyjście z do...while
  }

  /**
   * Metoda w której startuje aplikacja
   *
   * @param args -
   * @throws IOException - potrzebne dla biblioteka.load()
   */
  public static void main(String[] args) throws IOException {
    int menu = 0;
    Library biblioteka = new Library();
    biblioteka.load();
    //menu(biblioteka, menu);
    //biblioteka.save();
    //JSONSerializer.serialize(biblioteka, "src/main/resources/JSON/Books.json", "src/main/resources/JSON/People.json");
    JFrame gui = new GUI("Aplikacja Biblioteki", biblioteka);

    XMLConvertor.naXML(biblioteka);
  }
}
