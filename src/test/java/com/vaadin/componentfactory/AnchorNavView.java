package com.vaadin.componentfactory;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

/**
 * Server-side component Example for the <code>vcf-anchor-nav</code> element.
 *
 * @author Vaadin Ltd
 */
@Route("")
public class AnchorNavView extends VerticalLayout {

    public AnchorNavView() {
        basicExample();
    }

    private void basicExample() {
        setPadding(false);

        AnchorNav anchorNav = new AnchorNav();
        anchorNav.setHeightFull();
        anchorNav.addThemeVariants(AnchorNavVariant.EXPAND_LAST);
        anchorNav.setHeader(new H1("Main Header"));
        anchorNav.setNoHistory(true);

        Div introductionContent = new Div(
                new Text("Scroll down to see:"),
                new UnorderedList(
                        new ListItem("Tabs sticking to the top"),
                        new ListItem("Tab of visible section being highlighted"),
                        new ListItem("Tabs horizontally scrolling when overflowing")),
                new Text("Click on a tab to jump to section."));
        Div accessibilityContent = new Div(
                new Text("Accessibility has been taken seriously when implementing the component."),
                new OrderedList(
                        new ListItem("Try focusing tabs using keyboard, move focus with arrow keys"),
                        new ListItem("Hit ENTER or SPACE to jump to sections"),
                        new ListItem("Move focus to this text field by hitting TAB")),
                new TextField());

        anchorNav.addSection("Introduction", introductionContent);
        anchorNav.addSection("Accessibility", accessibilityContent);

        AnchorNavSection customHeaderSection = anchorNav.addSection("Custom Tab Name",
                new Span("Headers and tab names are set automatically. You can customize them to your needs."));
        customHeaderSection.setSectionHeader(new H1("Custom Header"));

        AnchorNavSection shortSection = anchorNav.addSection("Section 4", new Span("Section 4 is short."));
        shortSection.setHeight("20vh");

        AnchorNavSection longSection = anchorNav.addSection("Section 5", new Span("Section 5 is long."));
        longSection.setHeight("120vh");

        AnchorNavSection lastSection = anchorNav.addSection("Section 6", new Span("Section 6 content."));

        Span selectedIndexLabel = new Span();
        anchorNav.addSelectedSectionChangedListener(
                evt -> selectedIndexLabel.setText("Selected section index: " + evt.getSectionIndex()));
        Button selectLastSection = new Button("Select last section",
                evt -> anchorNav.setSelectedSection(lastSection));

        HorizontalLayout selectionLine = new HorizontalLayout(selectLastSection, selectedIndexLabel);
        selectionLine.setAlignItems(Alignment.BASELINE);
        selectionLine.setPadding(true);
        selectionLine.setMargin(true);
        selectionLine.getStyle().set("position", "fixed");
        selectionLine.getStyle().set("bottom", "0");
        selectionLine.getStyle().set("right", "0");
        selectionLine.getStyle().set("background", "var(--lumo-base-color)");
        selectionLine.getStyle().set("box-shadow", "var(--lumo-box-shadow-m)");

        add(new RouterLink("Go to second view", SecondView.class), anchorNav, selectionLine);
    }
}