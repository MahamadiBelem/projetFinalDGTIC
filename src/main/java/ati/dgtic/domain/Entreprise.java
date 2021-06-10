package ati.dgtic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Entreprise.
 */
@Entity
@Table(name = "entreprise")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Entreprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "sigle_entreprise", nullable = false)
    private String sigleEntreprise;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "nom_entreprise", nullable = false)
    private String nomEntreprise;

    @Column(name = "numero_rccm")
    private String numeroRCCM;

    @Column(name = "numero_ifu")
    private String numeroIFU;

    @Column(name = "ville")
    private String ville;

    @Column(name = "localisation")
    private String localisation;

    @Column(name = "telephone_1")
    private String telephone1;

    @Column(name = "telephone_2")
    private String telephone2;

    @ManyToOne
    @JsonIgnoreProperties(value = "entreprises", allowSetters = true)
    private Agrement agrement;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSigleEntreprise() {
        return sigleEntreprise;
    }

    public Entreprise sigleEntreprise(String sigleEntreprise) {
        this.sigleEntreprise = sigleEntreprise;
        return this;
    }

    public void setSigleEntreprise(String sigleEntreprise) {
        this.sigleEntreprise = sigleEntreprise;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public Entreprise nomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
        return this;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getNumeroRCCM() {
        return numeroRCCM;
    }

    public Entreprise numeroRCCM(String numeroRCCM) {
        this.numeroRCCM = numeroRCCM;
        return this;
    }

    public void setNumeroRCCM(String numeroRCCM) {
        this.numeroRCCM = numeroRCCM;
    }

    public String getNumeroIFU() {
        return numeroIFU;
    }

    public Entreprise numeroIFU(String numeroIFU) {
        this.numeroIFU = numeroIFU;
        return this;
    }

    public void setNumeroIFU(String numeroIFU) {
        this.numeroIFU = numeroIFU;
    }

    public String getVille() {
        return ville;
    }

    public Entreprise ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getLocalisation() {
        return localisation;
    }

    public Entreprise localisation(String localisation) {
        this.localisation = localisation;
        return this;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getTelephone1() {
        return telephone1;
    }

    public Entreprise telephone1(String telephone1) {
        this.telephone1 = telephone1;
        return this;
    }

    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public Entreprise telephone2(String telephone2) {
        this.telephone2 = telephone2;
        return this;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public Agrement getAgrement() {
        return agrement;
    }

    public Entreprise agrement(Agrement agrement) {
        this.agrement = agrement;
        return this;
    }

    public void setAgrement(Agrement agrement) {
        this.agrement = agrement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entreprise)) {
            return false;
        }
        return id != null && id.equals(((Entreprise) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entreprise{" +
            "id=" + getId() +
            ", sigleEntreprise='" + getSigleEntreprise() + "'" +
            ", nomEntreprise='" + getNomEntreprise() + "'" +
            ", numeroRCCM='" + getNumeroRCCM() + "'" +
            ", numeroIFU='" + getNumeroIFU() + "'" +
            ", ville='" + getVille() + "'" +
            ", localisation='" + getLocalisation() + "'" +
            ", telephone1='" + getTelephone1() + "'" +
            ", telephone2='" + getTelephone2() + "'" +
            "}";
    }
}
