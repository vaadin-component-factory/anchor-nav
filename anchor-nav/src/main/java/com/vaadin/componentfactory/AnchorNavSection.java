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

import com.vaadin.componentfactory.util.SlotHelper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.tabs.Tab;

@Tag("vcf-anchor-nav-section")
public class AnchorNavSection extends HtmlContainer {

	public AnchorNavSection(String title, Component... components) {
		setSectionTitle(title);
		add(components);
	}
	
	public AnchorNavSection(Tab titleTab, Component... components) {
		setSectionTab(titleTab);
		add(components);
	}

	/**
	 * Sets the section title which is displayed in tab panel
	 */
	public void setSectionTitle(String title) {
		getElement().setAttribute("name", title);
	}

	/**
	 * Sets the section tab which is displayed in tab panel
	 */
	public void setSectionTab(Tab tab) {
		SlotHelper.clearSlot(getElement(), "tab");
		
		if (tab != null) {
			tab.getElement().setAttribute("slot", "tab");
			getElement().appendChild(tab.getElement());
		}
	}

	/**
	 * Sets the section header
	 */
	public void setSectionHeader(Component header) {
		SlotHelper.clearSlot(getElement(), "header");

		if (header != null) {
			header.getElement().setAttribute("slot", "header");
			getElement().appendChild(header.getElement());
		}
	}

}
