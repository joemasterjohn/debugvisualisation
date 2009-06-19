/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.layouts;

import hu.cubussapiens.zestlayouts.LayoutRegistry.LayoutEntry;

import org.eclipse.jface.viewers.LabelProvider;

/**
 * LabelProvider for tha Layout Algorithm selector widget
 */
public class LayoutAlgorithmLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof LayoutEntry){
			LayoutEntry entry = (LayoutEntry)element;
			return entry.getName();
		}
		return super.getText(element);
	}

}
