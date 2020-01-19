package projet.data;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class NoteDAO {

    public static List<Note> retrieveByEtudiantId(Etudiant etudiant) {
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        Query q = em.createQuery("SELECT n FROM Note n WHERE n.etudiant = :etudiant");
                q.setParameter("etudiant", etudiant);

        @SuppressWarnings("unchecked")
		List<Note> listNotes = q.getResultList();
        
        return listNotes;
    }

    public static Note create(Etudiant etudiant, Module module, int valeur) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        // create new etudiant
        Note note = new Note();
        note.setValeur(valeur);
        note.setEtudiant(etudiant);
        em.persist(note);

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        return note;
    }
    public static Note update(Note note) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        // Attacher une entité persistante (etudiant) à l’EntityManager courant  pour réaliser la modification
        em.merge(note);

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        return note;
    }
    public static void remove(Note note) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        // Retrouver l'entité persistante et ses liens avec d'autres entités en vue de la suppression
        note = em.find(Note.class, note.getId());
        em.remove(note);

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        // if EclipseLink cache enable -->
        // GestionFactory.factory.getCache().evictAll();
    }

    public static void remove(int id) {

        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        em.getTransaction().begin();

        //
        em.createQuery("DELETE FROM Note AS n WHERE n.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

        // if EclipseLink cache enable -->
        // GestionFactory.factory.getCache().evictAll();
    }
}
