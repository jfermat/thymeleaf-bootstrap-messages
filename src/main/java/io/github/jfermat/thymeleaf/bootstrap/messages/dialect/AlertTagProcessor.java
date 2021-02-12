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

import io.github.jfermat.thymeleaf.bootstrap.messages.components.AlertsComponent;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;


/**
 * The type Alert tag processor.
 */
public class AlertTagProcessor extends AbstractElementTagProcessor {

    private static final String TAG_NAME = "alerts";
    private static final int PRECEDENCE = 1000;


    /**
     * Instantiates a new Alert tag processor.
     *
     * @param dialectPrefix the dialect prefix
     */
    public AlertTagProcessor(final String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, TAG_NAME, true, null, false, PRECEDENCE);
    }

    @Override
    protected void doProcess(final ITemplateContext context, final IProcessableElementTag tag, final IElementTagStructureHandler structureHandler) {
        AlertsComponent alertComponent = new AlertsComponent(context, tag);
        structureHandler.replaceWith(alertComponent.render(), false);
    }

}
