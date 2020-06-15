package com.vaadin.componentfactory.util;

import com.vaadin.flow.dom.Element;

public class SlotHelper {

	public static void clearSlot(Element parent, String slot) {
		parent.getChildren().filter(child -> slot.equals(child.getAttribute("slot"))).forEach(parent::removeChild);
	}
}
