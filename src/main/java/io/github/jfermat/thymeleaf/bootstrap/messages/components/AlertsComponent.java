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

import io.github.jfermat.thymeleaf.bootstrap.messages.util.TBMConstants;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.AttributeValueQuotes;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;

import java.util.*;

/**
 * Provide contextual feedback messages for typical user actions with the handful of available and flexible alert messages.
 */
public final class AlertsComponent extends BaseComponent {

    private boolean attrClosable;

    /**
     * Instantiates a new Alerts component.
     *
     * @param iTemplateContext the template context
     * @param tag              the tag
     */
    public AlertsComponent(ITemplateContext iTemplateContext, IProcessableElementTag tag) {
        super(iTemplateContext, tag);
        this.attrClosable = TBMConstants.ATTR_VALUE_TRUE.equalsIgnoreCase(tag.getAttributeValue(TBMConstants.ATTR_CLOSABLE));
    }

    @Override
    public IModel render() {

        final IModelFactory modelFactory = getTemplateContext().getModelFactory();
        final IModel model = modelFactory.createModel();
        final Map<String, List<String>> messages = super.getMessages();

        if (!messages.isEmpty()) {

            model.add(modelFactory.createOpenElementTag(
                    TBMConstants.HTML_DIV, TBMConstants.HTML_ATTR_CLASS, TBMConstants.CLASS_ROW)
            );

            model.add(modelFactory.createOpenElementTag(
                    TBMConstants.HTML_DIV, TBMConstants.HTML_ATTR_CLASS, TBMConstants.CLASS_COL_12)
            );

            for (Map.Entry<String, List<String>> entry : messages.entrySet()) {

                Map<String, String> attributes = new HashMap<>();
                attributes.put(TBMConstants.HTML_ATTR_CLASS, alertClasses(entry.getKey()));
                attributes.put(TBMConstants.HTML_ATTR_ROLE, TBMConstants.CLASS_ALERT);
                model.add(modelFactory.createOpenElementTag(
                        TBMConstants.HTML_DIV, attributes, AttributeValueQuotes.DOUBLE, false)
                );

                closeButton(modelFactory, model);

                for (Iterator<String> it = entry.getValue().iterator(); it.hasNext(); ) {
                    String message = it.next();
                    if (it.hasNext()) {
                        model.add(modelFactory.createOpenElementTag(TBMConstants.HTML_P));
                    } else {
                        model.add(modelFactory.createOpenElementTag(
                                TBMConstants.HTML_P,
                                TBMConstants.HTML_ATTR_CLASS,
                                TBMConstants.CLASS_MB_0));
                    }
                    model.add(modelFactory.createText(message));
                    model.add(modelFactory.createCloseElementTag(TBMConstants.HTML_P));
                }

                model.add(modelFactory.createCloseElementTag(TBMConstants.HTML_DIV));
            }


            model.add(modelFactory.createCloseElementTag(TBMConstants.HTML_DIV));
            model.add(modelFactory.createCloseElementTag(TBMConstants.HTML_DIV));
        }

        return model;
    }

    private String alertClasses(String level) {
        List<String> classes = new ArrayList<>();
        classes.add(TBMConstants.CLASS_ALERT);
        classes.add(TBMConstants.CLASS_ALERT + TBMConstants.STRING_GUION + level);
        classes.add(TBMConstants.CLASS_COL_12);
        if (attrClosable) {
            classes.add(TBMConstants.CLASS_ALERT_DISMISSIBLE);
            classes.add(TBMConstants.CLASS_SHOW);
            classes.add(TBMConstants.CLASS_FADE);
        }
        return String.join(TBMConstants.STRING_SPACE, classes);
    }

    private void closeButton(IModelFactory modelFactory, IModel model) {
        if (attrClosable) {
            Map<String, String> attributes = new HashMap<>();
            attributes.put(TBMConstants.HTML_ATTR_TYPE, TBMConstants.ATTR_TYPE_BUTTON_VALUE);
            attributes.put(TBMConstants.HTML_ATTR_CLASS, TBMConstants.CLASS_CLOSE);
            attributes.put(TBMConstants.HTML_ATTR_DATA_DISMISS, TBMConstants.CLASS_ALERT);
            attributes.put(TBMConstants.HTML_ATTR_ARIA_LABEL, TBMConstants.ARIA_LABEL_CLOSE_VALUE);
            model.add(modelFactory.createOpenElementTag(
                    TBMConstants.HTML_BUTTON,
                    attributes,
                    AttributeValueQuotes.DOUBLE,
                    false)
            );
            model.add(modelFactory.createOpenElementTag(
                    TBMConstants.HTML_SPAN,
                    TBMConstants.HTML_ATTR_ARIA_HIDDEN,
                    TBMConstants.ATTR_VALUE_TRUE)
            );
            model.add(modelFactory.createText(TBMConstants.HTML_ESCAPE_TIMES));
            model.add(modelFactory.createCloseElementTag(TBMConstants.HTML_SPAN));
            model.add(modelFactory.createCloseElementTag(TBMConstants.HTML_BUTTON));
        }
    }
}
