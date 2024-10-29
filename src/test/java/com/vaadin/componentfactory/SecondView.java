package com.vaadin.componentfactory;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("second")
public class SecondView extends VerticalLayout {

    public SecondView() {
        basicExample();
    }

    private void basicExample() {
        setPadding(false);

        AnchorNav anchorNav = new AnchorNav();
        anchorNav.setHeightFull();
        anchorNav.addThemeVariants(AnchorNavVariant.EXPAND_LAST);
        anchorNav.setHeader(new H1("Second View"));

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


        add(new RouterLink("Back to AnchorNavView", AnchorNavView.class), anchorNav);
    }
}