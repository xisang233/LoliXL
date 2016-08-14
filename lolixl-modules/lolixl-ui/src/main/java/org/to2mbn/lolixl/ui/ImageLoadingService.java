package org.to2mbn.lolixl.ui;

import javafx.beans.value.ObservableObjectValue;
import javafx.scene.image.Image;

public interface ImageLoadingService {

	ObservableObjectValue<Image> load(String location, ClassLoader caller);

}
