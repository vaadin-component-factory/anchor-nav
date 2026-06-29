package com.vaadin.componentfactory;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.testbench.TestBenchElement;

/**
 * Verifies that a programmatically selected section stays correctly aligned when
 * content above it resizes after the initial scroll, using the re-alignment API
 * added to {@link AnchorNav} ({@link AnchorNav#addRealignOnEvent(String)},
 * {@link AnchorNav#scrollToSelectedSection()},
 * {@link AnchorNav#scrollToSection(AnchorNavSection)}).
 *
 * @see RealignView
 */
public class RealignViewIT extends AbstractViewTest {

    public RealignViewIT() {
        super("realign");
    }

    @Test
    public void selectedSectionStaysAlignedAfterContentResize() {
        TestBenchElement anchorNav = $("vcf-anchor-nav").waitForFirst();
        waitUntil(d -> sectionsReady(anchorNav), 20);

        // After the lazy-loaded grids resize (~500ms) the nav must re-align to the
        // selected Section 4 (index 3) and must not jump/clamp to the last section.
        waitUntil(d -> alignedToSection(anchorNav, 3), 20);

        Assert.assertEquals("Selected section should be Section 4 (index 3)",
                Integer.valueOf(3), anchorNav.getPropertyInteger("selectedIndex"));
        Assert.assertFalse("View must not be clamped to the last section",
                isClampedToBottom(anchorNav));
    }

    @Test
    public void scrollToSectionScrollsToGivenSection() {
        TestBenchElement anchorNav = $("vcf-anchor-nav").waitForFirst();
        waitUntil(d -> sectionsReady(anchorNav), 20);
        // Start from the re-aligned state (Section 4).
        waitUntil(d -> alignedToSection(anchorNav, 3), 20);

        // Explicit API: scrollToSection(section2) must align to Section 2 (index 1).
        $(TestBenchElement.class).id("scroll-to-section-2").click();
        waitUntil(d -> alignedToSection(anchorNav, 1), 10);
    }

    /** True once the 6 sections exist and tab geometry is available. */
    private boolean sectionsReady(TestBenchElement anchorNav) {
        return Boolean.TRUE.equals(executeScript(
                "const n = arguments[0];"
                        + "return !!(n.sections && n.sections.length === 6 && n._tabHeight >= 0);",
                anchorNav));
    }

    /** True when the section at {@code index} is scrolled to the top of the nav. */
    private boolean alignedToSection(TestBenchElement anchorNav, int index) {
        return Boolean.TRUE.equals(executeScript(
                "const n = arguments[0];"
                        + "const i = arguments[1];"
                        + "if (!n.sections || n.sections.length <= i) return false;"
                        + "const target = n.sections[i].offsetTop - n._tabHeight;"
                        + "return Math.abs(n.scrollTop - target) <= 6;",
                anchorNav, index));
    }

    /** True when the nav is scrolled all the way to the bottom (the bug symptom). */
    private boolean isClampedToBottom(TestBenchElement anchorNav) {
        return Boolean.TRUE.equals(executeScript(
                "const n = arguments[0];"
                        + "return Math.abs(n.scrollTop - (n.scrollHeight - n.clientHeight)) <= 2;",
                anchorNav));
    }
}
