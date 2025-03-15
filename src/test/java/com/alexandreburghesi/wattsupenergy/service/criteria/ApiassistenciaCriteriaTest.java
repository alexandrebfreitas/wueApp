package com.alexandreburghesi.wattsupenergy.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ApiassistenciaCriteriaTest {

    @Test
    void newApiassistenciaCriteriaHasAllFiltersNullTest() {
        var apiassistenciaCriteria = new ApiassistenciaCriteria();
        assertThat(apiassistenciaCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void apiassistenciaCriteriaFluentMethodsCreatesFiltersTest() {
        var apiassistenciaCriteria = new ApiassistenciaCriteria();

        setAllFilters(apiassistenciaCriteria);

        assertThat(apiassistenciaCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void apiassistenciaCriteriaCopyCreatesNullFilterTest() {
        var apiassistenciaCriteria = new ApiassistenciaCriteria();
        var copy = apiassistenciaCriteria.copy();

        assertThat(apiassistenciaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(apiassistenciaCriteria)
        );
    }

    @Test
    void apiassistenciaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var apiassistenciaCriteria = new ApiassistenciaCriteria();
        setAllFilters(apiassistenciaCriteria);

        var copy = apiassistenciaCriteria.copy();

        assertThat(apiassistenciaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(apiassistenciaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var apiassistenciaCriteria = new ApiassistenciaCriteria();

        assertThat(apiassistenciaCriteria).hasToString("ApiassistenciaCriteria{}");
    }

    private static void setAllFilters(ApiassistenciaCriteria apiassistenciaCriteria) {
        apiassistenciaCriteria.id();
        apiassistenciaCriteria.chave();
        apiassistenciaCriteria.assistenciasId();
        apiassistenciaCriteria.distinct();
    }

    private static Condition<ApiassistenciaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getChave()) &&
                condition.apply(criteria.getAssistenciasId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ApiassistenciaCriteria> copyFiltersAre(
        ApiassistenciaCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getChave(), copy.getChave()) &&
                condition.apply(criteria.getAssistenciasId(), copy.getAssistenciasId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
