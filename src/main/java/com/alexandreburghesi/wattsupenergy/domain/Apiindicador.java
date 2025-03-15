package com.alexandreburghesi.wattsupenergy.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Apiindicador.
 */
@Entity
@Table(name = "apiindicador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Apiindicador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Parâmetros para consulta de indicadores
     */
    @Schema(description = "Parâmetros para consulta de indicadores")
    @Lob
    @Column(name = "filtro")
    private String filtro;

    /**
     * Dados do indicador a ser cadastrado
     */
    @Schema(description = "Dados do indicador a ser cadastrado")
    @Lob
    @Column(name = "dadosindicador")
    private String dadosindicador;

    /**
     * Chave do indicador a ser excluído
     */
    @Schema(description = "Chave do indicador a ser excluído")
    @Column(name = "chave")
    private String chave;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Apiindicador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFiltro() {
        return this.filtro;
    }

    public Apiindicador filtro(String filtro) {
        this.setFiltro(filtro);
        return this;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public String getDadosindicador() {
        return this.dadosindicador;
    }

    public Apiindicador dadosindicador(String dadosindicador) {
        this.setDadosindicador(dadosindicador);
        return this;
    }

    public void setDadosindicador(String dadosindicador) {
        this.dadosindicador = dadosindicador;
    }

    public String getChave() {
        return this.chave;
    }

    public Apiindicador chave(String chave) {
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
        if (!(o instanceof Apiindicador)) {
            return false;
        }
        return getId() != null && getId().equals(((Apiindicador) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Apiindicador{" +
            "id=" + getId() +
            ", filtro='" + getFiltro() + "'" +
            ", dadosindicador='" + getDadosindicador() + "'" +
            ", chave='" + getChave() + "'" +
            "}";
    }
}
