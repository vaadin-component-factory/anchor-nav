package com.vaadin.componentfactory;

/*
 * #%L
 * Anchor Nav for Flow
 * %%
 * Copyright (C) 2020 Vaadin Ltd
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.componentfactory.util.SlotHelper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.H2;

/**
 * Server-side component for the <code>vcf-anchor-nav</code> element.
 *
 * @author Vaadin Ltd
 */
@Tag("vcf-anchor-nav")
@NpmPackage(value = "@vaadin-component-factory/vcf-anchor-nav", version = "1.1.4")
@JsModule("@vaadin-component-factory/vcf-anchor-nav")
@SuppressWarnings("serial")
public class AnchorNav extends HtmlContainer implements HasTheme {

	/**
	 *  Adds a section created from the given title and content.
	 *
	 * @param title title of the section
	 * @param components content
	 *
	 * @return the section
	 */
	public AnchorNavSection addSection(String title, Component... components) {
		final AnchorNavSection section = new AnchorNavSection(title, components);
		add(section);
		return section;
	}

	/**
	 * Set the header text as a html h2 title
	 *
	 * @param headerText text of the header
	 */
	public void setHeaderText(String headerText) {
		if (headerText == null) {
			headerText = "";
		}
		setHeader(new H2(headerText));
	}

	/**
	 * Sets the component header
	 *
	 * @param header component to add as a header
	 */
	public void setHeader(Component header) {
		SlotHelper.clearSlot(getElement(), "header");

		if (header != null) {
			header.getElement().setAttribute("slot", "header");
			getElement().appendChild(header.getElement());
		}
	}
	
	/**
     * Adds theme variants to the component.
     *
     * @param variants
     *            theme variants to add
     */
    public void addThemeVariants(AnchorNavVariant... variants) {
        getThemeNames()
                .addAll(Stream.of(variants).map(AnchorNavVariant::getVariantName)
                        .collect(Collectors.toList()));
    }
}
