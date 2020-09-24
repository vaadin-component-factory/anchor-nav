package com.vaadin.componentfactory;

public enum AnchorNavVariant {
	
	EXPAND_LAST("expand-last");
	
	private final String variant;

	AnchorNavVariant(String variant) {
        this.variant = variant;
    }

    /**
     * Gets the variant name.
     *
     * @return variant name
     */
    public String getVariantName() {
        return variant;
    }
}
