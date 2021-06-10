package ati.dgtic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

//


/**
 * A Agrement. // org.spring
 */
@Entity
@Table(name = "agrement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

public class Agrement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code_agrement", nullable = false)
    private String codeAgrement;

    @OneToMany(mappedBy = "agrement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Entreprise> entreprises = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "agrement_qualification",
               joinColumns = @JoinColumn(name = "agrement_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "qualification_id", referencedColumnName = "id"))
    private Set<Qualification> qualifications = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "agrements", allowSetters = true)
    private Arrete arrete;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeAgrement() {
        return codeAgrement;
    }

    public Agrement codeAgrement(String codeAgrement) {
        this.codeAgrement = codeAgrement;
        return this;
    }

    public void setCodeAgrement(String codeAgrement) {
        this.codeAgrement = codeAgrement;
    }

    public Set<Entreprise> getEntreprises() {
        return entreprises;
    }

    public Agrement entreprises(Set<Entreprise> entreprises) {
        this.entreprises = entreprises;
        return this;
    }

    public Agrement addEntreprise(Entreprise entreprise) {
        this.entreprises.add(entreprise);
        entreprise.setAgrement(this);
        return this;
    }

    public Agrement removeEntreprise(Entreprise entreprise) {
        this.entreprises.remove(entreprise);
        entreprise.setAgrement(null);
        return this;
    }

    public void setEntreprises(Set<Entreprise> entreprises) {
        this.entreprises = entreprises;
    }

    public Set<Qualification> getQualifications() {
        return qualifications;
    }

    public Agrement qualifications(Set<Qualification> qualifications) {
        this.qualifications = qualifications;
        return this;
    }

    public Agrement addQualification(Qualification qualification) {
        this.qualifications.add(qualification);
        qualification.getAgrements().add(this);
        return this;
    }

    public Agrement removeQualification(Qualification qualification) {
        this.qualifications.remove(qualification);
        qualification.getAgrements().remove(this);
        return this;
    }

    public void setQualifications(Set<Qualification> qualifications) {
        this.qualifications = qualifications;
    }

    public Arrete getArrete() {
        return arrete;
    }

    public Agrement arrete(Arrete arrete) {
        this.arrete = arrete;
        return this;
    }

    public void setArrete(Arrete arrete) {
        this.arrete = arrete;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agrement)) {
            return false;
        }
        return id != null && id.equals(((Agrement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agrement{" +
            "id=" + getId() +
            ", codeAgrement='" + getCodeAgrement() + "'" +
            "}";
    }
}
