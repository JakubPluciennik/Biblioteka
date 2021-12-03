package com.biblioteka;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@SuppressWarnings("ALL")
public class XMLConvertor {
  static final String xmlBooksPath = "src/main/resources/XML/Books.xml";
  static final String xmlPeoplePath = "src/main/resources/XML/People.xml";

  public static void saveBookXML(Library biblioteka) {
    try {
      DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
      Document document = documentBuilder.newDocument();

      Element root = document.createElement("Books");
      document.appendChild(root);
      int i = 0;
      for (Book ksiazka : biblioteka.ksiazki) {
        Element book = document.createElement("book");
        root.appendChild(book);

        Attr attr = document.createAttribute("id");
        attr.setValue(Integer.toString(i));
        book.setAttributeNode(attr);

        Element tytuł = document.createElement("title");
        tytuł.appendChild(document.createTextNode(ksiazka.tytuł));
        book.appendChild(tytuł);

        Element autor = document.createElement("author");
        autor.appendChild(document.createTextNode(ksiazka.autor));
        book.appendChild(autor);

        Element datePublication = document.createElement("publication");
        datePublication.appendChild(document.createTextNode(String.valueOf(ksiazka.rokWydania)));
        book.appendChild(datePublication);

        Element availability = document.createElement("availability");
        availability.appendChild(document.createTextNode(Integer.toString(ksiazka.dostępne)));
        book.appendChild(availability);
        i++;
      }

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty("indent", "yes");
      DOMSource domSource = new DOMSource(document);
      StreamResult streamResult = new StreamResult(new File(xmlBooksPath));


      transformer.transform(domSource, streamResult);


    } catch (ParserConfigurationException | TransformerException pce) {
      pce.printStackTrace();
    }
  }

  public static void savePeopleXML(Library biblioteka) {
    try {
      DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
      Document document = documentBuilder.newDocument();

      Element root = document.createElement("People");
      document.appendChild(root);
      for (Person osoba : biblioteka.osoby) {
        Element person = document.createElement("person");
        root.appendChild(person);

        Element imie = document.createElement("name");
        imie.appendChild(document.createTextNode(osoba.imię));
        person.appendChild(imie);

        Element nazwisko = document.createElement("surname");
        nazwisko.appendChild(document.createTextNode(osoba.nazwisko));
        person.appendChild(nazwisko);

        Element login = document.createElement("login");
        login.appendChild(document.createTextNode(osoba.login));
        person.appendChild(login);

        Element hasło = document.createElement("password");
        hasło.appendChild(document.createTextNode(osoba.hasło));
        person.appendChild(hasło);


        for (int id : osoba.wypozyczoneKsiazki.keySet()) {

          Book tmp = osoba.wypozyczoneKsiazki.get(id);

          Element pożyczone = document.createElement("borrowed");
          pożyczone.appendChild(document.createTextNode(tmp.tytuł));
          person.appendChild(pożyczone);

          Attr attr = document.createAttribute("id");
          attr.setValue(Integer.toString(id));
          pożyczone.setAttributeNode(attr);

        }
      }


      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty("indent", "yes");
      DOMSource domSource = new DOMSource(document);
      StreamResult streamResult = new StreamResult(new File(xmlPeoplePath));


      transformer.transform(domSource, streamResult);

    } catch (ParserConfigurationException | TransformerException pce) {
      pce.printStackTrace();
    }
  }

  public static void naXML(Library bibioteka) {
    saveBookXML(bibioteka);
    savePeopleXML(bibioteka);
  }
}
