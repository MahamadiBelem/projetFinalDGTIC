package ati.dgtic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * A Qualification.
 */
@Entity
@Table(name = "qualification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Qualification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "service_concerne", nullable = false)
    private String serviceConcerne;

    @ManyToOne
    @JsonIgnoreProperties(value = "qualifications", allowSetters = true)
    private Domaine domaine;

    @ManyToOne
    @JsonIgnoreProperties(value = "qualifications", allowSetters = true)
    private Categorie categorie;

    @ManyToMany(mappedBy = "qualifications")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Agrement> agrements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceConcerne() {
        return serviceConcerne;
    }

    public Qualification serviceConcerne(String serviceConcerne) {
        this.serviceConcerne = serviceConcerne;
        return this;
    }

    public void setServiceConcerne(String serviceConcerne) {
        this.serviceConcerne = serviceConcerne;
    }

    public Domaine getDomaine() {
        return domaine;
    }

    public Qualification domaine(Domaine domaine) {
        this.domaine = domaine;
        return this;
    }

    public void setDomaine(Domaine domaine) {
        this.domaine = domaine;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public Qualification categorie(Categorie categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Set<Agrement> getAgrements() {
        return agrements;
    }

    public Qualification agrements(Set<Agrement> agrements) {
        this.agrements = agrements;
        return this;
    }

    public Qualification addAgrement(Agrement agrement) {
        this.agrements.add(agrement);
        agrement.getQualifications().add(this);
        return this;
    }

    public Qualification removeAgrement(Agrement agrement) {
        this.agrements.remove(agrement);
        agrement.getQualifications().remove(this);
        return this;
    }

    public void setAgrements(Set<Agrement> agrements) {
        this.agrements = agrements;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Qualification)) {
            return false;
        }
        return id != null && id.equals(((Qualification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Qualification{" +
            "id=" + getId() +
            ", serviceConcerne='" + getServiceConcerne() + "'" +
            "}";
    }
}
