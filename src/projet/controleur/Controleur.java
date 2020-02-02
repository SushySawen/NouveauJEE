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
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("serial")
// on doit changer le contenu a l'interieur d'un template (garder seulement un
// entete et un pieddepage)
// numéro : 54
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
		urlConsulterNotes = getServletConfig()
				.getInitParameter("urlConsulterNotes");
		urlConsulterAbsences = getServletConfig()
				.getInitParameter("urlConsulterAbsences");
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
/*			for (Etudiant etudiant : etudiants) {
				List<Module> modules = etudiant.getGroupe().getModules();
				for (Module module : modules) {
					NoteDAO.create(etudiant, module,
							(int) Math.round(Math.random() * 20));
				}
			}*/
		}
	}

	private int getRandomAbs() {
		return (int) (Math.random() * 8);
	}


	@Override
	public void destroy() {
		super.destroy();

		// Fermeture de la factory
		GestionFactory.close();
	}

	// POST
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

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
		boutonFiltre(request);

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
	//Consulter les notes des etudiants
	private void doConsulterNotes(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// Recupération des étudiants et des modules
		initNotesParEtu(request, response);

		// Chargement de la JSP consulter notes
		loadJSP(urlConsulterNotes, request, response);
	}
	//Modifier les notes des etudiants
	private void doNotes(HttpServletRequest request,
						 HttpServletResponse response) throws ServletException, IOException {

		//Changement de la valeur des notes
		if (request.getParameter("valeur")!=null){
			String valeurs[] = request.getParameterValues("valeur");
			String etuId[] = request.getParameterValues("etuId");
			String moduId[] = request.getParameterValues("modId");


			int i = 0;
			while (i<valeurs.length){
				String value = valeurs[i];
				if (value != null && !value.isEmpty()) {
					NoteDAO.merge(Integer.valueOf(etuId[i]), Integer.valueOf(moduId[i]), Integer.valueOf(value));
				}
				i++;
			}
		}

		// Récupération des notes
		initNotesParEtu(request, response);
		// Chargement de la JSP de consultation des notes
		loadJSP(urlNotes, request, response);
	}
	//Initialiser les notes des etudiants par modules
	private void initNotesParEtu(HttpServletRequest request,
								 HttpServletResponse response){
		Map<Integer, Map<Integer, Note>> notesParEtuModule = new HashMap<>();
		List<Module> modules;
		List <Note> notes;//
		List <Etudiant> etudiants;
		//Récupération de la variable de session : un filtre sur les groupes d'étudiant.
		Integer groupeID = (Integer) request.getSession().getAttribute("groupe");
		//Si la variable de groupe est != null, on va récupérer les étudiants associés a ce groupe
		if (groupeID != null) {
			Groupe groupe = GroupeDAO.findGroupe(groupeID);
			etudiants = groupe.getEtudiants();
			modules = groupe.getModules();
			notes = new ArrayList<>();
			for (Etudiant etudiant : etudiants){
				for (Note note : etudiant.getNotes()){
					notes.add(note);
				}
			}
			//s'il n'y a pas de variable de session de groupe, on récupère tous les étudiants
		} else {
			modules = ModuleDAO.getAll();
			etudiants = EtudiantDAO.getAll();
			notes = NoteDAO.getAll();
		}
		//On récupère les notes d'un étudiant pour tous les modules auquel il participe
		for (Note note : notes) {
			Map<Integer, Note> noteEtu = notesParEtuModule.get(note.getEtudiant().getId());
			if (noteEtu == null) {
				noteEtu = new HashMap<>();
				notesParEtuModule.put(note.getEtudiant().getId(), noteEtu);
			}
			noteEtu.put(note.getModule().getId(), note);
		}


		// Mettre l'objet jeu en attribut de requête
		request.setAttribute("notesParEtuModule", notesParEtuModule);
		request.setAttribute("modules",modules);
		request.setAttribute("etudiants",etudiants);
	}

	// ///////////////////////
	//
	private void doDetails(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Etudiant etu = EtudiantDAO
				.retrieveById(Integer.valueOf(request.getParameter("id")));
		request.setAttribute("etudiant", etu);

		// Chargement de la JSP de détail d'un étudiant
		loadJSP(urlDetails, request, response);
	}
	// ///////////////////////
	//
	private void doConsulterAbsences(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		initAbsences(request);

		//
		loadJSP(urlConsulterAbsences, request, response);
	}
	// va permettre de modifier les absences
	private void doAbsences(HttpServletRequest request,
							HttpServletResponse response) throws ServletException, IOException {
		//On récupère les param de la jsp : les absences et l'id de l'étudiant
		if (request.getParameter("etudiantAbsence")!=null && request.getParameter(("etuId"))!=null){
			String ids[] = request.getParameterValues("etuId");
			String valeurs[] = request.getParameterValues("etudiantAbsence");
		//
			int i = 0;
			for (String id : ids){
				Etudiant etudiant = EtudiantDAO.retrieveById(Integer.valueOf(id));
				etudiant.setNbAbsences(Integer.valueOf(valeurs[i]));
				EtudiantDAO.update(etudiant);
				i++;
			}
		}
		initAbsences(request);

		// Chargement de la JSP de consultation des absences
		loadJSP(urlAbsences, request, response);
	}

	private void initAbsences(HttpServletRequest request) {
		List <Etudiant> etudiants;
		Integer groupeID = (Integer) request.getSession().getAttribute("groupe");
		if (groupeID != null) {
			Groupe groupe = GroupeDAO.findGroupe(groupeID);
			etudiants = groupe.getEtudiants();
		} else {
			etudiants = EtudiantDAO.getAll();
		}
		request.setAttribute("etudiants", etudiants);
	}

	// ///////////////////////
	//
	private void doAccueil(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Chargement de la JSP d'acceuil
		loadJSP(urlAccueil, request, response);
	}
	// ///////////////////////
	//
	private void doEtudiants(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Récupérer les étudiants
		List<Etudiant> etudiants = EtudiantDAO.getAll();

		// Ajouter les étudiants à la requête pour affichage
		request.setAttribute("etudiants", etudiants);

		// Chargement de la JSP consulter étudiants
		loadJSP(urlEtudiants, request, response);
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
	//Renvoie un objet vide ou avec le filtre qui a été choisi a la jsp du filtre
	public static void boutonFiltre(HttpServletRequest request){

		//Gestion de la session
		String ajouterFiltre = request.getParameter("ajouterFiltre");
		String supprimerFiltre = request.getParameter("supprimerFiltre");
		if(ajouterFiltre != null){
			request.getSession().setAttribute("groupe",
					Integer.valueOf(ajouterFiltre));
		} else if (supprimerFiltre != null){
			request.getSession().setAttribute("groupe",null);
		}
	}
}
