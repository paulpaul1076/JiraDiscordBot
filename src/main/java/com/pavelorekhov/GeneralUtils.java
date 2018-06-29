package com.pavelorekhov;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class GeneralUtils {
    private static final Random rnd = new Random(System.currentTimeMillis());
    public static final Map<String, Supplier<String>> DICTIONARY =
            Collections.unmodifiableMap(new HashMap<String, Supplier<String>>() {{
                put("ping", () -> "pong");
                put("lerka", () -> "po pozitsiyam");
                put("random number", () -> String.valueOf(rnd.nextInt(1000)));
                put("орелрешка", () -> {
                    if (rnd.nextDouble() > 0.5) {
                        return "орел";
                    } else {
                        return "решка";
                    }
                });
                put("date", () -> "```" + LocalDate.now().toString() + "```");
                put("time", () -> "```" + LocalTime.now().toString() + "```");
                put("лерка", () -> "*она всегда работает только по позициям*");
                put("привет лерка", () -> "Привет! Я работаю только по позициям. Моя текущая позиция №" + rnd.nextInt(1_000_000));
                put("ннтд", () -> "не надо так делать");
                put("как работает лерка", () -> "она работает только по позициям, ее позиция -- динамическая, как ip адрес телеграмма, который не смог заблокировать роскомнадзор");
                put("вп", () -> "Привет, меня зовут Валерия Позицева");
                put("кд", () -> "кроссдоменность");
                put("кдч", () -> "кроссдоменность, чел");
                put("пока лерка", () -> "не надо так делать");
                put("lerka general help", () -> {
                    String options =
                            this.keySet()
                                    .stream()
                                    .filter(key -> !"lerka general help".equalsIgnoreCase(key) && !"lerka jira help".equalsIgnoreCase(key))
                                    .sorted()
                                    .collect(Collectors.joining("\n"));
                    return "Options:\n\n" + options;
                });
                put("lerka jira help", () -> {
                    String optionsForJira = JiraUtils.OPTIONS_LIST.stream().sorted().collect(Collectors.joining("\n"));
                    return "To find out information about a jira issue, please provide its task id and one of the following options:\n\n" + optionsForJira;
                });
            }});

    private GeneralUtils() {

    }
}
