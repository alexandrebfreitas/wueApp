package com.alexandreburghesi.wattsupenergy.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.alexandreburghesi.wattsupenergy.domain.Apiperiodoassistencia} entity. This class is used
 * in {@link com.alexandreburghesi.wattsupenergy.web.rest.ApiperiodoassistenciaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /apiperiodoassistencias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApiperiodoassistenciaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter chave;

    private Boolean distinct;

    public ApiperiodoassistenciaCriteria() {}

    public ApiperiodoassistenciaCriteria(ApiperiodoassistenciaCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.chave = other.optionalChave().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ApiperiodoassistenciaCriteria copy() {
        return new ApiperiodoassistenciaCriteria(this);
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

    public StringFilter getChave() {
        return chave;
    }

    public Optional<StringFilter> optionalChave() {
        return Optional.ofNullable(chave);
    }

    public StringFilter chave() {
        if (chave == null) {
            setChave(new StringFilter());
        }
        return chave;
    }

    public void setChave(StringFilter chave) {
        this.chave = chave;
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
        final ApiperiodoassistenciaCriteria that = (ApiperiodoassistenciaCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(chave, that.chave) && Objects.equals(distinct, that.distinct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chave, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApiperiodoassistenciaCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalChave().map(f -> "chave=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
