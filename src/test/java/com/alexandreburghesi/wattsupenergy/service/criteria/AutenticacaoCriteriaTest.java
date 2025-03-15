package com.alexandreburghesi.wattsupenergy.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AutenticacaoCriteriaTest {

    @Test
    void newAutenticacaoCriteriaHasAllFiltersNullTest() {
        var autenticacaoCriteria = new AutenticacaoCriteria();
        assertThat(autenticacaoCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void autenticacaoCriteriaFluentMethodsCreatesFiltersTest() {
        var autenticacaoCriteria = new AutenticacaoCriteria();

        setAllFilters(autenticacaoCriteria);

        assertThat(autenticacaoCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void autenticacaoCriteriaCopyCreatesNullFilterTest() {
        var autenticacaoCriteria = new AutenticacaoCriteria();
        var copy = autenticacaoCriteria.copy();

        assertThat(autenticacaoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(autenticacaoCriteria)
        );
    }

    @Test
    void autenticacaoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var autenticacaoCriteria = new AutenticacaoCriteria();
        setAllFilters(autenticacaoCriteria);

        var copy = autenticacaoCriteria.copy();

        assertThat(autenticacaoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(autenticacaoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var autenticacaoCriteria = new AutenticacaoCriteria();

        assertThat(autenticacaoCriteria).hasToString("AutenticacaoCriteria{}");
    }

    private static void setAllFilters(AutenticacaoCriteria autenticacaoCriteria) {
        autenticacaoCriteria.id();
        autenticacaoCriteria.usuario();
        autenticacaoCriteria.senha();
        autenticacaoCriteria.accessToken();
        autenticacaoCriteria.tokenType();
        autenticacaoCriteria.distinct();
    }

    private static Condition<AutenticacaoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getUsuario()) &&
                condition.apply(criteria.getSenha()) &&
                condition.apply(criteria.getAccessToken()) &&
                condition.apply(criteria.getTokenType()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AutenticacaoCriteria> copyFiltersAre(
        AutenticacaoCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getUsuario(), copy.getUsuario()) &&
                condition.apply(criteria.getSenha(), copy.getSenha()) &&
                condition.apply(criteria.getAccessToken(), copy.getAccessToken()) &&
                condition.apply(criteria.getTokenType(), copy.getTokenType()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
