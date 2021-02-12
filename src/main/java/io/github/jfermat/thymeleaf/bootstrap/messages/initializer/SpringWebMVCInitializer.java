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

package io.github.jfermat.thymeleaf.bootstrap.messages.initializer;

import io.github.jfermat.messages.MessageContext;
import io.github.jfermat.messages.MessagesUtils;
import io.github.jfermat.thymeleaf.bootstrap.messages.util.TBMConstants;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

/**
 * Spring web MVC initializer.
 */
public class SpringWebMVCInitializer implements Initializer {

    private final Model model;

    /**
     * Instantiates a new Spring web mvc initializer.
     *
     * @param model the model
     */
    public SpringWebMVCInitializer(Model model) {
        this.model = model;
    }

    @Override
    public void initialize() {
        if (model instanceof RedirectAttributes) {
            ((RedirectAttributes) model).addFlashAttribute(TBMConstants.MODEL_ATTRIBUTE, MessagesUtils.messages());
        } else  {
            Map<String, MessageContext> redirectAttributes = null;
            if (model.getAttribute(TBMConstants.MODEL_ATTRIBUTE) != null) {
                redirectAttributes = (Map<String, MessageContext>) model.getAttribute(TBMConstants.MODEL_ATTRIBUTE);
            }
            model.addAttribute(TBMConstants.MODEL_ATTRIBUTE, MessagesUtils.messages());

            if (redirectAttributes != null && !redirectAttributes.isEmpty()) {
                MessagesUtils.messages().putAll(redirectAttributes);
            }
        }
    }
}
