package com.xxd.x.common.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class XRegexUtils {


    public static boolean match(String src, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(src);
        return matcher.matches();
    }

    public static Optional<String> findOne(String src, String regex) {
        List<String> results = findAll(src, regex);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public static List<String> findAll(String src, String regex) {
        Pattern pattern = Pattern.compile(regex);
        List<String> results = new ArrayList<>();
        Matcher matcher = pattern.matcher(src);
        while (matcher.find()) {
            results.add(matcher.group());
        }
        return results;
    }

    public static List<Optional<String>> findBatch(String src, String... regexs) {
        List<Optional<String>> results = new ArrayList<>();
        for (String regex : regexs) {
            results.add(findOne(src, regex));
        }
        return results;
    }
}
