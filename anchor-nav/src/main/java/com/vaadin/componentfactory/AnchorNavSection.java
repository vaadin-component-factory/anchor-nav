package com.vaadin.componentfactory;

import com.vaadin.componentfactory.util.SlotHelper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;

@Tag("vcf-anchor-nav-section")
public class AnchorNavSection extends HtmlContainer {

	public AnchorNavSection(String title, Component... components) {
		setSectionTitle(title);
		add(components);
	}

	/**
	 * Sets the section title which is displayed in tab panel
	 */
	public void setSectionTitle(String title) {
		getElement().setAttribute("name", title);
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
