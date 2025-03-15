package com.alexandreburghesi.wattsupenergy.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.alexandreburghesi.wattsupenergy.domain.Autenticacao} entity. This class is used
 * in {@link com.alexandreburghesi.wattsupenergy.web.rest.AutenticacaoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /autenticacaos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AutenticacaoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter usuario;

    private StringFilter senha;

    private StringFilter accessToken;

    private StringFilter tokenType;

    private Boolean distinct;

    public AutenticacaoCriteria() {}

    public AutenticacaoCriteria(AutenticacaoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.usuario = other.optionalUsuario().map(StringFilter::copy).orElse(null);
        this.senha = other.optionalSenha().map(StringFilter::copy).orElse(null);
        this.accessToken = other.optionalAccessToken().map(StringFilter::copy).orElse(null);
        this.tokenType = other.optionalTokenType().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AutenticacaoCriteria copy() {
        return new AutenticacaoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUsuario() {
        return usuario;
    }

    public Optional<StringFilter> optionalUsuario() {
        return Optional.ofNullable(usuario);
    }

    public StringFilter usuario() {
        if (usuario == null) {
            setUsuario(new StringFilter());
        }
        return usuario;
    }

    public void setUsuario(StringFilter usuario) {
        this.usuario = usuario;
    }

    public StringFilter getSenha() {
        return senha;
    }

    public Optional<StringFilter> optionalSenha() {
        return Optional.ofNullable(senha);
    }

    public StringFilter senha() {
        if (senha == null) {
            setSenha(new StringFilter());
        }
        return senha;
    }

    public void setSenha(StringFilter senha) {
        this.senha = senha;
    }

    public StringFilter getAccessToken() {
        return accessToken;
    }

    public Optional<StringFilter> optionalAccessToken() {
        return Optional.ofNullable(accessToken);
    }

    public StringFilter accessToken() {
        if (accessToken == null) {
            setAccessToken(new StringFilter());
        }
        return accessToken;
    }

    public void setAccessToken(StringFilter accessToken) {
        this.accessToken = accessToken;
    }

    public StringFilter getTokenType() {
        return tokenType;
    }

    public Optional<StringFilter> optionalTokenType() {
        return Optional.ofNullable(tokenType);
    }

    public StringFilter tokenType() {
        if (tokenType == null) {
            setTokenType(new StringFilter());
        }
        return tokenType;
    }

    public void setTokenType(StringFilter tokenType) {
        this.tokenType = tokenType;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AutenticacaoCriteria that = (AutenticacaoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(usuario, that.usuario) &&
            Objects.equals(senha, that.senha) &&
            Objects.equals(accessToken, that.accessToken) &&
            Objects.equals(tokenType, that.tokenType) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuario, senha, accessToken, tokenType, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AutenticacaoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalUsuario().map(f -> "usuario=" + f + ", ").orElse("") +
            optionalSenha().map(f -> "senha=" + f + ", ").orElse("") +
            optionalAccessToken().map(f -> "accessToken=" + f + ", ").orElse("") +
            optionalTokenType().map(f -> "tokenType=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
