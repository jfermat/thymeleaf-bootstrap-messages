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

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Bootstrap messages abstract dialect.
 */
public abstract class BootstrapMessagesAbstractDialect extends AbstractProcessorDialect {

    /**
     * Instantiates a new Bootstrap messages dialect.
     *
     * @param prefix              the prefix
     * @param processorPrecedence the processor precedence
     */
    public BootstrapMessagesAbstractDialect(String prefix, int processorPrecedence) {
        super("Thymeleaf Bootstrap Messages Dialect", prefix, processorPrecedence);
    }

    @Override
    public Set<IProcessor> getProcessors(String s) {
        final Set<IProcessor> processors = new HashSet<>();
        processors.add(new AlertTagProcessor(getPrefix()));
        return processors;
    }

}
