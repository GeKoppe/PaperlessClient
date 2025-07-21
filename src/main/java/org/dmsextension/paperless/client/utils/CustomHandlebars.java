package org.dmsextension.paperless.client.utils;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Template;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import org.dmsextension.paperless.client.PaperlessClient;
import org.dmsextension.paperless.client.endpoints.EndpointFactory;
import org.dmsextension.paperless.client.endpoints.SingleCustomFieldEndpoint;
import org.dmsextension.paperless.client.templates.IDto;
import org.dmsextension.paperless.client.templates.TCustomFieldTemplate;
import org.dmsextension.paperless.client.templates.TCustomFieldValue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
 *         <td>Map field that contains custom fields, name of the custom field, default value</td>
 *         <td>{{get_cf_value custom_fields "Invoice number" "0000"}}</td>
 *     </tr>
 *     <tr>
 *         <td>datetime</td>
 *         <td>Datetime value (YYYY-MM-DD), Format</td>
 *         <td>{{datetime "2024-01-01" "Y"}} = "2024"; {{datetime "2024-01-01" "MM"}} = "01"; {{datetime "2024-01-01" "M"}} = "1"</td>
 *     </tr>
 * </table>
 */
public class CustomHandlebars extends Handlebars {
    /**
     * Moshi for converting objects to Maps
     */
    private static final Moshi moshi = new Moshi.Builder().build();
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(CustomHandlebars.class);
    /**
     * Map of all custom fields
     */
    private static final Map<String, TCustomFieldTemplate> customFieldTemplates = new HashMap<>();

    /**
     * Default constructor
     */
    public CustomHandlebars() {
        super();
        this.addSubstringHelper();
        this.addPadLeftHelper();
        this.addPaperlessHelper();
        this.addDatetimeHelper();
    }

    /**
     * Adds helper for substrings
     */
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

    /**
     * Adds helper for padding strings left
     */
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

    /**
     * Adds helper for converting datetime
     */
    private void addDatetimeHelper() {
        this.registerHelper("datetime", (context, options) -> {
            String dateStr = context.toString();
            String format = options.param(0);
            if (format.contains("%")) {
                format = format.replace("%", "");
            }
            if (format.equals("m")) format = "MM";
            else if (format.equals("D")) format = "DD";
            try {
                LocalDate date = LocalDate.parse(dateStr); // ISO 8601 ("YYYY-MM-DD")
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                return date.format(formatter);
            } catch (Exception e) {
                return "invalid-date";
            }
        });
    }

    /**
     * Adds helper for converting paperless values. Requires
     */
    private void addPaperlessHelper() {
        this.registerHelper("get_cf_value", (context, options) -> {
            if (!(context instanceof List<?> customFields)) {
                logger.debug("Custom fields context is not a List – returning null");
                return null;
            }

            String fieldName = options.param(0);
            String defaultValue = options.params.length > 1 ? options.param(1) : "";

            for (Object obj : customFields) {
                if (!(obj instanceof Map<?, ?> fieldMap)) {
                    logger.debug("Custom field entry is not a Map: {}", obj);
                    continue;
                }

                Object fieldObj = fieldMap.get("field");
                if (!(fieldObj instanceof Number number)) {
                    logger.debug("Field ID is not numeric: {}", fieldObj);
                    continue;
                }

                int fieldId = number.intValue();
                TCustomFieldTemplate template = getCustomFieldById(fieldId);
                if (template == null) {
                    logger.debug("Unknown field ID: {}", fieldId);
                    continue;
                }

                if (template.getName().equalsIgnoreCase(fieldName)) {
                    Object value = fieldMap.get("value");
                    return value != null ? value : defaultValue;
                }
            }

            return defaultValue;
        });
    }

    /**
     * Formats the custom paperless handlebars into handlebars usable by this class. Is called every time,
     * {@link CustomHandlebars#compileInline(String)} is called.
     * @param input String containing paperless handlebars
     * @return Sanitized String that only contains normal handlebars
     */
    @NotNull
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

    /**
     * Converts an {@link IDto} object to a {@link Map}, so that it can be used in {@link Template#apply(Context)}
     * @param object Object to convert into a map
     * @param clazz Class of the Object
     * @return The object put into a map
     * @param <T> Class of the object to be mapped
     * @throws IOException Thrown, if object could not be read into a json or back into a map
     */
    public static <T extends IDto> Map<String, Object> convertToMap(T object, Class<T> clazz) throws IOException {
        String jsonString = moshi.adapter(clazz).toJson(object);
        Type type = Types.newParameterizedType(Map.class, String.class, Object.class);
        JsonAdapter<Map<String, Object>> adapter = moshi.adapter(type);
        return adapter.fromJson(jsonString);
    }

    /**
     * As paperless saves the values of select fields not with their actual values, but with ids, this method converts
     * all values of select fields into their actual values. Should be called with all custom fields before using
     * {@link Template#apply(Context)} with those custom fields.
     * @param values All custom field values to be transformed
     * @param p Client that is used to get {@link TCustomFieldTemplate} instances from paperless
     * @return The values object with modified value fields.
     */
    @Contract("_, _ -> param1")
    public static List<TCustomFieldValue> getSelectFieldValues(List<TCustomFieldValue> values, @NotNull PaperlessClient p) {
        logger.debug(String.format("Converting all select field values of %s", values));
        SingleCustomFieldEndpoint ep = EndpointFactory.getCustomField(p.getUrl());
        for (var x : values) {
            try {
                TCustomFieldTemplate tpl = getCustomFieldById(x.getField());
                if (tpl == null) {
                    ep.pathParams(Map.of("id", "" + x.getField()));
                    tpl = (TCustomFieldTemplate) p.execute(ep, null);
                    if (tpl == null) {
                        logger.debug(String.format("Could not get template for custom field %s", x.getField()));
                        continue;
                    }
                    customFieldTemplates.put(tpl.getName(), tpl);
                }

                if (!tpl.getDataType().equals("select")) {
                    logger.debug(String.format("Field %s is of not type select, continuing", x.getField()));
                    continue;
                }

                for (var y : tpl.getExtraData().getSelectOptions()) {
                    if (y.getId().equals(x.getValue())) {
                        logger.debug(String.format("Parsing value %s to %s", x.getValue(), y.getLabel()));
                        x.setValue(y.getLabel());
                    }
                }
            } catch (Exception ex) {
                logger.info(String.format("Exception occurred while trying to format field %s: %s", x.getField(), ex));
            }
        }
        return values;
    }

    /**
     * Retrieves custom field by id from {@link CustomHandlebars#customFieldTemplates}
     * @param id Id of the custom field
     * @return The {@link TCustomFieldTemplate} instance with the given id
     */
    @Nullable
    private static TCustomFieldTemplate getCustomFieldById(@NotNull Integer id) {
        for (var x : customFieldTemplates.keySet())
            if (customFieldTemplates.get(x).getId().equals(id)) return customFieldTemplates.get(x);

        return null;
    }

    /**
     * {@inheritDoc}
     * Calls {@link CustomHandlebars#replacePaperlessHandlebars(String)} before executing {@link Handlebars#compileInline(String)}.
     * @param templateStr
     * @return
     * @throws IOException
     */
    public Template compileInline(String templateStr) throws IOException {
        String replaced = replacePaperlessHandlebars(templateStr);
        return super.compileInline(replaced);
    }
}
