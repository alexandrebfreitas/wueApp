package com.alexandreburghesi.wattsupenergy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Assistencias.
 */
@Entity
@Table(name = "assistencias")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Assistencias implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Lista de assistências encontradas
     */
    @Schema(description = "Lista de assistências encontradas")
    @Lob
    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "assistencias" }, allowSetters = true)
    private Apiassistencia apiassistencia;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Assistencias id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public Assistencias value(String value) {
        this.setValue(value);
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Apiassistencia getApiassistencia() {
        return this.apiassistencia;
    }

    public void setApiassistencia(Apiassistencia apiassistencia) {
        this.apiassistencia = apiassistencia;
    }

    public Assistencias apiassistencia(Apiassistencia apiassistencia) {
        this.setApiassistencia(apiassistencia);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Assistencias)) {
            return false;
        }
        return getId() != null && getId().equals(((Assistencias) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Assistencias{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
