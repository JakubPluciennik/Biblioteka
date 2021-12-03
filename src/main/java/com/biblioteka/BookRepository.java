package com.biblioteka;

import java.time.Year;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class BookRepository {
  private static EntityManagerFactory factory =
      Persistence.createEntityManagerFactory("thePersistenceUnit");
  private static EntityManager entityManager = factory.createEntityManager();

  public void createNewBook(String tytuł, String autor, Year rokWydania, int dostępne) {
    System.out.println(" ------------ CREATE ------------ ");
    Book newOne = new Book(tytuł, autor, rokWydania.getValue(),
        dostępne);  // rok wydania zamieniony na Integer
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    System.out.println(" ------ Persisting in new transaction ------ ");
    entityManager.persist(newOne);
    System.out.println("ID of a new book: " + newOne.getId());
    System.out.println(" ------ Closing transaction ------ ");
    transaction.commit();
  }
}
