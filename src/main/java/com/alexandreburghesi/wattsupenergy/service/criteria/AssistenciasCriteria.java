package com.alexandreburghesi.wattsupenergy.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.alexandreburghesi.wattsupenergy.domain.Assistencias} entity. This class is used
 * in {@link com.alexandreburghesi.wattsupenergy.web.rest.AssistenciasResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /assistencias?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssistenciasCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter apiassistenciaId;

    private Boolean distinct;

    public AssistenciasCriteria() {}

    public AssistenciasCriteria(AssistenciasCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.apiassistenciaId = other.optionalApiassistenciaId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AssistenciasCriteria copy() {
        return new AssistenciasCriteria(this);
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

    public LongFilter getApiassistenciaId() {
        return apiassistenciaId;
    }

    public Optional<LongFilter> optionalApiassistenciaId() {
        return Optional.ofNullable(apiassistenciaId);
    }

    public LongFilter apiassistenciaId() {
        if (apiassistenciaId == null) {
            setApiassistenciaId(new LongFilter());
        }
        return apiassistenciaId;
    }

    public void setApiassistenciaId(LongFilter apiassistenciaId) {
        this.apiassistenciaId = apiassistenciaId;
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
        final AssistenciasCriteria that = (AssistenciasCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(apiassistenciaId, that.apiassistenciaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apiassistenciaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssistenciasCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalApiassistenciaId().map(f -> "apiassistenciaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
