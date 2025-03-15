package com.alexandreburghesi.wattsupenergy.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ApiperiodoassistenciaCriteriaTest {

    @Test
    void newApiperiodoassistenciaCriteriaHasAllFiltersNullTest() {
        var apiperiodoassistenciaCriteria = new ApiperiodoassistenciaCriteria();
        assertThat(apiperiodoassistenciaCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void apiperiodoassistenciaCriteriaFluentMethodsCreatesFiltersTest() {
        var apiperiodoassistenciaCriteria = new ApiperiodoassistenciaCriteria();

        setAllFilters(apiperiodoassistenciaCriteria);

        assertThat(apiperiodoassistenciaCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void apiperiodoassistenciaCriteriaCopyCreatesNullFilterTest() {
        var apiperiodoassistenciaCriteria = new ApiperiodoassistenciaCriteria();
        var copy = apiperiodoassistenciaCriteria.copy();

        assertThat(apiperiodoassistenciaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(apiperiodoassistenciaCriteria)
        );
    }

    @Test
    void apiperiodoassistenciaCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var apiperiodoassistenciaCriteria = new ApiperiodoassistenciaCriteria();
        setAllFilters(apiperiodoassistenciaCriteria);

        var copy = apiperiodoassistenciaCriteria.copy();

        assertThat(apiperiodoassistenciaCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(apiperiodoassistenciaCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var apiperiodoassistenciaCriteria = new ApiperiodoassistenciaCriteria();

        assertThat(apiperiodoassistenciaCriteria).hasToString("ApiperiodoassistenciaCriteria{}");
    }

    private static void setAllFilters(ApiperiodoassistenciaCriteria apiperiodoassistenciaCriteria) {
        apiperiodoassistenciaCriteria.id();
        apiperiodoassistenciaCriteria.chave();
        apiperiodoassistenciaCriteria.distinct();
    }

    private static Condition<ApiperiodoassistenciaCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) && condition.apply(criteria.getChave()) && condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ApiperiodoassistenciaCriteria> copyFiltersAre(
        ApiperiodoassistenciaCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getChave(), copy.getChave()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
