package com.molistore.application.util;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Andrey Khanevych
 * @date 17.03.2017
 */
public class CollectionsUtil {

    public static <T, R> List<R> mapList(Collection<T> srcList, Function<? super T, ? extends R> mapper) {
        return srcList
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    public static <T, E> List<T> mapList(Collection<E> variables, Predicate<E> filter, Function<E, T> mapper) {
        return variables.stream()
                .filter(filter)
                .map(mapper)
                .collect(Collectors.toList());
    }

    public static <T, R> Set<R> mapSet(Collection<T> srcList, Function<? super T, ? extends R> mapper) {
        return srcList
                .stream()
                .map(mapper)
                .collect(Collectors.toSet());
    }

    public static <T, E> Optional<T> findFirstFromList(Collection<E> variables, Predicate<E> filter, Function<E, T> mapper) {
        return variables.stream()
                .filter(filter)
                .map(mapper)
                .findFirst();
    }

    public static <T> Optional<T> findFirstByPredicate(Collection<T> values, Predicate<T> filter) {
        return values
                .stream()
                .filter(filter)
                .findFirst();
    }

    public static <T, E> Optional<E> findFirst(Collection<T> values, Function<T, E> mapper) {
        return values
                .stream()
                .map(mapper)
                .findFirst();
    }

    public static <T, E> Set<E> flatMapAndCollectToSet(List<T> accounts, Function<T, Stream<? extends E>> accountStreamFunction) {
        return accounts.stream()
                .flatMap(accountStreamFunction)
                .collect(Collectors.toSet());
    }

    public static <T, E> E nullOrValue(T value, Predicate<T> filter, Function<T, E> mapper) {
        return Optional.ofNullable(value)
                .filter(filter)
                .map(mapper)
                .orElse(null);
    }

    public static <T, E> E nullOrValue(T value, Function<T, E> mapper) {
        return Optional.ofNullable(value)
                .map(mapper)
                .orElse(null);
    }

    public static <T> String emptyOrValue(T value, Function<T, String> mapper) {
        return Optional.ofNullable(value)
                .map(mapper)
                .orElse("");
    }

    public static <T, E extends Number> String emptyOrValueToStr(T value, Function<T, E> mapper) {
        return Optional.ofNullable(value)
                .map(mapper)
                .map(Object::toString)
                .orElse("");
    }

    public static <T> BigDecimal sum(Collection<T> collection, Function<T, BigDecimal> mapper) {
        return collection
                .stream()
                .map(mapper)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static <T> T findMaxOrElseNull(Collection<T> values, Predicate<T> filter, Comparator<T> comparator) {
        return values
                .stream()
                .filter(filter)
                .max(comparator)
                .orElse(null);
    }

    public static <T, R> Map<R, List<T>> collectToMapOfList(Collection<T> values, Function<? super T, ? extends R> mapper) {
        Map<R, List<T>> result = new HashMap<>();
        for (T value : values) {
            List<T> list = result.computeIfAbsent(mapper.apply(value), k -> new ArrayList<>());
            list.add(value);
        }
        return result;
    }

    public static <T, R> Map<R, T> collectToMap(Collection<T> values, Function<? super T, ? extends R> mapper) {
        return values.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(mapper, Function.identity()));
    }

    public static <T> boolean isOrderedAsc(List<T> list, Comparator<T> comparator) {
        return isOrdered(list, comparator, true);
    }

    public static <T> boolean isOrderedDesc(List<T> list, Comparator<T> comparator) {
        return isOrdered(list, comparator, false);
    }

    public static <T> void fillToSize(int size, List<T> destination, T object) {
        destination.addAll(new ArrayList<>(Collections.nCopies(size - destination.size(), object)));
    }

    private static <T> boolean isOrdered(List<T> list, Comparator<T> comparator, boolean asc) {
        if (list.size() > 1) {
            for (int i = 1; i < list.size(); i++) {
                boolean lessThanPrevious = comparator.compare(list.get(i), list.get(i - 1)) < 0;
                boolean greaterThanPrevious = comparator.compare(list.get(i), list.get(i - 1)) > 0;
                if (asc && lessThanPrevious || !asc && greaterThanPrevious) {
                    return false;
                }
            }
        }
        return true;
    }

    public static <T> T coalesce(T... items) {
        for (T i : items) {
            if (i != null) return i;
        }
        return null;
    }


    public static <T> List<T> limitCollectionElements(List<T> superDealIdsForMigration, int maxSize) {
        return superDealIdsForMigration
                .stream()
                .limit(maxSize)
                .collect(Collectors.toList());
    }

    public static <T, R> Stream<R> mapStream(Collection<T> srcList, Function<? super T, ? extends R> mapper) {
        return srcList
                .stream()
                .map(mapper);
    }

}
