package org.to2mbn.lolixl.ui.impl.container.presenter.panel.tiles;

import org.osgi.framework.BundleContext;
import org.to2mbn.lolixl.ui.container.presenter.Presenter;
import org.to2mbn.lolixl.ui.impl.container.view.panel.tils.HiddenTilesView;

public class HiddenTilesPresenter extends Presenter<HiddenTilesView> {

	private static final String FXML_LOCATION = "/ui/fxml/panel/hidden_tiles_panel.fxml";

	public HiddenTilesPresenter(BundleContext ctx) {
		super(ctx);
	}

	@Override
	protected String getFxmlLocation() {
		return FXML_LOCATION;
	}
}
