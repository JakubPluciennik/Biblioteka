package com.biblioteka.gui;

import com.biblioteka.Book;
import com.biblioteka.GUI;
import com.biblioteka.Library;
import com.biblioteka.Person;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class WyswietlWypozyczone extends PanelBazowy {

    JTable table;
    public WyswietlWypozyczone(Library biblioteka, JPanel mainPanel, CardLayout cards, GUI gui) {

        setLayout(null);

        JLabel title = new JLabel("Wypożyczone przez ciebie książki", SwingConstants.CENTER);
        title.setFont(new Font("Sans", Font.PLAIN, 20));
        title.setBounds(20, 10, gui.getWidth() - 40, 30);
        add(title);

        //nazwy kolumn w tabeli
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Tytuł");
        tableModel.addColumn("Autor");
        tableModel.addColumn("Rok Wydania");
        tableModel.addColumn("Dostępne");
        table = new JTable(tableModel);
        table.setFont(new Font("Sans", Font.PLAIN, 14));
        table.setBorder(new LineBorder(Color.black));


        table.setEnabled(false);
        add(table);

        //przycisk powrotu
        JButton backButton = new JButton("Powrót");
        backButton.setBounds(20, 10, 100, 25);
        backButton.setBackground(new Color(0xB85C38));
        backButton.setForeground(Color.white);
        backButton.addActionListener(e -> {
            cards.show(mainPanel, "MenuZalogowany");
        });

        add(backButton);
    }

    public void update(GUI gui) {

        Person zalogowanyUżytkownik = gui.getZalogowanyUżytkownik();
        DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
        tableModel.setRowCount(0);
        if(zalogowanyUżytkownik == null) {

            table.setBounds(20, 50, getWidth() - 60, 0);
        } else {
            //wpisywanie do tabeli książek wypożyczonych przez zalogowana
            var ksiazki = zalogowanyUżytkownik.getWypozyczoneKsiazki();
            for (int i = 0; i < ksiazki.size(); i++) {
                Integer index[] = ksiazki.keySet().toArray(new Integer[0]);
                Book b[] = ksiazki.values().toArray(new Book[0]);
                tableModel.addRow(new Object[]{index[i], b[i].getTytuł(), b[i].getAutor(), b[i].getRokWydania(), b[i].getDostępne()});
            }

            table.setBounds(20, 50, gui.getWidth() - 60, ksiazki.size() * 16);
        }
    }
}
