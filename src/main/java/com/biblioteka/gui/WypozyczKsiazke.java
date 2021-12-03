package com.biblioteka.gui;

import com.biblioteka.Book;
import com.biblioteka.GUI;
import com.biblioteka.Library;
import com.biblioteka.Person;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class WypozyczKsiazke extends PanelBazowy {

  JTable table;
  Library biblioteka;

  public WypozyczKsiazke(Library biblioteka, JPanel mainPanel, CardLayout cards, GUI gui) {
    this.biblioteka = biblioteka;
    setLayout(null);
    setBackground(Color.white);
    JLabel title = new JLabel("Dostępne książki", SwingConstants.CENTER);
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

    //JTable dostepne = new JTable(tableModel);
    table = new HighlightTable(tableModel, Color.white, Color.orange, new Color(0x79db70));

    table.setFont(new Font("Sans", Font.PLAIN, 14));
    table.setBorder(new LineBorder(Color.black));

    //Dodawanie wszystkich dostępnych pozycji
    int dostepneSize = 0;
    for (int i = 0; i < biblioteka.bookCount(); i++) {
      Book b = biblioteka.getKsiazka(i);
      if (b.getDostępne() > 0) {
        tableModel.addRow(
            new Object[] {i, b.getTytuł(), b.getAutor(), b.getRokWydania(), b.getDostępne()});
        dostepneSize++;
      }

      table.setBounds(20, 50, gui.getWidth() - 60, dostepneSize * 16);
      //dostepne.setEnabled(false);
      add(table);

      //INORMACJA jeśli źle podamy
      JLabel informacja = new JLabel("");
      informacja.setBounds(150, 275, 500, 25);
      add(informacja);

      //TEKST
      JLabel loginLabel = new JLabel("Podaj ID książki którą chcesz wypożyczyć.");
      loginLabel.setBounds(20, 250, 250, 25);
      add(loginLabel);

      //Miejsce na wpisanie id książki którą należy wypożyczyć
      JTextField idTEXT = new JTextField();
      idTEXT.setBounds(20, 275, 100, 25);
      add(idTEXT);

      //przycisk potwierdzenia
      JButton Wypożycz = new JButton("Wypożycz");
      Wypożycz.setBackground(new Color(0xB85C38));
      Wypożycz.setForeground(Color.white);
      Wypożycz.setBounds(20, 300, 100, 25);
      Wypożycz.addActionListener(e -> {
        String zawartosc = idTEXT.getText();
        int id;
        try {
          id = Integer.parseInt(zawartosc);
          Person zalogowanyUżytkownik = gui.getZalogowanyUżytkownik();
          var ksiazkiUzytkownika = zalogowanyUżytkownik.getWypozyczoneKsiazki();

          if (ksiazkiUzytkownika.containsKey(id)) {
            informacja.setText("Masz już wypożyczoną tą książke!");
          } else if (biblioteka.bookCount() > id && id >= 0 &&
              biblioteka.getKsiazka(id).getDostępne() > 0) {
            Book tmp = biblioteka.getKsiazka(id);
            zalogowanyUżytkownik.wypozyczKsiazke(id, tmp);
            gui.onUserStateChanged();
            informacja.setText("Wypożyczono: " + tmp.getTytuł());

          } else {
            JOptionPane.showMessageDialog(null, "Nie ma książki o tym ID!", "Błąd wypożyczenia",
                JOptionPane.ERROR_MESSAGE);
            informacja.setText("");
          }
          gui.onUserStateChanged();
        } catch (NumberFormatException exep) {
          JOptionPane.showMessageDialog(null, "Niepoprawny napis!", "Błąd wypożyczenia",
              JOptionPane.ERROR_MESSAGE);
          informacja.setText("");
        }
      });
      add(Wypożycz);

      // przycisk do wypożyczania zaznaczonych wierszy w tabeli
      JButton borrowSelectedButton = new JButton("Wypożycz zaznaczone");
      borrowSelectedButton.setBackground(new Color(0xB85C38));
      borrowSelectedButton.setForeground(Color.white);
      borrowSelectedButton.setBounds(gui.getWidth() - 50 - 200, 300, 200, 25);
      borrowSelectedButton.addActionListener(e -> {

        int[] selectedRows = table.getSelectedRows();
        TableModel model = table.getModel();

        var borrowed = new ArrayList<String>();
        var alreadyBorrowed = new ArrayList<String>();

        Person zalogowanyUżytkownik = gui.getZalogowanyUżytkownik();
        var ksiazkiUzytkownika = zalogowanyUżytkownik.getWypozyczoneKsiazki();

        if (selectedRows.length > 0) {

          for (int row : selectedRows) {
            int id = (int) model.getValueAt(row, 0);
            if (ksiazkiUzytkownika.containsKey(id)) {
              alreadyBorrowed.add(ksiazkiUzytkownika.get(id).getTytuł());
            } else {
              Book tmp = biblioteka.getKsiazka(id);
              borrowed.add(tmp.getTytuł());
              zalogowanyUżytkownik.wypozyczKsiazke(id, tmp);
            }
          }
          var messageBuilder = new StringBuilder();

          if (borrowed.size() > 0) {
            messageBuilder.append("Wypożyczono:\n");
            for (String bookTitle : borrowed) {
              messageBuilder.append(bookTitle);
              messageBuilder.append("\n");
            }
          }

          if (alreadyBorrowed.size() > 0) {
            if (messageBuilder.length() > 0) {
              messageBuilder.append("\n");
            }
            messageBuilder.append("Masz juży wypożyczone te książki:\n");
            for (String bookTitle : alreadyBorrowed) {
              messageBuilder.append(bookTitle);
              messageBuilder.append("\n");
            }

          }

          int dialogType =
              borrowed.size() > 0 ? JOptionPane.PLAIN_MESSAGE : JOptionPane.ERROR_MESSAGE;
          JOptionPane.showMessageDialog(null, messageBuilder.toString(), "Stan wypożyczenia",
              dialogType);

          //informacja.setText(wypozyczonoStringBuilder.toString());
          gui.onUserStateChanged();
          table.clearSelection();
        }
      });
      add(borrowSelectedButton);


      //przycisk powrotu
      JButton backButton = new JButton("Powrót");
      backButton.setBackground(new Color(0xB85C38));
      backButton.setForeground(Color.white);
      backButton.setBounds(20, 10, 100, 25);
      backButton.addActionListener(e -> {
        if (gui.getZalogowanyUżytkownik() == null) {
          cards.show(mainPanel, "MenuNiezalogowany");
        } else {
          cards.show(mainPanel, "MenuZalogowany");
        }
      });

      add(backButton);
    }
  }

  public void update(GUI gui) {
    DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
    tableModel.setRowCount(0);
    //Dodawanie wszystkich dostępnych pozycji
    int dostepneSize = 0;
    for (int i = 0; i < biblioteka.getKsiazki().size(); i++) {
      Book b = biblioteka.getKsiazka(i);
      if (b.getDostępne() > 0) {
        tableModel.addRow(
            new Object[] {i, b.getTytuł(), b.getAutor(), b.getRokWydania(), b.getDostępne()});
        dostepneSize++;
      }
    }
    table.setBounds(20, 50, gui.getWidth() - 60, dostepneSize * 16);
  }
}
