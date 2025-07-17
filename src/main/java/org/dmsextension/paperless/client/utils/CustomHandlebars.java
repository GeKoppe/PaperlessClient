package org.dmsextension.paperless.client.utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@inheritDoc}
 *
 * <b>Extends the handlebars library with custom helpers</b>
 *
 *
 * <table>
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
}
