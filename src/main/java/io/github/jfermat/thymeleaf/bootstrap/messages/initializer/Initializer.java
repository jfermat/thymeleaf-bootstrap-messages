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

/**
 * Interface to a message storage engine.
 */
public interface Initializer {

    /**
     * <p>Initialiser of where messages will be stored.</p>
     *
     * <p>Messages are stored by the next method:</p>
     * <code>io.github.jfermat.messages.MessagesUtils.MessagesUtils.messages().</code>
     * @see <a href="https://jfermat.github.io/docs/messages-utils/io/github/jfermat/messages/MessagesUtils.html">Documentation of MessageUtils</a>
     */
    void initialize();
}
