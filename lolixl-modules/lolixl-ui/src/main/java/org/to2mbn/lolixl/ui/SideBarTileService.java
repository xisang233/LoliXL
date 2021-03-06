package org.to2mbn.lolixl.ui;

import org.to2mbn.lolixl.ui.model.SidebarTileElement;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;

public interface SideBarTileService {

	/**
	 * 描述一个磁贴的状态
	 * <p>
	 * 当磁贴过多时 多出的磁贴会被设置为 HIDDEN 从磁贴容器隐藏
	 */
	enum StackingStatus {
		HIDDEN, SHOWN
	}

	/**
	 * 返回所有状态在 {@code types} 内的磁贴集合。
	 * 
	 * @param types
	 * @return
	 */
	ObservableList<SidebarTileElement> getTiles(StackingStatus... types);

	/**
	 * 返回 {@code element} 的堆叠状态，若不存在于磁贴集合中则 {@code null} 。
	 * 
	 * @param element
	 * @return
	 */
	StackingStatus getStatus(SidebarTileElement element);

	/**
	 * 返回 {@code element} 的 tagName ，若不存在于磁贴集合中则 {@code null} 。
	 * 
	 * @param element 磁贴
	 * @return
	 */
	String getTagName(SidebarTileElement element);

	/**
	 * 移动给定磁贴。
	 * 
	 * @param element 磁贴
	 * @param offset 该磁贴相对于原来位置的偏移量，正数代表向下移动，负数代表向上移动
	 * @return 磁贴实际的偏移量
	 */
	int moveTile(SidebarTileElement element, int offset);

	IntegerProperty maxShownTilesProperty();

}
