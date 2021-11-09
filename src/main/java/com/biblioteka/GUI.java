package com.biblioteka;

import com.biblioteka.gui.*;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private final JPanel mainPanel = new JPanel(new CardLayout());
    private final CardLayout cards;

    JPanel oknoL;
    JPanel oknoR;
    JPanel menuNZ;
    MenuZalogowany menuZ;
    WyswietlDostepne wyswietlD;
    WyswietlWypozyczone wyswietlW;
    OddajKsiazke oddajK;
    WypozyczKsiazke wypożyczK;

    private Person zalogowanyUżytkownik;

    public Person getZalogowanyUżytkownik() {
        return zalogowanyUżytkownik;
    }

    public void setZalogowanyUzytkownik(Person person) {
        zalogowanyUżytkownik = person;
        onUserStateChanged();
    }

    public void onUserStateChanged() {
        menuZ.update(zalogowanyUżytkownik);
        wyswietlW.update(this);
        oddajK.update(zalogowanyUżytkownik);
        wypożyczK.update(this);
        wyswietlD.update(this);
    }

    public GUI(String title, Library biblioteka) {
        super(title);
        setSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //okno stałego rozmiaru
        setResizable(false);
        add(mainPanel);

        //utworzenie kart dla mainPanel
        cards = (CardLayout) mainPanel.getLayout();

        //oddzielne JPanele dla każdej metody
        oknoL = new OknoLogowania(biblioteka, mainPanel, cards, this);
        oknoR = new OknoRejestracji(biblioteka, this, cards, mainPanel);
        menuNZ = new MenuNiezalogowany(biblioteka, this, mainPanel, cards);
        menuZ = new MenuZalogowany(biblioteka, this, mainPanel, cards);
        wyswietlD = new WyswietlDostepne(biblioteka, mainPanel, cards, this);
        wyswietlW = new WyswietlWypozyczone(biblioteka, mainPanel, cards, this);

        oddajK = new OddajKsiazke(cards, mainPanel, this);
        wypożyczK = new WypozyczKsiazke(biblioteka, mainPanel, cards, this);

        //dodanie paneli metod do mainPanel, w której można je zmieniać np. switchem
        mainPanel.add(oknoL, "OknoLogowania");
        mainPanel.add(oknoR, "OknoRejestracji");
        mainPanel.add(menuNZ, "MenuNiezalogowany");
        mainPanel.add(menuZ, "MenuZalogowany");
        mainPanel.add(wyswietlD, "WyswietlDostepne");
        mainPanel.add(wyswietlW, "WyswietlWypozyczone");

        mainPanel.add(oddajK, "OddajKsiążke");
        mainPanel.add(wypożyczK, "WypożyczKsiążke");



        //działa jak switch, po wpisaniu stringów wyżej pokazuje się to okno, aktualnie jest wyświetlenie dostęnych książek
        cards.show(mainPanel, "MenuNiezalogowany");


        setVisible(true);
    }
}
