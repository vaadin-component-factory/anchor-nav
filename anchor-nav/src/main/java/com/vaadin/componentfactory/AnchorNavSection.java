package com.vaadin.componentfactory;

import com.vaadin.componentfactory.util.SlotHelper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.H2;

@Tag("vcf-anchor-nav-section")
public class AnchorNavSection extends HtmlContainer {

	public AnchorNavSection(Component... components) {
		add(components);
	}
	public AnchorNavSection(String header, Component... components) {
		setHeaderText(header);
		add(components);
	}

	public void setHeaderText(String headerText) {
		if (headerText == null) {
			headerText = "";
		}
		setHeader(new H2(headerText));
	}

	/**
	 * Sets the component header
	 */
	public void setHeader(Component header) {
		SlotHelper.clearSlot(getElement(), "header");

		if (header != null) {
			header.getElement().setAttribute("slot", "header");
			getElement().appendChild(header.getElement());
		}
	}

}
