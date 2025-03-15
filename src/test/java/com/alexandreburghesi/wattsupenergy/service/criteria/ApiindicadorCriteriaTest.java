package com.alexandreburghesi.wattsupenergy.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ApiindicadorCriteriaTest {

    @Test
    void newApiindicadorCriteriaHasAllFiltersNullTest() {
        var apiindicadorCriteria = new ApiindicadorCriteria();
        assertThat(apiindicadorCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void apiindicadorCriteriaFluentMethodsCreatesFiltersTest() {
        var apiindicadorCriteria = new ApiindicadorCriteria();

        setAllFilters(apiindicadorCriteria);

        assertThat(apiindicadorCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void apiindicadorCriteriaCopyCreatesNullFilterTest() {
        var apiindicadorCriteria = new ApiindicadorCriteria();
        var copy = apiindicadorCriteria.copy();

        assertThat(apiindicadorCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(apiindicadorCriteria)
        );
    }

    @Test
    void apiindicadorCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var apiindicadorCriteria = new ApiindicadorCriteria();
        setAllFilters(apiindicadorCriteria);

        var copy = apiindicadorCriteria.copy();

        assertThat(apiindicadorCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(apiindicadorCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var apiindicadorCriteria = new ApiindicadorCriteria();

        assertThat(apiindicadorCriteria).hasToString("ApiindicadorCriteria{}");
    }

    private static void setAllFilters(ApiindicadorCriteria apiindicadorCriteria) {
        apiindicadorCriteria.id();
        apiindicadorCriteria.chave();
        apiindicadorCriteria.distinct();
    }

    private static Condition<ApiindicadorCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) && condition.apply(criteria.getChave()) && condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ApiindicadorCriteria> copyFiltersAre(
        ApiindicadorCriteria copy,
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
