package ati.dgtic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Categorie.
 */
@Entity
@Table(name = "categorie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code_categorie", nullable = false)
    private String codeCategorie;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "categorie")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Qualification> qualifications = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "categories", allowSetters = true)
    private Domaine domaine;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeCategorie() {
        return codeCategorie;
    }

    public Categorie codeCategorie(String codeCategorie) {
        this.codeCategorie = codeCategorie;
        return this;
    }

    public void setCodeCategorie(String codeCategorie) {
        this.codeCategorie = codeCategorie;
    }

    public String getLibelle() {
        return libelle;
    }

    public Categorie libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Qualification> getQualifications() {
        return qualifications;
    }

    public Categorie qualifications(Set<Qualification> qualifications) {
        this.qualifications = qualifications;
        return this;
    }

    public Categorie addQualification(Qualification qualification) {
        this.qualifications.add(qualification);
        qualification.setCategorie(this);
        return this;
    }

    public Categorie removeQualification(Qualification qualification) {
        this.qualifications.remove(qualification);
        qualification.setCategorie(null);
        return this;
    }

    public void setQualifications(Set<Qualification> qualifications) {
        this.qualifications = qualifications;
    }

    public Domaine getDomaine() {
        return domaine;
    }

    public Categorie domaine(Domaine domaine) {
        this.domaine = domaine;
        return this;
    }

    public void setDomaine(Domaine domaine) {
        this.domaine = domaine;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categorie)) {
            return false;
        }
        return id != null && id.equals(((Categorie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Categorie{" +
            "id=" + getId() +
            ", codeCategorie='" + getCodeCategorie() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
