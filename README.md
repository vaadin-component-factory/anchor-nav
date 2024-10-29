# Component Factory Anchor Nav for Vaadin 23

[Live Demo ↗](https://incubator.app.fi/anchor-nav-demo/anchor-nav)

[&lt;vcf-anchor-nav&gt;](https://www.npmjs.com/package/@vaadin-component-factory/vcf-anchor-nav) component organizes content sections with tabs and provides anchor navigation while linking the tabs and sections

## What does the component do?

Anchor Nav component organizes content sections with tabs and provides anchor navigation while linking the tabs and sections.

### Basic Usage

```java
AnchorNav anchorNav = new AnchorNav();
anchorNav.setHeight("400px");
anchorNav.addSection(new Span("Section 1"));
anchorNav.addSection(new Span("Section 2"));
```

## Compatibility

- Version 1.x.x -> Vaadin 14+
- Version 3.x.x -> Vaadin 24.5.x (improved accessibility)
- Version 23.x.x -> Vaadin 23+

## Development instructions

JavaScript modules can either be published as an NPM package or be kept as local
files in your project. The local JavaScript modules should be put in
`src/main/resources/META-INF/frontend` so that they are automatically found and
used in the using application.

If the modules are published then the package should be noted in the component
using the `@NpmPackage` annotation in addition to using `@JsModule` annotation.

Starting the test/demo server:

1. Run `mvn jetty:run`.
2. Open http://localhost:8080 in the browser.

## Publishing to Vaadin Directory

You can create the zip package needed for [Vaadin Directory](https://vaadin.com/directory/) using

```
mvn versions:set -DnewVersion=1.0.0 # You cannot publish snapshot versions
mvn install -Pdirectory
```

The package is created as `target/anchor-nav-1.0.0.zip`

For more information or to upload the package, visit https://vaadin.com/directory/my-components?uploadNewComponent

## Sponsored development

Major pieces of development of this add-on has been sponsored by multiple customers of Vaadin. Read more about Expert on Demand at: [Support](https://vaadin.com/support) and [Pricing](https://vaadin.com/pricing)

## License

This Add-on is distributed under [Apache 2.0](/LICENSE)
