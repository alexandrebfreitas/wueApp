package com.alexandreburghesi.wattsupenergy.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Apiperiodoassistencia.
 */
@Entity
@Table(name = "apiperiodoassistencia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Apiperiodoassistencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Dados do período a ser cadastrado
     */
    @Schema(description = "Dados do período a ser cadastrado")
    @Lob
    @Column(name = "dadosperiodo")
    private String dadosperiodo;

    /**
     * Chave do período a ser excluído
     */
    @Schema(description = "Chave do período a ser excluído")
    @Column(name = "chave")
    private String chave;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Apiperiodoassistencia id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDadosperiodo() {
        return this.dadosperiodo;
    }

    public Apiperiodoassistencia dadosperiodo(String dadosperiodo) {
        this.setDadosperiodo(dadosperiodo);
        return this;
    }

    public void setDadosperiodo(String dadosperiodo) {
        this.dadosperiodo = dadosperiodo;
    }

    public String getChave() {
        return this.chave;
    }

    public Apiperiodoassistencia chave(String chave) {
        this.setChave(chave);
        return this;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Apiperiodoassistencia)) {
            return false;
        }
        return getId() != null && getId().equals(((Apiperiodoassistencia) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Apiperiodoassistencia{" +
            "id=" + getId() +
            ", dadosperiodo='" + getDadosperiodo() + "'" +
            ", chave='" + getChave() + "'" +
            "}";
    }
}
