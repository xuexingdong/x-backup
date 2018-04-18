package com.xuexingdong.x.common.utils;


import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class XRegexUtils {


    public static boolean match(@Nonnull String src, @Nonnull String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(src);
        return matcher.matches();
    }

    public static Optional<String> findOne(@Nonnull String src, @Nonnull String regex) {
        List<String> results = findAll(src, regex);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Nonnull
    public static List<String> findAll(@Nonnull String src, @Nonnull String regex) {
        Pattern pattern = Pattern.compile(regex);
        List<String> results = new ArrayList<>();
        Matcher matcher = pattern.matcher(src);
        while (matcher.find()) {
            results.add(matcher.group());
        }
        return results;
    }

    public static List<Optional<String>> findBatch(@Nonnull String src, @Nonnull String... regexs) {
        List<Optional<String>> results = new ArrayList<>();
        for (String regex : regexs) {
            results.add(findOne(src, regex));
        }
        return results;
    }
}
