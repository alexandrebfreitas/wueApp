package com.alexandreburghesi.wattsupenergy.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Autenticacao.
 */
@Entity
@Table(name = "autenticacao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Autenticacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Usuário para autenticação
     */
    @Schema(description = "Usuário para autenticação")
    @Column(name = "usuario")
    private String usuario;

    /**
     * Senha do usuário
     */
    @Schema(description = "Senha do usuário")
    @Column(name = "senha")
    private String senha;

    /**
     * Token de acesso
     */
    @Schema(description = "Token de acesso")
    @Column(name = "access_token")
    private String accessToken;

    /**
     * Tipo do token
     */
    @Schema(description = "Tipo do token")
    @Column(name = "token_type")
    private String tokenType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Autenticacao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public Autenticacao usuario(String usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return this.senha;
    }

    public Autenticacao senha(String senha) {
        this.setSenha(senha);
        return this;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public Autenticacao accessToken(String accessToken) {
        this.setAccessToken(accessToken);
        return this;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public Autenticacao tokenType(String tokenType) {
        this.setTokenType(tokenType);
        return this;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Autenticacao)) {
            return false;
        }
        return getId() != null && getId().equals(((Autenticacao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Autenticacao{" +
            "id=" + getId() +
            ", usuario='" + getUsuario() + "'" +
            ", senha='" + getSenha() + "'" +
            ", accessToken='" + getAccessToken() + "'" +
            ", tokenType='" + getTokenType() + "'" +
            "}";
    }
}
