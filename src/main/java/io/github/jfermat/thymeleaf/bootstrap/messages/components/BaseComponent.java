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

package io.github.jfermat.thymeleaf.bootstrap.messages.components;

import io.github.jfermat.messages.Message;
import io.github.jfermat.messages.MessageBox;
import io.github.jfermat.messages.MessageContext;
import io.github.jfermat.thymeleaf.bootstrap.messages.util.TBMConstants;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.expression.IExpressionObjects;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IProcessableElementTag;
import org.unbescape.html.HtmlEscape;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * The type Base component.
 */
public abstract class BaseComponent {

    private final ITemplateContext iTemplateContext;
    private final Map<String, MessageContext> contextsMessages;

    private final String attrContext;
    private final String attrCategory;
    private final boolean attrEscapeHtml;
    private Map<String, List<String>> messages = new HashMap<>();
    private ResourceBundle resourceBundle;

    /**
     * Instantiates a new Base component.
     *
     * @param iTemplateContext the template context
     * @param tag              the tag
     */
    BaseComponent(final ITemplateContext iTemplateContext, final IProcessableElementTag tag) {

        this.iTemplateContext = iTemplateContext;
        // Getting messages
        final IExpressionObjects expressionObjects = iTemplateContext.getExpressionObjects();
        final HttpServletRequest request = (HttpServletRequest) expressionObjects.getObject(TBMConstants.REQUEST);
        this.contextsMessages = (Map<String, MessageContext>) request.getAttribute(TBMConstants.MODEL_ATTRIBUTE);

        // Initialize Common Attributes
        this.attrContext = tag.getAttributeValue(TBMConstants.ATTR_CONTEXT) == null
                ? "default" : tag.getAttributeValue(TBMConstants.ATTR_CONTEXT);
        this.attrCategory = tag.getAttributeValue(TBMConstants.ATTR_CATEGORY);
        String attrBundle = tag.getAttributeValue(TBMConstants.ATTR_BUNDLE);
        this.attrEscapeHtml = tag.getAttributeValue(TBMConstants.ATTR_ESCAPE_HTML) == null ||
                TBMConstants.ATTR_VALUE_TRUE.equalsIgnoreCase(tag.getAttributeValue(TBMConstants.ATTR_ESCAPE_HTML));

        if (attrBundle != null) {
            resourceBundle = ResourceBundle.getBundle(attrBundle, request.getLocale());
        }
    }

    /**
     * Gets template context.
     *
     * @return the template context
     */
    public ITemplateContext getTemplateContext() {
        return iTemplateContext;
    }

    /**
     * Gets messages.
     *
     * @return the messages
     */
    public Map<String, List<String>> getMessages() {
        this.messages = new HashMap<>();
        if (this.contextsMessages.containsKey(attrContext)) {
            getMessageContext(this.contextsMessages.get(attrContext));
        }
        return this.messages;
    }

    /**
     * Render component.
     *
     * @return the model
     */
    public abstract IModel render();

    private void getMessageContext(MessageContext messageContext) {
        if (!messageContext.messages().isEmpty()) {
            for (Map.Entry<MessageBox, List<Message>> entry : messageContext.messages().entrySet()) {
                if (this.attrCategory == null || this.attrCategory.equals(entry.getKey().getCategory())) {
                    this.messages.computeIfAbsent(
                            entry.getKey().getSeverity(),
                            p -> new ArrayList<>())
                            .addAll(processMessages(entry.getValue()));
                }
            }
        }
    }

    private List<String> processMessages(List<Message> toProcess) {
        List<String> processedMessages = new ArrayList<>();
        if (!toProcess.isEmpty()) {
            for (Message msg : toProcess) {
                String value = msg.getMessage();
                if (resourceBundle != null) {
                    value = resourceBundle.getString(value);
                }
                value = String.format(value, msg.getArgs());
                if (attrEscapeHtml) {
                    value = HtmlEscape.escapeHtml5(value);
                }
                processedMessages.add(value);
            }
        }
        return processedMessages;
    }

}
