package com.alexandreburghesi.wattsupenergy.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AssistenciasCriteriaTest {

    @Test
    void newAssistenciasCriteriaHasAllFiltersNullTest() {
        var assistenciasCriteria = new AssistenciasCriteria();
        assertThat(assistenciasCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void assistenciasCriteriaFluentMethodsCreatesFiltersTest() {
        var assistenciasCriteria = new AssistenciasCriteria();

        setAllFilters(assistenciasCriteria);

        assertThat(assistenciasCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void assistenciasCriteriaCopyCreatesNullFilterTest() {
        var assistenciasCriteria = new AssistenciasCriteria();
        var copy = assistenciasCriteria.copy();

        assertThat(assistenciasCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(assistenciasCriteria)
        );
    }

    @Test
    void assistenciasCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var assistenciasCriteria = new AssistenciasCriteria();
        setAllFilters(assistenciasCriteria);

        var copy = assistenciasCriteria.copy();

        assertThat(assistenciasCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(assistenciasCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var assistenciasCriteria = new AssistenciasCriteria();

        assertThat(assistenciasCriteria).hasToString("AssistenciasCriteria{}");
    }

    private static void setAllFilters(AssistenciasCriteria assistenciasCriteria) {
        assistenciasCriteria.id();
        assistenciasCriteria.apiassistenciaId();
        assistenciasCriteria.distinct();
    }

    private static Condition<AssistenciasCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getApiassistenciaId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AssistenciasCriteria> copyFiltersAre(
        AssistenciasCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getApiassistenciaId(), copy.getApiassistenciaId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
