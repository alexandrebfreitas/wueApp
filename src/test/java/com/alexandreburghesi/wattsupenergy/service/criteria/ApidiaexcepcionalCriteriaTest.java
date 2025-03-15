package com.alexandreburghesi.wattsupenergy.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class ApidiaexcepcionalCriteriaTest {

    @Test
    void newApidiaexcepcionalCriteriaHasAllFiltersNullTest() {
        var apidiaexcepcionalCriteria = new ApidiaexcepcionalCriteria();
        assertThat(apidiaexcepcionalCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void apidiaexcepcionalCriteriaFluentMethodsCreatesFiltersTest() {
        var apidiaexcepcionalCriteria = new ApidiaexcepcionalCriteria();

        setAllFilters(apidiaexcepcionalCriteria);

        assertThat(apidiaexcepcionalCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void apidiaexcepcionalCriteriaCopyCreatesNullFilterTest() {
        var apidiaexcepcionalCriteria = new ApidiaexcepcionalCriteria();
        var copy = apidiaexcepcionalCriteria.copy();

        assertThat(apidiaexcepcionalCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(apidiaexcepcionalCriteria)
        );
    }

    @Test
    void apidiaexcepcionalCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var apidiaexcepcionalCriteria = new ApidiaexcepcionalCriteria();
        setAllFilters(apidiaexcepcionalCriteria);

        var copy = apidiaexcepcionalCriteria.copy();

        assertThat(apidiaexcepcionalCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(apidiaexcepcionalCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var apidiaexcepcionalCriteria = new ApidiaexcepcionalCriteria();

        assertThat(apidiaexcepcionalCriteria).hasToString("ApidiaexcepcionalCriteria{}");
    }

    private static void setAllFilters(ApidiaexcepcionalCriteria apidiaexcepcionalCriteria) {
        apidiaexcepcionalCriteria.id();
        apidiaexcepcionalCriteria.chave();
        apidiaexcepcionalCriteria.distinct();
    }

    private static Condition<ApidiaexcepcionalCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) && condition.apply(criteria.getChave()) && condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<ApidiaexcepcionalCriteria> copyFiltersAre(
        ApidiaexcepcionalCriteria copy,
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
