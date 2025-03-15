package com.alexandreburghesi.wattsupenergy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Apiassistencia.
 */
@Entity
@Table(name = "apiassistencia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Apiassistencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Parâmetros de filtro para consulta
     */
    @Schema(description = "Parâmetros de filtro para consulta")
    @Lob
    @Column(name = "filtro")
    private String filtro;

    /**
     * Dados atualizados da assistência
     */
    @Schema(description = "Dados atualizados da assistência")
    @Lob
    @Column(name = "dadosassistencia")
    private String dadosassistencia;

    /**
     * Chave identificadora da assistência
     */
    @Schema(description = "Chave identificadora da assistência")
    @Column(name = "chave")
    private String chave;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "apiassistencia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "apiassistencia" }, allowSetters = true)
    private Set<Assistencias> assistencias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Apiassistencia id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFiltro() {
        return this.filtro;
    }

    public Apiassistencia filtro(String filtro) {
        this.setFiltro(filtro);
        return this;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public String getDadosassistencia() {
        return this.dadosassistencia;
    }

    public Apiassistencia dadosassistencia(String dadosassistencia) {
        this.setDadosassistencia(dadosassistencia);
        return this;
    }

    public void setDadosassistencia(String dadosassistencia) {
        this.dadosassistencia = dadosassistencia;
    }

    public String getChave() {
        return this.chave;
    }

    public Apiassistencia chave(String chave) {
        this.setChave(chave);
        return this;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public Set<Assistencias> getAssistencias() {
        return this.assistencias;
    }

    public void setAssistencias(Set<Assistencias> assistencias) {
        if (this.assistencias != null) {
            this.assistencias.forEach(i -> i.setApiassistencia(null));
        }
        if (assistencias != null) {
            assistencias.forEach(i -> i.setApiassistencia(this));
        }
        this.assistencias = assistencias;
    }

    public Apiassistencia assistencias(Set<Assistencias> assistencias) {
        this.setAssistencias(assistencias);
        return this;
    }

    public Apiassistencia addAssistencias(Assistencias assistencias) {
        this.assistencias.add(assistencias);
        assistencias.setApiassistencia(this);
        return this;
    }

    public Apiassistencia removeAssistencias(Assistencias assistencias) {
        this.assistencias.remove(assistencias);
        assistencias.setApiassistencia(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Apiassistencia)) {
            return false;
        }
        return getId() != null && getId().equals(((Apiassistencia) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Apiassistencia{" +
            "id=" + getId() +
            ", filtro='" + getFiltro() + "'" +
            ", dadosassistencia='" + getDadosassistencia() + "'" +
            ", chave='" + getChave() + "'" +
            "}";
    }
}
