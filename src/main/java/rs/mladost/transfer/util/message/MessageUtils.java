package rs.mladost.transfer.util.message;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageUtils {
    private static final Locale DEFAULT_LOCALE = new Locale("en", "GB");
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("message", DEFAULT_LOCALE);
    private static final String MESSAGE_POSTFIX = ".message";

    public static String getMessage(String name) {
        return RESOURCE_BUNDLE.getString(name + MESSAGE_POSTFIX);
    }
}
