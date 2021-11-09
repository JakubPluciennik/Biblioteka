package com.biblioteka.gui;

import com.biblioteka.GUI;
import com.biblioteka.Library;
import com.biblioteka.Person;

import javax.swing.*;
import java.awt.*;

public class MenuZalogowany extends MenuBazowe {


    public MenuZalogowany(Library biblioteka, GUI gui, JPanel mainPanel, CardLayout cards) {
        super(gui, biblioteka);

        String[] menuOpcje = {"Wyloguj się", "Wypożycz książkę", "Oddaj książkę", "Wypisz dostępne książki", "Wypisz wypożyczone przez Ciebie książki"};
        String[] nazwyKart = {"MenuNiezalogowany", "WypożyczKsiążke", "OddajKsiążke", "WyswietlDostepne", "WyswietlWypozyczone" };
        //Action[] actions = { null, null, null, null, null };
        Action[] actions = { () -> gui.setZalogowanyUzytkownik(null), null, null, null, null };
        GenerujListe(gui, cards, mainPanel, menuOpcje, nazwyKart, actions);
    }

    public  void update(Person user) {
        if (user != null) {
            loginStatusLabel.setText("Zalogowany jako " + user.getLoginString());
        }
    }
}
