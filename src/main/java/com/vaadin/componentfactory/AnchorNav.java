package com.vaadin.componentfactory;

/*
 * #%L
 * Anchor Nav for Flow
 * %%
 * Copyright (C) 2020 - 2026 Vaadin Ltd
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
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Server-side component for the <code>vcf-anchor-nav</code> element.
 *
 * @author Vaadin Ltd
 */
@Tag("vcf-anchor-nav")
@NpmPackage(value = "@vaadin-component-factory/vcf-anchor-nav", version = "4.0.0")
@JsModule("@vaadin-component-factory/vcf-anchor-nav")
@SuppressWarnings("serial")
public class AnchorNav extends HtmlContainer implements HasTheme {

	public AnchorNav() {
		this(false);
	}

	public AnchorNav(boolean noHistory) {
		setNoHistory(noHistory);
	}

	/**
	 * Toggles "no history" mode for the anchor. Setting the property for a single AnchorNav
	 * enabled "no history" mode for the entire view.
	 * @param noHistory
	 */
	public void setNoHistory(boolean noHistory) {
		getElement().setProperty("noHistory", noHistory);
	}

	/**
	 * Adds a section created from the given title and content.
	 *
	 * @param title      title of the section
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
	 * Adds a section created from the given Tab and content.
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
	 *                 theme variants to add
	 */
	public void addThemeVariants(AnchorNavVariant... variants) {
		getThemeNames()
				.addAll(Stream.of(variants).map(AnchorNavVariant::getVariantName)
						.collect(Collectors.toList()));
	}

	/**
	 * The most recent index passed to {@link #setSelectedSection(int)}. Used by
	 * {@link #scrollToSelectedSection()} to re-align to the section the caller
	 * intended, independent of the client-side {@code selectedIndex} which may
	 * drift as the user (or the intersection observer) changes the active tab.
	 */
	private Integer lastSelectedIndex;

	/**
	 * Selects a section based on its zero-based index.
	 *
	 * @param selectedIndex
	 *                      the zero-based index of the selected section
	 */
	public void setSelectedSection(int selectedIndex) {
		this.lastSelectedIndex = selectedIndex;
		getElement().callJsFunction("_setSelectedSection", selectedIndex);
	}

	/**
	 * Scrolls the given section to the top of the navigation area without
	 * changing which tab is selected.
	 * <p>
	 * This is the recommended way to <em>re-align</em> after content above the
	 * target has finished loading or resizing. A section's scroll position is an
	 * absolute pixel offset computed when {@link #setSelectedSection(int)} runs;
	 * if content above it then changes height (for example lazy-loaded grids or
	 * images), that offset becomes stale and the view ends up at the wrong
	 * section. Call this once the content has settled &mdash; typically from a
	 * listener on a "content loaded" event fired by your loading logic &mdash; to
	 * correct the alignment deterministically:
	 *
	 * <pre>
	 * AnchorNavSection details = anchorNav.addSection("Details", ...);
	 * anchorNav.setSelectedSection(details);
	 * // when the lazy-loaded content reports it is ready:
	 * anchorNav.scrollToSection(details);
	 * </pre>
	 *
	 * @param section
	 *                the section to scroll to, not {@code null}
	 * @throws IllegalArgumentException
	 *                                  if {@code section} is not a child of this
	 *                                  component
	 */
	public void scrollToSection(AnchorNavSection section) {
		int index = indexOf(section);
		if (index < 0) {
			throw new IllegalArgumentException(
					"Section to scroll to must be a child: " + section);
		}
		scrollToSection(index);
	}

	/**
	 * Scrolls the section with the given zero-based index to the top of the
	 * navigation area without changing which tab is selected. See
	 * {@link #scrollToSection(AnchorNavSection)} for when and why to use this.
	 *
	 * @param sectionIndex
	 *                     the zero-based index of the section to scroll to
	 */
	public void scrollToSection(int sectionIndex) {
		getElement().executeJs("this._scrollToSection($0, false, false);",
				sectionIndex);
	}

	/**
	 * Re-scrolls to the section most recently selected with
	 * {@link #setSelectedSection(int)} or {@link #setSelectedSection(AnchorNavSection)}.
	 * <p>
	 * Convenience for the common case where you want to re-align to the
	 * programmatically selected section without keeping a reference to it &mdash;
	 * for example from a "content loaded" listener after lazy-loaded content above
	 * it has settled. Equivalent to calling {@link #scrollToSection(int)} with the
	 * last selected index. Has no effect if no section has been selected
	 * programmatically yet.
	 */
	public void scrollToSelectedSection() {
		if (lastSelectedIndex != null) {
			scrollToSection(lastSelectedIndex);
		}
	}

	/**
	 * Registers a listener that re-aligns the view to the currently selected
	 * section (see {@link #scrollToSelectedSection()}) whenever a DOM event with
	 * the given name is fired on, or bubbles up to, this component.
	 * <p>
	 * This is the recommended way to correct the scroll position after content
	 * above the selected section finishes loading or resizing, without having to
	 * wire a listener through {@link #getElement()} yourself: have your loading
	 * logic dispatch a (bubbling) DOM event once the content has settled, and
	 * pass its name here.
	 *
	 * <pre>
	 * anchorNav.setSelectedSection(details);
	 * anchorNav.addRealignOnEvent("content-loaded");
	 * // on the client, once lazy loading is done, e.g.:
	 * // grid.dispatchEvent(new CustomEvent('content-loaded', { bubbles: true }));
	 * </pre>
	 *
	 * @param eventName
	 *                  the name of the DOM event that signals content is ready,
	 *                  not {@code null}
	 * @return a registration for removing the listener
	 */
	public Registration addRealignOnEvent(String eventName) {
		Objects.requireNonNull(eventName, "eventName cannot be null");
		return getElement().addEventListener(eventName,
				event -> scrollToSelectedSection());
	}

	/**
	 * Selects the given section.
	 *
	 * @param selectedSection
	 *                        the section to select, not <code>null</code>
	 * @throws IllegalArgumentException
	 *                                  if {@code selectedSection} is not a child of
	 *                                  this component
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
	 *                 the listener to add, not <code>null</code>
	 * @return a handle that can be used for removing the listener
	 */
	public Registration addSelectedSectionChangedListener(ComponentEventListener<SelectedSectionChangedEvent> listener) {
		return addListener(SelectedSectionChangedEvent.class, listener);
	}

	@DomEvent("selected-changed")
	public static class SelectedSectionChangedEvent extends ComponentEvent<AnchorNav> {
		private int sectionIndex;

		public SelectedSectionChangedEvent(AnchorNav source, boolean fromClient,
				@EventData("event.detail.index") int index) {
			super(source, fromClient);
			this.sectionIndex = index;
		}

		public int getSectionIndex() {
			return sectionIndex;
		}
	}
}
