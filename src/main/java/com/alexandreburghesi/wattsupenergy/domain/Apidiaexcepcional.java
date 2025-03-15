package com.alexandreburghesi.wattsupenergy.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Apidiaexcepcional.
 */
@Entity
@Table(name = "apidiaexcepcional")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Apidiaexcepcional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Dados do dia excepcional
     */
    @Schema(description = "Dados do dia excepcional")
    @Lob
    @Column(name = "dadosdiaexcepcional")
    private String dadosdiaexcepcional;

    /**
     * Chave do dia excepcional a ser excluído
     */
    @Schema(description = "Chave do dia excepcional a ser excluído")
    @Column(name = "chave")
    private String chave;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Apidiaexcepcional id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDadosdiaexcepcional() {
        return this.dadosdiaexcepcional;
    }

    public Apidiaexcepcional dadosdiaexcepcional(String dadosdiaexcepcional) {
        this.setDadosdiaexcepcional(dadosdiaexcepcional);
        return this;
    }

    public void setDadosdiaexcepcional(String dadosdiaexcepcional) {
        this.dadosdiaexcepcional = dadosdiaexcepcional;
    }

    public String getChave() {
        return this.chave;
    }

    public Apidiaexcepcional chave(String chave) {
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
        if (!(o instanceof Apidiaexcepcional)) {
            return false;
        }
        return getId() != null && getId().equals(((Apidiaexcepcional) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Apidiaexcepcional{" +
            "id=" + getId() +
            ", dadosdiaexcepcional='" + getDadosdiaexcepcional() + "'" +
            ", chave='" + getChave() + "'" +
            "}";
    }
}
