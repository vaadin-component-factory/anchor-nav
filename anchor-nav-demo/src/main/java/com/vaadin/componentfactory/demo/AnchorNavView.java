package com.vaadin.componentfactory.demo;

/*
 * #%L
 * Vaadin Component Factory Anchor Nav Example for Vaadin 14
 * %%
 * Copyright (C) 2017 - 2018 Vaadin Ltd
 * %%
 * This program is available under Commercial Vaadin Add-On License 3.0
 * (CVALv3).
 *
 * See the file license.html distributed with this software for more
 * information about licensing.
 *
 * You should have received a copy of the CVALv3 along with this program.
 * If not, see <http://vaadin.com/license/cval-3>.
 * #L%
 */

import com.vaadin.componentfactory.AnchorNav;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.demo.DemoView;
import com.vaadin.flow.router.Route;

/**
 * Server-side component Example for the <code>vcf-anchor-nav</code> element.
 *
 * @author Vaadin Ltd
 */
@Route("anchor-nav")
public class AnchorNavView extends DemoView {

	@Override
	protected void initView() {
		basicExample();
	}

	private void basicExample() {
		// begin-source-example
		// source-example-heading: Basic Example
		AnchorNav anchorNav = new AnchorNav();
		anchorNav.setHeight("400px");
		anchorNav.setHeader(new H1("Main Header"));
		anchorNav.addSection("First", new Span("Section 1"));
		anchorNav.addSection("Section 2", new Span("Section 2"));
		anchorNav.addSection(new Span("Section 3")).setHeader(new H1("Custom Header"));
		anchorNav.addSection("Section 4", new Span("Section 4"));
		anchorNav.addSection(new Span("Section 5"));

		// end-source-example
		addCard("Basic Example", anchorNav);
	}
}
