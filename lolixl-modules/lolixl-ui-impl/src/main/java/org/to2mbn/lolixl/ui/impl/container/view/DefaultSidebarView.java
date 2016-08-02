package org.to2mbn.lolixl.ui.impl.container.view;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.to2mbn.lolixl.ui.container.view.View;

public class DefaultSidebarView extends View {
	@FXML
	public BorderPane rootContainer;

	@FXML
	public Pane backgroundContainer;

	@FXML
	public BorderPane mainContentContainer;

	@FXML
	public StackPane userProfileContainer;

	@FXML
	public BorderPane functionalTileContainer;

	@FXML
	public VBox functionalTileCenterContainer;

	@FXML
	public StackPane functionalTileBottomContainer;

	@FXML
	public StackPane sidebarContainer;
}
