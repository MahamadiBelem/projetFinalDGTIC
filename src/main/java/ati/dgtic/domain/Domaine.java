package ati.dgtic.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Domaine.
 */
@Entity
@Table(name = "domaine")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Domaine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code_domaine", nullable = false)
    private String codeDomaine;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "domaine")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Categorie> categories = new HashSet<>();

    @OneToMany(mappedBy = "domaine")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Qualification> qualifications = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeDomaine() {
        return codeDomaine;
    }

    public Domaine codeDomaine(String codeDomaine) {
        this.codeDomaine = codeDomaine;
        return this;
    }

    public void setCodeDomaine(String codeDomaine) {
        this.codeDomaine = codeDomaine;
    }

    public String getLibelle() {
        return libelle;
    }

    public Domaine libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Categorie> getCategories() {
        return categories;
    }

    public Domaine categories(Set<Categorie> categories) {
        this.categories = categories;
        return this;
    }

    public Domaine addCategorie(Categorie categorie) {
        this.categories.add(categorie);
        categorie.setDomaine(this);
        return this;
    }

    public Domaine removeCategorie(Categorie categorie) {
        this.categories.remove(categorie);
        categorie.setDomaine(null);
        return this;
    }

    public void setCategories(Set<Categorie> categories) {
        this.categories = categories;
    }

    public Set<Qualification> getQualifications() {
        return qualifications;
    }

    public Domaine qualifications(Set<Qualification> qualifications) {
        this.qualifications = qualifications;
        return this;
    }

    public Domaine addQualification(Qualification qualification) {
        this.qualifications.add(qualification);
        qualification.setDomaine(this);
        return this;
    }

    public Domaine removeQualification(Qualification qualification) {
        this.qualifications.remove(qualification);
        qualification.setDomaine(null);
        return this;
    }

    public void setQualifications(Set<Qualification> qualifications) {
        this.qualifications = qualifications;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Domaine)) {
            return false;
        }
        return id != null && id.equals(((Domaine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Domaine{" +
            "id=" + getId() +
            ", codeDomaine='" + getCodeDomaine() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
