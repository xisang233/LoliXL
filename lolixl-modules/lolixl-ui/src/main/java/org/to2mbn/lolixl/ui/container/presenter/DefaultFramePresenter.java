package org.to2mbn.lolixl.ui.container.presenter;

import static org.to2mbn.lolixl.utils.FXUtils.checkFxThread;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.to2mbn.lolixl.ui.BackgroundManagingService;
import org.to2mbn.lolixl.ui.PanelDisplayService;
import org.to2mbn.lolixl.ui.container.view.DefaultFrameView;
import org.to2mbn.lolixl.ui.model.Panel;
import java.io.IOException;
import java.net.URL;
import java.util.Deque;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;

public class DefaultFramePresenter extends Presenter<DefaultFrameView> implements BackgroundManagingService, PanelDisplayService {

	private static class PanelEntry {
		Panel model;
		Pane view;
	}

	private Deque<PanelEntry> panels = new ConcurrentLinkedDeque<>();

	private Node sidebar;

	@Override
	public void initialize(URL fxmlLocation) throws IOException {
		super.initialize(fxmlLocation);
	}

	@Override
	public void changeBackground(Background background) {
		Platform.runLater(() -> view.rootPane.setBackground(background));
	}

	@Override
	public Background getCurrentBackground() {
		return view.rootPane.getBackground();
	}

	/*
	 * 面板指的是叠加在界面上的内容，并且占据整个界面。
	 * 比如我打开设置，这时候设置会占据整个界面，它就是个面板。
	 * 背景、侧边栏等等都不算面板。
	 * 当有面板显示的时候，侧边栏是不显示的。
	 * 如果打开了多个面板，它们会一层层的叠放（逻辑上的叠放，不是指JavaFX组件叠放在一起）。
	 * 如果我打开顺序是A->B->C，那么关闭顺序就是C->B->A，是FIFO的，所以这里用了Deque。
	 */

	@Override
	public void display(Panel model) {
		checkFxThread();

		boolean hideSidebar = panels.isEmpty(); // 空deque代表这个Panel是第一个Panel，得隐藏sidebar
		PanelEntry panel = createPanelEntry(model);
		panels.push(panel);

		double plus = hideSidebar ? view.sidebarPane.getWidth() : 0;
		panel.view.resize(view.contentPane.getWidth() + plus, view.contentPane.getHeight());
		if (hideSidebar) {
			sidebar = view.sidebarPane.getCenter();
			view.sidebarPane.setCenter(null);
		}

		ParallelTransition animation = generateAnimation(panel.view, false, hideSidebar);
		animation.setOnFinished(event -> {
			view.setContent(panel.view);
		});
		animation.play();
	}

	@Override
	public boolean closeCurrent() {
		checkFxThread();

		if (panels.isEmpty()) {
			return false;
		} else {
			panels.pop();
			boolean showSidebar = panels.isEmpty(); // 空deque代表这个Panel是最后一个Panel，得显示sidebar

			/*
			// TODO: 不知道怎么写
			
			Pane last = contents.poll();
			Pane previous = contents.poll();
			
			ParallelTransition animation = generateAnimation(last, true, hidedSidebar);
			animation.setOnFinished(event -> {
				if (hidedSidebar) {
					view.sidebarPane.setCenter(sidebar);
				}
				view.setContent(previous);
			});
			animation.play();
			*/
			return true;
		}
	}

	@Override
	public Optional<Panel> getCurrent() {
		return Optional.ofNullable(panels.peek().model);
	}

	@Override
	public Panel[] getOpenedPanels() {
		return panels.stream()
				.map(entry -> entry.model)
				.toArray(Panel[]::new);
	}

	private ParallelTransition generateAnimation(Pane pane, boolean reverse, boolean hidedSidebar) {
		TranslateTransition tran = new TranslateTransition(Duration.seconds(1), pane);
		double plus = hidedSidebar ? view.sidebarPane.getWidth() : 0;
		double fromX = (view.contentPane.getLayoutX() + view.contentPane.getWidth() + plus) / 5;
		double toX = hidedSidebar ? view.sidebarPane.getLayoutX() : view.contentPane.getLayoutX();
		tran.setFromX(reverse ? toX : fromX);
		tran.setToX(reverse ? fromX : toX);

		FadeTransition fade = new FadeTransition(Duration.seconds(1), pane);
		fade.setFromValue(1 / (reverse ? 2 : 1));
		fade.setToValue(1 / (reverse ? 1 : 2));

		ParallelTransition parallel = new ParallelTransition(tran, fade);
		parallel.setCycleCount(Animation.INDEFINITE);
		return parallel;
	}

	private PanelEntry createPanelEntry(Panel model) {
		PanelEntry entry = new PanelEntry();
		entry.model = model;
		// TODO: 根据要显示的面板的逻辑模型
		//       创建出一个JavaFX的Pane对象
		//       entry.view=...;
		return entry;
	}
}
