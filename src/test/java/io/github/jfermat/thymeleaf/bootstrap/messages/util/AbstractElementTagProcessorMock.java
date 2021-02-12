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

import io.github.jfermat.messages.MessagesUtils;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.expression.IExpressionObjects;
import org.thymeleaf.model.*;
import org.thymeleaf.processor.element.IElementTagStructureHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AbstractElementTagProcessorMock {

    private final StringBuilder html = new StringBuilder();
    private final Map<String, String> tags;

    private ITemplateContext context;
    private IExpressionObjects iExpressionObjects;
    private HttpServletRequest httpServletRequest;
    private IModelFactory iModelFactory;;
    private IModel iModel;
    private IElementTagStructureHandler iElementTagStructureHandler;
    private IProcessableElementTag iProcessableElementTag;

    public AbstractElementTagProcessorMock(Map<String, String> tags) {
        this.tags = tags;

        context = mock(ITemplateContext.class);
        iExpressionObjects = mock(IExpressionObjects.class);
        httpServletRequest = mock(HttpServletRequest.class);
        iModelFactory = mock(IModelFactory.class);
        iModel = mock(IModel.class);
        iElementTagStructureHandler = mock(IElementTagStructureHandler.class);
        iProcessableElementTag = mock(IProcessableElementTag.class);

        when(context.getExpressionObjects()).thenReturn(iExpressionObjects);
        when(context.getModelFactory()).thenReturn(iModelFactory);

        when(iExpressionObjects.getObject(anyString())).thenReturn(httpServletRequest);

        when(httpServletRequest.getAttribute(anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return MessagesUtils.messages();
            }
        });

        when(httpServletRequest.getLocale()).thenAnswer(new Answer<Locale>() {
            @Override
            public Locale answer(InvocationOnMock invocation) throws Throwable {
                return Locale.getDefault();
            }
        });

        when(iModelFactory.createModel()).thenReturn(iModel);

        when(iModelFactory.createOpenElementTag(anyString(), anyString(),anyString()))
                .thenAnswer(new Answer<IModelFactory>() {
            @Override
            public IModelFactory answer(InvocationOnMock invocation) throws Throwable {
                html.append("<")
                        .append(String.valueOf(invocation.getArguments()[0]))
                        .append(" ")
                        .append(String.valueOf(invocation.getArguments()[1]))
                        .append("\"")
                        .append(String.valueOf(invocation.getArguments()[2]))
                        .append("\"")
                        .append(">");
                return null;
            }
        });

        when(iModelFactory.createOpenElementTag(anyString(), anyMap(), any(AttributeValueQuotes.class), anyBoolean()))
            .thenAnswer(new Answer<IOpenElementTag>() {
            @Override
            public IOpenElementTag answer(InvocationOnMock invocation) throws Throwable {
                html.append("<").append(String.valueOf(invocation.getArguments()[0]));
                Map<String, String> attributes = (Map<String, String>) invocation.getArguments()[1];
                for (Map.Entry<String, String> attribute : attributes.entrySet()) {
                    html.append(" ")
                            .append(String.valueOf(attribute.getKey()))
                            .append("\"")
                            .append(String.valueOf(attribute.getValue()))
                            .append("\"");
                }
                html.append(">");
                return null;
            }
        });

        when(iModelFactory.createText(anyString())).thenAnswer(new Answer<IModelFactory>() {
            @Override
            public IModelFactory answer(InvocationOnMock invocation) throws Throwable {
                html.append(String.valueOf(invocation.getArguments()[0]));
                return null;
            }
        });

        when(iModelFactory.createCloseElementTag(anyString())).thenAnswer(new Answer<IModelFactory>() {
            @Override
            public IModelFactory answer(InvocationOnMock invocation) throws Throwable {
                html.append("</")
                        .append(String.valueOf(invocation.getArguments()[0]))
                        .append(">");
                return null;
            }
        });



        when(iProcessableElementTag.getAttributeValue(anyString())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                return tags.get(String.valueOf(invocation.getArguments()[0]));
            }
        });

    }

    public StringBuilder getHtml() {
        return html;
    }

    public ITemplateContext getContext() {
        return context;
    }

    public IExpressionObjects getiExpressionObjects() {
        return iExpressionObjects;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public IModelFactory getiModelFactory() {
        return iModelFactory;
    }

    public IModel getiModel() {
        return iModel;
    }

    public IElementTagStructureHandler getiElementTagStructureHandler() {
        return iElementTagStructureHandler;
    }

    public IProcessableElementTag getiProcessableElementTag() {
        return iProcessableElementTag;
    }
}
