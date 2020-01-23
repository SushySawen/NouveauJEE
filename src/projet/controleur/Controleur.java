/**
 * @author hb
 * @author hb
 */

/**
 * @author hb
 *
 */
package projet.controleur;

import projet.data.*;
import projet.data.Module;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("serial")
// on doit changer le contenu a l'interieur d'un template (garder seulement un
// entete et un pieddepage)
// dans init, mettre une fonction qui vérifie si la bdd est vide, et si c'est le
// cas, la remplire avec des notes.
// numéro : 54
// variable de session pour le filtre de groupes ? Quand on clique sur un
// groupe, cliquer ensuite sur consulter les notes etc, ne montre que ce groupe
public class Controleur extends HttpServlet {

    private String urlEtudiants;
    private String urlGroupes;
    private String urlAccueil;
    private String urlNotes;
    private String urlAbsences;
    private String urlConsulterNotes;
    private String urlConsulterAbsences;
    private String urlDetails;

    // INIT
    @Override
    public void init() throws ServletException {
        // Récupération des URLs en paramètre du web.xml
        urlEtudiants = getServletConfig().getInitParameter("urlEtudiants");
        urlGroupes = getServletConfig().getInitParameter("urlGroupes");
        urlAccueil = getServletConfig().getInitParameter("urlAccueil");
        urlNotes = getServletConfig().getInitParameter("urlNotes");
        urlAbsences = getServletConfig().getInitParameter("urlAbsences");
        urlConsulterNotes = getServletConfig().getInitParameter("urlConsulterNotes");
        urlConsulterAbsences = getServletConfig().getInitParameter("urlConsulterAbsences");
        urlDetails = getServletConfig().getInitParameter("urlDetails");

        // Création de la factory permettant la création d'EntityManager
        // (gestion des transactions)
        GestionFactory.open();

        ///// INITIALISATION DE LA BD
        // Normalement l'initialisation se fait directement dans la base de
        ///// données
        if ((GroupeDAO.getAll().size() == 0)
                && (EtudiantDAO.getAll().size() == 0)) {

            // Creation des groupes
            Groupe MIAM = GroupeDAO.create("MIAM");
            Groupe SIMO = GroupeDAO.create("SIMO");
            Groupe MESSI = GroupeDAO.create("MESSI");

            // Creation des étudiants
            List<Etudiant> etudiants = new LinkedList<Etudiant>();

            etudiants.add(EtudiantDAO.create("Francis", "Brunet-Manquat", MIAM,
                    getRandomAbs()));
            etudiants.add(EtudiantDAO.create("Philippe", "Martin", MIAM,
                    getRandomAbs()));
            etudiants.add(EtudiantDAO.create("Mario", "Cortes-Cornax", MIAM,
                    getRandomAbs()));
            etudiants.add(EtudiantDAO.create("Françoise", "Coat", SIMO,
                    getRandomAbs()));
            etudiants.add(EtudiantDAO.create("Laurent", "Bonnaud", MESSI,
                    getRandomAbs()));
            etudiants.add(EtudiantDAO.create("Sébastien", "Bourdon", MESSI,
                    getRandomAbs()));
            etudiants.add(EtudiantDAO.create("Mathieu", "Gatumel", SIMO,
                    getRandomAbs()));

            // Creation des groupes
            Module MI1 = ModuleDAO.create("MI1");
            Module MI4 = ModuleDAO.create("MI4");

            // Lie les groupes aux modules
            MI1.addGroupe(MIAM);
            MI4.addGroupe(MIAM);
            MI1.addGroupe(SIMO);

            // Ajoute les modules en BDD
            ModuleDAO.update(MI1);
            ModuleDAO.update(MI4);

            // On ajoute des notes pour chaque étudiants et pour toutes les
            // matières
            for (Etudiant etudiant : etudiants) {
                List<Module> modules = etudiant.getGroupe().getModules();
                for (Module module : modules) {
                    NoteDAO.create(etudiant, module,
                            (int) Math.round(Math.random() * 20));
                }
            }

        }
    }

    private int getRandomAbs() {
        return (int) (Math.random() * 8);
    }

    // Initialisation de la liste des notes si la BDD de notes est vide
    /*
     * private static final HashMap<Integer, Integer>
     * intializelistEtudiantNotes() {
     *
     *
     * // Création du hasmap (association clé/valeur) // Association etudiant id
     * -> notes HashMap<Integer, Integer> listEtudiantNotesTemp = new
     * HashMap<>();
     *
     * // Les notes sont générées aléatoirement Random rand = new Random(); for
     * (Etudiant etudiant : LISTE_ID_ETUDIANTS.values()) {
     * listEtudiantNotesTemp.put(etudiant.getId(), rand.nextInt(20)); }
     *
     * //On retourne la liste d'étudiant return listEtudiantNotesTemp; }
     */

