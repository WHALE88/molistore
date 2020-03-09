package com.molistore.application.util.criteria;


import com.google.common.base.Strings;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.time.Instant;
import java.util.List;

public class Predicates {
    public static Predicate notEmptyPattern(Path<String> path, String pattern, CriteriaBuilder builder) {
        return Strings.isNullOrEmpty(pattern) ? anyValue(builder) : builder.like(path, "%" + pattern + "%");
    }

    public static Predicate inRange(Path<Instant> path, Pair<Instant, Instant> period, CriteriaBuilder builder) {
        return builder.between(path, period.getLeft(), period.getRight());
    }

    public static Predicate between(Path<Long> path, Pair<Long, Long> range, CriteriaBuilder builder) {
        return builder.between(path, range.getLeft(), range.getRight());
    }

    public static Predicate between(Expression<Long> expression, Pair<Long, Long> range, CriteriaBuilder builder) {
        return builder.between(expression, range.getLeft(), range.getRight());
    }

    public static Predicate inOptionalRange(Path<Instant> path, Pair<Instant, Instant> period, CriteriaBuilder builder) {
        return period == null ? anyValue(builder) : builder.between(path, period.getLeft(), period.getRight());
    }

    public static <T> Predicate anyValueOrInList(Path<T> path, List<T> values, CriteriaBuilder builder) {
        return CollectionUtils.isEmpty(values) ? anyValue(builder) : path.in(values);
    }

    public static <T> Predicate anyValueOrNotInList(Path<T> path, List<T> values, CriteriaBuilder builder) {
        return CollectionUtils.isEmpty(values) ? anyValue(builder) : builder.not(path.in(values));
    }

    public static <T> Predicate existsOrAny(Path<T> path, Boolean value, CriteriaBuilder builder) {
        return value == null ? anyValue(builder) : exists(path, value);
    }

    public static <T> Predicate exists(Path<T> path, boolean value) {
        return value ? path.isNotNull() : path.isNull();
    }

    public static <T> Predicate isNullOrAnyValue(Path<T> path, boolean value, CriteriaBuilder builder) {
        return value ? builder.isNull(path) : anyValue(builder);
    }

    public static Predicate isIntGreaterOrLessThan(Path<Integer> path, Expression<Integer> expression, boolean isGreater, CriteriaBuilder builder) {
        return isGreater ? builder.greaterThan(path, expression) : builder.lessThan(path, expression);
    }

    public static Predicate anyValueOrGreaterThanOrEqualTo(Path<Integer> path, Integer value, CriteriaBuilder builder) {
        return value == null ? anyValue(builder) : builder.greaterThanOrEqualTo(path, value);
    }

    public static Predicate anyValueGreaterThan(Path<Integer> path, Integer value, CriteriaBuilder builder) {
        return value == null ? anyValue(builder) : builder.greaterThan(path, value);
    }

    private static Predicate anyValue(CriteriaBuilder builder) {
        return builder.and();
    }
}