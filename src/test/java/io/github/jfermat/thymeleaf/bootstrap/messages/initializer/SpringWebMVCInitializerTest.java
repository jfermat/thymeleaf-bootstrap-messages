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
import org.hamcrest.collection.IsMapWithSize;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SpringWebMVCInitializerTest {

    @Test
    public void initializeModelRedirectAttributes() {

        RedirectAttributes model = mock(RedirectAttributes.class);

        SpringWebMVCInitializer springWebMVCInitializer = new SpringWebMVCInitializer(model);
        springWebMVCInitializer.initialize();

        ArgumentCaptor<String> attributeName = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object> attributeValue = ArgumentCaptor.forClass(Object.class);
        verify(model).addFlashAttribute(attributeName.capture(), attributeValue.capture());

        assertEquals(TBMConstants.MODEL_ATTRIBUTE, attributeName.getValue());
        assertThat(attributeValue.getValue(), instanceOf(ConcurrentHashMap.class));

    }

    @Test
    public void initializeModelFromRedirectAttributes() {

        MessagesUtils.clear();

        assertThat(MessagesUtils.messages(), IsMapWithSize.anEmptyMap());
        Model model = new ExtendedModelMap();

        ConcurrentHashMap<String, MessageContext> attributesFromRedirect = new ConcurrentHashMap<>();
        attributesFromRedirect.put("fromRedirect", new MessageContext());
        model.addAttribute(TBMConstants.MODEL_ATTRIBUTE, attributesFromRedirect);

        SpringWebMVCInitializer springWebMVCInitializer = new SpringWebMVCInitializer(model);
        springWebMVCInitializer.initialize();

        assertThat(MessagesUtils.messages(), not(IsMapWithSize.anEmptyMap()));

    }
}
