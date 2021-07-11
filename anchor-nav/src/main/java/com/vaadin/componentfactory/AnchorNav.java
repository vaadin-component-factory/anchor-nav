package com.vaadin.componentfactory;

import java.util.Iterator;

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
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.shared.Registration;

/**
 * Server-side component for the <code>vcf-anchor-nav</code> element.
 *
 * @author Vaadin Ltd
 */
@Tag("vcf-anchor-nav")
@NpmPackage(value = "@vaadin-component-factory/vcf-anchor-nav", version = "1.2.11")
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
	 *  Adds a section created from the given Tab and content.
	 *
	 * @param sectionTab Tab of the section
	 * @param components content
	 *
	 * @return the section
	 */
	public AnchorNavSection addSection(Tab sectionTab, Component... components) {
		final AnchorNavSection section = new AnchorNavSection(sectionTab, components);
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
    
    /**
     * Selects a section based on its zero-based index.
     *
     * @param selectedIndex
     *            the zero-based index of the selected section
     */
    public void setSelectedSection(int selectedIndex) {
        getElement().callJsFunction("_setSelectedSection", selectedIndex);
    }
    
    /**
     * Selects the given section.
     *
     * @param selectedSection
     *            the section to select, not <code>null</code>
     * @throws IllegalArgumentException
     *             if {@code selectedSection} is not a child of this component
     */
    public void setSelectedSection(AnchorNavSection selectedSection) {
        int selectedIndex = indexOf(selectedSection);
        if (selectedIndex < 0) {
            throw new IllegalArgumentException(
                    "Section to select must be a child: " + selectedSection);
        }
        setSelectedSection(selectedIndex);
    }
    
    private int indexOf(AnchorNavSection selectedSection) {
        if (selectedSection == null) {
            throw new IllegalArgumentException(
                    "The 'selectedSection' parameter cannot be null");
        }
        Iterator<AnchorNavSection> it = this.getChildren()
        		.filter(AnchorNavSection.class::isInstance)
        		.map(AnchorNavSection.class::cast)
        		.sequential()
        		.iterator();
        int index = 0;
        while (it.hasNext()) {
        	AnchorNavSection section = it.next();
            if (section.equals(selectedSection)) {
                return index;
            }
            index++;
        }
        return -1;
	}

	/**
     * Adds a listener for {@link SelectedSectionChangedEvent}.
     *
     * @param listener
     *            the listener to add, not <code>null</code>
     * @return a handle that can be used for removing the listener
     */
    public Registration addSelectedSectionChangedListener(ComponentEventListener<SelectedSectionChangedEvent> listener) {
        return addListener(SelectedSectionChangedEvent.class, listener);
    }
    
    @DomEvent("selected-changed")
	public static class SelectedSectionChangedEvent extends ComponentEvent<AnchorNav> {
		private int sectionIndex;

		public SelectedSectionChangedEvent(AnchorNav source, boolean fromClient, @EventData("event.detail.index") int index) {
			super(source, fromClient);
			this.sectionIndex = index;
		}

		public int getSectionIndex() {
			return sectionIndex;
		}
	}
}
