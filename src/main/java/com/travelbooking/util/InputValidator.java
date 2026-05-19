package com.travelbooking.util;

import java.util.Set;
import java.util.regex.Pattern;

/** Server-side input validation utility. */
public class InputValidator {

    private static final Pattern EMAIL = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE = Pattern.compile(
        "^\\+?[0-9]{7,15}$");
    private static final Pattern SAFE_TEXT = Pattern.compile(
        "^[\\w\\s.,!?@#&()\\-'\":/]+$");
    private static final Pattern REPEATED_CHARS = Pattern.compile("(.)\\1{3,}");
    private static final Pattern HAS_LETTER = Pattern.compile("[a-z]");

    /** Obvious junk usernames that no legitimate user signs up with. */
    private static final Set<String> JUNK_LOCAL_PARTS = Set.of(
        "test", "tests", "testing", "test123", "asdf", "asdfgh", "qwerty",
        "abc", "abcd", "abcde", "aaa", "aaaa", "xxx", "xxxx", "foo", "bar",
        "foobar", "admin", "user", "sample", "demo", "dummy", "fake",
        "noreply", "no-reply", "donotreply"
    );

    /** Disposable / temporary mailbox providers. */
    private static final Set<String> DISPOSABLE_DOMAINS = Set.of(
        "mailinator.com", "tempmail.com", "temp-mail.org", "10minutemail.com",
        "guerrillamail.com", "guerrillamail.net", "sharklasers.com",
        "throwawaymail.com", "yopmail.com", "getnada.com", "maildrop.cc",
        "trashmail.com", "fakeinbox.com", "dispostable.com", "mintemail.com",
        "mohmal.com", "mytemp.email", "tempinbox.com"
    );

    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    /** Lenient format-only check — used for login. */
    public static boolean isValidEmail(String email) {
        return !isBlank(email) && EMAIL.matcher(email.trim()).matches();
    }

    /** Strict check — used for registration. Rejects obviously fake patterns. */
    public static boolean isValidEmailStrict(String email) {
        if (!isValidEmail(email)) return false;
        String value = email.trim().toLowerCase();
        int at = value.indexOf('@');
        String local = value.substring(0, at);
        String domain = value.substring(at + 1);

        if (local.length() < 3) return false;
        if (!HAS_LETTER.matcher(local).find()) return false;
        if (local.matches("\\d+")) return false;
        if (local.startsWith(".") || local.endsWith(".")) return false;
        if (REPEATED_CHARS.matcher(local).find()) return false;
        if (local.matches("^(0?1234|01234|12345|123456|123|1234)\\d*$")) return false;
        if (JUNK_LOCAL_PARTS.contains(local)) return false;

        if (DISPOSABLE_DOMAINS.contains(domain)) return false;
        if (!HAS_LETTER.matcher(domain).find()) return false;

        return true;
    }

    public static boolean isValidPhone(String phone) {
        return !isBlank(phone) && PHONE.matcher(phone.trim()).matches();
    }

    public static boolean isValidPassword(String pw) {
        return !isBlank(pw) && pw.length() >= 6;
    }

    public static boolean isValidName(String name) {
        return !isBlank(name) && name.trim().length() >= 2 && name.trim().length() <= 50;
    }

    public static boolean isPositiveNumber(String s) {
        if (isBlank(s)) return false;
        try {
            return Double.parseDouble(s.trim()) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isPositiveInt(String s) {
        if (isBlank(s)) return false;
        try {
            return Integer.parseInt(s.trim()) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** Sanitize text to prevent XSS. */
    public static String sanitize(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                     .replace("<", "&lt;")
                     .replace(">", "&gt;")
                     .replace("\"", "&quot;")
                     .replace("'", "&#x27;");
    }

    /** Trim or return empty. */
    public static String clean(String s) {
        return s == null ? "" : s.trim();
    }
}
