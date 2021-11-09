package com.biblioteka.gui;

import com.biblioteka.Book;
import com.biblioteka.GUI;
import com.biblioteka.Person;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

public class OddajKsiazke extends PanelBazowy {

    Person zalogowanyUżytkownik;
    JTable table;

    public OddajKsiazke(CardLayout cards, JPanel mainPanel, GUI gui) {
        setLayout(null);
        //oddajKsiążke.setBack
        // ground(Color.white);
        JLabel title = new JLabel("Którą książkę chcesz oddać?", SwingConstants.CENTER);
        title.setFont(new Font("Sans", Font.PLAIN, 20));
        title.setBounds(20, 10, gui.getWidth() - 40, 30);
        add(title);

        //nazwy kolumn w tabeli
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Tytuł");
        tableModel.addColumn("Autor");
        tableModel.addColumn("Rok Wydania");
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }

            // brudny hack który powoduje że przy zaznaczaniu wierszy w tabeli ostatni z nich nie znika
            @Override
            public int getSelectedRow() {
                int row = super.getSelectedRow();
                if (row == -1) return -2;
                return row;
            }
        };

        table.setFont(new Font("Sans", Font.PLAIN, 14));
        table.setBorder(new LineBorder(Color.black));
        add(table);

        //TEKST
        JLabel loginLabel = new JLabel("Podaj ID książki którą chcesz oddać.");
        loginLabel.setBounds(20, 250, 250, 25);
        add(loginLabel);

        //Miejsce na wpisanie id książki którą należy oddać
        JTextField idTEXT = new JTextField();
        idTEXT.setBounds(20, 275, 100, 25);
        add(idTEXT);

        //przycisk potwierdzenia
        JButton Oddaj = new JButton("Oddaj");
        Oddaj.setBackground(new Color(0xB85C38));
        Oddaj.setForeground(Color.white);
        Oddaj.setBounds(20, 300, 100, 25);
        Oddaj.addActionListener(e -> {
            String zawartosc = idTEXT.getText();
            int id;
            try {
                id = Integer.parseInt(zawartosc);
                var wypozyczoneKsiazki = zalogowanyUżytkownik.getWypozyczoneKsiazki();
                if (wypozyczoneKsiazki.containsKey(id)) {
                    Book tmp = wypozyczoneKsiazki.get(id);
                    zalogowanyUżytkownik.oddajKsiazke(id, tmp);
                    gui.onUserStateChanged();
                } else {
                    JOptionPane.showMessageDialog(null, "Nie masz wypożyczonej tej książki!", "Błąd oddania", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException exep) {
                JOptionPane.showMessageDialog(null, "Niepoprawny napis!", "Błąd oddania", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(Oddaj);

        // przycisk do oddawania zaznaczonych wierszy w tabeli
        JButton returnSelectedButton = new JButton("Oddaj zaznaczone");
        returnSelectedButton.setBackground(new Color(0xB85C38));
        returnSelectedButton.setForeground(Color.white);
        returnSelectedButton.setBounds(gui.getWidth() - 50 - 200, 300, 200, 25);
        returnSelectedButton.addActionListener(e -> {

            int[] selectedRows = table.getSelectedRows();
            TableModel model = table.getModel();

            Person zalogowanyUżytkownik = gui.getZalogowanyUżytkownik();
            var ksiazkiUzytkownika = zalogowanyUżytkownik.getWypozyczoneKsiazki();

            for (int row : selectedRows) {
                int id = (int)model.getValueAt(row, 0);
                Book tmp = ksiazkiUzytkownika.get(id);
                zalogowanyUżytkownik.oddajKsiazke(id, tmp);
            }

            gui.onUserStateChanged();
            table.clearSelection();
        });
        add(returnSelectedButton);

        //przycisk powrotu
        JButton backButton = new JButton("Powrót");
        backButton.setBackground(new Color(0xB85C38));
        backButton.setForeground(Color.white);
        backButton.setBounds(20, 10, 100, 25);
        backButton.addActionListener(e -> {
            cards.show(mainPanel, "MenuZalogowany");
        });

        add(backButton);
    }

    public void update(Person zalogowanyUżytkownik) {
        this.zalogowanyUżytkownik = zalogowanyUżytkownik;
        update();
    }

    public void update() {
        DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
        tableModel.setRowCount(0);

        if(zalogowanyUżytkownik != null) {
            var wypozyczoneKsiazki = zalogowanyUżytkownik.getWypozyczoneKsiazki();

            for (int i = 0; i < wypozyczoneKsiazki.size(); i++) {
                Integer index[] = wypozyczoneKsiazki.keySet().toArray(new Integer[0]);
                Book b[] = wypozyczoneKsiazki.values().toArray(new Book[0]);
                tableModel.addRow(new Object[]{index[i], b[i].getTytuł(), b[i].getAutor(), b[i].getRokWydania()});
            }

            table.setBounds(20, 50, getWidth() - 60, wypozyczoneKsiazki.size() * 16);

        }
    }
}
