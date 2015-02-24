/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2014, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.thymeleaf.aurora.engine;

import java.io.Writer;

import org.thymeleaf.exceptions.TemplateOutputException;


/**
 *
 * @author Daniel Fern&aacute;ndez
 * @since 3.0.0
 *
 */
public final class OutputTemplateHandler extends AbstractTemplateHandler {


    private final String templateName;
    private final Writer writer;



    /**
     * <p>
     *   Creates a new instance of this handler.
     * </p>
     *
     * @param templateName the name of the template being processed
     * @param writer the writer to which output will be written.
     */
    public OutputTemplateHandler(final String templateName, final Writer writer) {
        super();
        if (writer == null) {
            throw new IllegalArgumentException("Writer cannot be null");
        }
        this.writer = writer;
        this.templateName = templateName;
    }





    @Override
    public void handleText(final char[] buffer, final int offset, final int len, final int line, final int col) {
        
        try {
            MarkupOutput.writeText(this.writer, buffer, offset, len);
        } catch (final Exception e) {
            throw new TemplateOutputException("An error happened during template rendering", this.templateName, line, col, e);
        }

        // Just in case someone set us a 'next'
        super.handleText(buffer, offset, len, line, col);

    }



    @Override
    public void handleComment(
            final char[] buffer, 
            final int contentOffset, final int contentLen, 
            final int outerOffset, final int outerLen, 
            final int line, final int col) {
        
        try {
            MarkupOutput.writeComment(this.writer, buffer, outerOffset, outerLen);
        } catch (final Exception e) {
            throw new TemplateOutputException("An error happened during template rendering", this.templateName, line, col, e);
        }

        // Just in case someone set us a 'next'
        super.handleComment(buffer, contentOffset, contentLen, outerOffset, outerLen, line, col);

    }

    
    @Override
    public void handleCDATASection(
            final char[] buffer, 
            final int contentOffset, final int contentLen,
            final int outerOffset, final int outerLen,
            final int line, final int col) {
        
        try {
            MarkupOutput.writeCDATASection(this.writer, buffer, outerOffset, outerLen);
        } catch (final Exception e) {
            throw new TemplateOutputException("An error happened during template rendering", this.templateName, line, col, e);
        }

        // Just in case someone set us a 'next'
        super.handleCDATASection(buffer, contentOffset, contentLen, outerOffset, outerLen, line, col);

    }




    @Override
    public void handleStandaloneElement(
            final ElementDefinition elementDefinition,
            final String elementName,
            final ElementAttributes elementAttributes,
            final boolean minimized,
            final int line, final int col) {

        try {
            MarkupOutput.writeStandaloneElement(this.writer, elementName, elementAttributes, minimized);
        } catch (final Exception e) {
            throw new TemplateOutputException("An error happened during template rendering", this.templateName, line, col, e);
        }

        // Just in case someone set us a 'next'
        super.handleStandaloneElement(elementDefinition, elementName, elementAttributes, minimized, line, col);

    }


    @Override
    public void handleOpenElement(
            final ElementDefinition elementDefinition,
            final String elementName,
            final ElementAttributes elementAttributes,
            final int line, final int col) {

        try {
            MarkupOutput.writeOpenElement(this.writer, elementName, elementAttributes);
        } catch (final Exception e) {
            throw new TemplateOutputException("An error happened during template rendering", this.templateName, line, col, e);
        }

        // Just in case someone set us a 'next'
        super.handleOpenElement(elementDefinition, elementName, elementAttributes, line, col);

    }


    @Override
    public void handleAutoOpenElement(
            final ElementDefinition elementDefinition,
            final String elementName,
            final ElementAttributes elementAttributes,
            final int line, final int col) {

        try {
            MarkupOutput.writeAutoOpenElement(this.writer, elementName, elementAttributes);
        } catch (final Exception e) {
            throw new TemplateOutputException("An error happened during template rendering", this.templateName, line, col, e);
        }

        // Just in case someone set us a 'next'
        super.handleAutoOpenElement(elementDefinition, elementName, elementAttributes, line, col);

    }


    @Override
    public void handleCloseElement(
            final ElementDefinition elementDefinition,
            final String elementName,
            final int line, final int col) {

        try {
            MarkupOutput.writeCloseElement(this.writer, elementName);
        } catch (final Exception e) {
            throw new TemplateOutputException("An error happened during template rendering", this.templateName, line, col, e);
        }

        // Just in case someone set us a 'next'
        super.handleCloseElement(elementDefinition, elementName, line, col);

    }


    @Override
    public void handleAutoCloseElement(
            final ElementDefinition elementDefinition,
            final String elementName,
            final int line, final int col) {

        try {
            MarkupOutput.writeAutoCloseElement(this.writer, elementName);
        } catch (final Exception e) {
            throw new TemplateOutputException("An error happened during template rendering", this.templateName, line, col, e);
        }

        // Just in case someone set us a 'next'
        super.handleAutoCloseElement(elementDefinition, elementName, line, col);

    }


    @Override
    public void handleUnmatchedCloseElement(
            final ElementDefinition elementDefinition,
            final String elementName,
            final int line, final int col) {

        // We will write exactly the same as for non-unmatched close elements, because that does not matter from the markup point
        try {
            MarkupOutput.writeUnmatchedCloseElement(this.writer, elementName);
        } catch (final Exception e) {
            throw new TemplateOutputException("An error happened during template rendering", this.templateName, line, col, e);
        }

        // Just in case someone set us a 'next'
        super.handleUnmatchedCloseElement(elementDefinition, elementName, line, col);

    }




    @Override
    public void handleDocType(
            final String docType,
            final String keyword,
            final String elementName,
            final String type,
            final String publicId,
            final String systemId,
            final String internalSubset,
            final int line, final int col) {
        
        try {
            MarkupOutput.writeDocType(this.writer, docType);
        } catch (final Exception e) {
            throw new TemplateOutputException("An error happened during template rendering", this.templateName, line, col, e);
        }

        // Just in case someone set us a 'next'
        super.handleDocType(docType, keyword, elementName, type, publicId, systemId, internalSubset, line, col);

    }

    
    
    
    @Override
    public void handleXmlDeclaration(
            final String xmlDeclaration,
            final String keyword,
            final String version,
            final String encoding,
            final String standalone,
            final int line, final int col) {

        try {
            MarkupOutput.writeXmlDeclaration(this.writer, xmlDeclaration);
        } catch (final Exception e) {
            throw new TemplateOutputException("An error happened during template rendering", this.templateName, line, col, e);
        }

        // Just in case someone set us a 'next'
        super.handleXmlDeclaration(xmlDeclaration, keyword, version, encoding, standalone, line, col);

    }






    @Override
    public void handleProcessingInstruction(
            final String processingInstruction,
            final String target,
            final String content,
            final int line, final int col) {
        
        try {
            MarkupOutput.writeProcessingInstruction(this.writer, processingInstruction);
        } catch (final Exception e) {
            throw new TemplateOutputException("An error happened during template rendering", this.templateName, line, col, e);
        }

        // Just in case someone set us a 'next'
        super.handleProcessingInstruction(processingInstruction, target, content, line, col);

    }


    
    
}