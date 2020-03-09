package com.molistore.application.util;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonUtils {
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    public static <T> boolean isNotEmpty(List<T> lst) {
        return isNotNull(lst) && !lst.isEmpty();
    }

    public static <T> boolean isEmpty(List<T> lst) {
        if (isNull(lst)) {
            return true;
        }
        return isNotNull(lst) && lst.isEmpty();
    }

    public static <K, V> boolean isNotEmpty(Map<K, V> lst) {
        return isNotNull(lst) && !lst.isEmpty();
    }

    public static <T> T getMax(List<T> tList, Comparator<T> comparator) {
        if (isNotNull(tList)) {
            Optional<T> value = tList.parallelStream().max(comparator);
            if (value.isPresent()) {
                return value.get();
            }
        }
        return null;
    }

    public static <T> T getMin(List<T> tList, Comparator<T> comparator) {
        if (isNotNull(tList)) {
            Optional<T> value = tList.parallelStream().min(comparator);
            if (value.isPresent()) {
                return value.get();
            }
        }
        return null;
    }

    public static <T> List<T> concatTwoLists(List<T> lst1, List<T> lst2) {
        if (isNotNull(lst1) && isNotNull(lst2)) {
            return Stream.concat(lst1.stream(), lst2.stream()).collect(Collectors.toList());
        } else if (isNull(lst1) && isNotNull(lst2)) {
            return lst2;
        } else if (isNotNull(lst1)) {
            return lst1;
        } else {
            return Collections.emptyList();
        }
    }

    public static Boolean isTrue(Boolean val) {
        if (isNotNull(val) && val) {
            return val;
        } else return Boolean.FALSE;
    }


    public static Boolean isFalse(Boolean val) {
        if (isNull(val)) return Boolean.TRUE;
        else return !isTrue(val);
    }

    public static boolean isStrEqualsIgnoreCase(String str, String example) {
        return isNotNull(str) && isNotNull(example) && str.equalsIgnoreCase(example);
    }

    public static boolean isStrEquals(String str, String example) {
        if (isNull(str) && isNull(example)) {
            return true;
        } else if (isNull(str)) {
            return false;
        } else if (isNull(example)) {
            return false;
        } else {
            return str.equals(example);
        }
    }

    public static <T> boolean isObjectsEquals(T ob1, T ob2) {
        if (isNull(ob1) && isNull(ob2)) {
            return true;
        } else if (isNull(ob1)) {
            return false;
        } else if (isNull(ob2)) {
            return false;
        } else {
            return implementsInterface(ob1, Comparator.class) && implementsInterface(ob2, Comparator.class) && ob1.equals(ob2);
        }
    }

    public static boolean implementsInterface(Object object, Class interf) {
        for (Class c : object.getClass().getInterfaces()) {
            if (c.equals(interf)) {
                return true;
            }
        }
        return false;
    }

    public static int roundUp(double number) {
        if (!Double.isNaN(number)) {
            return BigDecimal.valueOf(number).setScale(0, RoundingMode.UP).intValue();
        }
        return 0;
    }

    public static double roundUp(double number, int scale) {
        if (!Double.isNaN(number)) {
            return BigDecimal.valueOf(number)
                    .setScale(scale, RoundingMode.UP).doubleValue();
        }
        return 0;
    }

    public static double roundHalfUp(double number, int scale) {
        if (!Double.isNaN(number)) {
            return BigDecimal.valueOf(number)
                    .setScale(scale, RoundingMode.HALF_UP).doubleValue();
        }
        return 0;
    }

    public static double roundHalfDown(double number, int scale) {
        if (!Double.isNaN(number)) {
            return BigDecimal.valueOf(number)
                    .setScale(scale, RoundingMode.HALF_DOWN).doubleValue();
        }
        return 0;
    }

    public static <T> Optional<T> findFirst(List<T> list,
                                            Predicate<T> predicate) {
        return list.stream()
                .filter(predicate)
                .findFirst();
    }

    public static <T> boolean anyEquals(T object, T... elementsEquality) {
        for (T object2 : elementsEquality) {
            if (object.equals(object2)) return true;
        }
        return false;
    }

    public static <T, E> E valueOrElse(T value, Function<T, E> mapper, E other) {
        return Optional.ofNullable(value)
                .map(mapper)
                .orElse(other);
    }

    public static <T> T[] concatenateArrays(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }
}
