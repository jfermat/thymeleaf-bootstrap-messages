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

package io.github.jfermat.thymeleaf.bootstrap.messages.util;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.fail;

public class TBMConstantsTest {
    @Test(expected= InvocationTargetException.class)
    public void newInstance() throws Exception {
        Constructor<TBMConstants> c = TBMConstants.class.getDeclaredConstructor();
        c.setAccessible(true);
        c.newInstance();
        fail("MessagesUtils class constructor should be private");
    }
}
