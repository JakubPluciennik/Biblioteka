package com.biblioteka.gui;

import com.biblioteka.Library;

import javax.swing.*;
import java.awt.*;

public class MenuNiezalogowany extends MenuBazowe {


    public MenuNiezalogowany(Library biblioteka, JFrame okno, JPanel mainPanel, CardLayout cards) {
        super(okno, biblioteka);

        String[] menuOpcje = {"Zaloguj się", "Załóż konto", "Wypisz dostępne książki"};
        String[] nazwyKart = {"OknoLogowania", "OknoRejestracji", "WyswietlDostepne" };
        Action[] actions = { null, null, null};

        GenerujListe(okno, cards, mainPanel, menuOpcje, nazwyKart, actions);

        loginStatusLabel.setText("Nie jesteś zalogowany");


    }
}
