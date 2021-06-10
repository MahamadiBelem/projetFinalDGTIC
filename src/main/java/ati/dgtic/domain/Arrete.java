package ati.dgtic.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Arrete.
 */
@Entity
@Table(name = "arrete")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Arrete implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "intitule_arrete")
    private String intituleArrete;

    @NotNull
    @Column(name = "numero_arrete", nullable = false)
    private String numeroArrete;

    @NotNull
    @Column(name = "date_signature", nullable = false)
    private LocalDate dateSignature;

    @Column(name = "nombre_agrement")
    private Integer nombreAgrement;

    @OneToMany(mappedBy = "arrete")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Agrement> agrements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntituleArrete() {
        return intituleArrete;
    }

    public Arrete intituleArrete(String intituleArrete) {
        this.intituleArrete = intituleArrete;
        return this;
    }

    public void setIntituleArrete(String intituleArrete) {
        this.intituleArrete = intituleArrete;
    }

    public String getNumeroArrete() {
        return numeroArrete;
    }

    public Arrete numeroArrete(String numeroArrete) {
        this.numeroArrete = numeroArrete;
        return this;
    }

    public void setNumeroArrete(String numeroArrete) {
        this.numeroArrete = numeroArrete;
    }

    public LocalDate getDateSignature() {
        return dateSignature;
    }

    public Arrete dateSignature(LocalDate dateSignature) {
        this.dateSignature = dateSignature;
        return this;
    }

    public void setDateSignature(LocalDate dateSignature) {
        this.dateSignature = dateSignature;
    }

    public Integer getNombreAgrement() {
        return nombreAgrement;
    }

    public Arrete nombreAgrement(Integer nombreAgrement) {
        this.nombreAgrement = nombreAgrement;
        return this;
    }

    public void setNombreAgrement(Integer nombreAgrement) {
        this.nombreAgrement = nombreAgrement;
    }

    public Set<Agrement> getAgrements() {
        return agrements;
    }

    public Arrete agrements(Set<Agrement> agrements) {
        this.agrements = agrements;
        return this;
    }

    public Arrete addAgrement(Agrement agrement) {
        this.agrements.add(agrement);
        agrement.setArrete(this);
        return this;
    }

    public Arrete removeAgrement(Agrement agrement) {
        this.agrements.remove(agrement);
        agrement.setArrete(null);
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
        if (!(o instanceof Arrete)) {
            return false;
        }
        return id != null && id.equals(((Arrete) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Arrete{" +
            "id=" + getId() +
            ", intituleArrete='" + getIntituleArrete() + "'" +
            ", numeroArrete='" + getNumeroArrete() + "'" +
            ", dateSignature='" + getDateSignature() + "'" +
            ", nombreAgrement=" + getNombreAgrement() +
            "}";
    }
}
