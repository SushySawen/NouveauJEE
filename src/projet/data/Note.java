package projet.data;

import javax.persistence.*;

@Entity
public class Note {
	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	Etudiant etudiant;

	@ManyToOne(fetch = FetchType.LAZY)
	Module module;

	@Column(nullable = false)
	private int valeur;

	public Note() {
	}

	public Note(Etudiant etudiant, Module module, int note) {
		this.etudiant = etudiant;
		this.module = module;
		this.valeur = note;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
}
