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

package io.github.jfermat.thymeleaf.bootstrap.messages;

import io.github.jfermat.messages.MessagesUtils;
import io.github.jfermat.thymeleaf.bootstrap.messages.initializer.SpringWebMVCInitializer;
import io.github.jfermat.thymeleaf.bootstrap.messages.util.TBMConstants;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.fail;

public class BootstrapMessagesTest {

    @Test(expected= InvocationTargetException.class)
    public void newInstance() throws Exception {
        Constructor<BootstrapMessages> c = BootstrapMessages.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
        fail("MessagesUtils class constructor should be private");
    }

    @Test
    public void init() {
        Model model = new ExtendedModelMap();
        BootstrapMessages.init(new SpringWebMVCInitializer(model));
        Assert.assertNotNull(model.getAttribute(TBMConstants.MODEL_ATTRIBUTE));
    }

    @Test
    public void defaultContextTest() {
        Assert.assertFalse(MessagesUtils.messages().containsKey("default"));
        BootstrapMessages.defaultContext();
        Assert.assertTrue(MessagesUtils.messages().containsKey("default"));
    }

    @Test
    public void context() {
        Assert.assertFalse(MessagesUtils.messages().containsKey("test"));
        BootstrapMessages.context("test");
        Assert.assertTrue(MessagesUtils.messages().containsKey("test"));
    }

}
