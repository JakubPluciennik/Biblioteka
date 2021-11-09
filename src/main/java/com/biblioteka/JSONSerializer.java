package com.biblioteka;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class JSONSerializer {

    public static void serialize(Library library, String bookPath, String peoplePath) throws IOException {
        // books
        var writer = new BufferedWriter(new FileWriter(bookPath));
        writer.write("[\n");

        for(int i = 0; i < library.bookCount(); i++) {
            var book = library.getKsiazka(i);
            writer.write("\t{\n");
            writer.write(String.format("\t\t\"id\":%d,\n", i));
            writer.write(String.format("\t\t\"title\":\"%s\",\n", book.getTytuł()));
            writer.write(String.format("\t\t\"author\":\"%s\",\n", book.getAutor()));
            writer.write(String.format("\t\t\"publication\":%s,\n", book.getRokWydania().toString()));
            writer.write(String.format("\t\t\"availability\":%d\n", book.getDostępne()));
            writer.write("\t}");
            if(i < library.bookCount() - 1) {
                writer.write(',');
            }
            writer.write('\n');
        }
        writer.write("]");
        writer.close();

        // people
        writer = new BufferedWriter(new FileWriter(peoplePath));
        writer.write("[\n");

        for(int i = 0; i < library.userCount(); i++) {
            var user = library.getUser(i);
            writer.write("\t{\n");
            writer.write(String.format("\t\t\"name\":\"%s\",\n", user.getName()));
            writer.write(String.format("\t\t\"surname\":\"%s\",\n", user.getSurname()));
            writer.write(String.format("\t\t\"login\":\"%s\",\n", user.getLogin()));
            writer.write(String.format("\t\t\"password\":\"%s\",\n", user.getHasło()));
            writer.write("\t\t\"borrowed\":[\n");

            var borrowed = user.getWypozyczoneKsiazki();
            var keys = borrowed.keySet().toArray();
            for (int j = 0; j < keys.length; j++) {
                int id = (int)keys[j];
                Book b = borrowed.get(id);
                writer.write("\t\t\t{\n");
                writer.write(String.format("\t\t\t\t\"id\":%d,\n", id));
                writer.write(String.format("\t\t\t\t\"name\":\"%s\"\n", b.getTytuł()));

                writer.write("\t\t\t}");
                if(j < keys.length - 1) {
                    writer.write(',');
                }
                writer.write('\n');
            }

            writer.write("\t\t]\n");

            writer.write("\t}");
            if(i <= library.bookCount() - 1) {
                writer.write(',');
            }
            writer.write('\n');
        }
        writer.write("]");
        writer.close();
    }
}