    @Override
    public void destroy() {
        super.destroy();

        // Fermeture de la factory
        GestionFactory.close();
    }

    // POST
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // on passe la main au GET
        doGet(request, response);
    }

    // GET
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // On récupère le path
        String action = request.getPathInfo();
        if (action == null) {
            action = "/etudiants";
        }

        // Log action
        System.out.println("PROJET JPA : action = " + action);

        // Exécution action
        if (action.equals("/etudiants")) {
            doEtudiants(request, response);
        } else if (action.equals("/groupes")) {
            doGroupes(request, response);
        } else if (action.equals("/accueil")) {
            doAccueil(request, response);
        } else if (action.equals("/notes")) {
            doNotes(request, response);
        } else if (action.equals("/absences")) {
            doAbsences(request, response);
        } else if (action.equals("/consulterNotes")) {
            doConsulterNotes(request, response);
        } else if (action.equals("/consulterAbsences")) {
            doConsulterAbsences(request, response);
        } else if (action.equals("/details")) {
            doDetails(request, response);
        } else {
            // Autres cas
            doEtudiants(request, response);
        }
    }



    // ///////////////////////
    //
    private void doConsulterNotes(HttpServletRequest request,
                                  HttpServletResponse response) throws ServletException, IOException {
        Collection<Etudiant> etudiants = EtudiantDAO.getAll();

        // Mettre l'objet jeu en attribut de requête
        request.setAttribute("etudiants", etudiants);

        // Chargement de la JSP consulter notes
        loadJSP(urlConsulterNotes, request, response);
    }

    private void doDetails(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        Etudiant etu = EtudiantDAO
                .retrieveById(Integer.valueOf(request.getParameter("id")));
        request.setAttribute("etudiants", etu);

        // Chargement de la JSP de détail d'un étudient
        loadJSP(urlDetails, request, response);
    }

    private void doConsulterAbsences(HttpServletRequest request,
                                     HttpServletResponse response) throws ServletException, IOException {

        // Mettre l'objet jeu en attribut de requête
        request.setAttribute("etudiants", EtudiantDAO.getAll());

        //
        loadJSP(urlConsulterAbsences, request, response);
    }

    private void doAccueil(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {

        // Chargement de la JSP d'acceuil
        loadJSP(urlAccueil, request, response);
    }

    private void doEtudiants(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException {

        // Récupérer les étudiants
        List<Etudiant> etudiants = EtudiantDAO.getAll();

        // Ajouter les étudiants à la requête pour affichage
        request.setAttribute("etudiants", etudiants);

        // Chargement de la JSP consulter étudiants
        loadJSP(urlEtudiants, request, response);
    }

    private void doNotes(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        Collection<Etudiant> etudiants = EtudiantDAO.getAll();

        // Mettre l'objet jeu en attribut de requête
        request.setAttribute("etudiants", etudiants);

        // Chargement de la JSP de consultation des notes
        loadJSP(urlNotes, request, response);
    }

    // va permettre de modifier les absences
    private void doAbsences(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {

        Collection<Etudiant> etudiants = EtudiantDAO.getAll();
        request.setAttribute("etudiants", etudiants);

        // Chargement de la JSP de consultation des absences
        loadJSP(urlAbsences, request, response);
    }

    // ///////////////////////
    //
    private void doGroupes(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {

        // Récupérer les étudiants
        List<Groupe> groupes = GroupeDAO.getAll();

        // Ajouter les étudiants à la requête pour affichage
        request.setAttribute("groupes", groupes);

        // Chargement de la JSP de consultation des groupes
        loadJSP(urlGroupes, request, response);
    }

    /**
     * Charge la JSP indiquée en paramètre
     *
     * @param url
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void loadJSP(String url, HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {

        // L'interface RequestDispatcher permet de transférer le contrôle à une
        // autre servlet
        // Deux méthodes possibles :
        // - forward() : donne le contrôle à une autre servlet. Annule le flux
        // de sortie de la servlet courante
        // - include() : inclus dynamiquement une autre servlet
        // + le contrôle est donné à une autre servlet puis revient à la servlet
        // courante (sorte d'appel de fonction).
        // + Le flux de sortie n'est pas supprimé et les deux se cumulent

        ServletContext sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(url);
        rd.forward(request, response);
    }

}
