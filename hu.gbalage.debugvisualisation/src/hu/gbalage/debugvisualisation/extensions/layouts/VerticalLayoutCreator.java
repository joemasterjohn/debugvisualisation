/**
 * 
 */
package hu.gbalage.debugvisualisation.extensions.layouts;

import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.VerticalLayoutAlgorithm;

import hu.gbalage.debugvisualisation.layouts.ILayoutAlgorithmCreator;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class VerticalLayoutCreator implements ILayoutAlgorithmCreator {

	/* (non-Javadoc)
	 * @see hu.gbalage.debugvisualisation.layouts.ILayoutAlgorithmCreator#create()
	 */
	public LayoutAlgorithm create() {
		return new VerticalLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
	}

}
