package com.vaadin.componentfactory;

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
@NpmPackage(value = "@vaadin-component-factory/vcf-anchor-nav", version = "1.0.15")
@JsModule("@vaadin-component-factory/vcf-anchor-nav")
@SuppressWarnings("serial")
public class AnchorNav extends HtmlContainer implements HasTheme {

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
