package com.biblioteka;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Person {
  String imię;
  String nazwisko;
  String login;
  String hasło;
  Map<Integer, Book> wypozyczoneKsiazki;

  public Map<Integer, Book> getWypozyczoneKsiazki() {
    return wypozyczoneKsiazki;
  }

  public String getName() {
    return imię;
  }

  public String getSurname() {
    return nazwisko;
  }

  public String getLogin() {
    return login;
  }

  public String getHasło() {
    return hasło;
  }

  public Person(String imię, String nazwisko, String login, String hasło) {
    this.imię = imię;
    this.nazwisko = nazwisko;
    this.login = login;
    this.hasło = hasło;
    this.wypozyczoneKsiazki = new HashMap<Integer, Book>();
  }

  public Boolean wypozyczKsiazke(int index, Book ksiazka) {
    if (ksiazka.getDostępne() > 0 && !wypozyczoneKsiazki.containsKey(index)) {
      wypozyczoneKsiazki.put(index, ksiazka);
      ksiazka.setDostępne(ksiazka.getDostępne() - 1);
      return true;
    } else {
      return false;
    }
  }

  //podczas ładowania z pliku nie zmnejszać liczby dostępnych książek
  void wypozyczKsiazkeLoadPerson(int index, Book ksiazka) {
    if (ksiazka.getDostępne() >= 0 && !wypozyczoneKsiazki.containsKey(index)) {
      wypozyczoneKsiazki.put(index, ksiazka);
    }
  }

  public Boolean oddajKsiazke(int index, Book ksiazka) {
    if (wypozyczoneKsiazki.containsValue(ksiazka)) {
      ksiazka.setDostępne(ksiazka.getDostępne() + 1);
      return wypozyczoneKsiazki.remove(index, ksiazka);
    }
    return false;
  }

  private String arrayToString(Object[] objectArray) {
    Integer[] array = Arrays.asList(objectArray).toArray(new Integer[0]);
    String wynik = "";
    for (int i = 0; i < array.length; i++) {
      if (i != array.length - 1) {
        wynik += array[i] + ";";
      } else {
        wynik += array[i];
      }
    }
    return wynik;
  }

  void wypozyczone() {
    for (int i = 0; i < wypozyczoneKsiazki.size(); i++) {
      Integer[] key = wypozyczoneKsiazki.keySet().toArray(new Integer[0]);
      System.out.println(key[i] + ": " + wypozyczoneKsiazki.get(key[i]).prettyToString());
    }
  }

  @Override
  public String toString() {
    return "Imię: '" + imię + '\''
        + ", Nazwisko: '" + nazwisko + '\''
        + ", Login: '" + login + '\''
        + ", Wypozyczone Ksiazki: " + wypozyczoneKsiazki
        + '}';
  }

  public String getLoginString() {
    return login + " (" + imię + " " + nazwisko + ")";
  }

  String saveWiersz() {
    //Imię|Nazwisko|Login|Hasło|Lista wypożyczonych książek

    //jeśli przy wpisywaniu hasło puste, zastąpione jest spacją
    if (hasło.equals("")) {
      hasło = " ";
    }

    //jeśli index -1, brak wypożyczonych książek
    String keys = "-1";
    if (wypozyczoneKsiazki.size() > 0) {
      keys = arrayToString(wypozyczoneKsiazki.keySet().toArray());
    }

    return String.format("%s,%s,%s,%s,%s\n", imię, nazwisko, login, hasło, keys);
  }
}
