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

import io.github.jfermat.messages.MessageContext;
import io.github.jfermat.messages.MessagesUtils;
import io.github.jfermat.thymeleaf.bootstrap.messages.initializer.Initializer;

/**
 * The type Bootstrap messages.
 */
public final class BootstrapMessages {

    private BootstrapMessages() {
        throw new UnsupportedOperationException();
    }

    /**
     * Init.
     *
     * @param initializer the initializer
     */
    public static void init(Initializer initializer) {
        initializer.initialize();
    }

    /**
     * Get default context.
     *
     * @return the message context
     * @see <a href="https://jfermat.github.io/docs/messages-utils/io/github/jfermat/messages/MessageContext.html">Documentation of MessageContext</a>
     */
    public static MessageContext defaultContext() {
        return MessagesUtils.defaultContext();
    }


    /**
     * Get context by name.
     *
     * @param contextName the context name
     * @return the message context
     * @see <a href="https://jfermat.github.io/docs/messages-utils/io/github/jfermat/messages/MessageContext.html">Documentation of MessageContext</a>
     */
    public static MessageContext context(String contextName) {
        return MessagesUtils.context(contextName);
    }

}
