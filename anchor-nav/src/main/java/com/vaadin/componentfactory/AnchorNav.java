package com.vaadin.componentfactory;

import com.vaadin.componentfactory.util.SlotHelper;
import com.vaadin.flow.component.Component;
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
@NpmPackage(value = "@vaadin-component-factory/vcf-anchor-nav", version = "1.0.4")
@JsModule("@vaadin-component-factory/vcf-anchor-nav")
@SuppressWarnings("serial")
public class AnchorNav extends HtmlContainer {

	/**
	 * Adds a section created from the given title and content.
	 */
	public AnchorNavSection addSection(String title, Component... components) {
		final AnchorNavSection section = new AnchorNavSection(title, components);
		add(section);
		return section;
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
