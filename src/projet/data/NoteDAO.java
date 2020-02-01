package projet.data;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

    public static Note retrieveNoteById(int id){
        // Creation de l'entity manager
        EntityManager em = GestionFactory.factory.createEntityManager();

        //
        Note note = em.find(Note.class, id);

        // Close the entity manager
        em.close();

        return note;
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
        note.setModule(module);
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
    
	// Retourne l'ensemble des etudiants
	public static List<Note> getAll() {

		// Creation de l'entity manager
		EntityManager em = GestionFactory.factory.createEntityManager();

		// Recherche
		Query q = em.createQuery("SELECT n FROM Note n");

		@SuppressWarnings("unchecked")
		List<Note> notes = q.getResultList();

		return notes;
	}
	//Va soit creer une note si elle n'existe pas, soit la mette a jour
	public static void merge(Integer etuId, Integer moduleId, Integer noteValue){
        EntityManager em = GestionFactory.factory.createEntityManager();
        em.getTransaction().begin();

        Query q = em.createQuery("SELECT n FROM Note n WHERE n.etudiant.id = :etudiant AND n.module.id = :module");
        q.setParameter("etudiant", etuId);
        q.setParameter("module", moduleId);
        Note note;
        try {
            note = (Note) q.getSingleResult();
        }
        catch (NoResultException e) {
            note = new Note();
            note.setEtudiant(EtudiantDAO.retrieveById(etuId));
            note.setModule(ModuleDAO.retrieveById(moduleId));
        }
        note.setValeur(noteValue);
        em.merge(note);
        // Commit
        em.getTransaction().commit();

        // Close the entity manager
        em.close();

    }
}
