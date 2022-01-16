package com.example.demo.task;

import com.example.demo.TestsUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class TaskSearchSpecificationTest {
    private static final String CRITERIA_FIELD = "criteria";
    @Test
    void toPredicateCriteriaOr() {
        var filters = List.of(CRITERIA_FIELD + ":or");
        var criteriaBuilder = mock(CriteriaBuilder.class);

        new TaskSearchSpecification(filters).toPredicate(mock(Root.class), mock(CriteriaQuery.class), criteriaBuilder);

        verify(criteriaBuilder, times(1)).or(any());
    }

    @Test
    void toPredicateCriteriaAnd() {
        var filters = List.of(CRITERIA_FIELD + ":and");
        var criteriaBuilder = mock(CriteriaBuilder.class);

        new TaskSearchSpecification(filters).toPredicate(mock(Root.class), mock(CriteriaQuery.class), criteriaBuilder);

        verify(criteriaBuilder, times(1)).and(any());
    }

    private static Stream<Arguments> toPredicateCriteriaByFieldArgs() {
        return Stream.of(arguments("completed", TestsUtil.RANDOM.nextBoolean()),
                         arguments("priority", TestsUtil.randomPriority()));
    }

    @ParameterizedTest
    @MethodSource("toPredicateCriteriaByFieldArgs")
    void toPredicateCriteriaByField(String field, Object value) {
        var filters = List.of(field + ":" + value);

        var root = mock(Root.class);
        var criteriaBuilder = mock(CriteriaBuilder.class);

        new TaskSearchSpecification(filters).toPredicate(root, mock(CriteriaQuery.class), criteriaBuilder);

        verify(root, times(1)).get(field);
        verify(criteriaBuilder, times(1)).equal(any(), eq(value));
    }
}