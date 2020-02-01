package projet.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Module implements Serializable {

    /**
	 * Default UID for serializable class
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    private Integer id;

    @Column(unique=true, nullable=false)
    private String nom;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "module_groupe",
    joinColumns = @JoinColumn(name = "module_id"),
    inverseJoinColumns = @JoinColumn(name = "groupe_id")
    )
    private List<Groupe> groupes = new ArrayList<>();



    @OneToMany(mappedBy = "module")
    private List<Note> notes = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setGroupes(List<Groupe> groupes) {
		this.groupes = groupes;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Groupe> getGroupes() {
        return groupes;
    }

    public void addGroupe(Groupe groupe) {
        groupes.add(groupe);
        groupe.getModules().add(this);
    }
    
    public void addGroupes(Groupe... groupes) {
        for (Groupe groupe : groupes) {
			this.groupes.add(groupe);
			groupe.getModules().add(this);
		}
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Module)) return false;
        return id != null && id.equals(((Module) o).id);
    }

    @Override
    public int hashCode() {
        return id;
    }
}