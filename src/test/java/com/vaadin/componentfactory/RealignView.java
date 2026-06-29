package com.vaadin.componentfactory;

import java.util.List;
import java.util.stream.LongStream;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

/**
 * Test view reproducing the scenario where content above a programmatically
 * selected section changes size <em>after</em> the initial scroll (lazy-loaded
 * grids whose height settles a bit later). It is used to verify that:
 * <ul>
 * <li>{@link AnchorNav#addRealignOnEvent(String)} +
 * {@link AnchorNav#scrollToSelectedSection()} keep the selected section aligned
 * once the content reports it has settled (the {@code content-loaded} event),
 * instead of the view jumping to the last section, and</li>
 * <li>{@link AnchorNav#scrollToSection(AnchorNavSection)} scrolls to a specific
 * section on demand.</li>
 * </ul>
 * Mirrors the AnchorNavSample reproduction app.
 *
 * @author Vaadin Ltd
 */
@Route("realign")
public class RealignView extends VerticalLayout implements BeforeEnterObserver {

    private final AnchorNav anchorNav;

    public RealignView() {
        setPadding(false);

        anchorNav = new AnchorNav();
        anchorNav.setHeightFull();
        anchorNav.addThemeVariants(AnchorNavVariant.EXPAND_LAST);
        anchorNav.setHeader(new H1("Header"));
        anchorNav.setNoHistory(true);

        PersonService service = new PersonService();

        anchorNav.addSection("Section 1", new Span("Section 1 content."));

        AnchorNavSection section2 = anchorNav.addSection("Section 2",
                new Span("Section 2 with 5 users"));
        Grid<Person> grid2 = createGrid();
        grid2.setItems(service.getItems(5));
        section2.add(grid2);

        AnchorNavSection section3 = anchorNav.addSection("Section 3",
                new Span("Section 3 no users"));
        Grid<Person> grid3 = createGrid();
        section3.add(grid3);

        AnchorNavSection section4 = anchorNav.addSection("Section 4",
                new Span("Section 4 with 10 users"));
        Grid<Person> grid4 = createGrid();
        grid4.setItems(service.getItems(10));
        section4.add(grid4);

        anchorNav.addSection("Section 5", new Span("Section 5 content."));
        anchorNav.addSection("Section 6", new Span("Section 6 content."));

        // Solution 4: re-align to the selected section once the lazy-loaded
        // content reports it has settled.
        anchorNav.addRealignOnEvent("content-loaded");

        // Button exercising the explicit scrollToSection(AnchorNavSection) API.
        Button scrollToSection2 = new Button("Scroll to Section 2",
                e -> anchorNav.scrollToSection(section2));
        scrollToSection2.setId("scroll-to-section-2");
        scrollToSection2.getStyle().set("position", "fixed");
        scrollToSection2.getStyle().set("bottom", "0");
        scrollToSection2.getStyle().set("right", "0");
        scrollToSection2.getStyle().set("z-index", "10");

        add(anchorNav, scrollToSection2);

        // Simulate lazy loading completing: grids change height after a timeout,
        // then announce "content-loaded" so the nav can re-align.
        setGridHeightWithTimeout(grid2, 200);
        setGridHeightWithTimeout(grid3, 100);
        setGridHeightWithTimeout(grid4, 300);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        anchorNav.setSelectedSection(3); // select Section 4 programmatically
    }

    private static void setGridHeightWithTimeout(Grid<Person> grid, int height) {
        grid.getElement().executeJs(
                "setTimeout(() => {"
                        + "  this.style.height = '" + height + "px';"
                        + "  this.dispatchEvent(new CustomEvent('content-loaded', { bubbles: true }));"
                        + "}, 500);");
    }

    private static Grid<Person> createGrid() {
        Grid<Person> grid = new Grid<>(Person.class, false);
        grid.addColumn(Person::id).setHeader("ID");
        grid.addColumn(Person::name).setHeader("Name");
        return grid;
    }

    private record Person(Long id, String name) {
    }

    private static class PersonService {
        private final List<Person> persons = LongStream.rangeClosed(1, 20)
                .mapToObj(i -> new Person(i, "Person " + i)).toList();

        List<Person> getItems(int count) {
            return persons.subList(0, count);
        }
    }
}
