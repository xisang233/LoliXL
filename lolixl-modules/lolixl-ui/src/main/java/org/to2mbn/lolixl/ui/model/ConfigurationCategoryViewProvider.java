package org.to2mbn.lolixl.ui.model;

import javafx.scene.layout.Region;

public interface ConfigurationCategoryViewProvider extends DisplayableTile {

	String PROPERTY_CATEGORY = "org.to2mbn.lolixl.ui.model.configuration.category";

	Region createConfiguringPanel();

}
