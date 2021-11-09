package com.biblioteka.gui;

import com.biblioteka.Library;
import com.biblioteka.XMLConvertor;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

interface Action {
    void action();
}

public class MenuBazowe extends PanelBazowy {
    private final Library biblioteka;
    protected JLabel loginStatusLabel;
    protected JList<String> lista;

    public MenuBazowe(JFrame okno, Library biblioteka) {
        super();
        this.biblioteka = biblioteka;

        setLayout(null);

        // dodawanie tytułu
        JLabel title = new JLabel("BIBLIOTEKA", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.PLAIN, 64));
        title.setForeground(new Color(0x5C3D2E));

        //rozmiary JLabel dla tytułu
        title.setBounds(20, 50, okno.getWidth() - 40, 64);
        add(title);

        //opis
        JLabel description = new JLabel("Wybierz opcję z listy", SwingConstants.CENTER);
        description.setFont(new Font("Sans", Font.PLAIN, 16));
        description.setBounds(20, 215, okno.getWidth() - 40, 30);
        add(description);

        //wyjście
        JButton exitButton = new JButton();
        exitButton.setBounds(okno.getWidth() - 120, okno.getHeight() - 70, 100, 30);
        exitButton.setBackground(new Color(0xB85C38));
        exitButton.setForeground(Color.white);
        exitButton.setText("Wyjdź");
        exitButton.addActionListener(e -> ZamknijOkno());

        add(exitButton);

        loginStatusLabel = new JLabel();
        loginStatusLabel.setBounds(10, okno.getHeight() - 70, 300, 30);
        loginStatusLabel.setFont(new Font("Sans", Font.PLAIN, 16));
        //loginStatusLabel.setText("Nie jesteś zalogowany");
        add(loginStatusLabel);
    }

    void ZamknijOkno() {
        try {
            biblioteka.save();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        XMLConvertor.naXML(biblioteka);

        //brutalne zamykanie
        System.exit(0);
    }

    protected void GenerujListe(JFrame okno, CardLayout cards, JPanel mainPanel, String[] menuOpcje, String[] nazwyKart, Action[] additionalActions) {
        if(lista != null) {
            remove(lista);
        }

        //JList<String> menuNiezalogowanyLista;
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addAll(Arrays.stream(menuOpcje).toList());

        //stworzenie listy z tekstów w tablicy menuOpcje
        lista = new JList<>(listModel);

        //tekst w liście na środku
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) lista.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        lista.setBorder(new LineBorder(new Color(0x5C3D2E)));
        lista.setBackground(new Color(0xe5dcc3));
        lista.setBounds((okno.getWidth() - 300)/2, (okno.getHeight() - 100)/2, 300, menuOpcje.length * 25);
        lista.setFont(new Font("Sans", Font.PLAIN, 16));

        //każde kliknięcie wartości na liście zwraca w konsoli index wybranej wartości
        lista.addListSelectionListener(e -> {
                    if (!e.getValueIsAdjusting()) {
                        int index = lista.getSelectedIndex();
                        if (index >= 0) {
                            cards.show(mainPanel, nazwyKart[index]);

                            if (additionalActions[index] != null) {
                                additionalActions[index].action();
                            }

                            lista.clearSelection();
                        }
                    }
                }
        );

        add(lista);
    }
}
