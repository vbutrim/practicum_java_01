package com.vbutrim.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author butrim
 */
public class StringUtils {
    private static final String LIST_OBJS_DELIMITER = ",";
    private static final String NULL_STRING = "null";

    public static String toString(List<Integer> intList) {
        if (intList == null) {
            return NULL_STRING;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<Integer> it = intList.iterator();
        while(it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(LIST_OBJS_DELIMITER);
            }
        }
        sb.append("]");

        return sb.toString();
    }

    public static List<Integer> parseIntegerList(String intListAsStr) {
        if (intListAsStr == null || intListAsStr.isBlank() || NULL_STRING.equals(intListAsStr)) {
            return null;
        }
        String[] ints = intListAsStr
                .substring(1, intListAsStr.length() - 1)
                .split(LIST_OBJS_DELIMITER);

        List<Integer> intList = new ArrayList<>(ints.length);

        for (String integer : ints) {
            intList.add(Integer.valueOf(integer));
        }

        return intList;
    }
}
