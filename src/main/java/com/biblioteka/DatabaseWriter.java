package com.biblioteka;

import java.time.Year;

public class DatabaseWriter {
    public static void saveBooks(Library library) {
        BookRepository bookRepo = new BookRepository();
        for (int i = 0; i < library.bookCount(); i++) {
            Book book = library.getKsiazka(i);
            bookRepo.createNewBook(book.getTytuł(), book.getAutor(), book.getRokWydania(), book.getDostępne());
        }
    }

    // bazy danych są zapisywane tylko jeśli użytkownik poprawnie wyjdzie z aplikacji (przyciskiem 'Wyjdź')
    public static void saveInDatabase(Library library) {
        saveBooks(library);
        // savePeople(library) ... - to do
    }
}
