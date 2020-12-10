# Component Factory Anchor Nav for Vaadin 14

[Live Demo ↗](https://incubator.app.fi/anchor-nav-demo/anchor-nav)

[&lt;vcf-anchor-nav&gt;](https://www.npmjs.com/package/@vaadin-component-factory/vcf-anchor-nav) component organizes content sections with tabs and provides anchor navigation while linking the tabs and sections

# What does the component do?

Anchor Nav component organizes content sections with tabs and provides anchor navigation while linking the tabs and sections

## Basic Usage
```java
AnchorNav anchorNav = new AnchorNav();
anchorNav.setHeight("400px");
anchorNav.addSection(new Span("Section 1"));
anchorNav.addSection(new Span("Section 2"));
```

# How to run the demo?

The Demo can be run by going to the project anchor-nav-demo and executing the maven goal:

```mvn jetty:run```

After server startup, you'll be able find the demo at [http://localhost:8080/anchor-nav](http://localhost:8080/anchor-nav)


## License & Author

This Add-on is distributed under Apache 2.0

Component Factory Anchor Nav is written by Vaadin Ltd.

### Sponsored development
Major pieces of development of this add-on has been sponsored by multiple customers of Vaadin. Read more  about Expert on Demand at: [Support](https://vaadin.com/support) and  [Pricing](https://vaadin.com/pricing)


## Setting up for development:

Clone the project in GitHub (or fork it if you plan on contributing)

```
git clone git@github.com:vaadin-component-factory/anchor-nav.git
```

to install project to your maven repository run
 
```mvn install```
