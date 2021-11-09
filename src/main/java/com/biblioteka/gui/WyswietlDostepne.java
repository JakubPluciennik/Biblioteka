package com.biblioteka.gui;

import com.biblioteka.Book;
import com.biblioteka.GUI;
import com.biblioteka.Library;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class WyswietlDostepne extends PanelBazowy {
    JTable table;
    Library biblioteka;
    public WyswietlDostepne(Library biblioteka, JPanel mainPanel, CardLayout cards, GUI gui) {
        //JPanel wyswietlDostepne = new JPanel();
        setLayout(null);
        this.biblioteka = biblioteka;
        //wyswietlDostepne.setBackground(Color.white);
        JLabel title = new JLabel("Dostępne książki", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        title.setForeground(new Color(0x5C3D2E));
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
        table.setBackground(new Color(0xE5DCC3));
        table.setBorder(new LineBorder(Color.black));

        //Dodawanie wszystkich dostępnych pozycji

        int dostepneSize = 0;
        for(int i = 0; i < biblioteka.size(); i++) {
            Book b = biblioteka.getKsiazka(i);
            if(b.getDostępne()>0) {
                tableModel.addRow(new Object[]{i, b.getTytuł(), b.getAutor(), b.getRokWydania(), b.getDostępne()});
                dostepneSize++;
            }
        }

        table.setBounds(20, 50, gui.getWidth() - 60, dostepneSize * 16);
        table.setEnabled(false);
        add(table);

        //
        JButton backButton = new JButton("Powrót");
        backButton.setBounds(20, 10, 100, 25);
        backButton.setBackground(new Color(0xB85C38));
        backButton.setForeground(Color.white);
        backButton.addActionListener(e -> {
            if (gui.getZalogowanyUżytkownik() == null)
                cards.show(mainPanel, "MenuNiezalogowany");
            else
                cards.show(mainPanel, "MenuZalogowany");
        });

        add(backButton);
    }
    public void update(GUI gui) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
        //Dodawanie wszystkich dostępnych pozycji
        int dostepneSize = 0;
        for (int i = 0; i < biblioteka.size(); i++) {
            Book b = biblioteka.getKsiazka(i);
            if (b.getDostępne() > 0) {
                tableModel.addRow(new Object[]{i, b.getTytuł(), b.getAutor(), b.getRokWydania(), b.getDostępne()});
                dostepneSize++;
            }
        }
        table.setBounds(20, 50, gui.getWidth() - 60, dostepneSize * 16);
    }
}
