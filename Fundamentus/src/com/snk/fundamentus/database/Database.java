package com.snk.fundamentus.database;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


public class Database<T> {

    EntityManager em;
    EntityManagerFactory emf;
    Class type;

    public Database(Class type) {
        this.type = type;
    }

    public EntityManager getEm() {
        if (null == em) {
            em = getEmf().createEntityManager();
        }
        return em;
    }

    public EntityManagerFactory getEmf() {
        if (null == emf) {
            emf = Persistence.createEntityManagerFactory("$objectdb/fundamentus.odb");
        }

        return emf;
    }

    public List<T> listAllElements() {
        TypedQuery<T> query = getEm().createQuery("SELECT p FROM Empresa p", type);
        return query.getResultList();
    }

    public void add(T obj) {
        getEm().getTransaction().begin();
        em.persist(obj);
        getEm().getTransaction().commit();
    }
}
