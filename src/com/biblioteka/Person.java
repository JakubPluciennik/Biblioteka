package com.biblioteka;

import java.util.*;

public class Person {
    String imię;
    String nazwisko;
    String login;
    String hasło;
    Map<Integer,Book> wypozyczoneKsiazki;

    Person(String imię, String nazwisko, String login, String hasło)
    {
        this.imię = imię;
        this.nazwisko = nazwisko;
        this.login = login;
        this.hasło = hasło;
        this.wypozyczoneKsiazki = new HashMap<Integer, Book>();
    }

     Boolean wypozyczKsiazke(int index, Book ksiazka)
    {
        if(ksiazka.getDostępne()>0)
        {
            ksiazka.setDostępne(ksiazka.getDostępne()-1);
            if(!wypozyczoneKsiazki.containsKey(index))
            {
                wypozyczoneKsiazki.put(index, ksiazka);
                return true;
            }
        }
      return false;
    }

    //podczas ładowania z pliku nie zmnejszać liczby dostępnych książek
    void wypozyczKsiazkeLoadPerson(int index, Book ksiazka)
    {
        if (ksiazka.getDostępne() > 0)
        {
            if (!wypozyczoneKsiazki.containsKey(index))
            {
                wypozyczoneKsiazki.put(index, ksiazka);
            }
        }
    }

     Boolean oddajKsiazke(int index, Book ksiazka)
    {
        if(wypozyczoneKsiazki.containsValue(ksiazka))
        {
            ksiazka.setDostępne(ksiazka.getDostępne()+1);
            return wypozyczoneKsiazki.remove(index, ksiazka);
        }
        return false;
    }

    private String arrayToString(Object[] objectArray)
    {
        Integer[] array = Arrays.asList(objectArray).toArray(new Integer[0]);
        String wynik = "";
        for (int i = 0; i < array.length; i++)
        {
            if(i!=array.length-1)
            {
                wynik += array[i] + ",";
            }
            else wynik += array[i];
        }
        return wynik;
    }
     void wypozyczone()
    {
        for (int i = 0; i < wypozyczoneKsiazki.size(); i++)
        {
            Integer[] key= wypozyczoneKsiazki.keySet().toArray(new Integer[0]);
            System.out.println(key[i]+": "+wypozyczoneKsiazki.get(key[i]));
        }
    }
    @Override
    public String toString()
    {
        return "Imię: '" + imię + '\'' +
                ", Nazwisko: '" + nazwisko + '\'' +
                ", Login: '" + login + '\'' +
                ", Wypozyczone Ksiazki: " + wypozyczoneKsiazki +
                '}';
    }

    String saveWiersz()
    {
        //Imię|Nazwisko|Login|Hasło|Lista wypożyczonych książek
        if(hasło.equals("")) hasło = " ";
        String keys = "-1";
        if(wypozyczoneKsiazki.size() > 0)
        {
            keys = arrayToString(wypozyczoneKsiazki.keySet().toArray());
        }

        return String.format("%s|%s|%s|%s|%s\n",imię, nazwisko, login, hasło,keys);
    }
}
