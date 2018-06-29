package com.pavelorekhov;

import java.io.Console;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MiscUtils {

    private static final Scanner sScanner = new Scanner(System.in);
    private static final Pattern ISSUE_PATTERN = Pattern.compile("\\w+-\\d+");

    private MiscUtils() {

    }

    public static String getUsernameFromCMD() {
        System.out.print("Username > ");
        return sScanner.next();
    }

    public static String getPasswordFromCMD() {
        Console console = System.console();
        if (console != null) {
            return String.valueOf(console.readPassword("Password > "));
        }
        System.out.println("This console doesn't support silent password reading, your password will be shown on screen.");
        System.out.print("Are you sure you want this? (y/n) > ");
        if ("y".equalsIgnoreCase(sScanner.next())) {
            System.out.print("Password > ");
            return sScanner.next();
        }
        throw new IllegalStateException("This application won't run without your password.");
    }

    public static String getTaskId(String messageString) {
        Matcher matcher = ISSUE_PATTERN.matcher(messageString);
        if (matcher.find()) {
            messageString = matcher.group();
        }
        return messageString;
    }
}
