package com.vaadin.componentfactory;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.testbench.TestBenchElement;

public class ViewIT extends AbstractViewTest {

    @Test
    public void componentWorks() {
        final TestBenchElement anchorNav = $("vcf-anchor-nav").waitForFirst();
        Assert.assertTrue(anchorNav.$(TestBenchElement.class).all().size() > 0);
    }
}
