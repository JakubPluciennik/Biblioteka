package com.biblioteka.gui;

import com.biblioteka.GUI;
import com.biblioteka.Library;
import com.biblioteka.Main;
import com.biblioteka.Person;

import javax.swing.*;
import java.awt.*;

public class OknoRejestracji extends PanelBazowy {

    public OknoRejestracji(Library biblioteka, GUI gui, CardLayout cards, JPanel mainPanel) {
        //Metoda podobna do oknoLogownia, do tego wpisanie imienia i nazwiska
        //JPanel oknoRejestracji = new JPanel();
        setLayout(null);
        //oknoRejestracji.setBackground(Color.white);

        JLabel title = new JLabel("Rejestracja", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        title.setBounds(20, 10, gui.getWidth() - 40, 30);
        title.setForeground(new Color(0x5C3D2E));
        add(title);

        JLabel nameLabel = new JLabel("Imię");
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        nameLabel.setForeground(new Color(0x5C3D2E));
        nameLabel.setBounds(20, 50, 70, 25);
        add(nameLabel);

        JTextField nameText = new JTextField();
        nameText.setBounds(80, 50, gui.getWidth() - 200, 25);
        add(nameText);

        JLabel surnameLabel = new JLabel("Nazwisko");
        surnameLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        surnameLabel.setForeground(new Color(0x5C3D2E));
        surnameLabel.setBounds(20, 80, 100, 25);
        add(surnameLabel);

        JTextField surnameText = new JTextField();
        surnameText.setBounds(80, 80, gui.getWidth() - 200, 25);
        add(surnameText);

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        loginLabel.setForeground(new Color(0x5C3D2E));
        loginLabel.setBounds(20, 110, 50, 25);
        add(loginLabel);

        JTextField loginText = new JTextField();
        loginText.setBounds(80, 110, gui.getWidth() - 200, 25);
        add(loginText);

        JLabel passwordLabel = new JLabel("Hasło");
        passwordLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        passwordLabel.setForeground(new Color(0x5C3D2E));
        passwordLabel.setBounds(20, 140, 50, 25);
        add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(80, 140, gui.getWidth() - 200, 25);
        add(passwordText);

        JLabel informacja = new JLabel("");
        informacja.setBounds(20, getHeight() - 100, gui.getWidth() - 200, 25);
        add(informacja);

        JButton logowanieButton = new JButton("Zaloguj");
        logowanieButton.setBounds(20, 170, 100, 25);
        logowanieButton.setBackground(new Color(0xB85C38));
        logowanieButton.setForeground(Color.white);
        logowanieButton.addActionListener(e -> {
            String myPass = String.valueOf(passwordText.getPassword());

            //tworzenie tymczasowej osoby, jeśli nie ma jej w bibliotece to jest zapisywana
            Person tmp = new Person(nameText.getText(), surnameText.getText(), loginText.getText(), myPass);

            if (biblioteka.dodajOsobe(tmp)) {
                informacja.setText("Udane utworzenie konta");
                if (Main.logowanie(tmp.getLogin(), tmp.getHasło(), biblioteka)) {
                    informacja.setText(informacja.getText() + ";    Udana próba logowania");
                    gui.setZalogowanyUzytkownik(Main.getZalogowana());
                    nameText.setText("");
                    surnameText.setText("");
                    loginText.setText("");
                    passwordText.setText("");
                    cards.show(mainPanel, "MenuZalogowany");


                } else
                    //to się chyba nigdy nie może wydarzyć
                    //informacja.setText(informacja.getText() + ";    Nieudana próba logowania");
                    JOptionPane.showMessageDialog(null, "Nie udało się zalogować!", "Nieudana próba logowania", JOptionPane.ERROR_MESSAGE);
            } else {
                //informacja.setText("Nie udało się utworzyć konta");
                JOptionPane.showMessageDialog(null, "Nie udało się utworzyć konta!", "Nieudana próba rejestracji", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(logowanieButton);

        //przycisk do powrotu:
        JButton backButton = new JButton("Powrót");
        backButton.setBounds(600, 170, 100, 25);
        backButton.setBackground(new Color(0xB85C38));
        backButton.setForeground(Color.white);
        backButton.addActionListener(e -> {

            nameText.setText("");
            surnameText.setText("");
            loginText.setText("");
            passwordText.setText("");
            cards.show(mainPanel, "MenuNiezalogowany");

        });

        add(backButton);
    }
}
