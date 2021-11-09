package com.biblioteka.gui;

import com.biblioteka.GUI;
import com.biblioteka.Library;
import com.biblioteka.Main;

import javax.swing.*;
import java.awt.*;

public class OknoLogowania extends PanelBazowy {

    public OknoLogowania(Library biblioteka, JPanel mainPanel, CardLayout cards, GUI gui) {

        setLayout(null);

        //tytuł na górze aplikacji
        JLabel title = new JLabel("Logowanie", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        title.setForeground(new Color(0x5C3D2E));
        title.setBounds(20, 10, gui.getWidth() - 40, 30);
        add(title);

        //Login:
        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        loginLabel.setBounds(20, 50, 50, 25);
        loginLabel.setForeground(new Color(0x5C3D2E));
        add(loginLabel);

        JTextField loginText = new JTextField();
        loginText.setBounds(80, 50, gui.getWidth() - 200, 25);
        //loginText.setBackground(Color.white);
        add(loginText);

        //hasło wypisywane kropkami
        JLabel passwordLabel = new JLabel("Hasło");
        passwordLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        passwordLabel.setBounds(20, 80, 50, 25);
        passwordLabel.setForeground(new Color(0x5C3D2E));
        add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        passwordText.setBounds(80, 80, gui.getWidth() - 200, 25);
        //passwordText.setBackground(Color.white);
        add(passwordText);

        /*
        //informacja czy użytkownik jest zalogowany
        JLabel informacja = new JLabel("");
        informacja.setBounds(20, getHeight() - 100, 250, 25);
        add(informacja);
        */

        //przycisk do logowania użytkownika
        JButton logowanieButton = new JButton("Zaloguj");
        logowanieButton.setBounds(20, 150, 100, 25);
        logowanieButton.setBackground(new Color(0xB85C38));
        logowanieButton.setForeground(Color.white);
        logowanieButton.addActionListener(e -> {
            String myPass = String.valueOf(passwordText.getPassword());
            if (Main.logowanie(loginText.getText(), myPass, biblioteka)) {
                //informacja.setText("Udana próba Logowania");
                gui.setZalogowanyUzytkownik(Main.getZalogowana());
                //informacja.setText("");
                passwordText.setText("");
                loginText.setText("");

                cards.show(mainPanel, "MenuZalogowany");


            } else {
                JOptionPane.showMessageDialog(null, "Nie udało się zalogować!", "Nieudana próba logowania", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(logowanieButton);

        //przycisk do powrotu:
        JButton backButton = new JButton("Powrót");
        backButton.setBounds(600, 150, 100, 25);
        backButton.setBackground(new Color(0xB85C38));
        backButton.setForeground(Color.white);
        backButton.addActionListener(e -> {

            passwordText.setText("");
            loginText.setText("");
            //informacja.setText("");
            cards.show(mainPanel, "MenuNiezalogowany");

        });

        add(backButton);



    }
}
