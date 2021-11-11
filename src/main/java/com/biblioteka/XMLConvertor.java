//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.biblioteka;

import java.io.File;
import java.util.Iterator;
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

public class XMLConvertor {
    static final String xmlBooksPath = "src/main/resources/XML/Books.xml";
    static final String xmlPeoplePath = "src/main/resources/XML/People.xml";

    public XMLConvertor() {
    }

    public static void saveBookXML(Library biblioteka) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("Books");
            document.appendChild(root);
            int i = 0;

            for(Iterator var6 = biblioteka.ksiazki.iterator(); var6.hasNext(); ++i) {
                Book ksiazka = (Book)var6.next();
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
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("indent", "yes");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("src/main/resources/XML/Books.xml"));
            transformer.transform(domSource, streamResult);
        } catch (TransformerException | ParserConfigurationException var14) {
            var14.printStackTrace();
        }

    }

    public static void savePeopleXML(Library biblioteka) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("People");
            document.appendChild(root);
            Iterator var5 = biblioteka.osoby.iterator();

            while(var5.hasNext()) {
                Person osoba = (Person)var5.next();
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
                Iterator var12 = osoba.wypozyczoneKsiazki.keySet().iterator();

                while(var12.hasNext()) {
                    int id = (Integer)var12.next();
                    Book tmp = (Book)osoba.wypozyczoneKsiazki.get(id);
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
            StreamResult streamResult = new StreamResult(new File("src/main/resources/XML/People.xml"));
            transformer.transform(domSource, streamResult);
        } catch (TransformerException | ParserConfigurationException var17) {
            var17.printStackTrace();
        }

    }

    public static void naXML(Library bibioteka) {
        saveBookXML(bibioteka);
        savePeopleXML(bibioteka);
    }
}
