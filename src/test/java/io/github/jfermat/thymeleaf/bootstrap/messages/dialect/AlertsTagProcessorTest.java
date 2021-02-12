/*
 * Copyright (c) 2021. Jordi Ferré Matamoros. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.jfermat.thymeleaf.bootstrap.messages.dialect;

import io.github.jfermat.thymeleaf.bootstrap.messages.BootstrapMessages;
import io.github.jfermat.thymeleaf.bootstrap.messages.util.AbstractElementTagProcessorMock;
import io.github.jfermat.thymeleaf.bootstrap.messages.util.TBMConstants;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class AlertsTagProcessorTest {

    private static final String CONTEXT_DEFAULT = "default";
    private static final String CONTEXT_1 = "CO1";
    private static final String CONTEXT_2 = "CO2";
    private static final String CONTEXT_HTML = "HTML";
    private static final String CONTEXT_BUNDLE = "BUNDLE";

    private static final String CATEGORY_1 = "CA1";
    private static final String CATEGORY_2 = "CA2";

    private static final String MESSAGE = "Context: %s | Category: %s";
    private static final String NONE = "none";
    private static final String MESSAGE_PROPERTY = "message.key";
    private static final String MESSAGE_PROPERTY_VALUE = "Message bundle.";
    private static final String HTML_STRONG_UNESCAPED = "<strong>Strong</strong>";
    private static final String HTML_STRONG_ESCAPED = "&lt;strong&gt;Strong&lt;/strong&gt;";

    @BeforeClass
    public static void setup() {

        BootstrapMessages.defaultContext()
                .addInfoMessage(String.format(MESSAGE, CONTEXT_DEFAULT, NONE));
        BootstrapMessages.defaultContext()
                .addInfoMessage(CATEGORY_1, String.format(MESSAGE, CATEGORY_1, NONE));
        BootstrapMessages.defaultContext()
                .addInfoMessage(HTML_STRONG_UNESCAPED);
        BootstrapMessages.defaultContext()
                .addInfoMessage(MESSAGE_PROPERTY);

        BootstrapMessages.context(CONTEXT_1)
                .addInfoMessage(String.format(MESSAGE, CONTEXT_DEFAULT, NONE));
        BootstrapMessages.context(CONTEXT_1)
                .addInfoMessage(CATEGORY_1, String.format(MESSAGE, CONTEXT_1, CATEGORY_1));
        BootstrapMessages.context(CONTEXT_1)
                .addInfoMessage(CATEGORY_2, String.format(MESSAGE, CONTEXT_1, CATEGORY_2));

        BootstrapMessages.context(CONTEXT_2)
                .addInfoMessage(String.format(MESSAGE, CONTEXT_2, NONE));

        BootstrapMessages.context(CONTEXT_HTML).addInfoMessage(HTML_STRONG_UNESCAPED);

        BootstrapMessages.context(CONTEXT_BUNDLE).addInfoMessage(MESSAGE_PROPERTY);
    }

    @Test
    public void doProcessDefault() {
        String html = alert(null, null, null, null, null);

        // Not Closable
        assertThat(html, not(containsString(TBMConstants.ARIA_LABEL_CLOSE_VALUE)));
        assertThat(html, not(containsString(TBMConstants.HTML_ESCAPE_TIMES)));

        // Context
        assertThat(html, containsString("Context: " + CONTEXT_DEFAULT));
        assertThat(html, not(containsString("Context: " + CONTEXT_1)));
        assertThat(html, not(containsString("Context: " + CONTEXT_2)));
        assertThat(html, not(containsString("Context: " + CONTEXT_HTML)));

        // Category
        assertThat(html, containsString("Category: " + NONE));
        assertThat(html, not(containsString("Category: " + CATEGORY_1)));
        assertThat(html, not(containsString("Category: " + CATEGORY_2)));

        // Escape HTML
        assertThat(html, containsString(HTML_STRONG_ESCAPED));
        assertThat(html, not(containsString(HTML_STRONG_UNESCAPED)));

        // Bundle
        assertThat(html, containsString(MESSAGE_PROPERTY));

    }

    @Test
    public void doProcessClosable() {
        String html = alert("true", null, null, null, null);
        assertThat(html, containsString(TBMConstants.ARIA_LABEL_CLOSE_VALUE));
        assertThat(html, containsString(TBMConstants.HTML_ESCAPE_TIMES));
    }

    @Test
    public void doProcessUnescapedHtml() {
        String html = alert(null, CONTEXT_HTML, null, "false", null);
        assertThat(html, containsString(HTML_STRONG_UNESCAPED));
    }

    @Test
    public void doProcessBundle() {

        String html = alert(null, CONTEXT_BUNDLE, null, null, "messages");
        assertThat(html, containsString(MESSAGE_PROPERTY_VALUE));

    }

    private String alert(String attrClosable, String attrContext, String attrCategory, String attrEscapeHtml, String attrBundle) {
        Map<String, String> tags = new HashMap<>();
        tags.computeIfAbsent(TBMConstants.ATTR_CLOSABLE, value -> attrClosable);
        tags.computeIfAbsent(TBMConstants.ATTR_CONTEXT, value -> attrContext);
        tags.computeIfAbsent(TBMConstants.ATTR_CATEGORY, value -> attrCategory);
        tags.computeIfAbsent(TBMConstants.ATTR_ESCAPE_HTML, value -> attrEscapeHtml);
        tags.computeIfAbsent(TBMConstants.ATTR_BUNDLE, value -> attrBundle);
        AbstractElementTagProcessorMock abstractElementTagProcessorMock = new AbstractElementTagProcessorMock(tags);

        AlertTagProcessor alertTagProcessor = new AlertTagProcessor("test");
        alertTagProcessor.doProcess(abstractElementTagProcessorMock.getContext(),
                abstractElementTagProcessorMock.getiProcessableElementTag(),
                abstractElementTagProcessorMock.getiElementTagStructureHandler());

        return abstractElementTagProcessorMock.getHtml().toString();
    }
}
