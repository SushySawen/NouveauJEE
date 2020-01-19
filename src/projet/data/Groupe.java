package projet.data;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Entity implementation class for Entity: Groupe
 *
 */
@Entity
public class Groupe implements Serializable {
   
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(unique=true, nullable=false)
	private String nom;
	
	// LAZY = fetch when needed, EAGER = fetch immediately
	@OneToMany(mappedBy="groupe", fetch= FetchType.LAZY)
	private List<Etudiant> etudiants;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<Module> modules;
	
	private static final long serialVersionUID = 1L;

	public Groupe() {
		super();
	}   
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}   
	
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom.toUpperCase();
	}
	
	public List<Etudiant> getEtudiants() {
		return this.etudiants;
	}


	public List<Module> getModules() {
		return modules;
	}

	/*
	 * TODO ajout de modules
	 * public void addModule(Module module) {
		if (!modules.contains(module)) {
			modules.add(module);
			module.addGroupe(this);
		}
	}*/

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Groupe)) return false;
		return id != null && id.equals(((Groupe) o).id);
	}

	@Override
	public int hashCode() {
		return id;
	}
}
