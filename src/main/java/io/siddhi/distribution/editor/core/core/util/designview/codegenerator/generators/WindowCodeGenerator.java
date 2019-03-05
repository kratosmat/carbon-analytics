/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.siddhi.distribution.editor.core.core.util.designview.codegenerator.generators;

import io.siddhi.distribution.editor.core.core.util.designview.beans.configs.siddhielements.WindowConfig;
import io.siddhi.distribution.editor.core.core.util.designview.constants.CodeGeneratorConstants;
import io.siddhi.distribution.editor.core.core.util.designview.constants.SiddhiCodeBuilderConstants;
import io.siddhi.distribution.editor.core.core.util.designview.exceptions.CodeGenerationException;
import io.siddhi.distribution.editor.core.core.util.designview.utilities.CodeGeneratorUtils;

/**
 * Generates the code for a Siddhi window element
 */
public class WindowCodeGenerator {

    /**
     * Generates the Siddhi code representation of a WindowConfig object
     *
     * @param window The WindowConfig object
     * @param isGeneratingToolTip If it is generating a tooltip or not
     * @return The Siddhi code representation of the given WindowConfig object
     * @throws CodeGenerationException Error when generating the code
     */
    public String generateWindow(WindowConfig window, boolean isGeneratingToolTip) throws CodeGenerationException {
        CodeGeneratorUtils.NullValidator.validateConfigObject(window);

        StringBuilder windowStringBuilder = new StringBuilder();
        if (!isGeneratingToolTip) {
            windowStringBuilder.append(SubElementCodeGenerator.generateComment(window.getPreviousCommentSegment()));
        }
        windowStringBuilder.append(SubElementCodeGenerator.generateAnnotations(window.getAnnotationList()))
                .append(SiddhiCodeBuilderConstants.NEW_LINE)
                .append(SiddhiCodeBuilderConstants.DEFINE_WINDOW)
                .append(SiddhiCodeBuilderConstants.SPACE)
                .append(window.getName())
                .append(SiddhiCodeBuilderConstants.NEW_LINE)
                .append(SiddhiCodeBuilderConstants.OPEN_BRACKET)
                .append(SubElementCodeGenerator.generateAttributes(window.getAttributeList()))
                .append(SiddhiCodeBuilderConstants.CLOSE_BRACKET)
                .append(SiddhiCodeBuilderConstants.NEW_LINE)
                .append(window.getType())
                .append(SiddhiCodeBuilderConstants.OPEN_BRACKET)
                .append(SubElementCodeGenerator.generateParameterList(window.getParameters()))
                .append(SiddhiCodeBuilderConstants.CLOSE_BRACKET);

        if (window.getOutputEventType() != null && !window.getOutputEventType().isEmpty()) {
            windowStringBuilder.append(SiddhiCodeBuilderConstants.NEW_LINE)
                    .append(SiddhiCodeBuilderConstants.OUTPUT)
                    .append(SiddhiCodeBuilderConstants.SPACE);
            switch (window.getOutputEventType().toUpperCase()) {
                case CodeGeneratorConstants.CURRENT_EVENTS:
                    windowStringBuilder.append(SiddhiCodeBuilderConstants.CURRENT_EVENTS);
                    break;
                case CodeGeneratorConstants.EXPIRED_EVENTS:
                    windowStringBuilder.append(SiddhiCodeBuilderConstants.EXPIRED_EVENTS);
                    break;
                case CodeGeneratorConstants.ALL_EVENTS:
                    windowStringBuilder.append(SiddhiCodeBuilderConstants.ALL_EVENTS);
                    break;
                default:
                    throw new CodeGenerationException("Unidentified window output event type: "
                            + window.getOutputEventType());
            }
        }
        windowStringBuilder.append(SiddhiCodeBuilderConstants.SEMI_COLON);

        return windowStringBuilder.toString();
    }

}
