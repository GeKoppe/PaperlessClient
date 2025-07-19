package org.dmsextension.paperless.client.utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@inheritDoc}
 *
 * <b>Extends the handlebars library with custom helpers</b>
 *
 *
 * <table border="1">
 *     <caption>Handlebar Helpers</caption>
 *     <tr>
 *         <th>Helper</th>
 *         <th>Options</th>
 *         <th>Example</th>
 *     </tr>
 *     <tr>
 *         <td>substring</td>
 *         <td>StartIndex (int), EndIndex (int)</td>
 *         <td>{{substring test 0 2}} = "te"</td>
 *     </tr>
 *     <tr>
 *         <td>padLeft</td>
 *         <td>PaddingString (String), Times (int)</td>
 *         <td>{{padLeft test a 5}} = "aaaaatest"</td>
 *     </tr>
 *     <tr>
 *         <td>get_cf_value</td>
 *         <td></td>
 *         <td>{{get_cf_value custom_fields "Invoice number" "0000"}}</td>
 *     </tr>
 * </table>
 */
public class CustomHandlebars extends Handlebars {
    /**
     * Logger
     */
    private final Logger logger = LoggerFactory.getLogger(CustomHandlebars.class);
    public CustomHandlebars() {
        super();
        this.addSubstringHelper();
        this.addPadLeftHelper();
        this.addPaperlessHelper();
        this.addDatetimeHelper();
    }

    private void addSubstringHelper() {
        Helper<String> helper =  (s, options) -> {
            int start = options.param(0);
            int end = options.param(1);
            if (s.length() < end) {
                end = s.length();
            }
            if (start > end) {
                start = end;
            }

            return s.substring(start, end);
        };
        this.registerHelper("substring", helper);
    }

    private void addPadLeftHelper() {
        Helper<String> helper =  (s, options) -> {
            StringBuilder paddingString = new StringBuilder();
            for (int i = 0; i < (int) options.param(1); i++) {
                paddingString.append((String) options.param(0));
            }

            return paddingString.toString() + s;
        };
        this.registerHelper("padLeft", helper);
    }

    private void addDatetimeHelper() {
        this.registerHelper("datetime", (context, options) -> {
            String dateStr = context.toString();
            String format = options.param(0);

            try {
                LocalDate date = LocalDate.parse(dateStr); // ISO 8601 ("YYYY-MM-DD")
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return date.format(formatter);
            } catch (Exception e) {
                return "invalid-date";
            }
        });
    }

    private void addPaperlessHelper() {
        this.registerHelper("get_cf_value", (context, options) -> {
            Map<String, Object> customFields = (Map<String, Object>) context;
            String fieldName = options.param(0);
            String defaultValue = options.params.length > 1 ? options.param(1) : "";

            Object value = customFields.get(fieldName);
            return value != null ? value.toString() : defaultValue;
        });
    }

    private static String replacePaperlessHandlebars(String input) {
        // Regex: matched alle "{{ custom_fields|get_cf_value(...)|datetime(...) }}" oder ohne datetime
        String regex = "\\{\\{\\s*custom_fields\\|get_cf_value\\(\"([^\"]+)\"(?:,\\s*\"([^\"]+)\")?\\)(?:\\|datetime\\('([^']+)'\\))?\\s*}}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            String field = matcher.group(1);
            String fallback = matcher.group(2);
            String format = matcher.group(3);

            StringBuilder inner = new StringBuilder("get_cf_value custom_fields \"" + field + "\"");
            if (fallback != null) {
                inner.append(" \"").append(fallback).append("\"");
            }

            String replacement;
            if (format != null) {
                replacement = "{{ datetime (" + inner + ") \"" + format + "\" }}";
            } else {
                replacement = "{{ " + inner + " }}";
            }

            matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(result);
        return result.toString();
    }

    public Template compileInline(String templateStr) throws IOException {
        String replaced = replacePaperlessHandlebars(templateStr);
        return super.compileInline(replaced);
    }
}
